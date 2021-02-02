package com.kostisProjects.MiniProject.services;
import com.kostisProjects.MiniProject.models.Product;
import com.kostisProjects.MiniProject.repositories.ProductRepository;
import com.mongodb.DBObject;
import netscape.javascript.JSObject;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@ComponentScan
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    public Page<Product> listAllProducts(Pageable pageable) {

        Product p;
        Page<Product> results = productRepository.findAll(pageable);
        return results;
    }

    public Page<Product> isContained(String key,String value,Pageable pageable){
        Product p= new Product();
        try {
            switch (key) {
                case"_id":
                    p.setId(value);
                    break;
                case"main_cat":
                    p.setCategory(value);
                    break;
                case"title":
                    p.setTitle(value);
                    break;
                case"price":
                    if(NumberUtils.isCreatable(value)) p.setPrice(value);
                    break;
                case"asin":
                    p.setAsin(value);
                    break;
                case"brand":
                    p.setBrand(value);
                    break;
                case"description":
                    //p.setDescription(value);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return contains(key,p,pageable);
    }

    public Page<Product> contains(String key,Product p,Pageable pageable){
        ExampleMatcher matcher = ExampleMatcher.matchingAny().withIgnoreCase().withMatcher(key,ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Product> productExample = Example.of(p,matcher);
        Page<Product> result =  productRepository.findAll(productExample,pageable);
        return result;
    }

    public List<Object> getVals(JSONArray filteredarray, JSONArray sortedarray, int pageSize, int pageNumber){
        List<Object> result = new ArrayList<>();
        String filterKey=null;
        String filterValue=null;
        String sortedKey=null;
        Boolean descValue=null;

        if(filteredarray.size()>0) {
            JSONObject filtered = (JSONObject) filteredarray.get(0);
            filterKey= filtered.get("id").toString();
            filterValue= filtered.get("value").toString();
        }
        if(sortedarray.size()>0) {
            JSONObject sorted = (JSONObject) sortedarray.get(0);
            sortedKey = sorted.get("id").toString();
            descValue = (Boolean) sorted.get("desc");
        }

        Pageable pageable = null;
        //switch(order){
        if(descValue!=null) {
            if (descValue) {
                pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC,"id",sortedKey));
            } else {
                pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC,"id",sortedKey));
            }
        }else{
            pageable = PageRequest.of(pageNumber,pageSize);
        }
        result.add(sortedKey);
        result.add(filterKey);
        result.add(filterValue);
        result.add(pageable);
        return result;
    }
}