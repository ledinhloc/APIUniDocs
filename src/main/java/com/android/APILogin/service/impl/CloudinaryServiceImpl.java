package com.android.APILogin.service.impl;

import com.android.APILogin.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    @Resource
    private Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file, String folderName) {
        try{
            //chứa các tùy chọn (options) cho Cloudinary
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            //chuyen thành byte va upload len cloudinary voi options
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);

            //lay publicId de tao ra https url
            String publicId = (String) uploadedFile.get("public_id");
            return cloudinary.url().secure(true).generate(publicId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
