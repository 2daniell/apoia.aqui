package com.apoiaqui.backend.controller.campaign.response;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public record CampaignResponse(
    String id,
    String title,
    String description,
    LocalDateTime createdAt,
    String ownerName,
    Integer donations,
    BigDecimal goal,
    BigDecimal raised,
    boolean isOwner
) {}

