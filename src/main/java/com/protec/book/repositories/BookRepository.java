package com.protec.book.repositories;

import com.protec.book.domain.Book;
import com.protec.book.domain.EBookType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBooksByCategoria(String categoria);
    List<Book> findBooksByTipo(EBookType tipo);

    @Query("select p from Book p where p.nome = ?1 and p.categoria = ?2")
    List<Book> findBookByNomeECategoria(String nome, String categoria);
}
