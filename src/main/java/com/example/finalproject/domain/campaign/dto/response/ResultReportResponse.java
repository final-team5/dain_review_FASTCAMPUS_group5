package com.example.finalproject.domain.campaign.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultReportResponse {
    private String reportId;
    private String campaignTitle;
    private int totalParticipants;
    private int completedParticipants;
    private String startDate;
    private String endDate;
    private PerformanceMetrics performanceMetrics;
    private String summary;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PerformanceMetrics {
        private int reach;
        private int engagement;
        private int conversion;
    }
}