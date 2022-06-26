package ru.eliseev.exchangeratedemo.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;
import ru.eliseev.exchangeratedemo.accessingdatajpa.entity.Request;

public interface RatesRepository extends CrudRepository <Request,Long> {

}
