package com.marketing.analytics.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PredictionResponseDTO {

    private Long predictionId;
    private Long customerId;
    private Double probability;
    private Double confidenceScore;
    private Double riskScore;
    private String riskCategory;
    private String recommendedAction;
    private String modelVersion;
}
