package com.project.openbook.repository;

import com.project.openbook.model.Book;
import com.project.openbook.model.ReadShelf;
import com.project.openbook.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReadShelfRepository extends CrudRepository<ReadShelf, Integer> {
    ReadShelf deleteById(int id);
    List<ReadShelf> getReadShelfByUser(User user);
}
