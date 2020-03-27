package com.project.openbook.repository;

import com.project.openbook.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {
    Page<Book> findBooksByTitleIgnoreCaseContaining(String title, Pageable pageable);
    Book getOne(int id);
}
