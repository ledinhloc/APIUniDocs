package com.android.APILogin.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUtils {
    // Đường dẫn thư mục gốc bên ngoài dự án, phải được cấu hình trong application.properties, ví dụ:
    // external.upload.dir=D:/externalUploads
    @Value("${external.upload.dir}")
    private String externalBaseDir;

    /**
     * Lưu file được upload vào thư mục chỉ định.
     * Phương thức chỉ nhận đối tượng file và đường dẫn tương đối (relativeDir),
     * đường dẫn tuyệt đối sẽ được tạo ra dựa trên externalBaseDir (được cấu hình bên ngoài dự án).
     *
     * @param file        Đối tượng file dạng MultipartFile nhận từ client.
     * @param relativeDir Đường dẫn tương đối (ví dụ: "uploads" hoặc "uploads/subfolder") nơi file sẽ được lưu.
     * @return Đường dẫn tương đối đến file đã lưu (bao gồm tên file) để lưu trữ, ví dụ: "uploads/tenfile.docx".
     * @throws IOException Nếu có lỗi xảy ra khi lưu file.
     */
    public String saveFile(MultipartFile file, String relativeDir) throws IOException {
        // Xác định thư mục gốc từ externalBaseDir đã cấu hình.
        Path basePath = Paths.get(externalBaseDir);

        // Nếu relativeDir là null hoặc rỗng, sử dụng mặc định là rỗng.
        if (relativeDir == null) {
            relativeDir = "";
        }

        // Lấy tên file gốc và xử lý (ví dụ: loại bỏ khoảng trắng)
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.trim().isEmpty()) {
            throw new IOException("Tên file không được để trống!");
        }
        //String sanitizedFileName = originalFileName.replaceAll("\\s+", "_");

        // Khởi tạo tên file mới với timestamp + "_" đầu tiên.
        String fileName = System.currentTimeMillis() + "_" + originalFileName;

        // Xác định đường dẫn tương đối đến file (bao gồm thư mục và tên file)
        Path relativeFilePath = Paths.get(relativeDir, fileName);

        // Tạo đường dẫn tuyệt đối dựa trên thư mục gốc và đường dẫn tương đối.
        Path absoluteFilePath = basePath.resolve(relativeFilePath);

        // Tạo thư mục chứa file nếu chưa tồn tại.
        if (!Files.exists(absoluteFilePath.getParent())) {
            Files.createDirectories(absoluteFilePath.getParent());
        }

        // Lưu file tại đường dẫn tuyệt đối đã chỉ định.
        file.transferTo(absoluteFilePath.toFile());

        // Trả về đường dẫn tương đối của file đã lưu.
        return relativeFilePath.toString();
    }

//    public File getFile(String relativePath) {
//        // Xác định thư mục gốc từ externalBaseDir đã cấu hình.
//        Path basePath = Paths.get(externalBaseDir);
//        // Kết hợp với đường dẫn tương đối để tạo ra đường dẫn tuyệt đối
//        Path absoluteFilePath = basePath.resolve(relativePath);
//        File file = absoluteFilePath.toFile();
//
//        // Kiểm tra sự tồn tại của file
//        return file.exists() ? file : null;
//    }

    public Resource getFile(String relativePath) {
        // Chống Path Traversal Attack
        if (relativePath.contains("..")) {
            throw new SecurityException("Đường dẫn không hợp lệ!");
        }
        try {
            Path basePath = Paths.get(externalBaseDir);
            Path absolutePath = basePath.resolve(relativePath).normalize();

            // Tạo Resource từ đường dẫn
            Resource resource = new UrlResource(absolutePath.toUri());

            // Kiểm tra file tồn tại và có thể đọc
            if (!resource.exists() || !resource.isReadable()) {
                throw new FileNotFoundException("File không tồn tại: " + relativePath);
            }
            return resource;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
