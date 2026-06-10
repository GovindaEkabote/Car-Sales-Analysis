package com.sales.analytics.service;

import com.sales.analytics.dto.*;
import com.sales.analytics.model.CarSales;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarSalesService {

    UploadSalesResponse uploadSalesSheet(MultipartFile  file);

    List<YearCount> getCarSalesByYear();

    List<CarSales> findCarsByModelName(String model);

//    List<MonthlySales> getMonthlySales();

    List<MonthlySales> getMonthlySales();

    List<TopSellingCar> getTopSellingCars();

    List<RevenueByYear> getRevenueByYear();

}
