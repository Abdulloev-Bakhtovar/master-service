package ru.master.service.auth.mapper;


import ru.master.service.auth.model.Role;
import ru.master.service.auth.model.dto.RoleDto;

public interface RoleMapper {

    RoleDto toDto(Role role);
}
