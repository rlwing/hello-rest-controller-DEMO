package com.galvanize.hellorest.controllers;

import com.galvanize.hellorest.entities.Person;
import com.galvanize.hellorest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

//    @GetMapping("/person1")
//    public Person createGetPerson(@RequestParam String name,
//                                @RequestParam String email,
//                                @RequestParam String birthDate){
//
//        Date bd = LocalDate.parse(birthDate);
//        return new Person(name, email, bd);
//
//    }

    /*** CREATE ***/
    @PostMapping("/person")
    public Person createPerson(@RequestBody Person person){
        return personRepository.save(person);
    }

    /*** READ ***/
    @GetMapping("/person")
    public List<Person> getPeople(){
        return personRepository.findAll();
    }
    @GetMapping("/person/{id}")
    public Person getPerson(@PathVariable Long id){
        return personRepository.findById(id);
    }

    /*** UPDATE ***/
    @PatchMapping("/person/{id}")
    public Person updatePerson(@RequestParam String email, @PathVariable Long id){
        return personRepository.updateEmail(id, email);
    }

    /*** DELETE ***/
    @DeleteMapping("/person/{id}")
    public ResponseEntity deletePerson(@PathVariable Long id){
        boolean deleted = personRepository.delete(id);
        if(!deleted){
            return ResponseEntity.notFound()
                    .header("errorMsg", "Person id "+id+" doesn't exist")
                    .build();
        }else{
            return ResponseEntity.ok().build();
        }
    }

}
