package com.bgc.exercise.dataplant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorLog {
    private TradeDocument tradeDocument;
    private String validationMessage;
    private LocalDateTime dateTime;
}
