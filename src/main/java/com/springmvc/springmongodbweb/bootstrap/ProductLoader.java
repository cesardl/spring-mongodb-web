package com.springmvc.springmongodbweb.bootstrap;

import com.springmvc.springmongodbweb.models.Product;
import com.springmvc.springmongodbweb.repositories.ProductRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductLoader implements ApplicationListener<ContextRefreshedEvent> {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ProductLoader.class);

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        String prodName = "Shirt";
        Product product = productRepository.findFirstByProdName(prodName);

        if (product == null) {
            Product shirt = new Product();
            shirt.setProdName("Shirt");
            shirt.setProdDesc("Spring Framework Guru Shirt");
            shirt.setProdPrice(BigDecimal.valueOf(18.95));
            shirt.setProdImage("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_shirt-rf412049699c14ba5b68bb1c09182bfa2_8nax2_512.jpg");
            shirt.setProdId("235268845711068308");
            productRepository.save(shirt);

            LOG.info("Saved Shirt - id: {}", shirt.getId());
        } else {
            LOG.info("Shirt already exits");
        }

        prodName = "Mug";

        product = productRepository.findFirstByProdName(prodName);

        if (product == null) {
            Product mug = new Product();
            mug.setProdName(prodName);
            mug.setProdDesc("Spring Framework Guru Mug");
            mug.setProdImage("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_coffee_mug-r11e7694903c348e1a667dfd2f1474d95_x7j54_8byvr_512.jpg");
            mug.setProdId("168639393495335947");
            mug.setProdPrice(BigDecimal.valueOf(11.95));
            productRepository.save(mug);

            LOG.info("Saved Mug - id: {}", mug.getId());
        } else {
            LOG.info("Mug already exits");
        }
    }
}
