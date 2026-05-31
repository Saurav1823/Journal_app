package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    // Spring Data will create an implementation to find a user by the `username` field
    User findByUserName(String username);
    void deleteByUserName(String username);
}
