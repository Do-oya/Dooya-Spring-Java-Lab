package tobyspring.hellospring;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class PaymentService {
    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        // 환율 가져오기
        BigDecimal exRate = getExRate(currency);

        // 금액 계산
        BigDecimal convertedAmount = getConvertedAmount(foreignCurrencyAmount, exRate);

        // 유효 시간 계산
        LocalDateTime validUtil = getValidUtil();

        return Payment.builder()
                .orderId(orderId)
                .currency(currency)
                .foreignCurrencyAmount(foreignCurrencyAmount)
                .exRate(exRate)
                .convertedAmount(convertedAmount)
                .validUntil(validUtil)
                .build();
    }

    private static BigDecimal getExRate(String currency) throws IOException {
        URL url = new URL("https://open.er-api.com/v6/latest/" + currency);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = br.lines().collect(Collectors.joining());
        br.close();

        ObjectMapper mapper = new ObjectMapper();
        ExRateData data = mapper.readValue(response, ExRateData.class);
        return data.rates().get("KRW");
    }

    private static BigDecimal getConvertedAmount(BigDecimal foreignCurrencyAmount, BigDecimal exRate) {
        return foreignCurrencyAmount.multiply(exRate);
    }

    private static LocalDateTime getValidUtil() {
        return LocalDateTime.now().plusMinutes(30);
    }
}
