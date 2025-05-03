package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.master.service.constant.DocumentType;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.ServiceCategoryMapper;
import ru.master.service.model.ServiceCategory;
import ru.master.service.model.dto.request.CreateServiceCategoryDto;
import ru.master.service.model.dto.response.ServiceCategoryResDto;
import ru.master.service.model.dto.response.ServiceCategoryWithSubServiceDto;
import ru.master.service.repository.ServiceCategoryRepo;
import ru.master.service.repository.SubServiceCategoryRepo;
import ru.master.service.service.FileStorageService;
import ru.master.service.service.ServiceCategoryService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

    private final ServiceCategoryRepo serviceCategoryRepo;
    private final ServiceCategoryMapper serviceCategoryMapper;
    private final SubServiceCategoryRepo subServiceCategoryRepo;
    private final FileStorageService fileStorageService;

    @Override
    @Transactional(readOnly = true)
    public List<ServiceCategoryResDto> getAll(String name) {

        List<ServiceCategory> categories = StringUtils.hasText(name)
                ? serviceCategoryRepo.findByNameContainingIgnoreCase(name)
                : serviceCategoryRepo.findAll();

        return categories.stream()
                .map(serviceCategoryMapper::toAllServiceCategoryDto)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public List<ServiceCategoryWithSubServiceDto> getAllWithSubService() {
        return serviceCategoryRepo.findAll().stream()
                .map(serviceCategoryMapper::toDtoWithSubService)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceCategoryWithSubServiceDto getById(UUID id) {
        var serviceCategory = serviceCategoryRepo.findById(id)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.SERVICE_CATEGORY_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
        return serviceCategoryMapper.toDtoWithSubService(serviceCategory);
    }

    @Override
    public void create(CreateServiceCategoryDto reqDto) throws IOException {

        if (serviceCategoryRepo.existsByName(reqDto.getName())) {
            throw new AppException(
                    ErrorMessage.SERVICE_CATEGORY_ALREADY_EXISTS,
                    HttpStatus.CONFLICT
            );
        }

        var serviceCategory = serviceCategoryMapper.toEntity(reqDto);

        fileStorageService.storeFile(reqDto.getPhoto(), DocumentType.SERVICE_CATEGORY_PHOTO, serviceCategory.getId());
        serviceCategoryRepo.save(serviceCategory);
    }
}
