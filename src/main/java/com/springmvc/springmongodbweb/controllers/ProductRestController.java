package com.springmvc.springmongodbweb.controllers;

import com.springmvc.springmongodbweb.OperationNotAllowedException;
import com.springmvc.springmongodbweb.ProductNotFoundException;
import com.springmvc.springmongodbweb.models.Product;
import com.springmvc.springmongodbweb.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Created on 08/12/2017.
 *
 * @author Cesardl
 */
@RestController
@RequestMapping(value = "/product", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProductRestController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductRestController.class);

    private final ProductRepository productRepository;

    @Autowired
    ProductRestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Product> listProducts() {
        LOG.info("Invoking listProducts");
        return this.productRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        LOG.info("Invoking createProduct");

        if (product.getId() != null) {
            throw new OperationNotAllowedException();
        }

        Product result = productRepository.save(product);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{productId}")
    public Product getProduct(@PathVariable String productId) {
        LOG.info("Invoking getProduct with id {}", productId);
        Product result = this.productRepository.findOne(productId);

        if (result == null) {
            throw new ProductNotFoundException(productId);
        }

        return result;
    }
}
