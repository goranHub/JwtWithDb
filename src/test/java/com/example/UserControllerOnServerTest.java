package com.example;


import com.example.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;



//with real server
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerOnServerTest {


    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getUserByIdTest(){

        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<User> response = testRestTemplate.getForEntity("http://localhost:8080/user/1", User.class);

        assertEquals("admin", response.getBody().getFname());
        assertEquals("La_rr_y", response.getBody().getLname());
        assertEquals("La_rr_y@", response.getBody().getEmail());


    }


}

