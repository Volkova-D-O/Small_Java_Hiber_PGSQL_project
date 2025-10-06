import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.wteam.entity.User;
import ru.wteam.entity.UserDAO;
import ru.wteam.entity.UserService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDAO mockUserDAO;

    @InjectMocks
    private UserService userService;


    @Test
    void testCreateUser() {
//Arrange
        User newUser = new User("John", "john@ololo.ru", 18);
        newUser.setCreatedAt(LocalDateTime.of(2026, 9, 15, 0, 25, 59, 588656000));
        newUser.setId(14L);
//Act
        userService.createUser(newUser);
//Assert
        verify(mockUserDAO, times(1)).createUser(newUser);
        verifyNoMoreInteractions(mockUserDAO);
    }

    @Test
    void testReadUser() {
        // Arrange
        Long userId = 15L;
        User expected = new User("Jan", "Jan@ya.ru", 36);
        expected.setCreatedAt(LocalDateTime.of(2026, 9, 15, 12, 17, 36, 588656000));
        expected.setId(userId);

        when(mockUserDAO.readUser(userId)).thenReturn(expected);

        // Act
        User result = userService.readUser(userId);

        // Assert
        verify(mockUserDAO, times(1)).readUser(userId);
        assertEquals(expected, result);
        verifyNoMoreInteractions(mockUserDAO);
    }

    @Test
    void testReadUserNotFound() {
        // Arrange
        Long userId = 99L;

        when(mockUserDAO.readUser(userId)).thenReturn(null);

        // Act
        User actualUser = userService.readUser(userId);

        // Assert
        verify(mockUserDAO, times(1)).readUser(userId);
        assertNull(actualUser);
        verifyNoMoreInteractions(mockUserDAO);
    }

    @Test
    void testUpdateUser() {
        // Arrange
        Long userId = 16L;
        User userToUpdate = new User("John Updated", "john.updated@ya.ru", 45);
        userToUpdate.setCreatedAt(LocalDateTime.of(2026, 9, 15, 12, 17, 36, 588656000));
        userToUpdate.setId(userId);

        // Act
        userService.updateUser(userToUpdate);

        // Assert
        verify(mockUserDAO, times(1)).updateUser(userToUpdate);
        verifyNoMoreInteractions(mockUserDAO);
    }

    @Test
    void testDeleteUser() {
        // Arrange
        Long userIdToDelete = 2L;

        // Act
        userService.deleteUser(userIdToDelete);

        // Assert
        verify(mockUserDAO, times(1)).deleteUser(userIdToDelete);
        verifyNoMoreInteractions(mockUserDAO);
    }

    @Test
    void testReadAllUsers() {
        // Arrange
        List<User> expected = Arrays.asList(
                new User("Alice", "alice@ya.ru",19),
                new User("Bob", "bob@ololo.ru",21)
        );

        when(mockUserDAO.readAllUsers()).thenReturn(expected);

        // Act
        List<User> result = userService.readAllUsers();

        // Assert
        verify(mockUserDAO, times(1)).readAllUsers();
        assertEquals(expected, result);
        verifyNoMoreInteractions(mockUserDAO);
    }

}
