package com.example.data.Implementation;


import com.example.data.RoleDataAccessService;
import com.example.data.UserDataAccessService;
import com.example.model.Role;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Qualifier("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDataAccessService userDataRepository;
    @Autowired
    private RoleDataAccessService roleDataAccessService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsManagerImpl userDetailsManager;
    @Autowired
    private User user;
    @Autowired
    private Role role;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        user = userDataRepository.loadUserByUsername(username);


        if (user.getFname().equals(username)) {

            if (userDetailsManager.userExists(username)) {
                return userDetailsManager.loadUserByUsername(username);
            } else if (username.equals("admin")) {
                user.setRole("ADMIN");
                userDetailsManager.createUser(user);
                roleDataAccessService.setRoleAdmin(role, user);
                return user;
            }

        }


        if (userDetailsManager.userExists(username)) {
            return userDetailsManager.loadUserByUsername(username);
        } else if (username.equals("develop")) {
            user.setRole("DEVELOPER");
            userDetailsManager.createUser(user);
            roleDataAccessService.setRoleDeveloper(role, user);

            return user;
        }
        return null;
    }


}





