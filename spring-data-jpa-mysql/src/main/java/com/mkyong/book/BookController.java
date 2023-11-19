package com.mkyong.book;

import com.mkyong.StartApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    private final Logger LOGGER = LoggerFactory.getLogger(StartApplication.class);
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Book> findById(@PathVariable Long id) throws UnknownHostException {
        LOGGER.info(String.format("Get book info by id %s", "{id}"));
        return bookService.findById(id);
    }

    // create a book
    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping
    public Book create(@RequestBody Book book) {
        LOGGER.info("Create book");
        return bookService.save(book);
    }

    // update a book
    @PutMapping
    public Book update(@RequestBody Book book) {
        LOGGER.info("Update book info");
        return bookService.save(book);
    }

    // delete a book
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        LOGGER.info("Delete book info");
        bookService.deleteById(id);
    }

    @GetMapping("/find/title/{title}")
    public List<Book> findByTitle(@PathVariable String title) {
        LOGGER.info(String.format("Get book info by title %s", "{title}"));
        return bookService.findByTitle(title);
    }

    @GetMapping("/find/date-after/{date}")
    public List<Book> findByPublishedDateAfter(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        LOGGER.info(String.format("Get book info after date %s", "{date}"));
        return bookService.findByPublishedDateAfter(date);
    }

}

