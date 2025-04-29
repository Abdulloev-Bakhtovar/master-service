package ru.master.service.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.request.CreateMasterProfileDto;
import ru.master.service.service.MasterProfileService;

import java.io.IOException;

@RestController
@RequestMapping("/master-profiles")
@RequiredArgsConstructor
public class MasterProfileController {

    private final MasterProfileService masterProfileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createMasterProfile(@ModelAttribute CreateMasterProfileDto reqDto) throws IOException {
        masterProfileService.create(reqDto);
    }
}
