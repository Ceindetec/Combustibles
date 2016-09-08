package co.org.ceindetec.combustible.modules.AgregarUsuario;

import android.content.Context;

import org.greenrobot.eventbus.Subscribe;

import co.org.ceindetec.combustible.entities.Usuario;
import co.org.ceindetec.combustible.lib.EventBus;
import co.org.ceindetec.combustible.lib.GreenRobotEventBus;
import co.org.ceindetec.combustible.modules.AgregarUsuario.events.AgregarUsuarioEvent;
import co.org.ceindetec.combustible.modules.AgregarUsuario.ui.AgregarUsuarioView;

/**
 * Created by Ceindetec02 on 19/08/2016.
 */
public class AgregarUsuarioPresenterImpl implements AgregarUsuarioPresenter {
    private EventBus eventBus;
    private AgregarUsuarioView agregarUsuarioView;
    private AgregarUsuarioInteractor agregarUsuarioInteractor;

    public AgregarUsuarioPresenterImpl(AgregarUsuarioView agregarUsuarioView) {
        this.agregarUsuarioView = agregarUsuarioView;
        this.agregarUsuarioInteractor = new AgregarUsuarioInteractorImpl();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate(Context context) {
        eventBus.register(this);
        agregarUsuarioInteractor.onCreate(context);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        agregarUsuarioView = null;
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
    public void agregarUsuario(String nombreUsuario, String codigoSeguridadUsuario) {
        agregarUsuarioView.showProgress(true);
        agregarUsuarioInteractor.agregarUsuario(nombreUsuario, codigoSeguridadUsuario);
    }

    @Override
    @Subscribe
    public void onEventMainThread(AgregarUsuarioEvent agregarUsuarioEvent) {
        if (agregarUsuarioView != null) {
            agregarUsuarioView.showProgress(false);
            switch (agregarUsuarioEvent.getEventType()) {
                case AgregarUsuarioEvent.onAddUsuarioSuccess:
                    onAddUsuarioSuccess(agregarUsuarioEvent.getUsuario());
                    break;
                case AgregarUsuarioEvent.onAddUsuarioError:
                    onAddUsuarioError(agregarUsuarioEvent.getErrorMessage());
                    break;
            }
        }
    }

    private void onAddUsuarioSuccess(Usuario usuario) {
        agregarUsuarioView.onAgregarUsuarioSuccess(usuario);
    }

    private void onAddUsuarioError(String errorMessage) {
        agregarUsuarioView.onAgregarUsuarioError(errorMessage);
    }
}
