package com.marketing.analytics.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {

    private Long customerId;
    private Integer age;
    private String gender;
    private Double income;
    private String education;
    private String maritalStatus;
}
