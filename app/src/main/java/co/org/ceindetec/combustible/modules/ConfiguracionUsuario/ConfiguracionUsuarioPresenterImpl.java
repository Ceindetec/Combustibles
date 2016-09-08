package co.org.ceindetec.combustible.modules.ConfiguracionUsuario;

import android.content.Context;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import co.org.ceindetec.combustible.entities.Usuario;
import co.org.ceindetec.combustible.lib.EventBus;
import co.org.ceindetec.combustible.lib.GreenRobotEventBus;
import co.org.ceindetec.combustible.modules.ConfiguracionUsuario.events.ConfiguracionUsuarioEvent;
import co.org.ceindetec.combustible.modules.ConfiguracionUsuario.ui.ConfiguracionUsuarioView;

/**
 * Created by Ceindetec02 on 17/08/2016.
 */
public class ConfiguracionUsuarioPresenterImpl implements ConfiguracionUsuarioPresenter {

    //Instanciamiento del EventBus
    private EventBus eventBus;

    //Instanciamiento del PlayListView
    private ConfiguracionUsuarioView configuracionUsuarioView;

    //Instanciamiento del PlayListInteractor
    private ConfiguracionUsuarioInteractor configuracionUsuarioInteractor;

    /**
     * Constructor de la clase
     *
     * @param configuracionUsuarioView
     */
    public ConfiguracionUsuarioPresenterImpl(ConfiguracionUsuarioView configuracionUsuarioView) {

        //Inicializacion de la vista
        this.configuracionUsuarioView = configuracionUsuarioView;

        //Inicializacion del interactuador
        this.configuracionUsuarioInteractor = new ConfiguracionUsuarioInteractorImpl();

        //Llamado al Singleton que obtiene la instancia del Bus de eventos
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate(Context context) {
        eventBus.register(this);
        configuracionUsuarioInteractor.onCreate(context);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        configuracionUsuarioView = null;
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
    public void obtenerListadoUsuarios() {
        configuracionUsuarioInteractor.obtenerListadoUsuarios();
    }
    @Override
    public void eliminarUsuario(int idUsuario) {
        configuracionUsuarioInteractor.eliminarUsuario(idUsuario);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ConfiguracionUsuarioEvent configuracionUsuarioEvent) {
        List<Usuario> usuarioList = configuracionUsuarioEvent.getUsuarioList();
        switch (configuracionUsuarioEvent.getEventType()) {
            case ConfiguracionUsuarioEvent.onGetUsuarioSuccess:
                onGetUsuarioSuccess(usuarioList);
                break;
            case ConfiguracionUsuarioEvent.onGetUsuarioError:
                onGetUsuarioError(configuracionUsuarioEvent.getErrorMessage());
                break;
            case ConfiguracionUsuarioEvent.onDelUsuarioSuccess:
                onDelUsuarioSuccess();
                break;
            case ConfiguracionUsuarioEvent.onDelUsuarioError:
                onDelUsuarioError(configuracionUsuarioEvent.getErrorMessage());
                break;
        }
    }

    private void onGetUsuarioSuccess(List<Usuario> usuarioList) {
        configuracionUsuarioView.addUsuarioList(usuarioList);
    }

    private void onGetUsuarioError(String errorMessage)  {
        configuracionUsuarioView.errorUsuarioList(errorMessage);
    }

    private void onDelUsuarioSuccess() {
        configuracionUsuarioView.onDelUsuarioSuccess();
    }

    private void onDelUsuarioError(String errorMessage) {
        configuracionUsuarioView.onDelUsuarioError(errorMessage);
    }


}
