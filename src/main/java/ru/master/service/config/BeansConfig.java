package ru.master.service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.master.service.auth.model.Role;
import ru.master.service.auth.repository.RoleRepo;
import ru.master.service.constants.RoleName;

@Configuration
@RequiredArgsConstructor
public class BeansConfig {

    @Bean
    public CommandLineRunner runner(RoleRepo roleRepo) {
        return args -> {
            for (RoleName roleName : RoleName.values()) {
                if (roleRepo.findByName(roleName).isEmpty()) {
                    roleRepo.save(
                            Role.builder()
                                    .name(roleName)
                                    .build()
                    );
                }
            }
        };
    }
}