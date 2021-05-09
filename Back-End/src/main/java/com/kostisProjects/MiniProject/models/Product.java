package com.kostisProjects.MiniProject.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
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

    public Product() {
    }

    public Product(String id,String title, String brand, String category, String price) {
        this.id = id;
        this.category=category;
        this.title = title;
        this.price = price;
        this.brand = brand;
        //this.description = description;
        this.asin=asin;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", brand='" + brand + '\'' +
                //", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", asin='" + asin + '\'' +
                '}';
    }
}