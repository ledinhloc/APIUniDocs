use uni_docs;
-- 1. User (Thêm vai trò sinh viên, giảng viên)
INSERT INTO `user` (`user_id`, `address`, `avatar`, `dob`, `email`, `gender`, `is_active`, `last_online`, `name`, `password`, `phone`, `role`, `status`) VALUES
                                                                                                                                                             (1, '227 Nguyễn Văn Cừ, Q.5', 'avatar1.jpg', '2000-05-15', '2150001@student.hcmus.edu.vn', 'Male', b'1', NOW(), 'Nguyễn Văn A', 'sv123', '0912345678', 'USER', 'ONLINE'),
                                                                                                                                                             (2, 'Phòng 201 KTX ĐH Bách Khoa', 'avatar2.png', '2001-02-20', '2150002@student.hcmus.edu.vn', 'Female', b'1', NOW(), 'Trần Thị B', 'sv456', '0987654321', 'USER', 'ONLINE'),
                                                                                                                                                             (3, 'Khoa CNTT - ĐH KHTN', 'avatar_prof.jpg', '1980-11-10', 'prof.long@hcmus.edu.vn', 'Male', b'1', NOW(), 'GS. Trần Văn Long', 'gv123', '0903123456', 'USER', 'OFFLINE'),
                                                                                                                                                             (4, 'Phòng 101 Nhà Điều Hành', 'admin.png', '1990-07-01', 'admin@uni.com', 'Female', b'1', NOW(), 'Quản Trị Viên', 'admin123', '0283822101', 'ADMIN', 'ONLINE');

-- 2. Category (Chuyên ngành đại học)
INSERT INTO `category` (`cate_id`, `cate_desc`, `cate_icon`, `cate_name`) VALUES
                                                                              (1, 'Tài liệu môn Cơ sở lập trình', 'cslt.png', 'CSLT'),
                                                                              (2, 'Tài liệu Toán cao cấp', 'toan.png', 'Toán Đại Cương'),
                                                                              (3, 'Đề thi các năm', 'exam.png', 'Đề Thi'),
                                                                              (4, 'Bài giảng Vật lý đại cương', 'physics.png', 'Vật Lý');

-- 3. Document (Tài liệu học thuật)
INSERT INTO `document` (`doc_id`, `created_at`, `doc_desc`, `doc_image_url`, `doc_name`, `doc_page`, `download`, `max_quantity`, `original_price`, `sell_price`, `type`, `view`, `cate_id`, `user_id`) VALUES
                                                                                                                                                                                           (1, NOW(), 'Bài giảng chương 1-4', 'cslt_ch1.jpg', 'CSLT - Chương 1: C++ Basics', 45, 120, 50, 0.0, 10000.0, 'DIGITAL', 300, 1, 3),
                                                                                                                                                                                           (2, NOW(), 'Đề thi 2023 có đáp án', 'de_thi_2023.jpg', 'Đề Thi Giữa Kỳ CSLT 2023', 10, 450, 50, 5000.0, 2000.0, 'DIGITAL', 1500, 3, 2),
                                                                                                                                                                                           (3, NOW(), 'Slide bài giảng full', 'physics_slide.jpg', 'Vật Lý Điện Từ Học', 120, 80, 50, 15000.0, 10000.0, 'BOTH', 200, 4, 3),
                                                                                                                                                                                           (4, NOW(), 'Tổng hợp công thức', 'math_cheat_sheet.jpg', 'Cheat Sheet Toán A1', 20, 300, 50, 0.0, 5000.0, 'DIGITAL', 800, 2, 1);

-- 4. Cart (Giỏ hàng sinh viên)
INSERT INTO `cart` (`cart_id`, `quantity`, `doc_id`, `user_id`) VALUES
                                                                    (1, 1, 2, 1),
                                                                    (2, 2, 4, 1),
                                                                    (3, 1, 3, 2),
                                                                    (4, 3, 1, 4);

-- 5. Payment Method (Phương thức thanh toán VN)
INSERT INTO `payment_method` (`method_id`, `method_name`) VALUES
                                                              (1, 'Momo'),
                                                              (2, 'ZaloPay'),
                                                              (3, 'Thẻ ATM'),
                                                              (4, 'Chuyển Khoản');

