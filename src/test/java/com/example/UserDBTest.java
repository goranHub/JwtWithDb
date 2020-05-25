package com.example;

import com.example.DemoApplication;
import com.example.config.DatabaseConfiguration;
import com.example.config.SecurityConfiguration;
import com.example.data.UserDataAccessService;
import com.example.model.User;
import com.example.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDBTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    DataSource dataSource;

    @Autowired
    private UserService userService;

    @MockBean
    UserDataAccessService userDataAccessService;

    @Test
    public void getUserTest() {
        when(userDataAccessService.findAll()).thenReturn(Stream.of(new User(3,"pass", "first"),
                new User (4, "passw", "second")).collect(Collectors.toList()));
        assertEquals(2, userService.getAllUsers().size());
    }


    @Test
    public void whenMockJdbcTemplate_thenReturnCorrectUserCount() {

        UserDataAccessService userDataAccessService = new UserDataAccessService();

        ReflectionTestUtils.setField(userDataAccessService, "jdbcTemplate", jdbcTemplate);

        when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.eq(Integer.class)))
                .thenReturn(2);

        assertEquals(2, userDataAccessService.getCountOfUsers());
    }

    @Test
    public void proveHowManyUserInDb() {
        UserDataAccessService userDataAccessService = new UserDataAccessService();
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        userDataAccessService.setDataSource(databaseConfiguration.customDataSource());
        assertEquals(4, userDataAccessService.getCountOfUsers());
    }


    @Test
    public void proveIds() {

        UserDataAccessService userDataAccessService = new UserDataAccessService();
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        userDataAccessService.setDataSource(databaseConfiguration.customDataSource());

        List<Integer> ids = new ArrayList<>();
        ids.add(0, 1);
        ids.add(1, 4);

        List<User> users = userDataAccessService.getUserFromIdListNamed(ids);

        assertEquals(1, users.get(0).getId());
        assertEquals(4, users.get(1).getId());
    }


    @Test
    public void proveFname() {
        UserDataAccessService userDataAccessService = new UserDataAccessService();
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        userDataAccessService.setDataSource(databaseConfiguration.customDataSource());

        List<String> fnames = new ArrayList<>();
        fnames.add(0, "neue");
        List<User> users = userDataAccessService.getUserFrombyFname(fnames);

        assertEquals(3, users.get(0).getId());


    }

    @Test
    public void presist() {

        UserDataAccessService localMockRepository = Mockito.mock(UserDataAccessService.class);
        Mockito.when(localMockRepository.loadUserByUsername("Ime")).thenReturn(new User("Ime", "password"));

        User userCount = localMockRepository.loadUserByUsername("Ime");

        Assert.assertEquals("Ime", userCount.getFname());
        Mockito.verify(localMockRepository).loadUserByUsername("Ime");
    }
}
