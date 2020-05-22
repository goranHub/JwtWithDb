package com.example.data;

import com.example.beladungTest.Employee;
import com.example.config.DatabaseConfiguration;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class UserDataAccessService {


    private  JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserDataAccessService() {
    }

    private NamedParameterJdbcTemplate namedJdbcTemplate;

    public List<User> getUserFromIdListNamed(List<Integer> ids) {
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        List<User> users = namedJdbcTemplate.query(
                "SELECT * FROM user WHERE id IN (:ids)",
                parameters,
                (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("fname"), rs.getString("lname")));

        return users;
    }

//for test
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    public List<User> selectAllUsers() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, mapUserFomDb());
    }


    //POST user
    public void setUser(User user) {
        this.jdbcTemplate.update(
                "INSERT INTO user (id, login, password, fname, lname, email) VALUES(?,?,?,?,?,?)",
                user.getId(), user.getLogin(), user.getPassword(),
                user.getFname(), user.getLname(), user.getEmail());
    }


    //PUT user
    public void updateUser(int userId, User user) {
        this.jdbcTemplate.update(
                "UPDATE user SET login = ?, password = ?, fname = ?, lname = ?, email = ? WHERE id = ?",
                user.getLogin(), user.getPassword(), user.getFname(), user.getLname(), user.getEmail(), userId);
    }


    //GET /user/{id}
    public User getUserbyID(int userID) {
        String sql = "SELECT * FROM user WHERE id=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userID}, mapUserFomDb());
    }

    //delete
    public boolean deleteUser(int userId) {
        return jdbcTemplate.update("DELETE FROM user where id = ?", userId) > 0;
    }


    public User loadUserByUsername(String username) {
        String sql = "SELECT * FROM user WHERE fname=?";
        User user = jdbcTemplate.queryForObject(sql, new Object[]{username}, mapUserFomDb());
        return user;
    }


    private RowMapper<User> mapUserFomDb() {
        return (resultSet, i) -> {
            int id = Integer.valueOf(resultSet.getString("id"));
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            String fname = resultSet.getString("fname");
            String lname = resultSet.getString("lname");
            String email = resultSet.getString("email");
            return new User(id, login, password, fname, lname, email);
        };
    }


    public int getCountOfUsers() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user", Integer.class);
    }

}
