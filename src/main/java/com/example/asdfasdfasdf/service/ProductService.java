package com.example.asdfasdfasdf.service;

import com.example.asdfasdfasdf.domain.Product;
import com.example.asdfasdfasdf.dto.ProductMyPriceRequestDto;
import com.example.asdfasdfasdf.dto.ProductRequestDto;
import com.example.asdfasdfasdf.repository.ProductRepository;
import com.example.asdfasdfasdf.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public Product createProduct(ProductRequestDto productRequestDto, Long userId) {
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Product product = new Product(productRequestDto, userId);
        productRepository.save(product);
        return product;
    }

    public Product updateProduct(Long id, ProductMyPriceRequestDto productMypriceRequestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
                );
        int myprice = productMypriceRequestDto.getMyprice();
        product.setMyprice(myprice);
        productRepository.save(product);
        return product;
    }

    public List<Product> getProducts(Long userId) {
        return productRepository.findAllByUserId(userId);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
