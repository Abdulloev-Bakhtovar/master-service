package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.master.service.constant.ClientOrderStatus;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.constant.MasterOrderStatus;
import ru.master.service.exception.AppException;
import ru.master.service.model.dto.response.MasterStatisticsResDto;
import ru.master.service.repository.MasterProfileRepo;
import ru.master.service.repository.OrderRepo;
import ru.master.service.service.MasterStatisticsService;
import ru.master.service.util.AuthUtil;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MasterStatisticsServiceImpl implements MasterStatisticsService {

    private final OrderRepo orderRepo;
    private final MasterProfileRepo masterProfileRepo;
    private final AuthUtil authUtil;

    @Override
    public MasterStatisticsResDto getStatisticsForMaster() {

        var authMaster = authUtil.getAuthenticatedUser();
        UUID userId = authMaster.getId();

        var master = masterProfileRepo.findByUserId(userId)
                .orElseThrow(() -> new AppException(
                    ErrorMessage.MASTER_PROFILE_NOT_FOUND,
                    HttpStatus.NOT_FOUND
                ));

        Instant now = Instant.now();
        Instant monthAgo = now.minus(30, ChronoUnit.DAYS);

        int receivedLastMonth = orderRepo.countByCityIdAndCreatedAtBetween(master.getCity().getId(), monthAgo, now);
        int closedLastMonth = orderRepo.countByMasterProfileIdAndMasterOrderStatusAndClosedAtBetween(
                master.getId(),
                MasterOrderStatus.COMPLETED,
                monthAgo,
                now
        );

        int receivedTotal = orderRepo.countByCityId(master.getCity().getId());
        int closedTotal = orderRepo.countByMasterProfileIdAndMasterOrderStatus(master.getId(),
                MasterOrderStatus.COMPLETED);

        return MasterStatisticsResDto.builder()
                .balance(master.getBalance())
                .availableOrders(orderRepo.countByCityIdAndClientOrderStatus(
                        master.getCity().getId(),
                        ClientOrderStatus.SEARCHING_FOR_MASTER
                ))
                .receivedLastMonth(receivedLastMonth)
                .closedLastMonth(closedLastMonth)
                .receivedTotal(receivedTotal)
                .closedTotal(closedTotal)
                .build();
    }

}
