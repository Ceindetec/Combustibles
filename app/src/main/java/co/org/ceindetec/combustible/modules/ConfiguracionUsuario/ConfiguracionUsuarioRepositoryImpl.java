package co.org.ceindetec.combustible.modules.ConfiguracionUsuario;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.org.ceindetec.combustible.R;
import co.org.ceindetec.combustible.domain.SQLiteHelper;
import co.org.ceindetec.combustible.entities.Usuario;
import co.org.ceindetec.combustible.lib.EventBus;
import co.org.ceindetec.combustible.lib.GreenRobotEventBus;
import co.org.ceindetec.combustible.modules.ConfiguracionUsuario.events.ConfiguracionUsuarioEvent;

/**
 * Created by Ceindetec02 on 17/08/2016.
 */
public class ConfiguracionUsuarioRepositoryImpl implements ConfiguracionUsuarioRepository {

    private EventBus eventBus;
    private SQLiteHelper sqliteHelper;
    private Context context;

    public ConfiguracionUsuarioRepositoryImpl() {
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate(Context context) {
        this.context = context;
        this.sqliteHelper = new SQLiteHelper(context);
    }

    @Override
    public void obtenerListadoUsuarios() {
        ConfiguracionUsuarioEvent configuracionUsuarioEvent = new ConfiguracionUsuarioEvent();

        List<Usuario> usuarioList = sqliteHelper.getDataAllUsuario();
        if (usuarioList.size() > 0) {
            configuracionUsuarioEvent.setUsuarioList(usuarioList);
            configuracionUsuarioEvent.setEventType(ConfiguracionUsuarioEvent.onGetUsuarioSuccess);
            eventBus.post(configuracionUsuarioEvent);
        } else {
            configuracionUsuarioEvent.setErrorMessage(context.getString(R.string.usuarioList_text_errorObtenerUsuarios));
            configuracionUsuarioEvent.setEventType(ConfiguracionUsuarioEvent.onGetUsuarioError);
            eventBus.post(configuracionUsuarioEvent);
        }
    }

    @Override
    public void eliminarUsuario(int idUsuario) {
        ConfiguracionUsuarioEvent configuracionUsuarioEvent = new ConfiguracionUsuarioEvent();
        if (idUsuario != 1) {
            if (sqliteHelper.deleteUsuario(idUsuario) > 0) {
                configuracionUsuarioEvent.setEventType(ConfiguracionUsuarioEvent.onDelUsuarioSuccess);
                eventBus.post(configuracionUsuarioEvent);
            } else {
                configuracionUsuarioEvent.setErrorMessage(context.getString(R.string.usuarioList_text_errorEliminarUsuarios));
                configuracionUsuarioEvent.setEventType(ConfiguracionUsuarioEvent.onDelUsuarioError);
                eventBus.post(configuracionUsuarioEvent);
            }
        } else {
            configuracionUsuarioEvent.setErrorMessage(context.getString(R.string.usuarioList_text_errorEliminarUsuarioAdmin));
            configuracionUsuarioEvent.setEventType(ConfiguracionUsuarioEvent.onDelUsuarioError);
            eventBus.post(configuracionUsuarioEvent);
        }
    }
}