-- 6. Orders (Đơn hàng thực tế)
INSERT INTO `orders` (`order_id`, `note`, `order_at`, `order_status`, `total_original_price`, `total_sell_price`, `method_id`, `user_id`) VALUES
                                                                                                                                              (1, 'Gửi qua email sinh viên', NOW(), 'DELIVERED', 20000.0, 14000.0, 1, 1),
                                                                                                                                              (2, 'In màu bản cứng', NOW(), 'SHIPPED', 30000.0, 30000.0, 3, 2),
                                                                                                                                              (3, 'Cần gấp trước 18h', NOW(), 'PENDING', 5000.0, 5000.0, 2, 1),
                                                                                                                                              (4, 'Xuất hóa đơn đỏ', NOW(), 'CONFIRMED', 10000.0, 10000.0, 4, 4);

-- 7. Order Detail (Chi tiết đơn)
INSERT INTO `order_detail` (`id`, `original_price`, `quantity`, `sell_price`, `doc_id`, `order_id`) VALUES
                                                                                                        (1, 2000.0, 2, 2000.0, 2, 1),
                                                                                                        (2, 10000.0, 1, 10000.0, 3, 2),
                                                                                                        (3, 5000.0, 1, 5000.0, 4, 3),
                                                                                                        (4, 10000.0, 1, 10000.0, 1, 4);

-- 8. Discount (Khuyến mãi sinh viên)
INSERT INTO `discount` (`discount_id`, `discount_name`, `discount_type`, `discount_value`, `end_at`, `max_price`, `min_price`, `scope`, `scope_id`, `start_date`, `status`, `update_at`, `usage_limit`, `used_count`, `user_id`) VALUES
                                                                                                                                                                                                                                     (1, 'SINHVIEN20', 'PERCENT', 20.0, '2024-12-31', 20000.0, 10000.0, 'CATEGORY', 1, NOW(), 'ACTIVE', NOW(), 1000, 234, 4),
                                                                                                                                                                                                                                     (2, 'FIRSTBUY', 'FIXED', 5000.0, '2024-06-30', NULL, 0.0, 'SHOP', NULL, NOW(), 'ACTIVE', NOW(), 500, 89, 4),
                                                                                                                                                                                                                                     (3, 'THITHU', 'PERCENT', 50.0, '2024-05-30', 10000.0, 0.0, 'DOCUMENT', 2, NOW(), 'EXPIRED', NOW(), 200, 200, 3),
                                                                                                                                                                                                                                     (4, 'HOCKYMOI', 'FIXED', 10000.0, '2024-09-01', 50000.0, 20000.0, 'CATEGORY', 4, NOW(), 'ACTIVE', NOW(), 300, 45, 4);

-- 9. Review (Đánh giá tài liệu)
INSERT INTO `review` (`review_id`, `content`, `created_at`, `rate`, `updated_at`, `doc_id`, `user_id`) VALUES
                                                                                                           (1, 'Tài liệu chi tiết, đáng mua!', NOW(), 5, NULL, 1, 1),
                                                                                                           (2, 'Đề thi sát với chương trình', NOW(), 4, NULL, 2, 2),
                                                                                                           (3, 'Slide trình bày đẹp nhưng hơi dài', NOW(), 3, NULL, 3, 4),
                                                                                                           (4, 'Cheat sheet tiện lợi', NOW(), 5, NULL, 4, 1);

-- 10. Evaluation Criteria (Tiêu chí đánh giá)
INSERT INTO `evaluation_criteria` (`criteria_id`, `description`, `name`) VALUES
                                                                             (1, 'Chất lượng nội dung', 'Nội Dung'),
                                                                             (2, 'Hình thức trình bày', 'Trình Bày'),
                                                                             (3, 'Độ chính xác', 'Chính Xác'),
                                                                             (4, 'Giá trị thực tiễn', 'Ứng Dụng');

-- 11. Review Criterial (Đánh giá chi tiết)
INSERT INTO `review_criterial` (`id`, `content`, `criteria_id`, `review_id`) VALUES
                                                                                (1, 'tot', 1, 1),
                                                                                (2, 'aa', 3, 1),
                                                                                (3, '5ss', 2, 4),
                                                                                (4, '3ff', 4, 3);

