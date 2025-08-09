import com.shop.dto.CreateUser;
import com.shop.dto.Login;

import com.shop.exception.UserNotFoundException;
import com.shop.model.User;
import com.shop.repository.UserRepository;
import com.shop.service.AuthService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;


import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    AuthService service;

    @Test
    public void testRegister(){
        CreateUser c = new CreateUser("Krithika", "krithika@gmail.com","9898989898","Krithika@123");
       when(userRepository.save(any(User.class))).thenReturn(new User(2L,"Krithika", "krithika@gmail.com","9898989898",""));

        Long userId = service.register(c);
        verify(userRepository).save(any(User.class));
        assertEquals(2L, userId);
    }

    @Test
    public void testRegisterWithExistingEmailId(){
        CreateUser c = new CreateUser("Krithika", "krithika@gmail.com","9898989898","Krithika@123");

        when(userRepository.save(any(User.class)))
                .thenThrow(new DataIntegrityViolationException("duplicate",
                        new SQLException("violates unique constraint \"unique_email_only\"")));

        assertThrows(DataIntegrityViolationException.class, () -> {
           service.register(c);
        });
        }

    @Test
    public void login(){
        Login l = new Login("albert@gmail.com","albert@123");
        User user = new User(2L, "Albert", "albert@gmail.com", "6492538750",
                "$2a$10$ZLOCrAvcMe0C9./i8XP/7.V3UNPXlwbLtNF7uHG6eL5GKK2d7HRc2");
        when(userRepository.findByEmail(l.getEmail())).thenReturn(user);
        String msg = service.login(l);
        assertEquals(msg, "Login successful");
    }

    @Test
    public void falseUserLogin(){
        Login l = new Login("albert@gmail.com","albert@123");
        when(userRepository.findByEmail(l.getEmail())).thenReturn(null);

        assertThrows(UserNotFoundException.class,()->{
            service.login(l);
        });
    }
}
