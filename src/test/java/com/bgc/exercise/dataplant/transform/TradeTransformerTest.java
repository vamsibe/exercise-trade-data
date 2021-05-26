package com.bgc.exercise.dataplant.transform;


import com.bgc.exercise.dataplant.model.TradeDocument;
import com.bgc.exercise.dataplant.model.v2.TradeEnvelop;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TradeTransformerTest {

    @InjectMocks
    TradeTransformer underTest;

    @Test
    void givenValidTradeShouldReturnParsedSides() throws IOException {

        List<TradeDocument> list = underTest.parseTradeData(JSON_TRADE_DATA);
        assertEquals(3, list.size());

        underTest.isValidTradeSide(list.get(0));
        underTest.isValidTradeSide(list.get(1));
        underTest.isValidTradeSide(list.get(2));

    }

    @Test
    void givenEmptySideIdShouldReturnParsedSidesAndValidationShouldFailForMissingSideForEmptySideId() throws IOException {

        List<TradeDocument> list = underTest.parseTradeData(JSON_TRADE_DATA_EMPTY_SIDE_ID);
        assertEquals(3, list.size());

        underTest.isValidTradeSide(list.get(0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> underTest.isValidTradeSide(list.get(1)));
        underTest.isValidTradeSide(list.get(2));

    }

    @Test
    void givenMissingSideShouldReturnParsedSidesAndValidationShouldFailForMissingSide() throws IOException {
        List<TradeDocument> list = underTest.parseTradeData(JSON_TRADE_DATA_MISSING_SIDE);
        assertEquals(3, list.size());

        underTest.isValidTradeSide(list.get(0));

        Assertions.assertThrows(IllegalArgumentException.class, () -> underTest.isValidTradeSide(list.get(1)));

        underTest.isValidTradeSide(list.get(2));
    }

    @Test
    void givenNoTradeSidesShouldReturnParsedTradeDocumentAndValidationShouldFail() throws JsonProcessingException {
        List<TradeDocument> list = underTest.parseTradeData(JSON_TRADE_DATA_EMPTY_TRADE_SIDES);
        assertEquals(1, list.size());

        Assertions.assertThrows(IllegalArgumentException.class, () -> underTest.isValidTradeSide(list.get(0)));
    }


    @Test
    void givenNoTradeShouldReturnParsedTradeDocumentAndValidationShouldFail() throws JsonProcessingException {
        List<TradeDocument> list = underTest.parseTradeData(JSON_TRADE_NO_TRADE_DATA);
        assertEquals(1, list.size());


        Assertions.assertThrows(IllegalArgumentException.class, () -> underTest.isValidTradeSide(list.get(0)));

    }

    @Test
    void givenValidTradeShouldGetParsed() throws JsonProcessingException {
        TradeEnvelop tradeEnvelop = underTest.parseTradeUsingDto(JSON_TRADE_DATA);
        assertEquals(3, tradeEnvelop.getTrade().getTradeSides().size());

    }


    private static final String JSON_TRADE_DATA = "{" +
            "\"schemaVersion\": \"0.123\"," +
            "\"documentVersion\": 6," +
            "\"environment\": \"BETA\"," +
            "\"trade\": {" +
            "\"tradeId\": \"51849291\"," +
            "\"tradeStatus\": \"NEW\"," +
            "\"tradeDate\": \"2021-05-17T21:11:17.265000+01:00\"," +
            "\"tradeSides\": [{" +
            "\"sideId\": \"1\"," +
            "\"side\": \"Seller\"," +
            "\"amount\": 250.00," +
            "\"price\": 10.2600," +
            "\"currency\": \"USD\"," +
            "\"counterpartyId\": \"123XYZ\"" +
            "}," +
            "{" +
            "\"sideId\": \"2\"," +
            "\"side\": \"Buyer\"," +
            "\"amount\": 150.00," +
            "\"price\": 7.3100," +
            "\"currency\": \"GBP\"," +
            "\"counterpartyId\": \"456abc\"" +
            "}," +
            "{" +
            "\"sideId\": \"3\"," +
            "\"side\": \"Buyer\"," +
            "\"amount\": 100.00," +
            "\"price\": 7.2500," +
            "\"currency\": \"GBP\"," +
            "\"counterpartyId\": \"789opv\"" +
            "}]" +
            "}" +
            "}";


    private static final String JSON_TRADE_DATA_EMPTY_SIDE_ID = "{" +
            "\"schemaVersion\": \"0.123\"," +
            "\"documentVersion\": 6," +
            "\"environment\": \"BETA\"," +
            "\"trade\": {" +
            "\"tradeId\": \"51849291\"," +
            "\"tradeStatus\": \"NEW\"," +
            "\"tradeDate\": \"2021-05-17T21:11:17.265000+01:00\"," +
            "\"tradeSides\": [{" +
            "\"sideId\": \"1\"," +
            "\"side\": \"Seller\"," +
            "\"amount\": 250.00," +
            "\"price\": 10.2600," +
            "\"currency\": \"USD\"," +
            "\"counterpartyId\": \"123XYZ\"" +
            "}," +
            "{" +
            "\"sideId\": \"\"," +
            "\"side\": \"Buyer\"," +
            "\"amount\": 150.00," +
            "\"price\": 7.3100," +
            "\"currency\": \"GBP\"," +
            "\"counterpartyId\": \"456abc\"" +
            "}," +
            "{" +
            "\"sideId\": \"3\"," +
            "\"side\": \"Buyer\"," +
            "\"amount\": 100.00," +
            "\"price\": 7.2500," +
            "\"currency\": \"GBP\"," +
            "\"counterpartyId\": \"789opv\"" +
            "}]" +
            "}" +
            "}";

    private static final String JSON_TRADE_DATA_MISSING_SIDE = "{" +
            "\"schemaVersion\": \"0.123\"," +
            "\"documentVersion\": 6," +
            "\"environment\": \"BETA\"," +
            "\"trade\": {" +
            "\"tradeId\": \"51849291\"," +
            "\"tradeStatus\": \"NEW\"," +
            "\"tradeDate\": \"2021-05-17T21:11:17.265000+01:00\"," +
            "\"tradeSides\": [{" +
            "\"sideId\": \"1\"," +
            "\"side\": \"Seller\"," +
            "\"amount\": 250.00," +
            "\"price\": 10.2600," +
            "\"currency\": \"USD\"," +
            "\"counterpartyId\": \"123XYZ\"" +
            "}," +
            "{" +
            "\"sideId\": \"2\"," +
            "\"amount\": 150.00," +
            "\"price\": 7.3100," +
            "\"currency\": \"GBP\"," +
            "\"counterpartyId\": \"456abc\"" +
            "}," +
            "{" +
            "\"sideId\": \"3\"," +
            "\"side\": \"Buyer\"," +
            "\"amount\": 100.00," +
            "\"price\": 7.2500," +
            "\"currency\": \"GBP\"," +
            "\"counterpartyId\": \"789opv\"" +
            "}]" +
            "}" +
            "}";


    private static final String JSON_TRADE_DATA_EMPTY_TRADE_SIDES = "{" +
            "\"schemaVersion\": \"0.123\"," +
            "\"documentVersion\": 6," +
            "\"environment\": \"BETA\"," +
            "\"trade\": {" +
            "\"tradeId\": \"51849291\"," +
            "\"tradeStatus\": \"NEW\"," +
            "\"tradeDate\": \"2021-05-17T21:11:17.265000+01:00\"," +
            "\"tradeSides\": []" +
            "}" +
            "}";

    private static final String JSON_TRADE_NO_TRADE_DATA = "{" +
            "\"schemaVersion\": \"0.123\"," +
            "\"documentVersion\": 6," +
            "\"environment\": \"BETA\"," +
            "\"trade\": {" +
            "\"tradeId\": \"51849291\"," +
            "\"tradeStatus\": \"NEW\"," +
            "\"tradeDate\": \"2021-05-17T21:11:17.265000+01:00\"," +
            "\"tradeSides\": []" +
            "}" +
            "}";

}