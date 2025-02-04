package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.UserEntryRepo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import static org.mockito.Mockito.*;

//@InjectMocks
public class UserDetailsServiceImplTests {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserEntryRepo userEntryRepo;

    @Disabled
    @Test
    void loadUserNameTest(){
//        when(userEntryRepo.findByUserName(ArgumentMatchers.anyString())).thenReturn(UserEntity);
        UserDetails user = userDetailsServiceImpl.loadUserByUsername("ram");
    }
}
