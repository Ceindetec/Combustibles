package co.org.ceindetec.combustible.modules.ConfiguracionUsuario;

import android.content.Context;

/**
 * Created by Ceindetec02 on 17/08/2016.
 */
public class ConfiguracionUsuarioInteractorImpl implements ConfiguracionUsuarioInteractor {


    //Instanciamiento del ConfiguracionUsuarioRepository
    private ConfiguracionUsuarioRepository configuracionUsuarioRepository;

    /**
     * Constructor de la clase
     */
    public ConfiguracionUsuarioInteractorImpl() {
        //Inicializacion del repositorio
        configuracionUsuarioRepository = new ConfiguracionUsuarioRepositoryImpl();
    }

    @Override
    public void onCreate(Context context) {
        configuracionUsuarioRepository.onCreate(context);
    }

    @Override
    public void obtenerListadoUsuarios() {
        configuracionUsuarioRepository.obtenerListadoUsuarios();
    }
    @Override
    public void eliminarUsuario(int idUsuario) {
        configuracionUsuarioRepository.eliminarUsuario(idUsuario);
    }
}
