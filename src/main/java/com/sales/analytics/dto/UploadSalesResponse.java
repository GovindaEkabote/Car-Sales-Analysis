package com.sales.analytics.dto;


import lombok.Getter;

@Getter
public class UploadSalesResponse {

    private int totalRecords;
    private int successRecords;
    private int failedCount;

    public UploadSalesResponse(int totalRecords, int successRecords, int failedCount) {
        this.totalRecords = totalRecords;
        this.successRecords = successRecords;
        this.failedCount = failedCount;
    }
}
