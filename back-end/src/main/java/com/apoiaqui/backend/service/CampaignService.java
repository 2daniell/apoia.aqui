package com.apoiaqui.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.apoiaqui.backend.domain.entity.Campaign;
import com.apoiaqui.backend.domain.entity.User;
import com.apoiaqui.backend.domain.exception.InvalidResourceException;
import com.apoiaqui.backend.domain.exception.NotFoundException;
import com.apoiaqui.backend.domain.exception.UnauthorizedException;
import com.apoiaqui.backend.domain.model.DashboardStats;
import com.apoiaqui.backend.repository.CampaignRepository;
import com.apoiaqui.backend.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository  repository;
    private final UserRepository userRepository;

    @Transactional
    public Campaign create(String title, String description, BigDecimal goal, Long userId) {

        if (goal.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidResourceException("Não é permitido valor negativo!");

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Usuario não encontrado"));

        Campaign campaign = new Campaign(title, description, goal, user);

        return repository.save(campaign);
    }

    @Transactional
    public Campaign update(String campaignId, String title, String description, BigDecimal goal, Long userId) {

        Campaign campaign = repository.findById(UUID.fromString(campaignId)).orElseThrow(() -> new NotFoundException("Campanha não encontrado"));

        if (!campaign.getOwner().getId().equals(userId)) {
            throw new UnauthorizedException("Não autorizado!");
        }

        campaign.setDescription(description);
        campaign.setTitle(title);
        campaign.setGoal(goal);

        return repository.save(campaign);
    }

    public List<Campaign> getUserCampaigns(Long userId) {
        return repository.findByOwnerId(userId);
    }

    public DashboardStats getUserDashboardStats(Long userId) {
        return repository.geStats(userId);
    }

    public List<Campaign> getCampaigns(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending());

        Page<Campaign> result = repository.findAllByOrderByCreatedAtDesc(pageable);
        return result.getContent();
    }

    public Optional<Campaign> getCampaignById(String id) {
        return repository.findById(UUID.fromString(id));
    }

    @Transactional
    public void delete(String id, Long userId) {
        Campaign campaign = repository.findById(UUID.fromString(id)).orElseThrow(() -> new  NotFoundException("Campanha não encontrada"));

        if (!campaign.getOwner().getId().equals(userId)) {
            throw new UnauthorizedException("Não autorizado");
        }

        repository.delete(campaign);
    }
}
