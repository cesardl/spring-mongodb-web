package com.springmvc.springmongodbweb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created on 08/12/2017.
 *
 * @author Cesardl
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(final String productId) {
        super("could not find product '" + productId + "'.");
    }
}
