package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.auth.model.User;
import ru.master.service.constant.VerificationStatus;
import ru.master.service.mapper.MasterRequestMapper;
import ru.master.service.model.MasterProfile;
import ru.master.service.model.MasterRequest;
import ru.master.service.model.dto.CityDto;
import ru.master.service.model.dto.MasterProfileForMasterRequestResDto;
import ru.master.service.model.dto.MasterSubserviceDto;
import ru.master.service.model.dto.UserDto;
import ru.master.service.model.dto.request.RejectMasterRequestReqDto;
import ru.master.service.model.dto.response.MasterRequestResDto;
import ru.master.service.model.dto.response.NewMasterRequestResDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MasterRequestMapperImpl implements MasterRequestMapper {

    @Override
    public MasterRequest toMasterRequestEntity(User user, VerificationStatus verificationStatus) {
        if (user == null) return null;

        user.setVerificationStatus(verificationStatus);

        return MasterRequest.builder()
                .user(user)
                .build();
    }

    @Override
    public NewMasterRequestResDto toNewMasterRequestResDto(MasterProfile masterProfile, MasterRequest masterRequest) {
        if (masterProfile == null) return null;

        var cityDto = CityDto.builder()
                .id(masterProfile.getCity().getId())
                .name(masterProfile.getCity().getName())
                .build();

        return NewMasterRequestResDto.builder()
                .id(masterRequest.getId())
                .firstName(masterProfile.getFirstName())
                .lastName(masterProfile.getLastName())
                .phoneNumber(masterProfile.getUser().getPhoneNumber())
                .workExperience(masterProfile.getWorkExperience())
                .cityDto(cityDto)
                .build();
    }

    @Override
    public void toEntityForApprove(User admin, MasterRequest masterRequest) {
        masterRequest.getUser().setVerificationStatus(VerificationStatus.APPROVED);
        masterRequest.setReviewedByAdminId(admin.getId());
    }

    @Override
    public void toEntityForReject(User admin, MasterRequest masterRequest, RejectMasterRequestReqDto reqDto) {
        masterRequest.getUser().setVerificationStatus(VerificationStatus.REJECTED);
        masterRequest.setReviewedByAdminId(admin.getId());

        if (reqDto.getRejectionReason() != null && !reqDto.getRejectionReason().trim().isEmpty()) {
            masterRequest.setRejectionReason(reqDto.getRejectionReason());
        }
    }

    @Override
    public MasterRequestResDto toMasterRequestResDto(MasterRequest entity, User user, MasterProfile masterProfile) {
        if (entity == null) return null;

        var userDto = UserDto.builder()
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .verificationStatus(user.getVerificationStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

        var cityDto = CityDto.builder()
                .id(masterProfile.getCity().getId())
                .name(masterProfile.getCity().getName())
                .build();

        List<MasterSubserviceDto> masterSebserviceDtos = masterProfile.getSubservices().stream()
                .map(sub -> MasterSubserviceDto.builder()
                        .id(sub.getId())
                        .name(sub.getName())
                        .createdAt(sub.getCreatedAt())
                        .updatedAt(sub.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        var masterInfoDto = MasterProfileForMasterRequestResDto.builder()
                .id(masterProfile.getId())
                .firstName(masterProfile.getFirstName())
                .lastName(masterProfile.getLastName())
                .email(masterProfile.getEmail())
                .education(masterProfile.getEducation())
                .maritalStatus(masterProfile.getMaritalStatus())
                .cityDto(cityDto)
                .subserviceDtos(masterSebserviceDtos)
                .build();

        return MasterRequestResDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .rejectionReason(entity.getRejectionReason())
                .reviewedByAdminId(entity.getReviewedByAdminId())
                .userDto(userDto)
                .masterInfoDto(masterInfoDto)
                .build();
    }
}
