package com.apoiaqui.backend.controller.campaign.request;

import java.math.BigDecimal;

public record CampaignRequest(
    String title,
    String description,
    BigDecimal goal
) {}

