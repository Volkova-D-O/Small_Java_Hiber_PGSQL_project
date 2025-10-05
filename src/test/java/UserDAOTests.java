import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.wteam.entity.User;
import ru.wteam.entity.UserDAOClass;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class UserDAOTests {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13.3")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");


    private static SessionFactory sessionFactory;
    private Session session;
    private UserDAOClass userDAO;


    @BeforeAll
    static void setUpAll() {
        assertTrue(postgres.isRunning());
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", postgres.getUsername());
        configuration.setProperty("hibernate.connection.password", postgres.getPassword());
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        configuration.addAnnotatedClass(User.class);

        sessionFactory = configuration.buildSessionFactory();
    }


    @AfterAll
    public static void close() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

    @BeforeEach
    void setUp() {
        userDAO = new UserDAOClass(sessionFactory);
    }

    @Test
    @DisplayName("Проверка сохранения нового пользователя")
    void testSaveUser() {
        User newUser = new User("Joe", "joe@ololo.ru", 78);
        newUser.setCreatedAt(LocalDateTime.of(2025, 9, 27, 0, 25, 59, 588656000));
        userDAO.createUser(newUser);

        assertNotNull(newUser.getId());
        User foundUser = session.find(User.class, newUser.getId());
        assertNotNull(foundUser, "New user must be found");
        assertEquals(newUser.getName(), foundUser.getName(), "User names must match");
        assertEquals(newUser.getEmail(), foundUser.getEmail(), "User emails must match");
    }

    @Test
    @DisplayName("Проверка чтения несуществующего пользователя")
    void shouldReturnEmptyForNonExistingId() {
        Long nonExistingId = 999L;

        User foundUser = userDAO.readUser(nonExistingId);

        assertNull(foundUser, "Not existing user must not be found");
    }

    @Test
    @DisplayName("Проверка чтения по идентификатору пользвоателя")
    void shouldReadUserByIdSuccessfully() {
        User newUser = new User("Joe", "joe@ololo.ru", 78);
        newUser.setCreatedAt(LocalDateTime.of(2025, 9, 27, 0, 25, 59, 588656000));

        userDAO.createUser(newUser);

        session.clear();

        User foundUser = userDAO.readUser(newUser.getId());

        assertNotNull(foundUser, "User must be found");
        assertNotNull(foundUser.getId(), "User id must not be null");
        assertEquals(newUser.getName(), foundUser.getName(), "User names must match");
        assertEquals(newUser.getEmail(), foundUser.getEmail(), "User emails must match");
        assertEquals(newUser, foundUser, "Users must equal");
    }

    @Test
    @DisplayName("Проверка обновления пользователя")
    void testUpdateUser() {
        User newUser = new User("Joe", "joe@ololo.ru", 78);
        newUser.setCreatedAt(LocalDateTime.of(2025, 9, 27, 0, 25, 59, 588656000));

        userDAO.createUser(newUser);

        session.clear();

        User updatedUser = userDAO.readUser(newUser.getId());
        updatedUser.setName("Updated Joe");
        updatedUser.setEmail("updated_joe@ololo.ru");
        userDAO.updateUser(updatedUser);

        session.clear();

        User foundUser = userDAO.readUser(newUser.getId());

        assertNotNull(foundUser, "User must be found");
        assertNotNull(foundUser.getId(), "User id must not be null");
        assertEquals(newUser.getId(), foundUser.getId(), "User names must match");
        assertEquals(updatedUser.getName(), foundUser.getName(), "User names must match");
        assertEquals(updatedUser.getEmail(), foundUser.getEmail(), "User emails must match");
        assertEquals(updatedUser, foundUser, "Users must equal");
    }

    @Test
    @DisplayName("Проверка удаелния пользователя")
    void deleteUserTest() {
        User newUser = new User("Joe", "joe@ololo.ru", 78);
        newUser.setCreatedAt(LocalDateTime.of(2025, 9, 27, 0, 25, 59, 588656000));

        userDAO.createUser(newUser);

        session.clear();

        userDAO.deleteUser(newUser.getId());

        session.clear();

        User foundUser = userDAO.readUser(newUser.getId());

        assertNull(foundUser, "User must not be deleted");
    }
}