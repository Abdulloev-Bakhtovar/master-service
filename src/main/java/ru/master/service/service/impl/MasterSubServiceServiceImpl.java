package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.constants.EntityName;
import ru.master.service.constants.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.MasterSubServiceMapper;
import ru.master.service.model.MasterProfile;
import ru.master.service.model.MasterSubService;
import ru.master.service.model.dto.ServiceCategoryDto;
import ru.master.service.model.dto.SubServiceCategoryDto;
import ru.master.service.repository.MasterSubServiceRepo;
import ru.master.service.repository.ServiceCategoryRepo;
import ru.master.service.repository.SubServiceCategoryRepo;
import ru.master.service.service.MasterSubServiceService;

import java.util.List;

import static ru.master.service.util.ErrorFormatterUtil.format;

@Service
@Transactional
@RequiredArgsConstructor
public class MasterSubServiceServiceImpl implements MasterSubServiceService {

    private final ServiceCategoryRepo serviceCategoryRepo;
    private final SubServiceCategoryRepo subServiceCategoryRepo;
    private final MasterSubServiceRepo masterSubServiceRepo;
    private final MasterSubServiceMapper masterSubServiceMapper;

    @Override
    public void create(List<ServiceCategoryDto> dtos, MasterProfile masterProfile) {

        for (ServiceCategoryDto selected : dtos) {
            var service = serviceCategoryRepo.findById(selected.getId())
                    .orElseThrow(() -> new AppException(
                            format(ErrorMessage.ENTITY_NOT_FOUND, EntityName.SERVICE_CATEGORY.get()),
                            HttpStatus.NOT_FOUND)
                    );

            for (SubServiceCategoryDto subService : selected.getSubServiceCategoryDtos()) {
                var sub = subServiceCategoryRepo.findById(subService.getId())
                        .orElseThrow(() -> new AppException(
                                format(ErrorMessage.ENTITY_NOT_FOUND, EntityName.SUB_SERVICE_CATEGORY.get()),
                                HttpStatus.NOT_FOUND)
                        );
                // Проверка, что sub принадлежит service
                if (!service.getSubServices().contains(sub)) {
                    throw new AppException(
                            "Subservice does not belong to selected service",
                            HttpStatus.BAD_REQUEST
                    );
                }

                MasterSubService masterSubService = masterSubServiceMapper.toEntity(masterProfile, service, sub);
                masterSubServiceRepo.save(masterSubService);
            }
        }

    }
}
