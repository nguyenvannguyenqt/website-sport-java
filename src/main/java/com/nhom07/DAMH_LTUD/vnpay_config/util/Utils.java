package com.nhom07.DAMH_LTUD.vnpay_config.util;

        import jakarta.servlet.http.HttpServletRequest;

        import javax.crypto.Mac;
        import javax.crypto.spec.SecretKeySpec;
        import java.nio.charset.StandardCharsets;
        import java.util.Formatter;

public class Utils {
    public static String hmacSHA512(String key, String inputData) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            Mac mac = Mac.getInstance("HmacSHA512");
            mac.init(secretKeySpec);

            byte[] hashBytes = mac.doFinal(inputData.getBytes(StandardCharsets.UTF_8));

            try (Formatter formatter = new Formatter()) {
                for (byte b : hashBytes) {
                    formatter.format("%02x", b);
                }
                return formatter.toString();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate HMAC SHA512", e);
        }
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ipAddress)) { //Địa chỉ IP 0:0:0:0:0:0:0:1 là địa chỉ IPv6 loopback
            ipAddress = "127.0.0.1"; // Chuyển đổi IPv6 loopback sang IPv4
        }
        return ipAddress;
    }
}
