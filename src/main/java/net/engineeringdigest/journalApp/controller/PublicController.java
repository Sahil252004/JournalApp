package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;
    @GetMapping("/health-check")
        public String gethealth(){
        return "OK";
    }
    @PostMapping("/create-user")
    public void createUser(@RequestBody UserEntity user){
        userService.saveNewUser(user);
    }
}
