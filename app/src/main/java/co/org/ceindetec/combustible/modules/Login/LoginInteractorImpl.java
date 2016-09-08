package co.org.ceindetec.combustible.modules.Login;

import android.content.Context;

/**
 * Created by Ceindetec02 on 16/08/2016.
 */
public class LoginInteractorImpl implements LoginInteractor {

    private LoginRepository loginRepository;

    public LoginInteractorImpl() {

        loginRepository = new LoginRepositoryImpl();

    }

    @Override
    public void onCreate(Context context) {
        loginRepository.onCreate(context);
        loginRepository.initAdmin();
    }

    @Override
    public void validateLoginCombustibles(String usuario, String codigoSeguridad) {
        loginRepository.validateLoginCombustibles(usuario, codigoSeguridad);
    }

}
