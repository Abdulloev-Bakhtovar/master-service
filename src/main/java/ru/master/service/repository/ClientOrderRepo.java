package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.ClientOrder;
import ru.master.service.model.MasterProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientOrderRepo extends JpaRepository<ClientOrder, UUID> {

    Optional<ArrayList<ClientOrder>> findAllByClientProfileId(UUID id);

    Optional<ArrayList<ClientOrder>> findAllByMasterProfileId(UUID id);

    List<ClientOrder> findByMasterProfileAndClientRatingIsNotNull(MasterProfile master);
}
