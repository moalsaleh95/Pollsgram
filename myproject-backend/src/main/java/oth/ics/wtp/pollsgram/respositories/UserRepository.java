package oth.ics.wtp.pollsgram.repositories;

import oth.ics.wtp.pollsgram.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username); // needed for login

    boolean existsByUsername(String username); // check for uniqueness before creating a new user
}