package co.org.ceindetec.combustible.modules.ConfiguracionUsuario.ui;

import java.util.List;

import co.org.ceindetec.combustible.entities.Usuario;

/**
 * Created by Ceindetec02 on 17/08/2016.
 */
public interface ConfiguracionUsuarioView {

    void addUsuarioList(List<Usuario> usuarioList);

    void errorUsuarioList(String errorMessage);

    void onDelUsuarioSuccess();

    void onDelUsuarioError(String errorMessage);

}
