package br.com.redesenhe.protectpassword.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class Registro implements Serializable {

    private Long id;
    private String nome;
    private String usuario;
    private String url;
    private String senha;
    private String comentario;
    private Long idGrupo;
    private Date dataCriacao;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // CONSTRUCTOR
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @JsonCreator
    @Deprecated
    public Registro(
            @JsonProperty("id") final Long id,
            @JsonProperty("nome") final String nome,
            @JsonProperty("usuario") final String usuario,
            @JsonProperty("url") final String url,
            @JsonProperty("senha") final String senha,
            @JsonProperty("comentario") final String comentario,
            @JsonProperty("idGrupo") final Long idGrupo,
            @JsonProperty("dataCriacao") final Date dataCriacao
    ) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        this.url = url;
        this.senha = senha;
        this.comentario = comentario;
        this.idGrupo = idGrupo;
        this.dataCriacao = dataCriacao;
    }

    public Registro(final Builder builder) {
        this.id = builder.id;
        this.nome = builder.nome;
        this.usuario = builder.usuario;
        this.url = builder.url;
        this.senha = builder.senha;
        this.comentario = builder.comentario;
        this.idGrupo = builder.idGrupo;
        this.dataCriacao = builder.dataCriacao;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // GETTERS
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @JsonProperty("id")
    public Long getId() {
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

    @JsonProperty("idGrupo")
    public Long getIdGrupo() {
        return idGrupo;
    }

    @JsonProperty("dataCriacao")
    public Date getDataCriacao() {
        return dataCriacao;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // BUILDER
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static class Builder {

        private Long id;
        private String nome;
        private String usuario;
        private String url;
        private String senha;
        private String comentario;
        private Long idGrupo;
        private Date dataCriacao;

        public Builder() {
        }

        public Builder comId(final Long value) {
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

        public Builder comIdGrupo(final Long value) {
            this.idGrupo = value;
            return this;
        }

        public Builder comDataCriacao(final Date value) {
            this.dataCriacao = value;
            return this;
        }

        public Registro build() {

            return new Registro(this);
        }

    }
}
