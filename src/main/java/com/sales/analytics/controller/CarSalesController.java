package com.sales.analytics.controller;

import com.sales.analytics.commons.response.ApiResponse;
import com.sales.analytics.dto.UploadSalesResponse;
import com.sales.analytics.dto.YearCount;
import com.sales.analytics.model.CarSales;
import com.sales.analytics.service.CarSalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/car/upload")
@RequiredArgsConstructor
public class CarSalesController {

    private final CarSalesService carSalesService;

    @PostMapping("/upload-csv")
    public ResponseEntity<ApiResponse<UploadSalesResponse>> uploadFile(
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {

            UploadSalesResponse uploadResponse =
                    new UploadSalesResponse(0, 0, 0);

            ApiResponse<UploadSalesResponse> response =
                    new ApiResponse<>(
                            false,
                            "File is Empty",
                            uploadResponse,
                            HttpStatus.BAD_REQUEST.value()
                    );

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }

        UploadSalesResponse uploadResponse =
                new UploadSalesResponse(100, 95, 5);

        ApiResponse<UploadSalesResponse> response =
                new ApiResponse<>(
                        true,
                        "File uploaded successfully",
                        uploadResponse,
                        HttpStatus.OK.value()
                );

        UploadSalesResponse uploadSalesResponse =   carSalesService.uploadSalesSheet(file);
        ApiResponse<UploadSalesResponse> apiResponses =getApiResponse(uploadSalesResponse);
        return ResponseEntity.ok(apiResponses);
    }

    private static ApiResponse<UploadSalesResponse> getApiResponse(UploadSalesResponse response){
        String message;
        boolean success;

        if(response.getFailedCount() == 0){
            success = true;
            message = "File uploaded successfully";
        } else if (response.getSuccessRecords() == 0){
            success = false;
            message = "File uploaded with errors";
        }else{
            success = false;
            message = "File uploaded with errors"+response.getFailedCount()+" records failed";
        }
        return new ApiResponse<UploadSalesResponse>(
                success,
                message,
                response,
                HttpStatus.OK.value()
        );
    }

    @GetMapping("/year-count")
    public ResponseEntity<ApiResponse<List<YearCount>>> getCarSalesByYear() {
        List<YearCount> yearCounts = carSalesService.getCarSalesByYear();
        ApiResponse<List<YearCount>> response = new ApiResponse<>(
                true,
                "Success",
                yearCounts,
                HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-car/{model}")
    public List<CarSales> getCarByModelName(@PathVariable String model) {
        return carSalesService.findCarsByModelName(model);
    }
}
