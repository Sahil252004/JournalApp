package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.UserEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserEntryRepo userRepo;

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public void saveNewUser(UserEntity user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("User"));
        userRepo.save(user);
    }

    public void saveUser(UserEntity user)
    {
        userRepo.save(user);
    }
    public List<UserEntity>getAll(){
        return userRepo.findAll();
    }

    public Optional<UserEntity> findById(ObjectId id)
    {
        return userRepo.findById(String.valueOf(id));
    }

    public void DeleteById(ObjectId id)
    {
        userRepo.deleteById(String.valueOf(id));
    }
    public UserEntity findByUserName(String userName)
    {
        return userRepo.findByUserName(userName);
    }
}
