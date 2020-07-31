package br.com.redesenhe.protectpassword.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class Usuario implements Serializable {

    private Long id;
    private String device;
    private String senha;
    private Date dataCriacao;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // CONSTRUCTOR
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @JsonCreator
    @Deprecated
    public Usuario(
            @JsonProperty("id") final Long id,
            @JsonProperty("device") final String device,
            @JsonProperty("senha") final String senha,
            @JsonProperty("dataCriacao") final Date dataCriacao
    ) {
        this.id = id;
        this.device = device;
        this.senha = senha;
        this.dataCriacao = dataCriacao;
    }

    public Usuario(final Builder builder) {
        this.id = builder.id;
        this.device = builder.device;
        this.senha = builder.senha;
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

    @JsonProperty("device")
    public String getDevice() {
        return device;
    }

    @JsonProperty("senha")
    public String getSenha() {
        return senha;
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
        private String device;
        private String senha;
        private Date dataCriacao;

        public Builder() {
        }

        public Builder comId(final Long value) {
            this.id = value;
            return this;
        }

        public Builder comDevice(final String value) {
            this.device = value;
            return this;
        }

        public Builder comSenha(final String value) {
            this.senha = value;
            return this;
        }

        public Builder comDataCriacao(final Date value) {
            this.dataCriacao = value;
            return this;
        }

        public Usuario build() {

            return new Usuario(this);
        }

    }
}
