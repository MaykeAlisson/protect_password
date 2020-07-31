package br.com.redesenhe.protectpassword.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class Grupo implements Serializable {

    private Long id;
    private String nome;
    private Date dataCriacao;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // CONSTRUCTOR
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @JsonCreator
    @Deprecated
    public Grupo(
            @JsonProperty("id") final Long id,
            @JsonProperty("nome") final String nome,
            @JsonProperty("dataCriacao") final Date dataCriacao
    ) {
        this.id = id;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
    }

    public Grupo(final Builder builder) {
        this.id = builder.id;
        this.nome = builder.nome;
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

        public Builder comDataCriacao(final Date value) {
            this.dataCriacao = value;
            return this;
        }

        public Grupo build() {

            return new Grupo(this);
        }

    }
}
