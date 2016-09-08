package co.org.ceindetec.combustible.modules.AgregarUsuario;

import android.content.Context;

import co.org.ceindetec.combustible.modules.AgregarUsuario.events.AgregarUsuarioEvent;

/**
 * Created by Ceindetec02 on 19/08/2016.
 */
public interface AgregarUsuarioPresenter {
    void onCreate(Context context);

    void onDestroy();

    void onPause();

    void onResume();

    void agregarUsuario(String nombreUsuario, String codigoSeguridadUsuario);

    void onEventMainThread(AgregarUsuarioEvent agregarUsuarioEvent);
}
