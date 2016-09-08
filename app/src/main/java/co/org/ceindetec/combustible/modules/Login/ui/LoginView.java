package co.org.ceindetec.combustible.modules.Login.ui;

import android.content.Context;

/**
 * Created by Ceindetec02 on 16/08/2016.
 */
public interface LoginView {

    void showProgress(final boolean show);

    void navigateToSuperActivity();

    void navigateToUsuarioActivity();

    void showErrorMessage(String errorMessage);
}
