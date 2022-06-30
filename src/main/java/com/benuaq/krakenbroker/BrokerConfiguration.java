package com.benuaq.krakenbroker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfiguration {

    public static final String KEY_PRIVATE = "tfQ1RvSvAE0bugbBww+jy/nvWIhNTnsb35KG8AFk6kAbTp2GeAhBzwm29MMYXbdnGZYKlgyqF9Sjm8Hah8TRmw==";
    public static final String KEY_PUBLIC = "NRK4nnWl1/DF0YyyXpmlAroHHGz6ohMX7im9qB/tnQ+MHog8LV4Kh2d1";
    
    @Bean
    KrakenSignService signService() {
        return new KrakenSignService(KEY_PRIVATE);
    }

    @Bean
    KrakenClient client(KrakenSignService signService) {
        return new KrakenClient(KEY_PUBLIC, signService);
    }

}
