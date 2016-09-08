package co.org.ceindetec.combustible.modules.AgregarUsuario.ui;

import co.org.ceindetec.combustible.entities.Usuario;

/**
 * Created by Ceindetec02 on 19/08/2016.
 */
public interface AgregarUsuarioView {
    void showProgress(boolean status);

    void onAgregarUsuarioSuccess(Usuario usuario);

    void onAgregarUsuarioError(String errorMessage);
}
