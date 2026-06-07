package com.sales.analytics.repository;

import com.sales.analytics.model.CarSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarSalesRepository extends JpaRepository<CarSales , Long> {

    boolean existsByCarNumber(String carNumber);
}
