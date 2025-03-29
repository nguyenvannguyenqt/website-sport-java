package com.nhom07.DAMH_LTUD.service;

import com.nhom07.DAMH_LTUD.model.OrderInfo;
import com.nhom07.DAMH_LTUD.model.VnpayResponse;
import com.nhom07.DAMH_LTUD.repository.IVnPayService;
import com.nhom07.DAMH_LTUD.vnpay_config.VnPayLibrary;
import com.nhom07.DAMH_LTUD.vnpay_config.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class VnPayService implements IVnPayService {

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
        vnpay.addRequestData("vnp_Version", version);
        vnpay.addRequestData("vnp_Command", command);
        vnpay.addRequestData("vnp_TmnCode", tmnCode);
        vnpay.addRequestData("vnp_Amount", String.valueOf((long)(model.getTotalPrice() * 100)));
        vnpay.addRequestData("vnp_CreateDate", model.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        vnpay.addRequestData("vnp_CurrCode", currCode);
        vnpay.addRequestData("vnp_IpAddr", Utils.getIpAddress(request));
        vnpay.addRequestData("vnp_Locale", locale);
        vnpay.addRequestData("vnp_OrderInfo", "Thanh toan don hang:" + model.getOrderId());
        vnpay.addRequestData("vnp_OrderType", "other");
        vnpay.addRequestData("vnp_ReturnUrl", paymentBackReturnUrl);
        vnpay.addRequestData("vnp_TxnRef", tick);

        String createDate = model.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String ipAddr = Utils.getIpAddress(request);
        String orderInfo = "Thanh toan don hang:" + model.getOrderId();
        String amount = String.valueOf(model.getTotalPrice() * 100);

        System.out.println("vnp_CreateDate: " + createDate);
        System.out.println("vnp_IpAddr: " + ipAddr);
        System.out.println("vnp_OrderInfo: " + orderInfo);
        System.out.println("vnp_Amount: " + amount);
        return vnpay.createRequestUrl(baseUrl, hashSecret);
    }

    @Override
    public VnpayResponse executePayment(Map<String, String> parameterMap) {
        VnPayLibrary vnpay = new VnPayLibrary();

        parameterMap.forEach((key, value) -> {
            if (key != null && key.startsWith("vnp_")) {
                vnpay.addResponseData(key, value);
            }
        });

        long vnp_orderId = Long.parseLong(vnpay.getResponseData("vnp_TxnRef"));
        long vnp_TransactionId = Long.parseLong(vnpay.getResponseData("vnp_TransactionNo"));
        String vnp_ResponseCode = vnpay.getResponseData("vnp_ResponseCode");

        // Lấy vnp_SecureHash từ parameterMap
        String vnp_SecureHash = parameterMap.get("vnp_SecureHash");
        String vnp_OrderInfo = vnpay.getResponseData("vnp_OrderInfo");

        boolean checkSignature = vnpay.validateSignature(vnp_SecureHash, hashSecret);

        if (!checkSignature) {
            return new VnpayResponse(false);
        }

        return new VnpayResponse(true, vnp_OrderInfo, String.valueOf(vnp_orderId), String.valueOf(vnp_TransactionId), vnp_SecureHash, vnp_ResponseCode);
    }
}