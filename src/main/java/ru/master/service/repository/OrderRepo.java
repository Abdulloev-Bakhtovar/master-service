package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.constant.ClientOrderStatus;
import ru.master.service.constant.MasterOrderStatus;
import ru.master.service.model.Order;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepo extends JpaRepository<Order, UUID> {

    Optional<ArrayList<Order>> findAllByClientProfileId(UUID id);

    Optional<Order> findByIdAndClientProfileId(UUID id, UUID clientProfileId);

    Optional<Order> findByIdAndMasterProfileId(UUID orderId, UUID id);

    Optional<List<Order>> findAllByCityIdAndMasterOrderStatus(UUID city_id, MasterOrderStatus masterOrderStatus);

    Optional<List<Order>> findAllByMasterProfileIdAndMasterOrderStatus(UUID id, MasterOrderStatus status);

    List<Order> findCurrentOrderByMasterProfileId(UUID id);

    int countByMasterProfileIdAndMasterOrderStatusAndClosedAtBetween(UUID masterId,
                                                                     MasterOrderStatus masterOrderStatus,
                                                                     Instant monthAgo,
                                                                     Instant now);

    int countByMasterProfileIdAndMasterOrderStatus(UUID masterId, MasterOrderStatus masterOrderStatus);

    int countByCityIdAndClientOrderStatus(UUID cityId, ClientOrderStatus clientOrderStatus);

    int countByCityId(UUID id);

    int countByCityIdAndCreatedAtBetween(UUID id, Instant monthAgo, Instant now);

    List<Order> findAllByMasterProfileIdAndMasterOrderStatusIn(
            UUID masterId,
            List<MasterOrderStatus> statuses
    );
}
