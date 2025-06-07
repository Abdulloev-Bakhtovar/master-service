package ru.master.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.request.CreateClientProfileReqDto;
import ru.master.service.model.dto.response.ClientInfoForCreateOrderResDto;
import ru.master.service.model.dto.response.ClientReferralInfoResDto;
import ru.master.service.service.ClientProfileService;
import ru.master.service.service.ReferralProgramService;

@RestController
@RequestMapping("/client-profiles")
@RequiredArgsConstructor
public class ClientProfileController {

    private final ClientProfileService clientProfileService;
    private final ReferralProgramService referralProgramService;

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public ClientInfoForCreateOrderResDto getClientInfo() {
        return clientProfileService.getClientInfo();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateClientProfileReqDto reqDto) {
        clientProfileService.create(reqDto);
    }

    @GetMapping("/referral/info")
    public ClientReferralInfoResDto getReferralInfo() {
        return referralProgramService.getReferralInfo();
    }
}
