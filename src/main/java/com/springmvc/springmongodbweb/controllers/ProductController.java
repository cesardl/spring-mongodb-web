package com.springmvc.springmongodbweb.controllers;

import com.springmvc.springmongodbweb.models.Product;
import com.springmvc.springmongodbweb.repositories.ProductRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.style.ToStringCreator;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created on 20/09/2017.
 *
 * @author Entelgy
 */
@Controller
public class ProductController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    private ProductRepository productRepository;

    @Autowired
    ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RequestMapping(value = "/product", produces = MediaType.TEXT_HTML_VALUE)
    public String product(Model model) {
        LOG.info("Getting list of products");
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        LOG.debug("Obtained {} products from database", products.size());
        return "product";
    }

    @RequestMapping("/create")
    public String create(Model model) {
        String productCode = generatedCode();
        model.addAttribute("productCode", productCode);
        LOG.info("Trying to create a product, random code {}", productCode);
        return "create";
    }

    @RequestMapping("/save")
    public String save(@RequestParam String prodId, @RequestParam String prodName, @RequestParam String prodDesc, @RequestParam BigDecimal prodPrice, @RequestParam String prodImage) {
        Product product = new Product();
        product.setProdId(prodId);
        product.setProdName(prodName);
        product.setProdDesc(prodDesc);
        product.setProdPrice(prodPrice);
        product.setProdImage(prodImage);

        LOG.debug("Trying to save object {}", new ToStringCreator(product));
        Product saved = productRepository.save(product);
        LOG.info("Product saved {}", saved.getId());

        return "redirect:/show/" + product.getId();
    }

    @RequestMapping("/show/{id}")
    public String show(@PathVariable String id, Model model) {
        model.addAttribute("product", productRepository.findOne(id));
        return "show";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam String id) {
        Product product = productRepository.findOne(id);
        productRepository.delete(product);

        return "redirect:/product";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        LOG.info("Looking for product with id {}", id);
        Product product = productRepository.findOne(id);

        if (StringUtils.isEmpty(product.getProdId())) {
            product.setProdId(generatedCode());
        }

        model.addAttribute("product", product);
        return "edit";
    }

    @RequestMapping("/update")
    public String update(@RequestParam String id, @RequestParam String prodId, @RequestParam String prodName, @RequestParam String prodDesc, @RequestParam BigDecimal prodPrice, @RequestParam String prodImage) {
        Product product = productRepository.findOne(id);
        product.setProdId(prodId);
        product.setProdName(prodName);
        product.setProdDesc(prodDesc);
        product.setProdPrice(prodPrice);
        product.setProdImage(prodImage);
        LOG.debug("Trying to update object {}", new ToStringCreator(product));
        Product updated = productRepository.save(product);
        LOG.info("Product updated {}", updated.getId());

        return "redirect:/show/" + product.getId();
    }

    private String generatedCode() {
        return RandomStringUtils.random(18, false, true);
    }
}
