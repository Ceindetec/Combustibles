package co.org.ceindetec.combustible.modules.ConfiguracionUsuario.ui.adapters;

import co.org.ceindetec.combustible.entities.Usuario;

/**
 * Created by Ceindetec02 on 13/07/2016.
 */
public interface OnItemClickListener {

    //Escuchador para click en item
    void onItemClick(Usuario usuarioItem);
    //Escuchador para click en item
    void onItemLongClick(Usuario usuarioItem);
}
