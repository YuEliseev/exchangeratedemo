package ru.eliseev.exchangeratedemo.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;
import ru.eliseev.exchangeratedemo.model.entity.Request;

public interface RatesRepository extends CrudRepository <Request,Long> {

}
