package co.org.ceindetec.combustible.modules.AgregarUsuario.events;

import java.util.List;

import co.org.ceindetec.combustible.entities.Usuario;

/**
 * Created by Ceindetec02 on 19/08/2016.
 */
public class AgregarUsuarioEvent {
    public static final int onAddUsuarioSuccess = 0;
    public static final int onAddUsuarioError = 1;

    private Usuario usuario;
    private int eventType;
    private String errorMessage;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
