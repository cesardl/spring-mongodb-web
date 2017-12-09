package com.springmvc.springmongodbweb.controllers;

import com.springmvc.springmongodbweb.SpringMongodbWebApplication;
import com.springmvc.springmongodbweb.models.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created on 08/12/2017.
 *
 * @author Cesardl
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringMongodbWebApplication.class)
@WebAppConfiguration
public class ProductRestControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

//        this.bookmarkRepository.deleteAllInBatch();
//        this.accountRepository.deleteAllInBatch();
//
//        this.account = accountRepository.save(new Account(userName, "password"));
//        this.bookmarkList.add(bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/1/" + userName, "A description")));
//        this.bookmarkList.add(bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/2/" + userName, "A description")));
    }

    @Test
    public void readProduct() throws Exception {
        mockMvc.perform(get("/product/" + "5a2b64603b746d2bd0b5b7b8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is("5a2b64603b746d2bd0b5b7b8")))
                .andExpect(jsonPath("$.prodName", is("Sugar")))
                .andExpect(jsonPath("$.prodDesc", is("Delicious for drinks and cakes")))
                .andExpect(jsonPath("$.prodPrice", is(111.0)))
                .andExpect(jsonPath("$.prodImage", is("https://timedotcom.files.wordpress.com/2015/10/sugar.jpg")));
    }

    @Test
    public void readBookmarks() throws Exception {
        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(11)))
                .andExpect(jsonPath("$[0].id", is("5a2b5ebd3b746d08d85f3de9")))
                .andExpect(jsonPath("$[0].prodName", is("jhoeller")))
                .andExpect(jsonPath("$[0].prodPrice", is(1004.0)))
                .andExpect(jsonPath("$[1].id", is("5a2b5ec53b746d08d85f3dea")))
                .andExpect(jsonPath("$[1].prodName", is("dsyer")))
                .andExpect(jsonPath("$[1].prodPrice", is(2541412.0)));
    }

    @Test
    public void createBookmark() throws Exception {
        Product product = new Product();
        product.setProdName("Tea");
        String productJson = json(product);

        this.mockMvc.perform(post("/product")
                .contentType(contentType)
                .content(productJson))
                .andExpect(status().isCreated());
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}