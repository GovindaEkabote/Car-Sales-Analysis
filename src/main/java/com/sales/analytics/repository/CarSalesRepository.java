package com.sales.analytics.repository;

import com.sales.analytics.dto.YearCount;
import com.sales.analytics.model.CarSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarSalesRepository extends JpaRepository<CarSales , Long> {

    boolean existsByCarNumber(String carNumber);


    @Query("""
            SELECT new com.sales.analytics.dto.YearCount(c.year, COUNT(c)) 
            FROM CarSales c 
            GROUP BY c.year
            ORDER BY c.year
            """)
    List<YearCount> getCarSalesByYear();

    @Query("""
            SELECT c FROM CarSales c WHERE c.model = :model
            """)
    List<CarSales> findCarsByModelName(@Param("model") String model);
}
