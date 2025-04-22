package com.abdullayev.demoshops.services.image;

import com.abdullayev.demoshops.dto.ImageDto;
import com.abdullayev.demoshops.exceptions.ImageNotFoundException;
import com.abdullayev.demoshops.models.Image;
import com.abdullayev.demoshops.models.Product;
import com.abdullayev.demoshops.repositories.ImageRepository;
import com.abdullayev.demoshops.services.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImagesDto = new ArrayList<>();

        for (MultipartFile file : files) {
            Image savedImage = imageRepository.save(createImage(file, product));

            savedImage.setDownloadUrl("/api/v1/images/image/download/" + savedImage.getId());
            imageRepository.save(savedImage);

            savedImagesDto.add(createImageDto(savedImage));
        }

        return savedImagesDto;
    }

    private Image createImage(MultipartFile file, Product product) {
        Image image = new Image();
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            image.setProduct(product);

            String buildDownloadUrl = "/api/v1/images/image/download/";
            String downloadUrl = buildDownloadUrl + image.getId();
            image.setDownloadUrl(downloadUrl);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return image;
    }

    private ImageDto createImageDto(Image savedImage) {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(savedImage.getId());
        imageDto.setFileName(savedImage.getFileName());
        imageDto.setDownloadUrl(savedImage.getDownloadUrl());
        return imageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image existingImage = getImageById(imageId);
        try {
            existingImage.setFileName(file.getOriginalFilename());
            existingImage.setFileType(file.getContentType());
            existingImage.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(existingImage);
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Failed to update image: " + e.getMessage());
        }
    }

    @Override
    public void deleteImageById(Long id) {
        Image image = getImageById(id);
        imageRepository.delete(image);
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException("Image not found!"));
    }
}
