package co.org.ceindetec.combustible.entities;

/**
 * Created by Ceindetec02 on 16/08/2016.
 */
public class Usuario {
    int usuarioId;
    String usuarioNombre;
    String usuarioPass;

    public Usuario() {
    }

    public Usuario(int usuarioId, String usuarioNombre, String usuarioPass) {
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.usuarioPass = usuarioPass;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getUsuarioPass() {
        return usuarioPass;
    }

    public void setUsuarioPass(String usuarioPass) {
        this.usuarioPass = usuarioPass;
    }
}
