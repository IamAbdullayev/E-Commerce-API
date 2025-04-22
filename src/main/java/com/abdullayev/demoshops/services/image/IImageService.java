package com.abdullayev.demoshops.services.image;

import com.abdullayev.demoshops.dto.ImageDto;
import com.abdullayev.demoshops.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);
    void deleteImageById(Long id);
    Image getImageById(Long id);
}
