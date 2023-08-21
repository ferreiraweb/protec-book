package com.protec.book.dtos;

import com.protec.book.domain.Book;
import com.protec.book.domain.EBookType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;

public record BookRecordDto(
        Optional<Long> id,
        @NotNull(message = "nome: não pode ser nulo")
        @NotEmpty(message = "nome: deve ser informado")
        @Size(min = 3, max = 50, message = "nome: dever ter no Mínimo 3 e no Máximo 50 caracteres")
        String nome,
        Date dataPublicacao,
        @Size(max = 30, message = "categoria: deve ter no máximo 30 caracteres")
        String categoria,
        @NotNull(message = "valor: deve ser informado")
        @Positive(message = "O valor deve ser maior que zero")
        BigDecimal valor,
        @NotNull(message = "tipo: deve ser informado")
        EBookType tipo
) {

    @NotNull
    public Book dtoToBook() {
        Book book = new Book();
        BeanUtils.copyProperties(this, book);

        return book;
    }

    public static BookRecordDto bookToDto(Book entity) {

        BookRecordDto dto = new BookRecordDto(
                Optional.ofNullable(entity.getId()),
                entity.getNome(),
                entity.getDataPublicacao(),
                entity.getCategoria(),
                entity.getValor(),
                entity.getTipo()
        );

        return dto;
    }




}
