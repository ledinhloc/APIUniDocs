package com.android.APILogin.controller;

import com.android.APILogin.configuration.VNPayConfig;
import com.android.APILogin.service.impl.PaymentService;
import com.android.APILogin.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private VNPayConfig vNPayConfig;

    @GetMapping("/vn-pay-callback")
    @ResponseBody
    public String vnPayCallback(HttpServletRequest request, Model model){
//        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "thanh toan thanh cong");
        int paymentStatus = orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus == 1 ? "orderSuccess" : "orderFail";
    }

    public int orderReturn(HttpServletRequest request) {
        Map<String, String> fields = new HashMap<>();

        // Bước 1: Lấy toàn bộ parameter gửi từ VNPay
        Map<String, String[]> paramMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            fields.put(entry.getKey(), entry.getValue()[0]);
        }

        // Bước 2: Lấy SecureHash gửi từ VNPay
        String vnp_SecureHash = fields.remove("vnp_SecureHash");
        String vnp_SecureHashType = fields.remove("vnp_SecureHashType"); // thường là SHA512, bỏ qua

        // Bước 3: Tạo lại chuỗi dữ liệu để hash
        String signData = VNPayUtil.hashAllFields(fields);

        // Bước 4: Tự mình hash với secretKey để kiểm tra
        String myHash = VNPayUtil.hmacSHA512(vNPayConfig.secretKey, signData);

        // Bước 5: So sánh SecureHash
        if (!myHash.equalsIgnoreCase(vnp_SecureHash)) {
            System.out.println("Sai checksum VNPay!");
            return -1; // Sai checksum => không tin cậy
        }

        // Bước 6: Đọc trạng thái giao dịch VNPay gửi về
        String responseCode = request.getParameter("vnp_ResponseCode");
        String transactionStatus = request.getParameter("vnp_TransactionStatus");

        // Bước 7: Nếu responseCode = "00" và transactionStatus = "00" => thanh toán thành công
        if ("00".equals(responseCode) && "00".equals(transactionStatus)) {
            return 1; // Thành công
        }

        return 0; // Thất bại
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<Map<String,String>> createPayment(
            @PathVariable String orderId,
            @RequestParam double amount,
            @RequestParam(required = false, defaultValue = "") String info) {
        // Sinh URL
        String url = paymentService.payOrder(orderId, amount, info);
        // Trả về JSON chứa URL
        Map<String,String> body = Collections.singletonMap("paymentUrl", url);
        return ResponseEntity.ok(body);
    }
}
