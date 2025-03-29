package com.nhom07.DAMH_LTUD.vnpay_config;
import com.nhom07.DAMH_LTUD.vnpay_config.util.Utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class VnPayLibrary {

    private final SortedMap<String, String> requestData = new TreeMap<>(new VnPayCompare());
    private final SortedMap<String, String> responseData = new TreeMap<>(new VnPayCompare());

    public void addRequestData(String key, String value) {
        if (value != null && !value.isEmpty()) {
            requestData.put(key, value);
        }
    }

    public void addResponseData(String key, String value) {
        if (value != null && !value.isEmpty()) {
            responseData.put(key, value);
        }
    }

    public String getResponseData(String key) {
        return responseData.getOrDefault(key, "");
    }

    public String createRequestUrl(String baseUrl, String vnpHashSecret) {
        StringBuilder data = new StringBuilder();

        for (Map.Entry<String, String> entry : requestData.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                data.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8))
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                        .append("&");
            }
        }

        String querystring = data.toString();
        baseUrl += "?" + querystring;

        String signData = querystring;
        if (signData.length() > 0) {
            signData = signData.substring(0, data.length() - 1);
        }

        String vnpSecureHash = Utils.hmacSHA512(vnpHashSecret, signData);
        baseUrl += "vnp_SecureHash=" + vnpSecureHash;

        return baseUrl;
    }

    public boolean validateSignature(String inputHash, String secretKey) {
        String rspRaw = getResponseData();
        String myChecksum = Utils.hmacSHA512(secretKey, rspRaw);
        return myChecksum.equalsIgnoreCase(inputHash);
    }

    private String getResponseData() {
        StringBuilder data = new StringBuilder();
        responseData.remove("vnp_SecureHashType");
        responseData.remove("vnp_SecureHash");

        for (Map.Entry<String, String> entry : responseData.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                data.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8))
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                        .append("&");
            }
        }

        if (data.length() > 0) {
            data.deleteCharAt(data.length() - 1);
        }

        return data.toString();
    }

    static class VnPayCompare implements Comparator<String> {
        @Override
        public int compare(String x, String y) {
            if (x.equals(y)) return 0;
            if(x == null) return -1;
            if (y == null) return 1;
            return x.compareTo(y);
        }
    }
}