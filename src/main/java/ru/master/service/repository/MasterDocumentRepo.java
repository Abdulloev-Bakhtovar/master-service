package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.MasterDocument;
import ru.master.service.model.MasterProfile;

import java.util.List;
import java.util.UUID;

public interface MasterDocumentRepo extends JpaRepository<MasterDocument, UUID> {

    List<MasterDocument> findAllByMaster(MasterProfile master);
}
