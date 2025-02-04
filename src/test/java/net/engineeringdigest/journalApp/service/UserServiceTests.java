package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.repository.UserEntryRepo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserEntryRepo userRepo;


//    @Disabled
//    @ParameterizedTest
//    @ValueSource( strings =
//            {
//                    "ram",
//                    "yash",
//                    "hitesh"
//            }
//    )
//    public void testAdd(String username)
//    {
//        assertNotNull(userRepo.findByUserName(username));
//    }

    @Disabled
    @ParameterizedTest
    @CsvSource(
            {
                    "1,1,2",
                    "3,3,9",
                    "4,4,8"
            }
    )
    public void test(int a,int b,int expected)
    {
        assertEquals(expected, a + b);
    }
}
