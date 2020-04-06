package com.example.gateway.service.serviceimpl;

import com.example.gateway.bean.auth.JwtToken;
import com.example.gateway.bean.auth.Role;
import com.example.gateway.bean.auth.User;
import com.example.gateway.exception.CustomException;
import com.example.gateway.repository.JwtTokenRepository;
import com.example.gateway.repository.UserRepository;
import com.example.gateway.security.JwtTokenProvider;
import com.example.gateway.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoginService implements ILoginService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Transactional
    @Override
    public String login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
                    password));
            User user = userRepository.findByEmail(username);
            if (user == null || user.getRole() == null || user.getRole().isEmpty()) {
                throw new CustomException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
            }
            //NOTE: normally we dont need to add "ROLE_" prefix. Spring does automatically for us.
            //Since we are using custom token using JWT we should add ROLE_ prefix
            String token = jwtTokenProvider.createToken(username, user.getRole().stream()
                    .map((Role role) -> "ROLE_" + role.getRole()).filter(Objects::nonNull).collect(Collectors.toList()));
            return token;

        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
        }
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public boolean logout(String token) {
        jwtTokenRepository.delete(new JwtToken(token));
        return true;
    }

    @Override
    public Boolean isValidToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    @Override
    public String createNewToken(String token) {
        String username = jwtTokenProvider.getUsername(token);
        List<String> roleList = jwtTokenProvider.getRoleList(token);
        return jwtTokenProvider.createToken(username, roleList);
    }

    @PostConstruct
    public void insertDummyUsers() {
        System.out.println("inserting dummy users");
        Role manager = new Role();
        manager.setId(1);
        manager.setRole("bank_manager");
        Set<Role> role1 = new HashSet<>();
        role1.add(manager);

        Role customer = new Role();
        customer.setId(2);
        customer.setRole("customer");
        Set<Role> role2 = new HashSet<>();
        role2.add(customer);

        List<User> users = new ArrayList<>();
        User user = userRepository.findByEmail("ironman@avengers.com");
        if(Objects.isNull(user)) {
            User user1 = new User();
            user1.setName("tony");
            user1.setLastName("stark");
            user1.setEmail("ironman@avengers.com");
            user1.setPassword("ironman");
            user1.setRole(role1);
            users.add(user1);
        }

        user = userRepository.findByEmail("captainamerica@avengers.com");
        if(Objects.isNull(user)) {
            User user2 = new User();
            user2.setName("steve");
            user2.setLastName("rogers");
            user2.setEmail("captainamerica@avengers.com");
            user2.setPassword("captainamerica");
            user2.setRole(role2);
            users.add(user2);
        }

        user = userRepository.findByEmail("blackwidow@avengers.com");
        if(Objects.isNull(user)) {
            User user3 = new User();
            user3.setName("natasha");
            user3.setLastName("romanova");
            user3.setEmail("blackwidow@avengers.com");
            user3.setPassword("blackwidow");
            user3.setRole(role2);
            users.add(user3);
        }

        user = userRepository.findByEmail("hulk@avengers.com");
        if(Objects.isNull(user)) {
            User user4 = new User();
            user4.setName("bruce");
            user4.setLastName("banner");
            user4.setEmail("hulk@avengers.com");
            user4.setPassword("hulk");
            user4.setRole(role2);
            users.add(user4);
        }
        users.forEach(this::saveUser);
        System.out.println("dummy users inserted");
    }
}
