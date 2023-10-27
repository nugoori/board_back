package com.korit.board.service;

import com.korit.board.dto.ProductRespDto;
import com.korit.board.repository.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;

    public List<ProductRespDto> getProducts() {
        List<ProductRespDto> productRespList = new ArrayList<>();
        productMapper.getProducts().forEach(product -> {
            productRespList.add(product.toProductDto());
        });

        return productRespList;
    }
}
