package com.bgc.exercise.dataplant.transform;


import com.bgc.exercise.dataplant.model.ErrorLog;
import com.bgc.exercise.dataplant.model.Side;
import com.bgc.exercise.dataplant.model.TradeDocument;
import com.bgc.exercise.dataplant.model.v2.TradeEnvelop;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TradeTransformer {

    private final Logger logger = LoggerFactory.getLogger(TradeTransformer.class);

    public List<TradeDocument> parseTradeData(String jsonTradeData) throws JsonProcessingException {

        List<TradeDocument> tradeDocumentList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(jsonTradeData);
        String environment = rootNode.path("environment").asText("");
        JsonNode trade = rootNode.path("trade");

        TradeDocument tradeDocument = TradeDocument.builder().environment(environment).build();

        if (!trade.isMissingNode()) {

            String tradeId = trade.path("tradeId").asText("");
            String tradeStatus = trade.path("tradeStatus").asText("");
            String tradeDate = trade.path("tradeDate").asText("");

            tradeDocument.setTradeId(tradeId);
            tradeDocument.setTradeStatus(tradeStatus);
            tradeDocument.setTradeDate(ZonedDateTime.parse(tradeDate).toLocalDate());
        }

        JsonNode tradeSides = rootNode.path("trade").path("tradeSides");

        if (tradeSides.isArray() && tradeSides.size() > 0) {

            for (JsonNode tradeSide : tradeSides) {
                String sideId = tradeSide.path("sideId").asText("");
                String side = tradeSide.path("side").asText("");
                double amount = tradeSide.path("amount").asDouble(0.0);
                double price = tradeSide.path("price").asDouble(0.0);
                String currency = tradeSide.path("currency").asText("");
                String counterpartyId = tradeSide.path("counterpartyId").asText("");

                TradeDocument document = tradeDocument.toBuilder()
                        .sideId(sideId)
                        .amount(BigDecimal.valueOf(amount))
                        .price(BigDecimal.valueOf(price))
                        .nominal(BigDecimal.valueOf(amount).multiply(BigDecimal.valueOf(price)))
                        .currency(currency)
                        .counterpartyId(counterpartyId)
                        .build();

                if(!side.isEmpty()) {
                    document.setSide((Side.valueOf(side)));
                }

                tradeDocumentList.add(document);
            }
        } else {
            tradeDocumentList.add(tradeDocument);
        }

        //print
        logger.info("");
        toJsonMessage(tradeDocumentList);

        return tradeDocumentList;
    }

    public void toJsonMessage(List<TradeDocument> tradeDocuments) {

        ObjectMapper mapper = new ObjectMapper();

        tradeDocuments
                .stream()
                .map(tradeDocument -> {
                    try {
                        return mapper.writeValueAsString(tradeDocument);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return "";
                })
                .forEach(System.out::println);
    }

    public void isValidTradeSide(TradeDocument tradeDocument) throws IOException {

        if (!StringUtils.hasText(tradeDocument.getEnvironment())) {
            writeToDeadLetterQueueFile(tradeDocument, "environment can't be blank/empty/null");
            throw new IllegalArgumentException("environment can't be blank/empty/null");
        }

        if (!StringUtils.hasText(tradeDocument.getTradeId())) {
            writeToDeadLetterQueueFile(tradeDocument, "tradeId can't be blank/empty/null");
            throw new IllegalArgumentException("tradeId can't be blank/empty/null");
        }

        if (!StringUtils.hasText(tradeDocument.getTradeStatus())) {
            writeToDeadLetterQueueFile(tradeDocument, "tradeStatus can't be blank/empty/null");
            throw new IllegalArgumentException("tradeStatus can't be blank/empty/null");
        }

        if (!StringUtils.hasText(tradeDocument.getSideId())) {
            writeToDeadLetterQueueFile(tradeDocument, "sideId can't be blank/empty/null");
            throw new IllegalArgumentException("sideId can't be blank/empty/null");
        }

        if (tradeDocument.getSide() == null || !StringUtils.hasText(tradeDocument.getSide().toString())) {
            writeToDeadLetterQueueFile(tradeDocument, "side can't be blank/empty/null");
            throw new IllegalArgumentException("side can't be blank/empty/null");
        }

        if (!StringUtils.hasText(tradeDocument.getCurrency())) {
            writeToDeadLetterQueueFile(tradeDocument, "currency can't be blank/empty/null");
            throw new IllegalArgumentException("currency can't be blank/empty/null");
        }

        if (!StringUtils.hasText(tradeDocument.getCounterpartyId())) {
            writeToDeadLetterQueueFile(tradeDocument, "counterpartyId can't be blank/empty/null");
            throw new IllegalArgumentException("counterpartyId can't be blank/empty/null");
        }

        if (tradeDocument.getAmount().equals(BigDecimal.ZERO)) {
            writeToDeadLetterQueueFile(tradeDocument, "amount can't be blank/empty/null");
            throw new IllegalArgumentException("amount can't be zero/blank/empty/null");
        }

        if (tradeDocument.getPrice().equals(BigDecimal.ZERO)) {
            writeToDeadLetterQueueFile(tradeDocument, "price can't be zero/blank/empty/null");
            throw new IllegalArgumentException("price can't be zero/blank/empty/null");
        }
    }

    public void writeToDeadLetterQueueFile(TradeDocument tradeDocument, String validationMessage) throws IOException {

        logger.error("tradeDocument: {} , failed with validation message {}", tradeDocument, validationMessage);

        ObjectMapper mapper = new ObjectMapper();

        ErrorLog errorLog = ErrorLog.builder()
                .tradeDocument(tradeDocument)
                .validationMessage(validationMessage)
                .dateTime(LocalDateTime.now()).build();

        BufferedWriter writer = new BufferedWriter(new FileWriter("DeadLetterQueueFile.txt", true));
        writer.append(System.lineSeparator());
        writer.append(mapper.writeValueAsString(errorLog));
        writer.close();

    }

    public TradeEnvelop parseTradeUsingDto(String jsonTradeData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TradeEnvelop tradeEnvelop = objectMapper.readValue(jsonTradeData, TradeEnvelop.class);
        logger.info(tradeEnvelop.toString());
        return tradeEnvelop;
    }
}