-- 12. Conversation (Nhóm trao đổi học tập)
INSERT INTO `conversation` (`con_id`, `background_url`, `con_name`, `created_at`, `image`, `is_group`, `num_member`, `theme_color`) VALUES
                                                                                                                                        (1, NULL, 'Nhóm CSLT K24', NOW(), 'cslt_group.jpg', b'1', 15, '#2196F3'),
                                                                                                                                        (2, NULL, 'Hỗ trợ mua tài liệu', NOW(), 'support.jpg', b'0', 2, '#4CAF50'),
                                                                                                                                        (3, NULL, 'Toán A1 Study', NOW(), 'math_study.png', b'1', 8, '#FFC107'),
                                                                                                                                        (4, NULL, 'Thảo luận Vật lý', NOW(), 'physics_chat.jpg', b'1', 12, '#9C27B0');

-- 13. Participant (Thành viên nhóm)
INSERT INTO `participant` (`id`, `join_at`, `left_at`, `con_id`, `user_id`) VALUES
                                                                                (1, NOW(), NOW(), 1, 1),
                                                                                (2, NOW(), NOW(), 1, 2),
                                                                                (3, NOW(), NOW(), 2, 4),
                                                                                (4, NOW(), NOW(), 3, 3);

-- 14. Chat Line (Tin nhắn học tập)
INSERT INTO `chat_line` (`chat_line_id`, `chat_status`, `chat_line_parent_id`, `content`, `send_at`, `con_id`, `user_id`) VALUES
                                                                                                                            (1, 'SEEN_BY_ALL', NULL, 'Ai có đề thi năm ngoái không?', NOW(), 1, 1),
                                                                                                                            (2, 'DELIVERED', NULL, 'Tôi cần hỗ trợ hoàn tiền', NOW(), 2, 4),
                                                                                                                            (3, 'SENT', NULL, 'Công thức tích phân trang bao nhiêu?', NOW(), 3, 2),
                                                                                                                            (4, 'SEEN_BY_SOME', NULL, 'Slide chương 2 có lỗi font', NOW(), 4, 3);

-- 15. File Document (File học liệu)
INSERT INTO `file_document` (`file_id`, `file_type`, `file_url`, `doc_id`) VALUES
                                                                               (1, 'PDF', 'https://drive.uni.com/cslt_ch1.pdf', 1),
                                                                               (2, 'DOCX', 'https://drive.uni.com/de_thi_2023.docx', 2),
                                                                               (3, 'PPTX', 'https://drive.uni.com/vatly.pptx', 3),
                                                                               (4, 'PDF', 'https://drive.uni.com/toan_cheatsheet.pdf', 4);

-- 16. Notification (Thông báo từ hệ thống)
INSERT INTO `notification` (`noti_id`, `content`, `created_at`, `title`, `type`) VALUES
                                                                                     (1, 'Đơn hàng #001 đã sẵn sàng', NOW(), 'Tải xuống thành công', 'DOWNLOAD'),
                                                                                     (2, 'Bạn có tin nhắn mới từ GS. Long', NOW(), 'Tin nhắn mới', 'MESSAGE'),
                                                                                     (3, 'Tài liệu Toán A1 vừa cập nhật', NOW(), 'Tài liệu mới', 'UPDATE'),
                                                                                     (4, 'Áp dụng mã GIAMGIA20', NOW(), 'Khuyến mãi', 'PROMOTION');

-- 17. User Notifi (Trạng thái đọc)
INSERT INTO `user_notifi` (`id`, `is_read`, `noti_id`, `user_id`) VALUES
                                                                      (1, b'1', 1, 1),
                                                                      (2, b'0', 2, 3),
                                                                      (3, b'1', 3, 2),
                                                                      (4, b'0', 4, 4);

-- 18. OTP (Xác thực sinh viên)
INSERT INTO `otp` (`otp_id`, `is_active`, `otp_expired`, `otp_num`, `user_id`) VALUES
                                                                                   (1, b'1', DATE_ADD(NOW(), INTERVAL 5 MINUTE), '198234', 1),
                                                                                   (2, b'0', DATE_ADD(NOW(), INTERVAL -2 MINUTE), '873412', 2),
                                                                                   (3, b'1', DATE_ADD(NOW(), INTERVAL 10 MINUTE), '562819', 3),
                                                                                   (4, b'1', DATE_ADD(NOW(), INTERVAL 7 MINUTE), '409567', 4);

