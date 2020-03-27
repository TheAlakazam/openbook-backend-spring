package com.project.openbook.controller;

import com.project.openbook.dto.CurrentShelfToReadShelfDto;
import com.project.openbook.dto.ReadBookDto;
import com.project.openbook.dto.ToReadToCurrentShelfDto;
import com.project.openbook.model.*;
import com.project.openbook.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/user")
public class UserController {
    private UserRepository userRepository;
    private ReadShelfRepository readShelfRepository;
    private BookRepository bookRepository;
    private CurrentShelfRepository currentShelfRepository;
    private ToReadShelfRepository toReadShelfRepository;
    @Autowired
    public UserController(UserRepository userRepository, ReadShelfRepository readShelfRepository,
                          BookRepository bookRepository, CurrentShelfRepository currentShelfRepository,
                          ToReadShelfRepository toReadShelfRepository) {
        this.userRepository = userRepository;
        this.readShelfRepository = readShelfRepository;
        this.bookRepository = bookRepository;
        this.currentShelfRepository = currentShelfRepository;
        this.toReadShelfRepository = toReadShelfRepository;
    }

    @GetMapping("readBooks")
    public List<ReadShelf> getReadBooks(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        return readShelfRepository.getReadShelfByUser(user);
    }

    @GetMapping("currentBooks")
    public List<CurrentShelf> getCurrentBooks(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        return currentShelfRepository.getCurrentShelfByUser(user);
    }

    @GetMapping("toReadBooks")
    public List<ToReadShelf> getToReadBooks(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        return toReadShelfRepository.getToReadShelfByUser(user);
    }

    @PostMapping("addBooksRead")
    public void addReadBook(Principal principal, @RequestBody ReadBookDto readBookDto) {
        ReadShelf readShelf = new ReadShelf();
        readShelf.setUser(userRepository.findByUsername(principal.getName()));
        readShelf.setBook(bookRepository.getOne(readBookDto.bookid));
        readShelf.setStart_date(readBookDto.start_date);
        readShelf.setEnd_date(readBookDto.end_date);
        readShelfRepository.save(readShelf);
    }

    @PostMapping("addBooksToRead")
    public void addToReadBook(Principal principal, @RequestBody ReadBookDto readBookDto) {
        ToReadShelf readShelf = new ToReadShelf();
        readShelf.setUser(userRepository.findByUsername(principal.getName()));
        readShelf.setBook(bookRepository.getOne(readBookDto.bookid));
        toReadShelfRepository.save(readShelf);
    }

    @PostMapping("addBookCurrentRead")
    public void addCurrentBook(Principal principal, @RequestBody ReadBookDto readBookDto) {
        CurrentShelf readShelf = new CurrentShelf();
        readShelf.setUser(userRepository.findByUsername(principal.getName()));
        readShelf.setBook(bookRepository.getOne(readBookDto.bookid));
        readShelf.setStart_date(readBookDto.start_date);
        currentShelfRepository.save(readShelf);
    }

    @PostMapping("startReadingFromToRead")
    @Transactional
    public void startReadingFromToRead(Principal principal, @RequestBody ToReadToCurrentShelfDto toReadToCurrentShelfDto) {
        ReadBookDto readBookDto = new ReadBookDto();
        readBookDto.bookid = toReadToCurrentShelfDto.bookId;
        readBookDto.start_date = toReadToCurrentShelfDto.startDate;
        this.addCurrentBook(principal, readBookDto);
        this.deleteToReadBook(toReadToCurrentShelfDto.shelfId);
    }

    @PostMapping("finishReadingFromCurrent")
    @Transactional
    public void finishReadingFromToRead(Principal principal, @RequestBody CurrentShelfToReadShelfDto currentShelfToReadShelfDto) {
        ReadBookDto readBookDto = new ReadBookDto();
        readBookDto.start_date = currentShelfToReadShelfDto.startDate;
        readBookDto.end_date = currentShelfToReadShelfDto.endDate;
        readBookDto.bookid = currentShelfToReadShelfDto.bookId;
        this.addReadBook(principal, readBookDto);
        this.deleteCurrentBook(currentShelfToReadShelfDto.shelfId);
    }

    @DeleteMapping("removeBookFromReadShelf")
    @Transactional
    public void deleteReadBook(@RequestParam(name = "shelfId") int shelfId) {
        readShelfRepository.deleteById(shelfId);
    }

    @DeleteMapping("removeBookFromCurrentShelf")
    @Transactional
    public void deleteCurrentBook(@RequestParam(name = "shelfId") int shelfId) {
        currentShelfRepository.deleteById(shelfId);
    }

    @DeleteMapping("removeBookFromToReadShelf")
    @Transactional
    public void deleteToReadBook(@RequestParam(name = "shelfId") int shelfId) {
        toReadShelfRepository.deleteById(shelfId);
    }
}
