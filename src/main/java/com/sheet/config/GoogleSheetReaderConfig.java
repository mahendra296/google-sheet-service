package com.sheet.config;


import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Arrays;

@Configuration
public class GoogleSheetReaderConfig {

    @Bean
    public GoogleCredentials getGoogleCredentials() throws IOException {
        return GoogleCredentials.fromStream(GoogleSheetReaderConfig.class.getResourceAsStream("/google-datasheet-service-account.json")).createScoped(Arrays.asList("https://www.googleapis.com/auth/analytics.readonly",
                "https://www.googleapis.com/auth/drive",
                "https://www.googleapis.com/auth/spreadsheets"));
    }

    @Bean
    public HttpCredentialsAdapter getGoogleCredentialsAdapter(GoogleCredentials googleCredentials) {
        return new HttpCredentialsAdapter(googleCredentials);
    }
}
