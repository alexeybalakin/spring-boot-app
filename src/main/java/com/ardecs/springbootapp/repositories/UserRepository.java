package com.ardecs.springbootapp.repositories;


import com.ardecs.springbootapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.name like ?1%")
    List<User> findByNameStartsWith (String login);

    User findByLogin (String login);
}
