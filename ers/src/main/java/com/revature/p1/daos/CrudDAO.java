package com.revature.p1.daos;

import java.util.List;

public interface CrudDAO <T> {
    void save(T obj);
    void delete(T obj);
    void update(T obj);
    List<T> findAll();
    T findById();

}
