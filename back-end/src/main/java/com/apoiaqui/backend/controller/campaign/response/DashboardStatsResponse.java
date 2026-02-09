package com.apoiaqui.backend.controller.campaign.response;

import java.math.BigDecimal;

public record DashboardStatsResponse(
    Long campaigns,
    BigDecimal totalCollection,
    Long donations
) {}
