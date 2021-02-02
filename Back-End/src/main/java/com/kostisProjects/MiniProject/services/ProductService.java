package com.kostisProjects.MiniProject.services;

import com.kostisProjects.MiniProject.models.Product;
import org.json.simple.JSONArray;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@ComponentScan
@Service
public interface ProductService {

        Page<Product> listAllProducts(Pageable pageable);
        Page<Product> isContained(String key,String value, Pageable pageable);
        Page<Product> contains(String key,Product p,Pageable pageable);

        List<Object> getVals(JSONArray filteredarray, JSONArray sortedarray, int pageSize, int pageNumber);
}