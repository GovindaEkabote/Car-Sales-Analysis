package com.sales.analytics.repository;

import com.sales.analytics.dto.YearCount;
import com.sales.analytics.dto.MonthlySales;
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

    @Query(value = """
         SELECT
            DATE_FORMAT(date_of_purchase, '%Y-%m') AS month,
            COUNT(*) AS totalSales,
            SUM(price) AS totalRevenue
        FROM car_sales
        GROUP BY DATE_FORMAT(date_of_purchase, '%Y-%m')
        ORDER BY month
        """,
            nativeQuery = true)
    List<MonthlySales> getMonthlySales();


    @Query(
            value = """
                    SELECT 
                        model,
                        COUNT(*) AS totalSales
                    FROM car_sales
                    GROUP BY model
                    ORDER BY totalSales DESC
                    LIMIT 10
                    """,
            nativeQuery = true
    )
    List<Object[]> getTopSellingCars();
}
