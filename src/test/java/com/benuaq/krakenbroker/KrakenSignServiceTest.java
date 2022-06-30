package com.benuaq.krakenbroker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KrakenSignServiceTest {

    @Test
    void shouldReturnSignature() {
        // given
        KrakenSignService signService = new KrakenSignService("kQH5HW/8p1uGOVjbgWA7FunAmGO8lsSUXNsu3eow76sz84Q18fWxnyRzBHCd3pd5nE9qa99HAZtuZuj6F1huXg==");
        String uri = "/0/private/AddOrder";
        String nonce = "1616492376594";
        String data = "nonce=1616492376594&ordertype=limit&pair=XBTUSD&price=37500&type=buy&volume=1.25";

        // when
        String actualSignature = signService.sign(uri, nonce, data);

        // then
        String expectedSignature = "4/dpxb3iT4tp/ZCVEwSnEsLxx0bqyhLpdfOpc6fn7OR8+UClSV5n9E6aSS8MPtnRfp32bAb0nmbRn6H8ndwLUQ==";
        assertThat(actualSignature).isEqualTo(expectedSignature);
    }
}