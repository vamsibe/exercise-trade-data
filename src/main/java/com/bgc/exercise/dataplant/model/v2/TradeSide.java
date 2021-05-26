package com.bgc.exercise.dataplant.model.v2;

import com.bgc.exercise.dataplant.model.Side;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeSide {
    @JsonProperty(value = "sideId")
    private String sideId;
    @JsonProperty(value = "side")
    private Side side;
    @JsonProperty(value = "amount")
    private BigDecimal amount;
    @JsonProperty(value = "price")
    private BigDecimal price;

    private BigDecimal nominal;
    @JsonProperty(value = "currency")
    private String currency;
    @JsonProperty(value = "counterpartyId")
    private String counterpartyId;
}
