package com.sales.analytics.dto;

import java.math.BigDecimal;

public interface MonthlySales{
    String getMonth();

    Long getTotalSales();

    BigDecimal getTotalRevenue();
}
