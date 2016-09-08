package co.org.ceindetec.combustible.modules.AgregarUsuario;

import android.content.Context;

/**
 * Created by Ceindetec02 on 19/08/2016.
 */
public class AgregarUsuarioInteractorImpl implements AgregarUsuarioInteractor {

    AgregarUsuarioRepository agregarUsuarioRepository;

    public AgregarUsuarioInteractorImpl() {
        this.agregarUsuarioRepository = new AgregarUsuarioRepositoryImpl();
    }

    @Override
    public void onCreate(Context context) {
        agregarUsuarioRepository.onCreate(context);
    }

    @Override
    public void agregarUsuario(String nombreUsuario, String codigoSeguridadUsuario) {
        agregarUsuarioRepository.agregarUsuario(nombreUsuario, codigoSeguridadUsuario);
    }
}
