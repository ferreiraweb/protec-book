package com.protec.book.repositories;

import com.protec.book.domain.Book;
import com.protec.book.domain.EBookType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    private Book book;
    private List<Book> listBooks;

    @BeforeEach
    void before() {
        book = new Book("UML 2 - uma abordagem prática ",
                new Date(2021, 2, 10),
                "Eng SW", new BigDecimal(156),
                EBookType.TECNICO);

        String[] categories = {"", "Eng SW", "Eng SW", "Design Pattern"};

        listBooks = new ArrayList<Book>();
        for (int i = 1; i < 4; i++) {
            Book book = new Book("UML 2 - uma abordagem pratica " + i,
                    new Date(2021, 2 + i, 10 + i),
                    categories[i], new BigDecimal(22 * i),
                    EBookType.TECNICO);

            listBooks.add(book);
        }
    }

    @Test
    void deve_salvar_book() {

        Book savedBook = repository.save(book);

        assertNotNull(savedBook);
        assertTrue(savedBook.getId() > 0);
    }

    @Test
    void deve_retornar_lista_de_books() {

        /* Given/Arrange */

        listBooks.forEach(itemBook -> {
            repository.save(itemBook);
        });

        /* when/act */
        List<Book> returnBooks = repository.findAll();

        // then/Assert
        assertNotNull(returnBooks);
        assertEquals(3, returnBooks.size());
        assertArrayEquals(listBooks.toArray(), returnBooks.toArray());
    }

    @Test
    void deve_retornar_book_buscado_por_id() {

        //When
        repository.save(book);
        System.out.println(book);
        Book foundBook = repository.findById(book.getId()).get();
        /* Then */
        assertNotNull(foundBook);
        assertTrue(foundBook.getId() > 0);
        assertEquals(book.getId(), foundBook.getId());
    }

    @Test
    void deve_retornar_lista_books_em_busca_por_categoria() {

        /* Given */
        listBooks.forEach(book -> repository.save(book));

        /* when */
        List<Book> filteredBooks = repository.findBooksByCategoria("Eng SW");

        System.out.println(filteredBooks);

        /* then */
        assertNotNull(filteredBooks);
        assertEquals(2, filteredBooks.size());
    }

    @Test
    void deve_retornar_lista_vazia_em_busca_por_categoria_inexistente() {

        /* Given */
        listBooks.forEach(book -> repository.save(book));

        /* when */
        List<Book> filteredBooks = repository.findBooksByCategoria("inexistente");

        /* then */
        //assertNull(filteredBooks);
        assertEquals(0, filteredBooks.size());

    }

    @Test
    void deve_atualizar_book() {

        /* when */
        Book newBook = repository.save(book);

        newBook.setNome("Linux - a Biblia");
        newBook.setCategoria("SO");

        Book updatedBook = repository.save(newBook);

        /* then */
        assertNotNull(updatedBook);
        assertEquals("Linux - a Biblia", updatedBook.getNome());
        assertEquals("SO", updatedBook.getCategoria());
        assertEquals(newBook, updatedBook);
    }

    @Test
    void deve_remover_book() {

        /* when */
        repository.save(book);
        repository.deleteById(book.getId());


        Optional<Book> bookOptional = repository.findById(book.getId());

        /* then */
        assertTrue(bookOptional.isEmpty());

    }

    @Test
    void deve_retornar_book_por_nome_e_categoria() {

        /* when */
        listBooks.forEach(itemBook -> repository.save(itemBook));

        List<Book> foundBooks = repository
                .findBookByNomeECategoria("UML 2 - uma abordagem pratica 1", "Eng SW");

        System.out.println(foundBooks);
        /* then */
        assertNotNull(foundBooks);
        assertEquals(1, foundBooks.size());
    }
}
