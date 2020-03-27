package com.project.openbook.repository;

import com.project.openbook.model.CurrentShelf;
import com.project.openbook.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CurrentShelfRepository extends CrudRepository<CurrentShelf, Integer> {
    CurrentShelf deleteById(int id);
    List<CurrentShelf> getCurrentShelfByUser(User user);
}
