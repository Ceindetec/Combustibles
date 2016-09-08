package co.org.ceindetec.combustible.modules.Login;

import android.content.Context;

/**
 * Created by Ceindetec02 on 16/08/2016.
 */
public interface LoginInteractor {

    void onCreate(Context context);

    void validateLoginCombustibles(String usuario, String password);

}
