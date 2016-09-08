package co.org.ceindetec.combustible.modules.Login.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import co.org.ceindetec.combustible.R;
import co.org.ceindetec.combustible.modules.ConfiguracionPrecio.ui.ConfiguracionPrecioActivity;
import co.org.ceindetec.combustible.modules.ConfiguracionUsuario.ui.ConfiguracionUsuarioActivity;
import co.org.ceindetec.combustible.modules.Login.LoginPresenter;
import co.org.ceindetec.combustible.modules.Login.LoginPresenterImpl;

public class LoginActivity extends AppCompatActivity implements LoginView {

    //Declaracion del presentador
    private LoginPresenter loginPresenter;

    // Referencias al UI
    @Bind(R.id.pgbLoginProgress)
    ProgressBar pgbLoginProgress;
    @Bind(R.id.edtLoginUsuario)
    EditText edtLoginUsuario;
    @Bind(R.id.edtLoginCodigoSeguridad)
    EditText edtLoginCodigoSeguridad;
    @Bind(R.id.btnConfirmarCodigo)
    Button btnConfirmarCodigo;
    @Bind(R.id.llyLoginFormUsuario)
    LinearLayout llyLoginFormUsuario;
    @Bind(R.id.scvLoginForm)
    ScrollView scvLoginForm;

    /**
     * Creacion de la vista
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        loginPresenter = new LoginPresenterImpl(this);
        loginPresenter.onCreate(this);
    }


    /**
     * Metodo para el intento de login valida el formulario segun el usuario y el codigo de seguridad
     */
    @OnClick(R.id.btnConfirmarCodigo)
    public void initLogin() {

        // Oculta el teclado
        hideKeyboard();

        // Resetea errores
        edtLoginUsuario.setError(null);
        edtLoginCodigoSeguridad.setError(null);

        // Almacena valores en el intento de login
        String usuario = edtLoginUsuario.getText().toString();
        String codigoSeguridad = edtLoginCodigoSeguridad.getText().toString();

        boolean cancelLogin = false;
        View focusView = null;

        // Valida el codigoSeguridad si es valido
        if (!TextUtils.isEmpty(codigoSeguridad) && !isCodigoSeguridadValido(codigoSeguridad)) {
            edtLoginCodigoSeguridad.setError(getString(R.string.login_error_codigoSeguridad));
            focusView = edtLoginCodigoSeguridad;
            cancelLogin = true;
        }

        // Valida el usuario si es valido
        if (TextUtils.isEmpty(usuario)) {
            edtLoginUsuario.setError(getString(R.string.login_error_campoRequerido));
            focusView = edtLoginUsuario;
            cancelLogin = true;
        } else if (!isUsuarioValido(usuario)) {
            edtLoginUsuario.setError(getString(R.string.login_error_usuario));
            focusView = edtLoginUsuario;
            cancelLogin = true;
        }

        if (cancelLogin) {
            focusView.requestFocus();
        } else {
            loginPresenter.validateLoginCombustibles(usuario.trim().toUpperCase(), codigoSeguridad);
        }
    }

    /**
     * Metodo que valida el usuario
     *
     * @param usuario
     * @return
     */
    private boolean isUsuarioValido(String usuario) {
        return usuario.length() > 2;
    }

    /**
     * Metodo que valida el codigo de seguridad
     *
     * @param codigoSeguridad
     * @return
     */
    private boolean isCodigoSeguridadValido(String codigoSeguridad) {
        return codigoSeguridad.length() > 4;
    }

    /**
     * Metodo que oculta el teclado
     */
    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException npe) {
            Log.e(getLocalClassName(), Log.getStackTraceString(npe));
        }
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            scvLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
            scvLoginForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    scvLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            pgbLoginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            pgbLoginProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    pgbLoginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            pgbLoginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            scvLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void navigateToSuperActivity() {
        Intent intent = new Intent(this, ConfiguracionUsuarioActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void navigateToUsuarioActivity() {
        Intent intent = new Intent(this, ConfiguracionPrecioActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}

