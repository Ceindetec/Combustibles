package co.org.ceindetec.combustible.modules.ConfiguracionUsuario.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.org.ceindetec.combustible.R;
import co.org.ceindetec.combustible.entities.Usuario;

/**
 * Created by Ceindetec02 on 19/07/2016.
 */
public class UsuarioListAdapter extends RecyclerView.Adapter<UsuarioListAdapter.ViewHolder> {


    //Declaracion de la variable contactList para almacenar un List de tipo User
    private List<Usuario> usuarioList;

    //Instanciamiento de la interfaz OnItemClickListener
    private OnItemClickListener onItemClickListener;

    private Context context;

    /**
     * Constructor de la clase principal
     *
     * @param usuarioList
     * @param onItemClickListener
     */
    public UsuarioListAdapter(List<Usuario> usuarioList, OnItemClickListener onItemClickListener, Context context) {
        this.usuarioList = usuarioList;
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }

    /**
     * Metodo implementado de la clase RecyclerView que se ejecuta en la creacion del objeto
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_usuario_list, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Metodo implementado de la clase RecyclerView para el bindeo de los datos del objeto
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        Usuario usuario = usuarioList.get(position);
        viewHolder.setClickListener(usuario, onItemClickListener);

        String idUsuario = String.format(context.getResources().getString(R.string.usuarioList_text_idUsuario), Integer.toString(usuario.getUsuarioId()));
        String nombreUsuario = String.format(context.getResources().getString(R.string.usuarioList_text_nombreUsuario), usuario.getUsuarioNombre());
        String passUsuario = String.format(context.getResources().getString(R.string.usuarioList_text_codigoSeguridadUsuario), usuario.getUsuarioPass());

        viewHolder.txvUsuarioId.setText(idUsuario);
        viewHolder.txvUsuarioNombre.setText(nombreUsuario);
        viewHolder.txvUsuarioPass.setText(passUsuario);

    }

    /**
     * Metodo implementado de la clase RecyclerView que devuelve el conteo de los datos dentro del objeto
     *
     * @return
     */
    @Override
    public int getItemCount() {
        try {
            return usuarioList.size();
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * Metodo implementado de la clase RecyclerView para agregar items al Recycler View
     *
     * @param usuario
     */
    public void add(Usuario usuario) {
        if (!usuarioList.contains(usuario)) {
            usuarioList.add(usuario);
            notifyDataSetChanged();
        }
    }

    /**
     * Metodo implementado de la clase RecyclerView para actualizar items del Recycler View
     *
     * @param usuario
     */
    public void update(Usuario usuario) {
        int index = usuarioList.indexOf(foundUsuario(usuario));
        if (index != -1) {
            usuarioList.set(index, usuario);
            notifyDataSetChanged();
        } else {
            add(usuario);
        }
    }

    /**
     * Metodo implementado de la clase RecyclerView para eñiminar items del Recycler View
     */
    public void clear() {
        usuarioList.clear();
        notifyDataSetChanged();
    }

    /**
     * Metodo implementado de la clase RecyclerView para eñiminar items del Recycler View
     *
     * @param usuario
     */
    public void remove(Usuario usuario) {
        if (!usuarioList.contains(usuario)) {
            usuarioList.remove(usuario);
            notifyDataSetChanged();
        }
    }

    /**
     * Metodo para encontrar la cancion dentro de la lista de canciones basado en el nombre
     *
     * @param usuario
     * @return
     */
    private Usuario foundUsuario(Usuario usuario) {
        int i = 0;
        boolean aux = true;

        while (i < usuarioList.size()) {
           /* if (usuarioList.get(i).getCodigoCancion().equals(usuario.getCodigoCancion())) {
                break;
            }*/
            i++;
        }
        return usuarioList.get(i);
    }


    /**
     *
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txvUsuarioId)
        TextView txvUsuarioId;
        @Bind(R.id.txvUsuarioNombre)
        TextView txvUsuarioNombre;
        @Bind(R.id.txvUsuarioPass)
        TextView txvUsuarioPass;


        //Declaracion de una variable de tipo vista para manejar los datos del RecyclerView
        private View view;

        /**
         * Contructor de la clase
         *
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);

            //Bindeo de los controles mediante ButterKnife de la vista que esta en ViewHolder
            ButterKnife.bind(this, itemView);

            this.view = itemView;

        }

        /**
         * Metodo que asigna los escuchadores a la vista
         *
         * @param usuarioItem
         * @param listener
         */
        private void setClickListener(final Usuario usuarioItem, final OnItemClickListener listener) {
            view.setOnLongClickListener(new View.OnLongClickListener() {

                /**
                 * Metodo implementado de la clase OnClickListener que escucha el click sobre un objeto
                 *
                 * @param v
                 */
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(usuarioItem);
                    return false;
                }
            });

            view.setOnClickListener(new View.OnClickListener() {

                /**
                 * Metodo implementado de la clase OnClickListener que escucha el click sobre un objeto
                 *
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    listener.onItemClick(usuarioItem);
                }
            });
        }
    }
}
