package ru.master.service.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.DocFileDto;
import ru.master.service.model.dto.MasterProfileDto;
import ru.master.service.service.MasterProfileService;

import java.io.IOException;

@RestController
@RequestMapping("/master-profiles")
@RequiredArgsConstructor
public class MasterProfileController {

    private final MasterProfileService masterProfileService;

    @PostMapping("/info")
    public void create(@RequestBody MasterProfileDto masterProfileDto) {
        masterProfileService.create(masterProfileDto);
    }

    @PostMapping(value = "/document", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addDocFile(@ModelAttribute DocFileDto docFileDto) throws IOException {
        masterProfileService.addDocFile(docFileDto);
    }
}
