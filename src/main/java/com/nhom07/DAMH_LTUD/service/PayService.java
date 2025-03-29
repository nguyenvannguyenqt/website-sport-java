package com.nhom07.DAMH_LTUD.service;

import com.nhom07.DAMH_LTUD.model.OrderInfo;
import com.nhom07.DAMH_LTUD.model.VnpayResponse;
import com.nhom07.DAMH_LTUD.repository.IVnPayService;
import com.nhom07.DAMH_LTUD.vnpay_config.VnPayLibrary;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class PayService implements IVnPayService {
    @Value("${vnpay.version}")
    private String version;

    @Value("${vnpay.command}")
    private String command;

    @Value("${vnpay.tmnCode}")
    private String tmnCode;

    @Value("${vnpay.hashSecret}")
    private String hashSecret;

    @Value("${vnpay.baseUrl}")
    private String baseUrl;

    @Value("${vnpay.currCode}")
    private String currCode;

    @Value("${vnpay.locale}")
    private String locale;

    @Value("${vnpay.paymentBackReturnUrl}")
    private String paymentBackReturnUrl;

    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ipAddress)) {
            ipAddress = "127.0.0.1"; // Chuyển đổi IPv6 loopback sang IPv4
        }
        return ipAddress;
    }
    @Override
    public String initialPayment(HttpServletRequest request, OrderInfo model) {
        String tick = String.valueOf(System.currentTimeMillis());
        VnPayLibrary vnpay = new VnPayLibrary();
        return vnpay.createRequestUrl(baseUrl, hashSecret);
    }

    @Override
    public VnpayResponse executePayment(Map<String, String> parameterMap) {
        return null;
    }

}
