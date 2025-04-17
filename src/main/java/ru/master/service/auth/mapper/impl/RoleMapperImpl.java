package ru.master.service.auth.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.auth.mapper.RoleMapper;
import ru.master.service.auth.model.Role;
import ru.master.service.auth.model.dto.RoleDto;

@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public RoleDto toDto(Role role) {
        if (role == null) return null;

        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
