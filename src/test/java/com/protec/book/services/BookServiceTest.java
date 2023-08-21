package com.protec.book.services;

import com.protec.book.domain.Book;
import com.protec.book.domain.EBookType;
import com.protec.book.dtos.BookRecordDto;
import com.protec.book.exceptions.BookNameCannotBeRepeatedException;
import com.protec.book.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {


    @Mock
    private BookRepository repository;


    @InjectMocks
    private BookService service;


    private Book book;
    private List<Book> listBooks;

    @BeforeEach
    private void before() {

        book = new Book(
                "UML 2 - uma abordagem prática",
                new Date(2021, 2, 10),
                "Eng SW",
                new BigDecimal(156),
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

    /* ------------------------------------------------------------- */

    @Test
    void findById_deve_retornar_um_book_buscado_pelo_id() {
        /* when */
        when(repository.findById(anyLong())).thenReturn(Optional.of(book));
        BookRecordDto returnedBookDto = service.findById(1L);

        /* then */
        assertEquals(BookRecordDto.bookToDto(book), returnedBookDto);
    }

    @Test
    void save_deve_salvar_book() {

        /* when */
        BookRecordDto bookDto = BookRecordDto.bookToDto(book);
        when(repository.save(book)).thenReturn(book);

        BookRecordDto returnedBookDto = service.save(bookDto);

        /* then */
        assertNotNull(returnedBookDto);
        assertEquals(EBookType.TECNICO, returnedBookDto.tipo());
        assertEquals(returnedBookDto, bookDto);
    }

    @Test
    void save_deve_lancar_exception_para_book_nome_repetido() {

        /* when */
        BookRecordDto bookDto = BookRecordDto.bookToDto(book);
        when(repository.findBookByNome(bookDto.nome())).thenReturn(Optional.of(book));

        /* then */
        assertThrows(BookNameCannotBeRepeatedException.class,
                () -> service.save(bookDto),
                () -> "Nome de livro já cadastrado");

        verify(repository, never()).save(any(Book.class));

    }

    @Test
    void getAll_deve_retornar_lista_de_livros() {

        /* when */
        when(repository.findAll()).thenReturn(listBooks);
        List<BookRecordDto> returnedListBooks = service.getAll();

        List<BookRecordDto> listBooksDTO = listBooks
                .stream()
                .map(book -> BookRecordDto.bookToDto(book))
                .toList();

        /* then */
        assertTrue(returnedListBooks.size() > 0);
        assertEquals(listBooks.size(), returnedListBooks.size());
        assertEquals(listBooksDTO, returnedListBooks);

    }

    @Test
    void update_deve_atualizar_um_livro() {

        book.setId(1L);
        System.out.println(book);
        /* when */
        when(repository.findById(anyLong())).thenReturn(Optional.of(book));

        book.setNome("Linux - a Bíblia");
        book.setCategoria("Linguagem programacao");

        when(repository.save(book)).thenReturn(book);

        BookRecordDto updatedBook = service.update(BookRecordDto.bookToDto(book));

        System.out.println(updatedBook);

        assertNotNull(updatedBook);
        assertEquals("Linux - a Bíblia", updatedBook.nome());
        assertEquals("Linguagem programacao", updatedBook.categoria());
    }

    @Test
    void delete_deve_remover_um_livro() {
        service.delete(1L);
        verify(repository, times(1)).deleteById(1L);
    }

}
