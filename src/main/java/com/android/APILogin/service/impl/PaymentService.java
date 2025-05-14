package com.android.APILogin.service.impl;

import com.android.APILogin.configuration.VNPayConfig;
import com.android.APILogin.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final VNPayConfig vnpayConfig;
    public String payOrder(String orderId, double amount, String orderInfo) {
        Map<String, String> vnp_HashFields = new HashMap<>();
        Map<String, String> vnp_Params = vnpayConfig.getVNPayConfig();
        vnp_Params.put("vnp_Amount", String.valueOf((int)amount * 100));
        vnp_HashFields.put("vnp_Amount", String.valueOf((int)amount * 100));
        vnp_Params.put("vnp_IpAddr", VNPayUtil.getIpAddress2());
        vnp_Params.put("vnp_TxnRef",  orderId);
        vnp_Params.put("vnp_OrderInfo", orderInfo.isEmpty() ? "Thanh toan don hang:" + orderId : orderInfo);
        vnp_HashFields.put("vnp_OrderInfo", orderInfo.isEmpty() ? "Thanh toan don hang:" + orderId : orderInfo);

        String queryUrl = VNPayUtil.getPaymentURL(vnp_Params, true);
        String hashData = VNPayUtil.getPaymentURL(vnp_Params, false);

        String vnpSecureHash = VNPayUtil.hmacSHA512(vnpayConfig.secretKey, hashData);
        System.out.println(vnpSecureHash);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        return vnpayConfig.vnp_PayUrl + "?" + queryUrl;
    }

//    public int orderReturn(HttpServletRequest request){
//        Map fields = new HashMap();
//        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
//            String fieldName = null;
//            String fieldValue = null;
//            try {
//                fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
//                fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            if ((fieldValue != null) && (fieldValue.length() > 0)) {
//                fields.put(fieldName, fieldValue);
//            }
//        }
//
//        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
//        if (fields.containsKey("vnp_SecureHashType")) {
//            fields.remove("vnp_SecureHashType");
//        }
//        if (fields.containsKey("vnp_SecureHash")) {
//            fields.remove("vnp_SecureHash");
//        }
//        String signValue = vnpayConfig.hashAllFields(fields);
//        if (signValue.equals(vnp_SecureHash)) {
//            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
//                return 1;
//            } else {
//                return 0;
//            }
//        } else {
//            return -1;
//        }
//    }
}
