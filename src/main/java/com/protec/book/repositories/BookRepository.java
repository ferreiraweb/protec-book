package com.protec.book.repositories;

import com.protec.book.domain.Book;
import com.protec.book.domain.EBookType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    public List<Book> findBooksByCategoria(String categoria);
    public List<Book> findBooksByTipo(EBookType tipo);
}
