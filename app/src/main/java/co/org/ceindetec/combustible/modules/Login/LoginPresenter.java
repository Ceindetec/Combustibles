package co.org.ceindetec.combustible.modules.Login;

import android.content.Context;

import co.org.ceindetec.combustible.modules.Login.events.LoginEvent;

/**
 * Created by Ceindetec02 on 16/08/2016.
 */
public interface LoginPresenter {

    void onCreate(Context context);

    void onDestroy();

    void onResume();

    void onPause();

    void validateLoginCombustibles(String usuario, String codigoSeguridad);

    void onEventMainThread(LoginEvent loginEvent);
}
