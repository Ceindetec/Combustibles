package co.org.ceindetec.combustible.modules.Login;

import android.content.Context;

import org.greenrobot.eventbus.Subscribe;

import co.org.ceindetec.combustible.lib.EventBus;
import co.org.ceindetec.combustible.lib.GreenRobotEventBus;

import co.org.ceindetec.combustible.modules.Login.events.LoginEvent;
import co.org.ceindetec.combustible.modules.Login.ui.LoginView;

/**
 * Created by Ceindetec02 on 16/08/2016.
 */
public class LoginPresenterImpl implements LoginPresenter {


    //Instanciamiento del EventBus
    private EventBus eventBus;

    //Instanciamiento del LoginView
    private LoginView loginView;

    //Instanciamiento del LoginInteractor
    private LoginInteractor loginInteractor;

    /**
     * Constructor de la clase
     *
     * @param loginView
     */
    public LoginPresenterImpl(LoginView loginView) {

        //Inicializacion de la vista
        this.loginView = loginView;

        //Inicializacion del interactuador
        this.loginInteractor = new LoginInteractorImpl();

        //Llamado al Singleton que obtiene la instancia del Bus de eventos
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate(Context context) {
        eventBus.register(this);
        loginInteractor.onCreate(context);
    }

    @Override
    public void onDestroy() {
        loginView = null;
        eventBus.unregister(this);
    }

    @Override
    public void onResume() {
        eventBus.register(this);
    }

    @Override
    public void onPause() {
        eventBus.unregister(this);
    }

    @Override
    public void validateLoginCombustibles(String usuario, String codigoSeguridad) {
        loginView.showProgress(true);
        loginInteractor.validateLoginCombustibles(usuario, codigoSeguridad);
    }

    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent loginEvent) {
        switch (loginEvent.getEventType()) {
            case LoginEvent.onSignInSuccessUsuario:
                onSignInSuccessUsuario();
                break;
            case LoginEvent.onSignInSuccessAdmin:
                onSignInSuccessAdmin();
                break;
            case LoginEvent.onSignInError:
                onSignInError(loginEvent.getErrorMessage());
                break;
        }
    }

    private void onSignInSuccessUsuario() {
        loginView.showProgress(false);
        loginView.navigateToUsuarioActivity();
    }

    private void onSignInSuccessAdmin() {
        loginView.showProgress(false);
        loginView.navigateToSuperActivity();
    }

    private void onSignInError(String errorMessage) {
        loginView.showProgress(false);
        loginView.showErrorMessage(errorMessage);
    }
}
