package ru.eliseev.exchangeratedemo.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;
import ru.eliseev.exchangeratedemo.accessingdatajpa.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
