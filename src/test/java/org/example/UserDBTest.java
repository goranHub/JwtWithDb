package org.example;

import com.example.config.DatabaseConfiguration;
import com.example.data.UserDataAccessService;
import com.example.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)

public class UserDBTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    DataSource dataSource;


    @Test
    public void whenMockJdbcTemplate_thenReturnCorrectUserCount() {

        UserDataAccessService userDataAccessService = new UserDataAccessService();

        ReflectionTestUtils.setField(userDataAccessService, "jdbcTemplate", jdbcTemplate);

        Mockito.when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.eq(Integer.class)))
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
    public void givenLargeIdList_whenGetEmployeesFromIdList_thenReturnCorrectEmployees() {

        UserDataAccessService userDataAccessService = new UserDataAccessService();
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        userDataAccessService.setDataSource(databaseConfiguration.customDataSource());

        List<Integer> ids = new ArrayList<>();
        ids.add(0,1);
        ids.add(1,4);

        List<User> users = userDataAccessService.getUserFromIdListNamed(ids);

        assertEquals(1, users.get(0).getId());
        assertEquals(4, users.get(1).getId());
    }
}
