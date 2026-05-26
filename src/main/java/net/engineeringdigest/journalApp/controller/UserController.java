package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        System.out.println("Getting all users in controller");
        return userService.getAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        System.out.println("Creating user in controller");
        userService.saveEntry(user);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody User user , @PathVariable String username){
        // find existing user by username
        User userInDb = userService.findByUsername(username);

        if(user.getPassword() != null){
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userService.saveEntry(userInDb);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
