package co.org.ceindetec.combustible.modules.ConfiguracionUsuario.events;

import java.util.List;

import co.org.ceindetec.combustible.entities.Usuario;

/**
 * Created by Ceindetec02 on 18/08/2016.
 */
public class ConfiguracionUsuarioEvent {
    public static final int onGetUsuarioSuccess = 0;
    public static final int onGetUsuarioError = 1;
    public static final int onDelUsuarioSuccess = 2;
    public static final int onDelUsuarioError = 3;

    private List<Usuario> usuarioList;
    private int eventType;
    private String errorMessage;

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String error) {
        this.errorMessage = error;
    }
}
