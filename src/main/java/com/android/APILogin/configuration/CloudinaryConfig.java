package com.android.APILogin.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary getCloudinary() {
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", "locdinhle");
        config.put("api_key", "453485589114544");
        config.put("api_secret", "OsPuxwBG-h_j_zZ4Ge-u52jGc64");
        config.put("secure", true);
        return new Cloudinary(config);
    }
}
