package br.com.redesenhe.protectpassword.repository;

import java.util.List;

import br.com.redesenhe.protectpassword.model.Grupo;

public interface IGrupoRepository {
    boolean salvar(Grupo grupo);

    List<Grupo> buscaTodos();

    boolean deleta(long idGrupo);
}
