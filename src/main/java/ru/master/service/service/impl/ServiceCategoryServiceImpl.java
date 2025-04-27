package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.constants.DocumentType;
import ru.master.service.constants.EntityName;
import ru.master.service.constants.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.ServiceCategoryMapper;
import ru.master.service.model.ServiceCategory;
import ru.master.service.model.dto.ServiceCategoryDto;
import ru.master.service.model.dto.request.ServiceCategoryReqDto;
import ru.master.service.repository.ServiceCategoryRepo;
import ru.master.service.repository.SubServiceCategoryRepo;
import ru.master.service.service.FileStorageService;
import ru.master.service.service.ServiceCategoryService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.master.service.util.ErrorFormatterUtil.format;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

    private final ServiceCategoryRepo serviceCategoryRepo;
    private final ServiceCategoryMapper serviceCategoryMapper;
    private final SubServiceCategoryRepo subServiceCategoryRepo;
    private final FileStorageService fileStorageService;

    @Override
    public List<ServiceCategoryDto> getAll(String name) {
        List<ServiceCategory> categories;
        if (name == null || name.isBlank()) {
            categories = serviceCategoryRepo.findAll();
        } else {
            categories = serviceCategoryRepo.findByNameContainingIgnoreCase(name);
        }

        return categories.stream()
                .map(serviceCategoryMapper::toDto)
                .toList();
    }

    @Override
    public List<ServiceCategoryDto> getAllWithSubService() {
        return serviceCategoryRepo.findAll().stream()
                .map(serviceCategoryMapper::toDtoWithSubService)
                .toList();
    }

    @Override
    public void create(ServiceCategoryReqDto dto) throws IOException {

        if (serviceCategoryRepo.existsByName(dto.getName())) {
            throw new AppException(
                    format(ErrorMessage.ENTITY_ALREADY_EXISTS, EntityName.SERVICE_CATEGORY.get()),
                    HttpStatus.CONFLICT
            );
        }

        var subServices = Optional.ofNullable(dto.getSubServiceCategoryIds())
                .orElse(Collections.emptyList())
                .stream()
                .map(id -> subServiceCategoryRepo.findById(id)
                        .orElseThrow(() -> new AppException(
                                String.format(ErrorMessage.ENTITY_NOT_FOUND, EntityName.SUB_SERVICE_CATEGORY.get()),
                                HttpStatus.NOT_FOUND
                        ))
                )
                .collect(Collectors.toList());

        var serviceCategory = serviceCategoryMapper.toEntity(dto, subServices);
        serviceCategoryRepo.save(serviceCategory);

        fileStorageService.storeFile(dto.getPhoto(), DocumentType.SERVICE_CATEGORY_PHOTO, serviceCategory.getId());
    }
}
