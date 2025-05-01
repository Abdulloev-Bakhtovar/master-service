package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.SubServiceCategoryMapper;
import ru.master.service.model.dto.request.CreateSubServiceCategoryDto;
import ru.master.service.model.dto.response.AllSubServiceCategoryDto;
import ru.master.service.repository.ServiceCategoryRepo;
import ru.master.service.repository.SubServiceCategoryRepo;
import ru.master.service.service.SubServiceCategoryService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SubServiceCategoryServiceImpl implements SubServiceCategoryService {

    private final SubServiceCategoryRepo subServiceCategoryRepo;
    private final SubServiceCategoryMapper subServiceCategoryMapper;
    private final ServiceCategoryRepo serviceCategoryRepo;

    @Override
    public List<AllSubServiceCategoryDto> getAll() {
        return subServiceCategoryRepo.findAll().stream()
                .map(subServiceCategoryMapper::toDto)
                .toList();
    }

    @Override
    public void create(CreateSubServiceCategoryDto reqDto) {

        var serviceCategory = serviceCategoryRepo.findById(reqDto.getServiceCategoryId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.SERVICE_CATEGORY_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var subService = subServiceCategoryMapper.toEntity(reqDto, serviceCategory);
        subServiceCategoryRepo.save(subService);
    }
}
