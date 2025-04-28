package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.master.service.model.City;
import ru.master.service.model.News;
import ru.master.service.model.ServiceCategory;
import ru.master.service.model.SubServiceCategory;
import ru.master.service.repository.CityRepo;
import ru.master.service.repository.NewsRepo;
import ru.master.service.repository.ServiceCategoryRepo;
import ru.master.service.repository.SubServiceCategoryRepo;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDataRunner implements CommandLineRunner {

    private final CityRepo cityRepository;
    private final ServiceCategoryRepo serviceCategoryRepository;
    private final SubServiceCategoryRepo subServiceCategoryRepository;
    private final NewsRepo newsRepository;

    @Override
    public void run(String... args) {
        // Создание двух городов
        City city1 = new City();
        city1.setName("Москва");
        city1.setVisible(true);

        City city2 = new City();
        city2.setName("Санкт-Петербург");
        city2.setVisible(true);

        cityRepository.saveAll(List.of(city1, city2));

        // Создание двух подкатегорий
        SubServiceCategory sub1 = new SubServiceCategory();
        sub1.setName("Подуслуга 1");

        SubServiceCategory sub2 = new SubServiceCategory();
        sub2.setName("Подуслуга 2");

        subServiceCategoryRepository.saveAll(List.of(sub1, sub2));

        // Создание двух категорий с привязанными подкатегориями
        ServiceCategory category1 = new ServiceCategory();
        category1.setName("Категория 1");
        category1.getSubServices().add(sub1);

        ServiceCategory category2 = new ServiceCategory();
        category2.setName("Категория 2");
        category2.getSubServices().add(sub2);

        serviceCategoryRepository.saveAll(List.of(category1, category2));

        // Создание двух новостей, привязанных к городам
        News news1 = new News();
        news1.setTitle("Новость 1");
        news1.setContent("Текст новости 1");
        news1.setVisible(true);
        news1.setCity(city1);

        News news2 = new News();
        news2.setTitle("Новость 2");
        news2.setContent("Текст новости 2");
        news2.setVisible(true);
        news2.setCity(city2);

        newsRepository.saveAll(List.of(news1, news2));
    }
}

