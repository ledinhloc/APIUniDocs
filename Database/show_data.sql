SELECT con_id, GROUP_CONCAT(user_id ORDER BY user_id) AS user_ids
FROM Participant
GROUP BY con_id;



-- 1. Hiển thị danh sách tất cả các table
SHOW TABLES;

-- 2. Xem dữ liệu từng table cụ thể

-- Bảng người dùng
SELECT * FROM user LIMIT 4;

-- Bảng danh mục
SELECT * FROM category LIMIT 4;

-- Bảng tài liệu
SELECT * FROM document LIMIT 4;

-- Bảng giỏ hàng
SELECT * FROM cart LIMIT 4;

-- Bảng phương thức thanh toán
SELECT * FROM payment_method LIMIT 4;

-- Bảng đơn hàng
SELECT * FROM orders LIMIT 4;

-- Bảng chi tiết đơn hàng
SELECT * FROM order_detail LIMIT 4;

-- Bảng khuyến mãi
SELECT * FROM discount LIMIT 4;

-- Bảng đánh giá
SELECT * FROM review LIMIT 4;

-- Bảng tiêu chí đánh giá
SELECT * FROM evaluation_criteria LIMIT 4;

-- Bảng chi tiết đánh giá
SELECT * FROM review_criterial LIMIT 4;

-- Bảng hội thoại
SELECT * FROM conversation LIMIT 4;

-- Bảng thành viên hội thoại
SELECT * FROM participant LIMIT 4;

-- Bảng tin nhắn
SELECT * FROM chat_line LIMIT 4;

-- Bảng file tài liệu
SELECT * FROM file_document LIMIT 4;

-- Bảng thông báo
SELECT * FROM notification LIMIT 4;

-- Bảng thông báo người dùng
SELECT * FROM user_notifi LIMIT 4;

-- Bảng OTP
SELECT * FROM otp LIMIT 4;