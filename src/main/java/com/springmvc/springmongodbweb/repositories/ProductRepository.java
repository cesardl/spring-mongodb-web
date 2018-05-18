package com.springmvc.springmongodbweb.repositories;

import com.springmvc.springmongodbweb.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created on 20/09/2017.
 *
 * @author Entelgy
 */
public interface ProductRepository extends MongoRepository<Product, String> {

    Product findFirstByProdName(String prodName);

    Product findByProdIdAndProdName(String prodId, String prodName);

    //Supports native JSON query string
    @Query("{prodName:'?0'}")
    Product findCustomByProduct(String domain);

    @Query("{prodName: { $regex: ?0 } })")
    List<Product> findCustomByRegExProduct(String domain);
}
