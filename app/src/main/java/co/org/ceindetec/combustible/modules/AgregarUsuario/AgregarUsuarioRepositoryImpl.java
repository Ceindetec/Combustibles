package co.org.ceindetec.combustible.modules.AgregarUsuario;

import android.content.Context;

import co.org.ceindetec.combustible.R;
import co.org.ceindetec.combustible.domain.SQLiteHelper;
import co.org.ceindetec.combustible.entities.Usuario;
import co.org.ceindetec.combustible.lib.EventBus;
import co.org.ceindetec.combustible.lib.GreenRobotEventBus;
import co.org.ceindetec.combustible.modules.AgregarUsuario.events.AgregarUsuarioEvent;

/**
 * Created by Ceindetec02 on 19/08/2016.
 */
public class AgregarUsuarioRepositoryImpl implements AgregarUsuarioRepository {

    private EventBus eventBus;
    private SQLiteHelper sqliteHelper;
    private Context context;

    public AgregarUsuarioRepositoryImpl() {
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate(Context context) {
        this.sqliteHelper = new SQLiteHelper(context);
        this.context = context;
    }

    @Override
    public void agregarUsuario(String nombreUsuario, String codigoSeguridadUsuario) {
        AgregarUsuarioEvent agregarUsuarioEvent = new AgregarUsuarioEvent();

        if (sqliteHelper.insertUsuario(nombreUsuario, codigoSeguridadUsuario)) {

            int idUsuario = sqliteHelper.getIdUsuario(nombreUsuario, codigoSeguridadUsuario);

            if (idUsuario != 0) {
                Usuario usuario = new Usuario(idUsuario, nombreUsuario, codigoSeguridadUsuario);
                agregarUsuarioEvent.setUsuario(usuario);
                agregarUsuarioEvent.setEventType(AgregarUsuarioEvent.onAddUsuarioSuccess);
                eventBus.post(agregarUsuarioEvent);
            } else {
                agregarUsuarioEvent.setErrorMessage(context.getString(R.string.agregarUsuario_error_agregarUsuarioError));
                agregarUsuarioEvent.setEventType(AgregarUsuarioEvent.onAddUsuarioError);
                eventBus.post(agregarUsuarioEvent);
            }
        } else {
            agregarUsuarioEvent.setErrorMessage(context.getString(R.string.agregarUsuario_error_agregarUsuarioError));
            agregarUsuarioEvent.setEventType(AgregarUsuarioEvent.onAddUsuarioError);
            eventBus.post(agregarUsuarioEvent);
        }

    }
}
