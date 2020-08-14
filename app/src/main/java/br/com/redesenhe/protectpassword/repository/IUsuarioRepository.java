package br.com.redesenhe.protectpassword.repository;

import br.com.redesenhe.protectpassword.model.Usuario;

public interface IUsuarioRepository {
    boolean existeUsuario();

    boolean salvar(Usuario usuario);

    Usuario buscar();

    boolean atualizar(Long idUser, String novaSenha);
}
