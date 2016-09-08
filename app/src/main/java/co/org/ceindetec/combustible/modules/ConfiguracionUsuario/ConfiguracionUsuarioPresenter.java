package co.org.ceindetec.combustible.modules.ConfiguracionUsuario;

import android.content.Context;

import co.org.ceindetec.combustible.modules.ConfiguracionUsuario.events.ConfiguracionUsuarioEvent;

/**
 * Created by Ceindetec02 on 17/08/2016.
 */
public interface ConfiguracionUsuarioPresenter {

    void onCreate(Context context);

    void onDestroy();

    void onPause();

    void onResume();

    void obtenerListadoUsuarios();

    void eliminarUsuario(int idUsuario);

    void onEventMainThread(ConfiguracionUsuarioEvent configuracionUsuarioEvent);

}
