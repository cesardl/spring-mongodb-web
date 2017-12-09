package com.springmvc.springmongodbweb.repositories;

import com.springmvc.springmongodbweb.models.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * Created on 20/09/2017.
 *
 * @author Entelgy
 */
public interface ProductRepository extends CrudRepository<Product, String> {

}
