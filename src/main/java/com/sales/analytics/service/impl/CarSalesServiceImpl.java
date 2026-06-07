package com.sales.analytics.service.impl;

import com.sales.analytics.dto.UploadSalesResponse;
import com.sales.analytics.model.CarSales;
import com.sales.analytics.repository.CarSalesRepository;
import com.sales.analytics.service.CarSalesService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Setter
@RequiredArgsConstructor
@Service
public class CarSalesServiceImpl implements CarSalesService {

    private final CarSalesRepository carSalesRepository;

    @Override
    public UploadSalesResponse uploadSalesSheet(MultipartFile file) {

        List<CarSales> carSales = new ArrayList<>();

        int failCount = 0;
        int totalRecords = 0;

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try (BufferedReader bufferedReader =
                     new BufferedReader(
                             new InputStreamReader(
                                     file.getInputStream(),
                                     StandardCharsets.UTF_8))) {

            CSVFormat builder = CSVFormat.DEFAULT.builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setIgnoreHeaderCase(true)
                    .setTrim(true)
                    .build();

            CSVParser csvParser = CSVParser.parse(bufferedReader, builder);

            for (CSVRecord csvRecord : csvParser) {

                try {

                    totalRecords++;

                    csvRecord.get("Car Number");
                    csvRecord.get("Brand");
                    csvRecord.get("Model");
                    csvRecord.get("Year");
                    csvRecord.get("Price (Rs)");

                    String car = csvRecord.get("Car Number");
                    String brand = csvRecord.get("Brand");
                    String model = csvRecord.get("Model");
                    String year = csvRecord.get("Year");
                    String price = csvRecord.get("Price (Rs)");

                    boolean exists = carSalesRepository.existsByCarNumber(car);

                    if (exists) {
                        failCount++;
                        System.out.println("Duplicate Data Found : " + car);
                        continue;
                    }

                    CarSales carSales1 = new CarSales();

                    carSales1.setCarNumber(csvRecord.get("Car Number"));
                    carSales1.setBrand(csvRecord.get("Brand"));
                    carSales1.setModel(csvRecord.get("Model"));
                    carSales1.setColor(csvRecord.get("Color"));

                    carSales1.setYear(
                            Integer.parseInt(csvRecord.get("Year").trim()));

                    /*
                     * Adjust the formatter according to your CSV values.
                     * Example:
                     * 2025-01-15T10:30:00
                     */
                    carSales1.setDateOfPurchase(
                            LocalDateTime.of(
                                    LocalDate.parse(
                                            csvRecord.get("Date of Purchase").trim(),
                                            dateFormatter
                                    ),
                                    LocalTime.MIDNIGHT
                            )
                    );

                    carSales1.setTimeOfPurchase(
                            LocalDateTime.of(
                                    LocalDate.now(),
                                    LocalTime.parse(
                                            csvRecord.get("Time of Purchase").trim(),
                                            timeFormatter
                                    )
                            )
                    );

                    carSales1.setPrice(
                            Long.parseLong(
                                    csvRecord.get("Price (Rs)").trim()));

                    carSales1.setMileage(
                            Double.parseDouble(
                                    csvRecord.get("Mileage (km/l)").trim()));

                    carSales1.setEngine(
                            Integer.parseInt(
                                    csvRecord.get("Engine (cc)").trim()));

                    carSales1.setFuletype(
                            csvRecord.get("Fuel Type"));

                    carSales1.setPaymentMode(
                            csvRecord.get("Payment Mode"));

                    carSales1.setState(
                            csvRecord.get("State"));

                    carSales1.setCity(
                            csvRecord.get("City"));

                    carSales1.setCustomerName(
                            csvRecord.get("Customer Name"));

                    carSales1.setCustomerNumber(
                            csvRecord.get("Contact Number"));

                    carSales1.setEmail(
                            csvRecord.get("Email"));

                    carSales1.setWarrantyPeriod(
                            Integer.parseInt(
                                    csvRecord.get("Warranty Period (years)").trim()));

                    carSales.add(carSales1);

                } catch (Exception e) {
                    failCount++;
                    throw new RuntimeException(
                            "Unable to parse CSV row "
                                    + csvRecord.getRecordNumber()
                                    + " : "
                                    + e.getMessage());
                }
            }

            if (!carSales.isEmpty()) {
                carSalesRepository.saveAll(carSales);
            }

        } catch (Exception e) {
            throw new RuntimeException("unable to Parse CSV: " + e.getMessage());
        }

        int successCount = totalRecords - failCount;

        return new UploadSalesResponse(
                totalRecords,
                successCount,
                failCount);
    }
}
