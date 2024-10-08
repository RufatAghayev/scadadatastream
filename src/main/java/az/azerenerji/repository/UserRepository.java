package az.azerenerji.repository;

import az.azerenerji.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>findByUserName(String username);
    boolean existsByUserName(String userName);
//@EntityGraph(attributePaths = {"roles"})
//Optional<User> findByUserName(String email);
}
