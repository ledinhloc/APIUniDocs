package com.android.APILogin.controller;

import com.android.APILogin.configuration.VNPayConfig;
import com.android.APILogin.dto.request.CreateOrderFromCartRequest;
import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.entity.Order;
import com.android.APILogin.service.OrderService;
import com.android.APILogin.service.impl.PaymentService;
import com.android.APILogin.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final VNPayConfig vNPayConfig;
    private final OrderService orderService;

    @GetMapping("/vn-pay-callback")
    public String vnPayCallback(HttpServletRequest request, Model model){
//        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "thanh toan thanh cong");
        int paymentStatus = orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        // 1. Parse chuỗi gốc
        DateTimeFormatter inputFmt  = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime dateTime      = LocalDateTime.parse(paymentTime, inputFmt);

        // 2. Định nghĩa formatter đầu ra yyyy/MM/dd - HH:mm:ss
        DateTimeFormatter outputFmt = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss");
        String formattedDateTime    = dateTime.format(outputFmt);

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", formattedDateTime);
        model.addAttribute("transactionId", transactionId);

        if(paymentStatus == 1){
            orderService.handlePaymentSuccess(Long.valueOf(orderInfo));
            return "payment/orderSuccess";
        }

        return "payment/orderFail";
    }

    /**
     * Xử lý callback VNPay, kiểm tra checksum, nội dung đơn hàng,
     * cập nhật trạng thái và trả về 1 nếu thành công, ngược lại 0.
     */
    private int orderReturn(HttpServletRequest request) {
        try {
            // 1. Đọc tất cả params và URL-encode
            Map<String, String> fields = new HashMap<>();
            Enumeration<String> params = request.getParameterNames();
            while (params.hasMoreElements()) {
                String name = params.nextElement();
                String value = URLEncoder.encode(
                        request.getParameter(name),
                        StandardCharsets.US_ASCII.toString()
                );
                if (value != null && !value.isEmpty()) {
                    fields.put(name, value);
                }
            }

            // 2. Lưu và loại bỏ tham số checksum
            String vnpSecureHash = request.getParameter("vnp_SecureHash");
            fields.remove("vnp_SecureHash");
            fields.remove("vnp_SecureHashType");

            // 3. Tạo chữ ký và so sánh
            String computedHash = VNPayUtil.hashAllFields(fields, vNPayConfig.getSecretKey());
            if (!computedHash.equals(vnpSecureHash)) {
                // checksum không hợp lệ
                return 0;
            }

            // 4. Lấy thông tin đơn hàng
            String orderId = request.getParameter("vnp_TxnRef");
            double amount   = Double.parseDouble(request.getParameter("vnp_Amount"));
            String respCode = request.getParameter("vnp_ResponseCode");

            // 5. Kiểm tra orderId, số tiền, trạng thái đang chờ
//            if (!paymentService.checkOrderId(orderId)) {
//                return 0;
//            }
//            if (!paymentService.checkAmount(orderId, amount)) {
//                return 0;
//            }
//            if (!paymentService.checkOrderPendingStatus(orderId)) {
//                return 0;
//            }

            // 6. Xử lý thành công / thất bại
            if ("00".equals(respCode)) {
                // thanh toán OK
//                paymentService.completeOrder(orderId);
                return 1;
            } else {
                // thanh toán lỗi (VD: user hủy hoặc từ chối)
//                paymentService.updateOrderStatus(orderId, OrderStatus.UNPAID);
                return 0;
            }
        } catch (Exception ex) {
            // bất kỳ lỗi nào khác
            ex.printStackTrace();
            return 0;
        }
    }

//    @GetMapping("/{orderId}")
//    @ResponseBody
//    public ResponseEntity<Map<String,String>> createPayment(
//            @PathVariable String orderId,
//            @RequestParam double amount,
//            @RequestParam(required = false, defaultValue = "") String info) {
//        // Sinh URL
//        String url = paymentService.payOrder(orderId, amount, info);
//        // Trả về JSON chứa URL
//        Map<String,String> body = Collections.singletonMap("paymentUrl", url);
//        return ResponseEntity.ok(body);
//    }

    @PostMapping("/")
    @ResponseBody
    public ResponseData<Map<String,String>> createPayment(@RequestBody CreateOrderFromCartRequest request) {
        //tao order
        Order order = orderService.createOrderFromCart(request);
        // Sinh URL
        String url = paymentService.payOrder(String.valueOf(order.getOrderId()), order.getTotalSellPrice(), order.getOrderId().toString());
        // Trả về JSON chứa URL
        Map<String,String> body = Collections.singletonMap("paymentUrl", url);
        return new ResponseData<>(HttpStatus.OK.value(), "success", body);
    }

//    @PostMapping("/create")
//    public ResponseData<Long> createOrderFromCart(@RequestBody CreateOrderFromCartRequest request) {
//        Order order = orderService.createOrderFromCart(request);
//        return new ResponseData<>(HttpStatus.OK.value(),"success",order.getOrderId()) ;
//    }

    @GetMapping("/demo")
    public String demo(Model model) {
        return "payment/orderSuccess";
    }
}
