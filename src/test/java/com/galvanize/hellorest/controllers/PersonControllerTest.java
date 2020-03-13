package com.galvanize.hellorest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.hellorest.entities.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

    @Test
    void helloPerson() {
        PersonController personController = new PersonController();
        Person person = personController.createGetPerson("Rob", "rob.wing@galvanize.com",
                "1962-11-16");

        assertNotNull(person);

    }

    @Test
    void helloPersonMvc() throws Exception {
        mvc.perform(get("/api/person1?name=Rob&email=rob.wing@galvanize.com&birthDate=1962-11-16"))
                .andExpect(status().isOk())
                .andDo(print())
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

//    @Test
//    void testNewLocalDate() throws JsonProcessingException {
//        Person person = new Person("Rob", "rob.wing@galvanize.com", LocalDate.now());
//        System.out.println(mapper.writeValueAsString(person));
//
////        {"id":null,"name":"Rob","email":"rob.wing@galvanize.com","birthDate":{"year":2020,"month":"MARCH","monthValue":3,"dayOfMonth":12,"era":"CE","dayOfYear":72,"dayOfWeek":"THURSDAY","leapYear":true,"chronology":{"id":"ISO","calendarType":"iso8601"}},"age":0}
//    }
}