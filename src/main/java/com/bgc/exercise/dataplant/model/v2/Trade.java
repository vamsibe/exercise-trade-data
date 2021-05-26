package com.bgc.exercise.dataplant.model.v2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trade {

    @JsonProperty(value = "tradeId")
    private String tradeId;

    @JsonProperty(value = "tradeStatus")
    private String tradeStatus;

    @JsonProperty(value = "tradeDate")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", locale = "en_GB")
    private String tradeDate;

    @JsonProperty(value = "tradeSides")
    private List<TradeSide> tradeSides;

}
