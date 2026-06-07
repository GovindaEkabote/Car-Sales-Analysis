package com.sales.analytics.commons.response;


import com.sales.analytics.dto.UploadSalesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<UploadSalesResponse>> handleAllException(Exception ex){
        UploadSalesResponse uploadSalesResponse = new UploadSalesResponse(
                0,
                0,
                0);
        ApiResponse<UploadSalesResponse> apiResponse = new ApiResponse<UploadSalesResponse>(
                false,
                ex.getMessage(),
                uploadSalesResponse,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return new ResponseEntity<ApiResponse<UploadSalesResponse>>(
                apiResponse,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
