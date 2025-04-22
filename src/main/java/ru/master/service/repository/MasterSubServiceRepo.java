package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.MasterSubService;

import java.util.List;
import java.util.UUID;

public interface MasterSubServiceRepo extends JpaRepository<MasterSubService, UUID> {

    List<MasterSubService> findAllByMasterProfileId(UUID id);
}
