package com.apoiaqui.backend.controller.campaign;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apoiaqui.backend.controller.campaign.request.CampaignRequest;
import com.apoiaqui.backend.controller.campaign.response.CampaignResponse;
import com.apoiaqui.backend.domain.entity.Campaign;
import com.apoiaqui.backend.service.CampaignService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/campaign")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService service;

    @PostMapping
    public ResponseEntity<CampaignResponse> create(@RequestBody CampaignRequest request, @AuthenticationPrincipal Jwt jwt) {

        Long userId = Long.valueOf(jwt.getSubject());

        Campaign campaign = service.create(request.title(), request.description(), request.goal(), userId);

        return ResponseEntity.status(HttpStatus.OK).body(mapper(campaign));
    }

    @GetMapping("/me")
    public ResponseEntity<List<CampaignResponse>> me(@AuthenticationPrincipal Jwt jwt) {

        Long userId = Long.valueOf(jwt.getSubject());

        var campaigns = service.getUserCampaigns(userId);

        return ResponseEntity.status(HttpStatus.OK).body(campaigns.stream().map(c -> mapper(c)).toList());
    }

    @GetMapping("/all")
    public ResponseEntity<List<CampaignResponse>> get(@RequestParam(defaultValue = "1") int page,  @RequestParam(defaultValue = "100") int pageSize) {

        var campaigns = service.getCampaigns(page, pageSize);

        return ResponseEntity.status(HttpStatus.OK).body(campaigns.stream().map(c -> mapper(c)).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampaignResponse> update(@RequestBody CampaignRequest request, @PathVariable String id, @AuthenticationPrincipal Jwt jwt) {

        Long userId = Long.valueOf(jwt.getSubject());

        Campaign campaign = service.update(id, request.title(), request.description(), request.goal(), userId);

        return ResponseEntity.status(HttpStatus.OK).body(mapper(campaign));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@RequestBody CampaignRequest request, @AuthenticationPrincipal Jwt jwt, @PathVariable String id) {

        Long userId = Long.valueOf(jwt.getSubject());

        service.delete(id, userId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private CampaignResponse mapper(Campaign campaign) {
        return new CampaignResponse(campaign.getId().toString(), campaign.getTitle(), campaign.getDescription(), campaign.getCreatedAt(), 
        campaign.getOwner().getFirstName(), campaign.getDonations(), campaign.getGoal(), campaign.getRaised());
    }
    
}
