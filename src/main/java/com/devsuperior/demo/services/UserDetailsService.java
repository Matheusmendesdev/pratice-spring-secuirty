package com.devsuperior.demo.services;

import com.devsuperior.demo.entities.Role;
import com.devsuperior.demo.entities.User;
import com.devsuperior.demo.projections.UserDetailsProjections;
import com.devsuperior.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsService {

    @Autowired
    private UserRepository repository;

    public UserDetails loadUsernameByEmail(String userName){
        List<UserDetailsProjections> result = repository.searchUserAndRolesByEmail(userName);

        if (result.isEmpty()){
            throw new UsernameNotFoundException("Username not found");
        }

        User user = new User();
        user.setEmail(userName);
        user.setPassword(result.get(0).getPassword());

        for (UserDetailsProjections projections : result){
            user.addRole(new Role(projections.getRoleId(), projections.getAuthority()));
        }

        return user;
    }
}
