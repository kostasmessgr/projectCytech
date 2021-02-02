package com.kostisProjects.MiniProject.repositories;
import com.kostisProjects.MiniProject.models.Product;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;


@ComponentScan
@Repository
public interface ProductRepository extends MongoRepository<Product,String>, QueryByExampleExecutor<Product> {
}

