package com.DanSteLlaEs.CursoSpring.controller;

import com.DanSteLlaEs.CursoSpring.entity.User;
import com.DanSteLlaEs.CursoSpring.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //Create a new User
    @PostMapping("/save")
    public ResponseEntity<?> create(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    //Read an user
    @GetMapping("/read/{id}")
    public ResponseEntity<?> read(@PathVariable("id") Long id){
        Optional<User> user= userService.findById(id);

        if (!user.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    //Update an user
    @PutMapping("/put/{id}")
    public ResponseEntity<?> update(@RequestBody User userDetails,@PathVariable("id") Long id){
        Optional<User> user = userService.findById(id);

        if (!user.isPresent()){
            return ResponseEntity.notFound().build();
        }

        //BeanUtils.copyProperties(userDetails,user.get());
        user.get().setName(userDetails.getName());
        user.get().setUsername(userDetails.getUsername());
        user.get().setEmail(userDetails.getEmail());
        user.get().setEnabled(userDetails.getEnabled());

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user.get()));
    }

    //Delete an user
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        if (!userService.findById(id).isPresent()){
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    //read all users
    @GetMapping("/update")
    public List<User> readAll(){
        List<User> users= StreamSupport
                .stream(userService.findAll().spliterator(),false)
                .collect(Collectors.toList());

        return users;
    }

}
