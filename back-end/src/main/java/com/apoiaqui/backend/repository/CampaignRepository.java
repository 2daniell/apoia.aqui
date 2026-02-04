package com.apoiaqui.backend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apoiaqui.backend.domain.entity.Campaign;
import com.apoiaqui.backend.domain.model.DashboardStats;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, UUID> {

    @Query("SELECT COUNT(c), COALESCE(SUM(c.raised), 0), COALESCE(SUM(c.donations), 0) FROM Campaign c WHERE c.owner.id = :userId")
    DashboardStats geStats(@Param("userId")  Long userId);

    List<Campaign> findByOwnerId(Long userId);

    Page<Campaign> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
