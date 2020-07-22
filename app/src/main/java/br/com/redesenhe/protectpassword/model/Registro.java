package br.com.redesenhe.protectpassword.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

public class Registro implements Serializable {

    private String id;
    private String nome;
    private String usuario;
    private String url;
    private String senha;
    private String comentario;
    private LocalDateTime dataCriacao;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // CONSTRUCTOR
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @JsonCreator
    @Deprecated
    public Registro(
            @JsonProperty("id") final String id,
            @JsonProperty("nome") final String nome,
            @JsonProperty("usuario") final String usuario,
            @JsonProperty("url") final String url,
            @JsonProperty("senha") final String senha,
            @JsonProperty("comentario") final String comentario,
            @JsonProperty("dataCriacao") final LocalDateTime dataCriacao
    ) {
        this.id = requireNonNull(id, "Id não pode ser nulo");
        this.nome = nome;
        this.usuario = usuario;
        this.url = url;
        this.senha = senha;
        this.comentario = comentario;
        this.dataCriacao = dataCriacao;
    }

    public Registro(final Builder builder) {
        this.id = builder.id;
        this.nome = builder.nome;
        this.usuario = builder.usuario;
        this.url = builder.url;
        this.senha = builder.senha;
        this.comentario = builder.comentario;
        this.dataCriacao = builder.dataCriacao;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // GETTERS
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("nome")
    public String getNome() {
        return nome;
    }

    @JsonProperty("usuario")
    public String getUsuario() {
        return usuario;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("senha")
    public String getSenha() {
        return senha;
    }

    @JsonProperty("comentario")
    public String getComentario() {
        return comentario;
    }

    @JsonProperty("dataCriacao")
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // BUILDER
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static class Builder {

        private String id;
        private String nome;
        private String usuario;
        private String url;
        private String senha;
        private String comentario;
        private LocalDateTime dataCriacao;

        public Builder() {}

        public Builder comId(final String value) {
            this.id = value;
            return this;
        }

        public Builder comNome(final String value) {
            this.nome = value;
            return this;
        }

        public Builder comUsuario(final String value) {
            this.usuario = value;
            return this;
        }

        public Builder comUrl(final String value) {
            this.url = value;
            return this;
        }

        public Builder comSenha(final String value) {
            this.senha = value;
            return this;
        }

        public Builder comComentario(final String value) {
            this.comentario = value;
            return this;
        }

        public Builder comDataCriacao(final LocalDateTime value) {
            this.dataCriacao = value;
            return this;
        }

        public Registro build() {

            return new Registro(this);
        }

    }
}
