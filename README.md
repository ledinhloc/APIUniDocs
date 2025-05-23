# APIUniDocs
## Setup
**1. Clone dự án**

```bash
git clone https://github.com/ledinhloc/APIUniDocs.git
```
**2. Tạo cơ sở dữ liệu database**
```bash
create database uni_docs
```
**3. Thay đổi tên người dùng và mật khẩu mysql theo cài đặt của bạn**

+ Mở `src/main/resources/application.yaml`
+ Thêm biến môi trường Database `DATASOURCE_NAME, DATASOURCE_PASS` 
+ Thêm biến môi trường Email `EMAIL, PASSWORD`
+ Thêm biến môi trường Cloudinary `CLOUDINARY_API_KEY, CLOUDINARY_API_SECRET, CLOUDINARY_CLOUD_NAME`

**4. Đường dẫn chạy dự án và test API**

+ Ứng dụng chạy trên <http://localhost:8080>
+ Swagger chạy trên <http://localhost:8080/swagger-ui/index.html#/>




## API Endpoint
### Users

| Method | Url                                       | Description                                   | Sample Valid Request Body                                                                                         |
|--------|-------------------------------------------|-----------------------------------------------|-------------------------------------------------------------------------------------------------------------------|
| POST   | /api/user/register                        | Đăng ký người dùng mới                        | `{"name": "Tran An","age": 25,"gender": "male","birthday": "1998-05-12","email": "tranan@gmail.com","password": "123"}` |
| POST   | /api/user/login                           | Đăng nhập người dùng                          | `{ "email": "user@gmail.com", "password": "rd123" }`                                                            |
| POST   | /api/user/forgot-password                 | Gửi yêu cầu quên mật khẩu qua email           | `email=user@gmail.com`                                                                                          |
| POST   | /api/user/verify-otp-for-activation       | Xác minh OTP để kích hoạt tài khoản người dùng | `{ "email": "user@gmail.com", "otp": "123456" }`                                                                |
| POST   | /api/user/verify-otp-for-password-reset   | Xác minh OTP để thay đổi mật khẩu người dùng  | `{ "email": "user@gmail.com", "otp": "123456", "newPassword": "123" }`                                            |
