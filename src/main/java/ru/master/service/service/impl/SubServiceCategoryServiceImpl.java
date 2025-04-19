package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.constants.EntityName;
import ru.master.service.constants.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.SubServiceCategoryMapper;
import ru.master.service.model.dto.SubServiceCategoryDto;
import ru.master.service.repository.SubServiceCategoryRepo;
import ru.master.service.service.SubServiceCategoryService;

import java.util.List;

import static ru.master.service.util.ErrorFormatterUtil.format;

@Service
@Transactional
@RequiredArgsConstructor
public class SubServiceCategoryServiceImpl implements SubServiceCategoryService {

    private final SubServiceCategoryRepo subServiceCategoryRepo;
    private final SubServiceCategoryMapper subServiceCategoryMapper;

    @Override
    public List<SubServiceCategoryDto> getAll() {
        return subServiceCategoryRepo.findAll().stream()
                .map(subServiceCategoryMapper::toDto)
                .toList();
    }

    @Override
    public void create(SubServiceCategoryDto dto) {

        if (subServiceCategoryRepo.existsByName(dto.getName())) {
           throw new AppException(
                   format(ErrorMessage.ENTITY_ALREADY_EXISTS, EntityName.SUB_SERVICE_CATEGORY.get()),
                   HttpStatus.CONFLICT
           );
        }

        var subService = subServiceCategoryMapper.toEntity(dto);
        subServiceCategoryRepo.save(subService);
    }
}
