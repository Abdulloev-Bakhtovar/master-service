package ru.master.service.contoller;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.master.service.constants.Education;
import ru.master.service.constants.MaritalStatus;
import ru.master.service.constants.Role;
import ru.master.service.model.dto.EnumDto;
import ru.master.service.service.EnumService;

import java.util.List;

@PermitAll
@RestController
@RequestMapping("/enums")
@RequiredArgsConstructor
public class EnumController {

    private final EnumService enumService;

    @GetMapping("/education")
    public List<EnumDto> getEducation() {
        return enumService.getAllValues(Education.class);
    }

    @GetMapping("/marital-status")
    public List<EnumDto> getMaritalStatus() {
        return enumService.getAllValues(MaritalStatus.class);
    }

    @GetMapping("/role")
    public List<EnumDto> getRoles() {
        return enumService.getAllValues(Role.class);
    }
}