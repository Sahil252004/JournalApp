package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.UserEntryRepo;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserEntryRepo userEntryRepo;



    @PutMapping
    public ResponseEntity<?> editUser(@RequestBody UserEntity user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity userInDb = userService.findByUserName(userName);
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveNewUser(userInDb);
        return new ResponseEntity<UserEntity>(userInDb,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody UserEntity user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userEntryRepo.deleteByUserName(authentication.getName());
        return new ResponseEntity<UserEntity>(HttpStatus.NO_CONTENT);
    }

}
