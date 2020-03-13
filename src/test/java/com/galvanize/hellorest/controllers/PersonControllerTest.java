package com.galvanize.hellorest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.hellorest.entities.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper();

//    @Test
//    void helloPerson() {
//        PersonController personController = new PersonController();
//        Person person = personController.createGetPerson("Rob", "rob.wing@galvanize.com",
//                "1962-11-16");
//
//        assertNotNull(person);
//
//    }

//    @Test
//    void helloPersonMvc() throws Exception {
//        mvc.perform(get("/api/person?name=Rob&email=rob.wing@galvanize.com&birthDate=1962-11-16"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(content().string(containsString("rob.wing@galvanize.com")));
//    }


    @Test
    void helloPersonMvc2() throws Exception {
        String sPerson = mapper.writeValueAsString(new Person("Rob", "rob.wing@galvanize.com", Date.valueOf(LocalDate.of(1962, 11, 16))));
        mvc.perform(post("/api/person").contentType(MediaType.APPLICATION_JSON).content(sPerson))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("rob.wing@galvanize.com")));
    }

    @Test
    void createPerson() throws Exception {
        String body = "{\"name\":\"Rob\",\"email\":\"rob.wing@galvanize.com\",\"birthDate\":\"11/16/1962\"}";
        mvc.perform(post("/api/person").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andDo(print());
    }

    @Test
    void getById() throws Exception {
        String body = "{\"email\":\"rob@wingjr.com\"}";
        mvc.perform(patch("/api/person/10?email=rob@wingjr.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("rob@wingjr.com"))
                .andDo(print());
    }

    @Test
    void testNewLocalDate() throws JsonProcessingException {
        Person person = new Person("Rob", "rob.wing@galvanize.com", Date.valueOf(LocalDate.of(1962, 11, 16)));
        //De-serialize the Person object into a Json String
        String sPerson = mapper.writeValueAsString(person);
        System.out.println(sPerson);

        //Serialize the String back to a Person object
        Person person1 = mapper.readValue(sPerson, Person.class);
        System.out.println(person1);

    }
}