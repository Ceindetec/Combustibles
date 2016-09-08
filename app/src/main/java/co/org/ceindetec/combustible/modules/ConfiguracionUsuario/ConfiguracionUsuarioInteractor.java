package co.org.ceindetec.combustible.modules.ConfiguracionUsuario;

import android.content.Context;

/**
 * Created by Ceindetec02 on 17/08/2016.
 */
public interface ConfiguracionUsuarioInteractor {

    void onCreate(Context context);

    void obtenerListadoUsuarios();

    void eliminarUsuario(int idUsuario);
}
