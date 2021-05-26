package com.bgc.exercise.dataplant.model.v2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeEnvelop {

    @JsonProperty(value = "schemaVersion")
    private String schemaVersion;

    @JsonProperty(value = "documentVersion")
    private String documentVersion;

    @JsonProperty(value = "environment")
    private String environment;

    @JsonProperty(value ="trade")
    private Trade trade;


}