-- 1. User (Thêm sinh viên và giảng viên)
INSERT INTO `user` (`user_id`, `address`, `avatar`, `dob`, `email`, `gender`, `is_active`, `last_online`, `name`, `password`, `phone`, `role`, `status`) VALUES
                                                                                                                                                             (5, 'KTX Khu A ĐHQG', 'avatar5.jpg', '2002-09-03', '2150003@student.hcmus.edu.vn', 'Female', b'1', NOW(), 'Lê Thị C', 'sv789', '0911222333', 'USER', 'ONLINE'),
                                                                                                                                                             (6, 'Khoa Toán - ĐH KHTN', 'avatar_prof2.jpg', '1975-08-12', 'prof.hien@hcmus.edu.vn', 'Female', b'1', NOW(), 'PGS. Nguyễn Thị Hiền', 'gv456', '0909988776', 'USER', 'OFFLINE'),
                                                                                                                                                             (7, '123 Lý Thường Kiệt', 'avatar7.png', '2000-12-25', '2150004@student.hcmus.edu.vn', 'Male', b'1', NOW(), 'Phạm Văn D', 'sv101', '0977123456', 'USER', 'ONLINE'),
                                                                                                                                                             (8, 'Phòng 303 Nhà A', 'admin2.jpg', '1985-04-18', 'admin2@uni.com', 'Male', b'1', NOW(), 'Phó Quản Trị', 'admin456', '0283822102', 'ADMIN', 'ONLINE');

-- 2. Category (Thêm chuyên ngành)
INSERT INTO `category` (`cate_id`, `cate_desc`, `cate_icon`, `cate_name`) VALUES
                                                                              (5, 'Tài liệu Hóa hữu cơ', 'chemistry.png', 'Hóa Học'),
                                                                              (6, 'Tài liệu Kinh tế học', 'economics.png', 'Kinh Tế'),
                                                                              (7, 'Bài tập Nguyên lý kế toán', 'accounting.png', 'Kế Toán'),
                                                                              (8, 'Tài liệu Văn học Việt Nam', 'literature.png', 'Văn Học');

-- 3. Document (Thêm tài liệu học thuật)
INSERT INTO `document` (`doc_id`, `created_at`, `doc_desc`, `doc_image_url`, `doc_name`, `doc_page`, `download`, `max_quantity`, `original_price`, `sell_price`, `type`, `view`, `cate_id`, `user_id`) VALUES
                                                                                                                                                                                           (5, NOW(), 'Tổng hợp phản ứng hóa hữu cơ', 'hoa_huu_co.jpg', 'Hóa Hữu Cơ Cơ Bản', 80, 90, 50, 8000.0, 5000.0, 'DIGITAL', 150, 5, 6),
                                                                                                                                                                                           (6, NOW(), 'Slide bài giảng full chương trình', 'kinh_te_vi_mo.jpg', 'Kinh Tế Vĩ Mô', 95, 200, 50, 12000.0, 8000.0, 'BOTH', 450, 6, 6),
                                                                                                                                                                                           (7, NOW(), 'Đề thi + đáp án 2024', 'nguyen_ly_ke_toan.jpg', 'Nguyên Lý Kế Toán', 15, 180, 50, 0.0, 3000.0, 'DIGITAL', 600, 7, 5),
                                                                                                                                                                                           (8, NOW(), 'Phân tích tác phẩm Chí Phèo', 'van_hoc.jpg', 'Văn Học 1945-1954', 45, 75, 50, 7000.0, 5000.0, 'PHYSICAL', 120, 8, 7);

-- 4. Cart (Thêm vào giỏ hàng)
INSERT INTO `cart` (`cart_id`, `quantity`, `doc_id`, `user_id`) VALUES
                                                                    (5, 1, 5, 5),
                                                                    (6, 2, 6, 7),
                                                                    (7, 1, 7, 5),
                                                                    (8, 3, 8, 8);

