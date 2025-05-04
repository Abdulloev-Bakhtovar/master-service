package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.constant.MasterOrderStatus;
import ru.master.service.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepo extends JpaRepository<Order, UUID> {

    Optional<ArrayList<Order>> findAllByClientProfileId(UUID id);

    Optional<ArrayList<Order>> findAllByMasterProfileId(UUID id);

    Optional<Order> findByIdAndClientProfileId(UUID id, UUID clientProfileId);

    Optional<Order> findByIdAndMasterProfileId(UUID orderId, UUID id);

    Optional<List<Order>> findAllByCityId(UUID id);

    Optional<List<Order>> findAllByCityIdAndMasterOrderStatus(UUID city_id, MasterOrderStatus masterOrderStatus);

    Optional<List<Order>> findAllByMasterProfileIdAndMasterOrderStatus(UUID id, MasterOrderStatus status);

    List<Order> findCurrentOrderByMasterProfileId(UUID id);
}
