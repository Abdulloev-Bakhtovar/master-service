package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.admin.model.dto.responce.AdminOrderSummaryResDto;
import ru.master.service.constant.*;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.OrderMapper;
import ru.master.service.model.Order;
import ru.master.service.model.OrderPostponement;
import ru.master.service.model.dto.request.*;
import ru.master.service.model.dto.response.*;
import ru.master.service.repository.*;
import ru.master.service.service.*;
import ru.master.service.util.AuthUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final ClientProfileRepo clientProfileRepo;
    private final SubserviceRepo subserviceRepo;
    private final OrderMapper orderMapper;
    private final AuthUtil authUtil;
    private final MasterFeedbackRepo masterFeedbackRepo;
    private final MasterFeedbackService masterFeedbackService;
    private final MasterProfileService masterProfileService;
    private final MasterProfileRepo masterProfileRepo;
    private final OrderPostponementRepo orderPostponementRepo;
    private final ReferralRepo referralRepo;
    private final ReferralProgramService referralProgramService;
    private final PaymentService paymentService;

    @Override
    public IdDto create(CreateOrderReqDto reqDto) {

        var user = authUtil.getAuthenticatedUser();

        var clientProfile = clientProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.CLIENT_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var subServiceCategory = subserviceRepo.findById(reqDto.getSubserviceId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.SUB_SERVICE_CATEGORY_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var orderEntity = orderMapper.toOrderEntity(
                reqDto,
                clientProfile,
                subServiceCategory,
                ClientOrderStatus.SEARCHING_FOR_MASTER,
                MasterOrderStatus.SEARCHING_FOR_MASTER);

        orderRepo.save(orderEntity);

        return IdDto.builder()
                .id(orderEntity.getId())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDetailForClientResDto getByIdForClient(UUID orderId) {
        var user = authUtil.getAuthenticatedUser();

        var client = clientProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.CLIENT_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
        var order = orderRepo.findByIdAndClientProfileId(orderId, client.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.ORDER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
        var masterFeedback = masterFeedbackRepo.findByOrderId(order.getId())
                .orElse(null);

        return orderMapper.toOrderDetailResForClientDto(order, masterFeedback);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AllClientOrderResDto> getAllClientOrders() {
        var user = authUtil.getAuthenticatedUser();

        var client = clientProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.CLIENT_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
        var orders = orderRepo.findAllByClientProfileId(client.getId())
                .orElse(new ArrayList<>());

        return orders.stream()
                .map(orderMapper::toAllClientOrderResDto)
                .toList();
    }

    @Override
    public void cancelOrderForClient(UUID orderId, CancelOrderForClientDto reqDto) {
        var order = getClientOrder(orderId);
        orderMapper.toCancelOrderForClient(reqDto, order);
        orderRepo.save(order);
    }

    @Override
    public void completeOrderForClient(UUID orderId, CompleteOrderForClientDto reqDto) {
        var order = getClientOrder(orderId);

        orderMapper.toCompleteOrderForClient(reqDto, order);
        masterFeedbackService.create(reqDto.getMasterFeedbackDto(), order);

        masterProfileService.updateMasterAverageRating(
                order.getMasterProfile(),
                reqDto.getMasterFeedbackDto().getRating()
        );
        orderRepo.save(order);

        referralRepo.findByReferred(order.getClientProfile())
                .filter(ref -> !ref.isFirstOrderCompleted())
                .ifPresent(referral -> {
                    referral.setFirstOrderCompleted(true);
                    referralRepo.save(referral);

                    referralProgramService.addReferralFirstOrderPoints(referral.getReferrer());
                });
    }

    @Override
    public List<MasterAvailableOrdersResDto> getMasterAvailableOrders() {
        var user = authUtil.getAuthenticatedUser();

        var master = masterProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.MASTER_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        List<Order> orders =  switch (master.getMasterStatus()) {
            case WAITING_FOR_ORDERS ->
                    orderRepo.findAllByCityIdAndMasterOrderStatus(
                                    master.getCity().getId(),
                                    MasterOrderStatus.SEARCHING_FOR_MASTER
                            )
                            .orElse(new ArrayList<>());
            case ON_ORDER ->
                    orderRepo.findCurrentOrderByMasterProfileId(master.getId());
            case OFFLINE ->
                    Collections.emptyList();
        };

        return orderMapper.toAllAvailableOrderForMasterDto(orders);
    }

    @Override
    public List<MasterCompletedOrdersResDto> getMasterCompletedOrders() {
        var user = authUtil.getAuthenticatedUser();

        var master = masterProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.MASTER_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
        var orders = orderRepo.findAllByMasterProfileIdAndMasterOrderStatus(
                        master.getId(), MasterOrderStatus.COMPLETED
                )
                .orElseThrow(() -> new AppException(
                        ErrorMessage.ORDER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        return orders.stream()
                .map(orderMapper::toMasterCompletedOrdersResDto)
                .toList();
    }

    @Override
    public List<MasterActiveOrdersResDto> getMasterActiveOrders() {
        var user = authUtil.getAuthenticatedUser();
        if (user.getRole() != Role.MASTER) {
            throw new AppException(
                    ErrorMessage.INVALID_ROLE_FOR_OPERATION,
                    HttpStatus.FORBIDDEN
            );
        }
        var master = masterProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.MASTER_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
        var statuses = List.of(
                MasterOrderStatus.TAKEN_IN_WORK,
                MasterOrderStatus.DEFERRED_REPAIR,
                MasterOrderStatus.ARRIVED_AT_CLIENT
        );
        List<Order> orders = orderRepo.findAllByMasterProfileIdAndMasterOrderStatusIn(master.getId(), statuses);

        return orders.stream()
                .map(orderMapper::toMasterActiveOrdersResDto)
                .toList();
    }

    @Override
    public OrderDetailForMasterResDto getByIdForMaster(UUID orderId) {

        var order = orderRepo.findById(orderId)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.ORDER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
        var masterFeedback = masterFeedbackRepo.findByOrderId(order.getId())
                .orElse(null);

        return orderMapper.toOrderDetailForMasterResDto(order, masterFeedback);
    }

    @Override
    public void arriveOrderForMaster(UUID orderId) {

        var order = orderRepo.findById(orderId)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.ORDER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        order.setMasterOrderStatus(MasterOrderStatus.ARRIVED_AT_CLIENT);
        orderRepo.save(order);
    }

    @Override
    public void acceptOrderForMaster(UUID orderId) {
        var user = authUtil.getAuthenticatedUser();

        var master = masterProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.MASTER_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
        var order = orderRepo.findById(orderId)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.ORDER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        if (order.getClientOrderStatus() == ClientOrderStatus.TAKEN_IN_WORK) {
            throw new AppException(
                    ErrorMessage.ORDER_TAKEN,
                    HttpStatus.NOT_FOUND
            );
        }
        order.setMasterProfile(master);
        order.setClientOrderStatus(ClientOrderStatus.TAKEN_IN_WORK);
        order.setMasterOrderStatus(MasterOrderStatus.TAKEN_IN_WORK);
        master.setMasterStatus(MasterStatus.ON_ORDER);

        orderRepo.save(order);
    }

    @Override
    public void availabilityOrderForMaster() {
        // TODO при нажатия на кнопку готов принимать заказы тут изменяется
        // TODO                                     его статус на (ЖДУ ЗАЯВОК)
        var user = authUtil.getAuthenticatedUser();
        var master = masterProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.MASTER_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
        master.setMasterStatus(MasterStatus.WAITING_FOR_ORDERS);
        masterProfileRepo.save(master);
    }

    @Override
    public void postponeOrderForMaster(UUID orderId, PostponeReqForMasterDto reqDto) {
        var user = authUtil.getAuthenticatedUser();

        var master = masterProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.MASTER_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
        var order = orderRepo.findByIdAndMasterProfileId(orderId, master.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.ORDER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        order.setMasterOrderStatus(MasterOrderStatus.DEFERRED_REPAIR);

        OrderPostponement postponement = OrderPostponement.builder()
                .newAppointmentDate(reqDto.getNewAppointmentDate())
                .reason(reqDto.getReason())
                .order(order)
                .master(master)
                .build();

        orderPostponementRepo.save(postponement);
    }

    @Override
    public void updateOrderPrice(UUID orderId, UpdateOrderPriceReqDto reqDto) {
        var user = authUtil.getAuthenticatedUser();

        var client = masterProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.CLIENT_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
        var order = orderRepo.findByIdAndClientProfileId(orderId, client.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.ORDER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        order.setPrice(reqDto.getNewPrice().setScale(2, RoundingMode.HALF_UP));
        orderRepo.save(order);
    }

    @Override
    public AdminOrderSummaryResDto getAdminOrderSummary() {

        var openStatuses = List.of(
                ClientOrderStatus.TAKEN_IN_WORK,
                ClientOrderStatus.SEARCHING_FOR_MASTER
        );

        var closedStatuses = List.of(ClientOrderStatus.FINISHED);

        int open = orderRepo.countByClientOrderStatusIn(openStatuses);
        int closed = orderRepo.countByClientOrderStatusIn(closedStatuses);
        int total = open + closed;

        double openPct = total == 0 ? 0.0 : (open * 100.0) / total;
        double closedPct = total == 0 ? 0.0 : (closed * 100.0) / total;

        BigDecimal revenue = orderRepo.findAllByClientOrderStatus(ClientOrderStatus.FINISHED).stream()
                .map(Order::getPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new AdminOrderSummaryResDto(open, openPct, closed, closedPct, revenue);
    }

    @Override
    public PaymentResDto createPaymentForOrder(UUID orderId) {
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .price(BigDecimal.valueOf(1500))
                .build();
                /*orderRepo.findById(orderId)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.ORDER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));*/

        // Можете использовать параметры из заказа, если это необходимо
        var paymentReqDto = PaymentReqDto.builder()
                        .amount(order.getPrice())
                        .orderId(orderId)
                        .description("Оплата за заказ №" + orderId)
                        .build();

        // Создаем платеж через сервис PaymentService
        return paymentService.createPayment(paymentReqDto);
    }

    @Override
    public void choosePaymentMethod(UUID orderId, ChoosePaymentMethodReqDto dto) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new AppException(
                ErrorMessage.ORDER_NOT_FOUND,
                HttpStatus.NOT_FOUND
        ));

        order.setPaymentMethod(dto.getPaymentMethod());
        orderRepo.save(order);
    }

    private Order getClientOrder(UUID reqDto) {
        var user = authUtil.getAuthenticatedUser();

        if (!user.getRole().equals(Role.CLIENT)) {
            throw new AppException(
                    ErrorMessage.INVALID_ROLE_FOR_OPERATION,
                    HttpStatus.FORBIDDEN
            );
        }
        var client = clientProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.CLIENT_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var order = orderRepo.findById(reqDto)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.ORDER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        if (client.getId() != order.getClientProfile().getId()) {
            throw new AppException(
                    ErrorMessage.ORDER_ACCESS_DENIED,
                    HttpStatus.FORBIDDEN
            );
        }
        return order;
    }
}
