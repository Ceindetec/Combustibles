package co.org.ceindetec.combustible.modules.Login.events;

/**
 * Created by Ceindetec02 on 16/08/2016.
 */
public class LoginEvent {
    public static final int onSignInSuccessUsuario = 0;
    public static final int onSignInSuccessAdmin = 1;
    public static final int onSignInError = 2;

    private int eventType;
    private String errorMessage;

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
