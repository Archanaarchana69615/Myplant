package com.example.library.service.imple;
import com.example.library.dto.ProductDto;
import com.example.library.model.Image;
import com.example.library.model.Product;
import com.example.library.repository.*;
import com.example.library.service.Productservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.library.utils.ImageUpload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductserviceImple implements Productservice {


    private ProductRepository productRepository;

    private ImageRepository imageRepository;

    private ImageUpload imageUpload;

    public ProductserviceImple(ProductRepository productRepository, ImageRepository imageRepository, ImageUpload imageUpload) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.imageUpload = imageUpload;
    }


//    @Override
//    public List<ProductDto> findAll() {
//        return data(productRepository.findAll());
//    }

    @Override
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = data(products);
        return productDtos;
    }

    @Override
    public ProductDto findById(Long id) {
        ProductDto productDto=new ProductDto();
        Product product = productRepository.getReferenceById(id);
        productDto.setId(product.getId());
        productDto.setDescription(product.getDescription());
        productDto.setName(product.getName());
        productDto.setSalesPrice(product.getSalePrice());
        productDto.setCostPrice(product.getCostPrice());
        productDto.setCategory(product.getCategory());
        productDto.setImage(product.getImages());
        return productDto;
    }

    @Override
    public List<ProductDto> findAllByOrder() {
        return null;
    }

    @Override
    public Product update(List<MultipartFile> imageProducts, ProductDto productDto) {
        try {
            long id = productDto.getId();
            Product productUpdate = productRepository.findById(id);
//            Product.productUpdate = imageRepository.findById(id);
            productUpdate.setCategory(productDto.getCategory());
            productUpdate.setName(productDto.getName());
            productUpdate.setDescription(productDto.getDescription());
            productUpdate.setSalePrice(productDto.getSalesPrice());
            productUpdate.setCostPrice(productDto.getCostPrice());
            productRepository.save(productUpdate);
            if (imageProducts != null && !imageProducts.isEmpty() && imageProducts.size() != 1) {
                List<Image> imagesList = new ArrayList<>();
                List<Image> image = imageRepository.findImageBy(id);
                int i = 0;
                for (MultipartFile imageProduct : imageProducts) {
                    String imageName = imageUpload.storeFile(imageProduct);
                    image.get(i).setName(imageName);
                    image.get(i).setProduct(productUpdate);
                    imageRepository.save(image.get(i));
                    imagesList.add(image.get(i));
                    i++;
                }
                productUpdate.setImages(imagesList);
            }

//            return productUpdate;
//        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


        @Override
        public Product save(List<MultipartFile> imageProducts, ProductDto productDto) {
        Product product = new Product();
        try {
//
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setSalePrice(productDto.getSalesPrice());
//            product.setCostPrice(productDto.getCostPrice());
            product.setCategory(productDto.getCategory());
            product.set_activated(true);
            Product savedProduct = productRepository.save(product);

            if (imageProducts == null) {
                product.setImages(null);
            } else {
                List<Image> imagesList = new ArrayList<>();
                for (MultipartFile imageProduct : imageProducts) {
                    Image image = new Image();
                    String imageName = imageUpload.storeFile(imageProduct);
                    image.setName(imageName);
                    image.setProduct(product);

                    imageRepository.save(image);
                    imagesList.add(image);
                }
                product.setImages(imagesList);
            }
            return savedProduct;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Product disable(long id) {
        Product product = productRepository.findById(id);
        if (product != null)
            product.set_activated(false);

        productRepository.save(product);
        return null;
    }

    @Override
    public Product enable(long id) {

        Product product = productRepository.findById(id);
        product.set_activated(true);
        product.set_deleted(false);
        productRepository.save(product);
        return null;
    }

    @Override
    public Product deleted(long id) {
        Product product = productRepository.findById(id);
        product.set_activated(true);
        product.set_deleted(false);
        productRepository.save(product);

        return null;
    }

    @Override
    public List<ProductDto> findAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = data(products);
        return productDtos;
    }


//    @Override
//    public List<ProductDto> findAllProducts() {
//        return null;
//    }




    public List<ProductDto> data(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setSalesPrice(product.getSalePrice());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setDescription(product.getDescription());
            productDto.setImage(product.getImages());
            productDto.setCategory(product.getCategory());
            productDto.setActivated(product.is_activated());
            productDtos.add(productDto);
        }
        return productDtos;
    }
}