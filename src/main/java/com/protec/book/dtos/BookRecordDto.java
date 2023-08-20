package com.protec.book.dtos;

import com.protec.book.domain.Book;
import com.protec.book.domain.EBookType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

public record BookRecordDto(
       @NotNull(message = "nome não pode ser nulo")
       @Size(max = 50, message = "Máximo 50 caracteres")
       String nome,
       Date dataPublicacao,
       @Size(max = 30, message = "categoria deve ter no máximo 30 caracteres")
       String categoria,
        @NotNull (message = "Um valor deve ser informado")
        BigDecimal valor,
        @NotNull (message = "O tipo de livro deve ser informado")
        EBookType tipo
        ) {

    @NotNull
    public Book ToEntity() {

        Book book = new Book();
       /*
        book.setNome(this.nome);
        book.setCategoria(this.categoria);
        book.setDataPublicacao(this.dataPublicacao);
        book.setValor(this.valor);
        book.setTipo(this.tipo);
        */

        BeanUtils.copyProperties(this, book);

        return book;

    }


}
