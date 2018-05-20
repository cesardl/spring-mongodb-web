package com.springmvc.springmongodbweb.controllers;

import javax.validation.constraints.NotNull;

/**
 * Created on 20/05/2018.
 *
 * @author Cesardl
 */
public class ProductForm {

    private String id;

    @NotNull
    private String prodId;

    @NotNull
    private String prodName;

    private String prodDesc;
    private Double prodPrice;
    private String prodImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
