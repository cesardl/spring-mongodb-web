package com.springmvc.springmongodbweb.controllers;

import com.springmvc.springmongodbweb.forms.ProductForm;
import com.springmvc.springmongodbweb.models.Product;
import com.springmvc.springmongodbweb.repositories.ProductRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
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
    private ModelMapper modelMapper;

    @Autowired
    ProductController(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/product")
    public String product(Model model) {
        LOG.info("Getting list of products");
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        LOG.debug("Obtained {} products from database", products.size());
        return "product";
    }

    @RequestMapping("/create")
    public String create(Model model) {
        ProductForm form = new ProductForm();
        form.setProdId(generatedCode());

        model.addAttribute("productForm", form);
        LOG.info("Trying to create a product, random code {}", form.getProdId());
        return "create";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid ProductForm productForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOG.warn("Have {} errors during the creation process, please check it!", bindingResult.getErrorCount());
            return "create";
        }

        Product product = modelMapper.map(productForm, Product.class);

        LOG.debug("Trying to save object {}", ToStringBuilder.reflectionToString(product));
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

        model.addAttribute("productForm", modelMapper.map(product, ProductForm.class));
        return "edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid ProductForm productForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOG.warn("Have {} errors during the update process, please check it!", bindingResult.getErrorCount());
            return "edit";
        }

        Product product = productRepository.findOne(productForm.getId());
        modelMapper.map(productForm, product);

        LOG.debug("Trying to update object {}", ToStringBuilder.reflectionToString(product));
        Product updated = productRepository.save(product);
        LOG.info("Product updated {}", updated.getId());

        return "redirect:/show/" + product.getId();
    }

    private String generatedCode() {
        return RandomStringUtils.random(18, false, true);
    }
}
