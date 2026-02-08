package com.aws.app.springdbcross.Controller;

import com.aws.app.springdbcross.Eo.User;
import com.aws.app.springdbcross.Repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class UserController {

    private final UserRepo repo;

    public UserController(UserRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/users")
    public List<User> fetchUsers(){
        log.info("Users are fetched");
        return repo.findAll();
    }

    @PostMapping("/saveUser")
    public String saveUser(@RequestBody User user){
        repo.save(user);
        log.info("Following user is saved {}", user.getName());
        return "User is saved";
    }
}
