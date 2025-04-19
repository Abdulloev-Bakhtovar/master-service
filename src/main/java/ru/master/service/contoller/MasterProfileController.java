package ru.master.service.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.master.service.model.dto.MasterProfileDto;
import ru.master.service.service.MasterProfileService;

import java.io.IOException;

@RestController
@RequestMapping("/master-profile")
@RequiredArgsConstructor
public class MasterProfileController {

    private final MasterProfileService masterProfileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void create(@ModelAttribute MasterProfileDto masterProfileDto) throws IOException {
        masterProfileService.create(masterProfileDto);
    }
}
