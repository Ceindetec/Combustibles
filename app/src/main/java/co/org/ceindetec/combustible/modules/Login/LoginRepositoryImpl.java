package co.org.ceindetec.combustible.modules.Login;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import co.org.ceindetec.combustible.domain.SQLiteHelper;
import co.org.ceindetec.combustible.lib.EventBus;
import co.org.ceindetec.combustible.lib.GreenRobotEventBus;
import co.org.ceindetec.combustible.modules.Login.events.LoginEvent;

/**
 * Created by Ceindetec02 on 16/08/2016.
 */
public class LoginRepositoryImpl implements LoginRepository {

    private SQLiteHelper sqliteHelper;
    private static final String usuarioAdmin = "ADMIN";
    private static final String codigoSeguridadAdmin = "CEINDETEC2016*.";

    public LoginRepositoryImpl() {

    }

    @Override
    public void onCreate(Context context) {
        this.sqliteHelper = new SQLiteHelper(context);
    }

    @Override
    public void initAdmin() {

        Cursor adminCursor = sqliteHelper.verifyUsuario(usuarioAdmin, codigoSeguridadAdmin);
        if (adminCursor.getCount() == 0) {
            if (sqliteHelper.insertUsuario(usuarioAdmin, codigoSeguridadAdmin)) {
                Log.i("Usuario Admin: ", "Usuario Administrador Inicializado");
            }
        }
    }

    @Override
    public void validateLoginCombustibles(String usuario, String codigoSeguridad) {
        Cursor adminCursor = sqliteHelper.verifyUsuario(usuario, codigoSeguridad);
        if (adminCursor.getCount() == 1) {
            if (usuario.equals(usuarioAdmin)) {
                postEvent(LoginEvent.onSignInSuccessAdmin);
            } else {
                postEvent(LoginEvent.onSignInSuccessUsuario);
            }
        } else {
            postEvent(LoginEvent.onSignInError, "Usuario no existe en la App");
        }
    }

    private void postEvent(int type) {
        postEvent(type, null);
    }

    private void postEvent(int type, String errorMessage) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        if (errorMessage != null) {
            loginEvent.setErrorMessage(errorMessage);
        }
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }

}
