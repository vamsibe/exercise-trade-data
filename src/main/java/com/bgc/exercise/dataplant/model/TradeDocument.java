package com.bgc.exercise.dataplant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class TradeDocument {

    private String environment;
    private String tradeId;
    private String tradeStatus;
    private LocalDate tradeDate;
    private String sideId;
    private Side side;
    private BigDecimal amount;
    private BigDecimal price;
    private BigDecimal nominal;
    private String currency;
    private String counterpartyId;

}
