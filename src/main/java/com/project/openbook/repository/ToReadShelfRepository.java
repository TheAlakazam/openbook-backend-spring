package com.project.openbook.repository;

import com.project.openbook.model.ToReadShelf;
import com.project.openbook.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ToReadShelfRepository extends CrudRepository<ToReadShelf, Integer> {
    ToReadShelf deleteById(int id);
    List<ToReadShelf> getToReadShelfByUser(User user);
}
