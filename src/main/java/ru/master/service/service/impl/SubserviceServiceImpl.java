package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.SubserviceMapper;
import ru.master.service.model.dto.request.CreateSubserviceReqDto;
import ru.master.service.model.dto.response.SubserviceResDto;
import ru.master.service.repository.ServiceCategoryRepo;
import ru.master.service.repository.SubserviceRepo;
import ru.master.service.service.SubserviceService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SubserviceServiceImpl implements SubserviceService {

    private final SubserviceRepo subserviceRepo;
    private final SubserviceMapper subserviceMapper;
    private final ServiceCategoryRepo serviceCategoryRepo;

    @Override
    @Transactional(readOnly = true)
    public List<SubserviceResDto> getAllByServiceId(UUID serviceId) {
        return subserviceRepo.findAllByServiceCategoryId(serviceId).stream()
                .map(subserviceMapper::toSubserviceResDto)
                .toList();
    }

    @Override
    public void create(UUID serviceId, CreateSubserviceReqDto reqDto) {

        var serviceCategory = serviceCategoryRepo.findById(serviceId)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.SERVICE_CATEGORY_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var subService = subserviceMapper.toEntity(reqDto, serviceCategory);
        subserviceRepo.save(subService);
    }
}
