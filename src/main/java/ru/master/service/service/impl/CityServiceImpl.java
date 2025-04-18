package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.constants.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.CityMapper;
import ru.master.service.model.City;
import ru.master.service.model.dto.CityDto;
import ru.master.service.repository.CityRepo;
import ru.master.service.service.CityService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepo cityRepo;
    private final CityMapper cityMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CityDto> getAll() {
        return cityRepo.findAll().stream()
                .map(cityMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public City getById(UUID id) {
        return cityRepo.findById(id)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.CITY_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
    }

    @Override
    public void create(CityDto dto) {

        if (cityRepo.existsByName(dto.getName())) {
            throw new AppException(
                    ErrorMessage.CITY_ALREADY_EXISTS,
                    HttpStatus.CONFLICT
            );
        }

        var city = cityMapper.toEntity(dto);
        cityRepo.save(city);
    }
}
