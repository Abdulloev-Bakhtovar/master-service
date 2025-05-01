package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.MasterSubServiceMapper;
import ru.master.service.model.MasterProfile;
import ru.master.service.model.MasterSubService;
import ru.master.service.model.dto.ServiceCategoryForMasterProfileDto;
import ru.master.service.repository.MasterSubServiceRepo;
import ru.master.service.repository.ServiceCategoryRepo;
import ru.master.service.repository.SubServiceCategoryRepo;
import ru.master.service.service.MasterSubServiceService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MasterSubServiceServiceImpl implements MasterSubServiceService {

    private final ServiceCategoryRepo serviceCategoryRepo;
    private final SubServiceCategoryRepo subServiceCategoryRepo;
    private final MasterSubServiceRepo masterSubServiceRepo;
    private final MasterSubServiceMapper masterSubServiceMapper;

    @Override
    public void create(List<ServiceCategoryForMasterProfileDto> reqDtos, MasterProfile masterProfile) {
        for (var selected : reqDtos) {
            var service = serviceCategoryRepo.findById(selected.getId())
                    .orElseThrow(() -> new AppException(
                            ErrorMessage.SERVICE_CATEGORY_NOT_FOUND,
                            HttpStatus.NOT_FOUND)
                    );

            for (var subDto : selected.getSubServiceCategoryDtos()) {
                var sub = subServiceCategoryRepo.findById(subDto.getId())
                        .orElseThrow(() -> new AppException(
                                ErrorMessage.SUB_SERVICE_CATEGORY_NOT_FOUND,
                                HttpStatus.NOT_FOUND
                        ));

                // Если подуслуга не относится к этой услуге — пропускаем её
                if (!service.getSubServices().contains(sub)) {
                    throw new AppException(
                            ErrorMessage.SUB_SERVICE_DOES_NOT_BELONG_THIS_SERVICE,
                            HttpStatus.BAD_REQUEST
                    );
                }

                MasterSubService masterSubService = masterSubServiceMapper.toEntity(masterProfile, service, sub);
                masterSubServiceRepo.save(masterSubService);
            }
        }
    }

}