-- 5. Payment Method (Thêm phương thức)
INSERT INTO `payment_method` (`method_id`, `method_name`) VALUES
                                                              (5, 'VNPAY'),
                                                              (6, 'ShopeePay'),
                                                              (7, 'Thẻ Tín Dụng'),
                                                              (8, 'Tiền Mặt');

-- 6. Orders (Thêm đơn hàng)
INSERT INTO `orders` (`order_id`, `note`, `order_at`, `order_status`, `total_original_price`, `total_sell_price`, `method_id`, `user_id`) VALUES
                                                                                                                                              (5, 'Giao hàng nhanh', NOW(), 'DELIVERED', 15000.0, 15000.0, 5, 5),
                                                                                                                                              (6, 'In màu bìa cứng', NOW(), 'CONFIRMED', 24000.0, 16000.0, 6, 7),
                                                                                                                                              (7, 'Xuất hóa đơn VAT', NOW(), 'PENDING', 5000.0, 5000.0, 8, 8),
                                                                                                                                              (8, 'Ưu tiên giao sáng', NOW(), 'SHIPPED', 9000.0, 9000.0, 7, 5);

-- 7. Order Detail (Chi tiết đơn mới)
INSERT INTO `order_detail` (`id`, `original_price`, `quantity`, `sell_price`, `doc_id`, `order_id`) VALUES
                                                                                                        (5, 5000.0, 3, 5000.0, 5, 5),
                                                                                                        (6, 8000.0, 2, 8000.0, 6, 6),
                                                                                                        (7, 3000.0, 1, 3000.0, 7, 7),
                                                                                                        (8, 5000.0, 1, 5000.0, 8, 8);

-- 8. Discount (Mã giảm giá mới)
INSERT INTO `discount` (`discount_id`, `discount_name`, `discount_type`, `discount_value`, `end_at`, `max_price`, `min_price`, `scope`, `scope_id`, `start_date`, `status`, `update_at`, `usage_limit`, `used_count`, `user_id`) VALUES
                                                                                                                                                                                                                                     (5, 'HOCPHI2024', 'PERCENT', 15.0, '2024-12-01', 50000.0, 20000.0, 'SHOP', NULL, NOW(), 'ACTIVE', NOW(), 1000, 150, 8),
                                                                                                                                                                                                                                     (6, 'KETTHUCHOCPHAN', 'FIXED', 7000.0, '2024-07-15', NULL, 10000.0, 'DOCUMENT', 6, NOW(), 'ACTIVE', NOW(), 300, 92, 8),
                                                                                                                                                                                                                                     (7, 'SINHNHATTRUONG', 'PERCENT', 25.0, '2024-11-20', 30000.0, 0.0, 'CATEGORY', 8, NOW(), 'ACTIVE', NOW(), 500, 0, 4),
                                                                                                                                                                                                                                     (8, 'CUOIKY2024', 'FIXED', 10000.0, '2024-12-25', 50000.0, 30000.0, 'SHOP', NULL, NOW(), 'ACTIVE', NOW(), 200, 34, 8);

-- 9. Review (Đánh giá thêm)
INSERT INTO `review` (`review_id`, `content`, `created_at`, `rate`, `updated_at`, `doc_id`, `user_id`) VALUES
                                                                                                           (5, 'Rất hữu ích cho ôn thi', NOW(), 5, NULL, 5, 5),
                                                                                                           (6, 'Slide thiếu ví dụ thực tế', NOW(), 3, NULL, 6, 7),
                                                                                                           (7, 'Đề thi sát với đề cương', NOW(), 4, NULL, 7, 8),
                                                                                                           (8, 'Phân tích sâu sắc', NOW(), 5, NULL, 8, 5);

-- 10. Evaluation Criteria (Tiêu chí mới)
INSERT INTO `evaluation_criteria` (`criteria_id`, `description`, `name`) VALUES
                                                                             (5, 'Cập nhật kiến thức mới', 'Cập Nhật'),
                                                                             (6, 'Mức độ đầy đủ ví dụ', 'Ví Dụ'),
                                                                             (7, 'Độ khó phù hợp', 'Độ Khó'),
                                                                             (8, 'Hỗ trợ sau mua', 'Hỗ Trợ');

