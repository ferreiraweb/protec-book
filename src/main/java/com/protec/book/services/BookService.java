package com.protec.book.services;

import com.protec.book.domain.Book;
import com.protec.book.dtos.BookRecordDto;
import com.protec.book.exceptions.BookNameCannotBeRepeatedException;
import com.protec.book.exceptions.BookNotExistsException;
import com.protec.book.exceptions.IdMustBeNonNullException;
import com.protec.book.repositories.BookRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public BookRecordDto save(@NotNull BookRecordDto bookDto) {

        Optional<Book> savedBook = repository.findBookByNome(bookDto.nome());

        if (savedBook.isPresent()) {
            throw new BookNameCannotBeRepeatedException("Nome do livro já existe (" + bookDto.nome() + ")");
        }

        Book book = repository.save(bookDto.dtoToBook());

        return BookRecordDto.bookToDto(book);
    }

    public BookRecordDto findById(Long id) throws BookNotExistsException {
        Book book = this.repository.findById(id)
                .orElseThrow(() -> new BookNotExistsException("Livro não encontrado na base de dados"));

        return BookRecordDto.bookToDto(book);
    }

    public BookRecordDto update(BookRecordDto dto) {

        if (dto.id().isEmpty()) {
            throw new IdMustBeNonNullException("O Id do livro a ser editado deve ser informado");
        }

        Book book = this.repository.findById(dto.id().get()).orElseThrow(
                () -> new BookNotExistsException("Livro não encontrado para edição"));

        book.setNome(dto.nome());
        book.setTipo(dto.tipo());
        book.setDataPublicacao(dto.dataPublicacao());
        book.setCategoria(dto.categoria());
        book.setValor(dto.valor());
        book.setTipo(dto.tipo());

        return BookRecordDto.bookToDto(this.repository.save(book));
    }

    public List<BookRecordDto> getAll() {
        return this.repository.findAll()
                .stream()
                .map(book -> BookRecordDto.bookToDto(book))
                .toList();
    }

    public void delete(Long id) {
        this.repository.deleteById(id);
    }
}
