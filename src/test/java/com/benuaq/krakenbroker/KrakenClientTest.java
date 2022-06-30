package com.benuaq.krakenbroker;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KrakenClientTest {
    
    @Test
    void shouldRetrieveStatus() {
        // given
        KrakenSignService signService = new KrakenSignService(BrokerConfiguration.KEY_PRIVATE);
        KrakenClient client = new KrakenClient(BrokerConfiguration.KEY_PUBLIC, signService);

        // when
        String status = client.getStatus().block();

        // then
        log.info("STATUS : " + status);
    }

    @Test
    void shouldRetrieveBalance() {
        // given
        KrakenSignService signService = new KrakenSignService(BrokerConfiguration.KEY_PRIVATE);
        KrakenClient client = new KrakenClient(BrokerConfiguration.KEY_PUBLIC, signService);

        // when
        String balance = client.getBalance().block();

        // then
        log.info("BALANCE : " + balance);
    }
}
