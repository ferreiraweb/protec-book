package com.protec.book.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.protec.book.controller.BookController;
import com.protec.book.domain.Book;
import com.protec.book.domain.EBookType;
import com.protec.book.dtos.BookRecordDto;
import com.protec.book.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    /* Injections */
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BookService service;

    /* properties */
    private Book book;
    private List<Book> listBooks;

    /* BeforeEach */
    @BeforeEach
    void before() {

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

    /* ---------------- Tests ------------------------- */


    @Test
    void save_deve_salvar_um_livro() throws Exception {

        /* given */
        when(service.save(BookRecordDto.bookToDto(book)))
                .thenReturn(BookRecordDto.bookToDto(book));

        /* when */
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(BookRecordDto.bookToDto(book))));

        /* then */
        result.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.nome").value(book.getNome()));
    }

    @Test
    void findById_deve_retornar_book() throws Exception {

        /* given */
        when(service.findById(anyLong()))
                .thenReturn(BookRecordDto.bookToDto(book));

        /* when */
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/books/{id}", anyLong()));

        /* then */
        result.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void update_deve_atualizar_livro() throws Exception {

        /* given */
        BookRecordDto bookDto = BookRecordDto.bookToDto(book);
        when(service.update(bookDto)).thenReturn(bookDto);

        /* when */

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)));

        /* then */

        result.andDo(print()).andExpect(status().isAccepted());

    }

    @Test
    void delete_deve_remover_livro() throws Exception {

        /* when */

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/books/{id}", anyLong())
        );

        /* then */
        result.andExpect(status().isNoContent());

    }
}
