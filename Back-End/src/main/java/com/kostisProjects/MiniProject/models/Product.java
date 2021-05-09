package com.kostisProjects.MiniProject.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

//Data model class specifies properties of product
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")

public class Product {

    @Id
    @JsonProperty("_id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("price")
    private String price;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("main_cat")
    private String category;

    @JsonProperty("asin")
    private String asin;
    
}