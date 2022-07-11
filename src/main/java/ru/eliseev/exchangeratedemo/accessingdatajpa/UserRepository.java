package ru.eliseev.exchangeratedemo.accessingdatajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.eliseev.exchangeratedemo.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
