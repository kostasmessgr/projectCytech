package com.kostisProjects.MiniProject.controllers;
import com.kostisProjects.MiniProject.models.Product;
import com.kostisProjects.MiniProject.services.ProductService;
import com.kostisProjects.MiniProject.services.ProductServiceImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@RestController
//Controller class
public class ProductController {

    private static final int DEFAULT_PAGE_SIZE=20;
    private static final int STARTING_PAGE=0;
    private static final int [] AVAILABLE_PAGE_SIZES = {10,20,50};

    @Autowired
    private ProductService productService;
    private Page<Product> page;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/allProducts")
    @ResponseBody

    public ResponseEntity<Page> getProducts(@RequestBody JSONObject request){
        String req = request.toJSONString();
        System.out.println(req);
        Integer pageNumber =(int)request.get("page");
        Integer pageSize = (int)request.get("per");
        JSONParser parser = new JSONParser();
        try{
            JSONObject obj  = (JSONObject) parser.parse(String.valueOf(request));
            JSONArray filteredarray = (JSONArray) obj.get("filtered");
            JSONArray sortedarray = (JSONArray) obj.get("sorted");
            List<Object> values =  new ProductServiceImpl().getVals(filteredarray,sortedarray,pageSize,pageNumber);

            String sortedKey = values.get(0)==null? null:values.get(0).toString();
            String filterKey = values.get(1)==null? null:values.get(1).toString();
            String filterValue = values.get(2)==null? null:values.get(2).toString();
            Pageable pageable = values.get(3)==null? null:(Pageable) values.get(3);

            if(filterKey==null && sortedKey==null ) {
                pageable = PageRequest.of(pageNumber,pageSize);
                Page<Product> res = productService.listAllProducts(pageable);
                return new ResponseEntity<>(res,HttpStatus.OK);
            }else if(sortedKey == null){
                pageable = PageRequest.of(pageNumber,pageSize);

            }else if(filterKey == null){
                pageable = PageRequest.of(0,pageSize);
                Page<Product> res = productService.listAllProducts(pageable);
                return new ResponseEntity<>(res,HttpStatus.OK);
            }


            page = productService.isContained(filterKey,filterValue,pageable);
            if(page.hasContent()){
                System.out.println("Has content");
                return new ResponseEntity<>(page,HttpStatus.OK);
            }else{
                //page = productService.listAllProducts(pageable);
                System.out.println("Not found");
                return new ResponseEntity<>(page,HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            //exception catch 1
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}