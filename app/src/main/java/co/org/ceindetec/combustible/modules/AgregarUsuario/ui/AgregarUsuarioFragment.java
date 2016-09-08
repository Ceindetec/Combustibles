package co.org.ceindetec.combustible.modules.AgregarUsuario.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.org.ceindetec.combustible.R;
import co.org.ceindetec.combustible.entities.Usuario;
import co.org.ceindetec.combustible.modules.AgregarUsuario.AgregarUsuarioPresenter;
import co.org.ceindetec.combustible.modules.AgregarUsuario.AgregarUsuarioPresenterImpl;
import co.org.ceindetec.combustible.modules.ConfiguracionUsuario.ui.adapters.OnUsuarioAddedListener;

/**
 * Created by Ceindetec02 on 19/08/2016.
 */
public class AgregarUsuarioFragment extends DialogFragment implements AgregarUsuarioView, DialogInterface.OnShowListener {

    OnUsuarioAddedListener onUsuarioAddedListener;

    @Bind(R.id.edtAgregarNombreUsuario)
    EditText edtAgregarNombreUsuario;
    @Bind(R.id.edtAgregarCodigoSeguridadUsuario)
    EditText edtAgregarCodigoSeguridadUsuario;
    @Bind(R.id.pgbAgregarUsuarioProgress)
    ProgressBar pgbAgregarUsuarioProgress;
    @Bind(R.id.rlyAgregarUsuarioForm)
    RelativeLayout rlyAgregarUsuarioForm;

    private AgregarUsuarioPresenter agregarUsuarioPresenter;

    public AgregarUsuarioFragment() {
        agregarUsuarioPresenter = new AgregarUsuarioPresenterImpl(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        onUsuarioAddedListener = (OnUsuarioAddedListener) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.agregarUsuario_text_agregarUsuario)
                .setPositiveButton(R.string.agregarUsuario_text_agregarUsuarioAdd, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.agregarUsuario_text_agregarUsuarioCancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_add_usuario, null);
        ButterKnife.bind(this, view);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);
        return dialog;
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        final AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);

            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initAgregarusuario();
                }
            });

            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        agregarUsuarioPresenter.onCreate(getContext());
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            rlyAgregarUsuarioForm.setVisibility(show ? View.GONE : View.VISIBLE);
            rlyAgregarUsuarioForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    rlyAgregarUsuarioForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            pgbAgregarUsuarioProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            pgbAgregarUsuarioProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    pgbAgregarUsuarioProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            pgbAgregarUsuarioProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            rlyAgregarUsuarioForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onAgregarUsuarioSuccess(Usuario usuario) {
        onUsuarioAddedListener.onUsuarioAdded(usuario);
        dismiss();
    }

    @Override
    public void onAgregarUsuarioError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void initAgregarusuario() {


        // Resetea errores
        edtAgregarNombreUsuario.setError(null);
        edtAgregarCodigoSeguridadUsuario.setError(null);

        // Almacena valores en el intento de login
        String nombreUsuario = edtAgregarNombreUsuario.getText().toString().toUpperCase();
        String codigoSeguridadUsuario = edtAgregarCodigoSeguridadUsuario.getText().toString();

        boolean cancelLogin = false;
        View focusView = null;

        // Valida el codigoSeguridad si es valido
        if (!TextUtils.isEmpty(codigoSeguridadUsuario) && !isCodigoSeguridadValido(codigoSeguridadUsuario)) {
            edtAgregarCodigoSeguridadUsuario.setError(getString(R.string.login_error_codigoSeguridad));
            focusView = edtAgregarCodigoSeguridadUsuario;
            cancelLogin = true;
        }

        // Valida el usuario si es valido
        if (TextUtils.isEmpty(nombreUsuario)) {
            edtAgregarNombreUsuario.setError(getString(R.string.login_error_campoRequerido));
            focusView = edtAgregarNombreUsuario;
            cancelLogin = true;
        } else if (!isUsuarioValido(nombreUsuario)) {
            edtAgregarNombreUsuario.setError(getString(R.string.login_error_usuario));
            focusView = edtAgregarNombreUsuario;
            cancelLogin = true;
        }

        if (cancelLogin) {
            focusView.requestFocus();
        } else {
            agregarUsuarioPresenter.agregarUsuario(nombreUsuario.trim().toUpperCase(), codigoSeguridadUsuario.trim());
        }

    }

    private boolean isCodigoSeguridadValido(String codigoSeguridadUsuario) {
        if (codigoSeguridadUsuario.length() > 4) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isUsuarioValido(String nombreUsuario) {
        if (nombreUsuario.length() > 4) {
            return true;
        } else {
            return false;
        }
    }

}