package co.org.ceindetec.combustible.modules.AgregarUsuario;

import android.content.Context;

/**
 * Created by Ceindetec02 on 19/08/2016.
 */
public interface AgregarUsuarioRepository {
    void onCreate(Context context);

    void agregarUsuario(String nombreUsuario, String codigoSeguridadUsuario);
}
