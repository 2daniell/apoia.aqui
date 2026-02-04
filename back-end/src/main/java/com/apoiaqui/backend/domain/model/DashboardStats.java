package com.apoiaqui.backend.domain.model;

import java.math.BigDecimal;

public interface DashboardStats {

    Long getCount();

    BigDecimal getSum();

    Long getDonations();
}

