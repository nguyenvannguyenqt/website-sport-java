package com.nhom07.DAMH_LTUD.repository;

import com.nhom07.DAMH_LTUD.model.OrderInfo;
import com.nhom07.DAMH_LTUD.model.VnpayResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

public interface IVnPayService {
    String initialPayment(HttpServletRequest request,OrderInfo model);
    VnpayResponse executePayment(Map<String, String> parameterMap);
}
