package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.constant.MasterOrderStatus;
import ru.master.service.model.ClientOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientOrderRepo extends JpaRepository<ClientOrder, UUID> {

    Optional<ArrayList<ClientOrder>> findAllByClientProfileId(UUID id);

    Optional<ArrayList<ClientOrder>> findAllByMasterProfileId(UUID id);

    Optional<ClientOrder> findByIdAndClientProfileId(UUID id, UUID clientProfileId);

    Optional<ClientOrder> findByIdAndMasterProfileId(UUID orderId, UUID id);

    Optional<List<ClientOrder>> findAllByCityId(UUID id);

    Optional<List<ClientOrder>> findAllByMasterProfileIdAndMasterOrderStatus(UUID id, MasterOrderStatus status);
}