-- 11. Review Criterial (Đánh giá chi tiết)
INSERT INTO `review_criterial` (`id`, `content`, `criteria_id`, `review_id`) VALUES
                                                                                (5, 'tot', 5, 5),
                                                                                (6, '2as', 6, 6),
                                                                                (7, '5aa', 7, 7),
                                                                                (8, '4ff', 8, 8);

-- 12. Conversation (Nhóm mới)
INSERT INTO `conversation` (`con_id`, `background_url`, `con_name`, `created_at`, `image`, `is_group`, `num_member`, `theme_color`) VALUES
                                                                                                                                        (5, NULL, 'Hóa Hữu Cơ K23', NOW(), 'chemistry_group.jpg', b'1', 10, '#E91E63'),
                                                                                                                                        (6, NULL, 'Hỗ Trợ Kế Toán', NOW(), 'accounting_support.jpg', b'0', 2, '#795548'),
                                                                                                                                        (7, NULL, 'Văn Học Hiện Đại', NOW(), 'literature_chat.jpg', b'1', 7, '#3F51B5'),
                                                                                                                                        (8, NULL, 'Kinh Tế Phát Triển', NOW(), 'economics_discuss.png', b'1', 9, '#009688');

-- 13. Participant (Thành viên mới)
INSERT INTO `participant` (`id`, `join_at`, `left_at`, `con_id`, `user_id`) VALUES
                                                                                (5, NOW(), NOW(), 5, 5),
                                                                                (6, NOW(), NOW(), 6, 8),
                                                                                (7, NOW(), NOW(), 7, 7),
                                                                                (8, NOW(), NOW(), 8, 6);

-- 14. Chat Line (Tin nhắn mới)
INSERT INTO `chat_line` (`chat_line_id`, `chat_status`, `chat_line_parent_id`, `content`, `send_at`, `con_id`, `user_id`) VALUES
                                                                                                                            (5, 'DELIVERED', NULL, 'Ai có note chương 3 Hóa hữu cơ không?', NOW(), 5, 5),
                                                                                                                            (6, 'SEEN_BY_ALL', NULL, 'Cần hỗ trợ định khoản nghiệp vụ', NOW(), 6, 8),
                                                                                                                            (7, 'SENT', NULL, 'Bình luận về tác phẩm Vợ Nhặt', NOW(), 7, 7),
                                                                                                                            (8, 'EDITED', NULL, 'Thảo luận GDP 2024', NOW(), 8, 6);

-- 15. File Document (File mới)
INSERT INTO `file_document` (`file_id`, `file_type`, `file_url`, `doc_id`) VALUES
                                                                               (5, 'PDF', 'https://drive.uni.com/hoa_huu_co.pdf', 5),
                                                                               (6, 'PPTX', 'https://drive.uni.com/kinh_te_vimo.pptx', 6),
                                                                               (7, 'DOCX', 'https://drive.uni.com/ke_toan.docx', 7),
                                                                               (8, 'PDF', 'https://drive.uni.com/van_hoc.pdf', 8);

-- 16. Notification (Thông báo mới)
INSERT INTO `notification` (`noti_id`, `content`, `created_at`, `title`, `type`) VALUES
                                                                                     (5, 'Tài liệu Kế Toán vừa được cập nhật', NOW(), 'Cập Nhật Tài Liệu', 'UPDATE'),
                                                                                     (6, 'Đơn hàng #005 đã được xác nhận', NOW(), 'Xác Nhận Đơn Hàng', 'ORDER'),
                                                                                     (7, 'Mã GIAMGIA30 áp dụng đến 30/11', NOW(), 'Khuyến Mãi Mới', 'PROMOTION'),
                                                                                     (8, 'Nhận xét mới về tài liệu của bạn', NOW(), 'Phản Hồi Đánh Giá', 'REVIEW');

-- 17. User Notifi (Trạng thái mới)
INSERT INTO `user_notifi` (`id`, `is_read`, `noti_id`, `user_id`) VALUES
                                                                      (5, b'0', 5, 5),
                                                                      (6, b'1', 6, 7),
                                                                      (7, b'0', 7, 8),
                                                                      (8, b'1', 8, 6);

