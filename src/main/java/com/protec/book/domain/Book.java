package com.protec.book.domain;


import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="book")
public class Book implements Serializable {
    private static final Long serialVersionUID = 1L;

    // properties ------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String nome;
    @Column(nullable = true, length = 30)
    private String editora;
    @Column(length = 30)
    private String autor;
    @Column
    private Date dataPublicacao;
    @Column(length = 40)
    private String categoria;
    @Column(nullable = false)
    private BigDecimal valor;
   @Enumerated(EnumType.STRING)
    private EBookType tipo;

   // construtores

    public Book(){
    }

    // gettes and setters -------------------------


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public EBookType getTipo() {
        return tipo;
    }

    public void setTipo(EBookType tipo) {
        this.tipo = tipo;
    }

    // hasCode e equals


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(nome, book.nome) && Objects.equals(editora, book.editora) && Objects.equals(autor, book.autor) && Objects.equals(dataPublicacao, book.dataPublicacao) && Objects.equals(categoria, book.categoria) && Objects.equals(valor, book.valor) && tipo == book.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, editora, autor, dataPublicacao, categoria, valor, tipo);
    }
}
