package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.service.UserDetailsServiceImpl;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;



@Service
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String gethealth(){
        return "OK";
    }

    @PostMapping("/signup")
    public void signup(@RequestBody UserEntity user){
        userService.saveNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserEntity user){

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails user1 = userDetailsService.loadUserByUsername(user.getUserName());
            String s = jwtUtils.generateToken(user1.getUsername());
            return new ResponseEntity<>(s, HttpStatus.OK);
        } catch (Exception e)
        {
            log.error("Exception occured while creating jwt token",e);
            return  new ResponseEntity<>("Incorrect Username or password",HttpStatus.BAD_REQUEST);
        }


    }
}