-- 18. OTP (Xác thực mới)
INSERT INTO `otp` (`otp_id`, `is_active`, `otp_expired`, `otp_num`, `user_id`) VALUES
                                                                                   (5, b'1', DATE_ADD(NOW(), INTERVAL 8 MINUTE), '673452', 5),
                                                                                   (6, b'0', DATE_ADD(NOW(), INTERVAL -5 MINUTE), '985634', 6),
                                                                                   (7, b'1', DATE_ADD(NOW(), INTERVAL 12 MINUTE), '234567', 7),
                                                                                   (8, b'1', DATE_ADD(NOW(), INTERVAL 6 MINUTE), '745689', 8);

-- 1. Bảng Reaction (Phản ứng tin nhắn)
INSERT INTO `reaction` (`reaction_id`, `icon_type`, `chatline_id`, `user_id`) VALUES
                                                                                  (1, 'LIKE', 1, 1),
                                                                                  (2, 'LOVE', 3, 2),
                                                                                  (3, 'WOW', 5, 5),
                                                                                  (4, 'LAUGH', 2, 4);

-- 2. Bảng Seen (Xác nhận đã xem)
INSERT INTO `seen` (`seen_id`, `seen_at`, `chat_line_id`, `user_id`) VALUES
                                                                        (1, NOW(), 1, 2),
                                                                        (2, NOW(), 3, 5),
                                                                        (3, NOW(), 5, 7),
                                                                        (4, NOW(), 2, 3);

-- 3. Bảng Question_Answer (Hỏi đáp tài liệu)
INSERT INTO `question_answer` (`qa_id`, `content`, `created_at`, `parent_qa_id`, `doc_id`, `user_id`) VALUES
                                                                                                          (1, 'Tài liệu này còn bản cập nhật mới không?', NOW(), 0, 1, 2),
                                                                                                          (2, 'Có thể share thêm ví dụ về phần này không?', NOW(), 1, 1, 3),
                                                                                                          (3, 'Đáp án câu 5 ở trang bao nhiêu?', NOW(), 0, 2, 5),
                                                                                                          (4, 'File có lỗi font chữ trang 10', NOW(), 0, 3, 7);

-- Sửa lại giá trị file_type cho phù hợp ENUM
INSERT INTO `file_media` (`file_id`, `file_type`, `file_url`, `review_id`) VALUES
                                                                               (1, 'PDF', 'https://drive.uni.com/loi_font.pdf', 4),
                                                                               (2, 'DOCX', 'https://drive.uni.com/bo_sung.docx', 2),
                                                                               (3, 'PDF', 'https://drive.uni.com/vi_du_them.pdf', 5),
                                                                               (4, 'PDF', 'https://drive.uni.com/cau_hoi.pdf', 3);

-- 5. Bảng File_Chatline (File trong chat)
INSERT INTO `file_chat_line` (`file_id`, `file_type`, `file_url`, `chat_line_id`) VALUES
                                                                                    (1, 'PDF', 'https://drive.uni.com/tailieu_them.pdf', 3),
                                                                                    (2, 'DOCX', 'https://drive.uni.com/dinh_khoan_mau.docx', 6),
                                                                                    (3, 'PDF', 'https://drive.uni.com/bang_cong_thuc.pdf', 5), -- Đổi IMAGE -> PDF và .jpg -> .pdf
                                                                                    (4, 'PDF', 'https://drive.uni.com/de_cuong_ontap.pdf', 8);

-- 6. Bảng Order_Discount (Áp dụng mã giảm giá)
INSERT INTO `order_discount` (`id`, `discount_amount`, `discount_id`, `order_id`) VALUES
                                                                                      (1, 2000.0, 1, 1),
                                                                                      (2, 5000.0, 2, 2),
                                                                                      (3, 3000.0, 3, 5),
                                                                                      (4, 7000.0, 6, 6);
-- 1. DocumentImage
INSERT INTO `document_image` (`image_id`, `doc_image_url`, `doc_id`) VALUES
                                                                                      (1, 'https://drive.google.com/file/d/1xkVuSSgp2DgidgeUdEjntLPC-oj6B4-0/view?usp=sharing', 1),
                                                                                      (2, 'https://drive.google.com/file/d/1kiHhyvtDc5R_pmsjaqM23Rv_DOOg-Ion/view?usp=sharing', 1);