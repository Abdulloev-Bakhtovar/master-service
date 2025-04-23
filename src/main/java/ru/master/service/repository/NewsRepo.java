package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.News;

import java.util.UUID;

public interface NewsRepo extends JpaRepository<News, UUID> {

}
