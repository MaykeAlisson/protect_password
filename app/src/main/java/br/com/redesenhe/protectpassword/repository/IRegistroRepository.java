package br.com.redesenhe.protectpassword.repository;

import java.util.List;

import br.com.redesenhe.protectpassword.model.Registro;

public interface IRegistroRepository {
    boolean salvar(Registro registro);

    List<Registro> buscaTodosPorIdGrupo(Long idGrupo);
}
