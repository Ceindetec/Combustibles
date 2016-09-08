package co.org.ceindetec.combustible.modules.ConfiguracionUsuario.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.org.ceindetec.combustible.R;
import co.org.ceindetec.combustible.entities.Usuario;
import co.org.ceindetec.combustible.modules.AgregarUsuario.ui.AgregarUsuarioFragment;
import co.org.ceindetec.combustible.modules.ConfiguracionUsuario.ConfiguracionUsuarioPresenter;
import co.org.ceindetec.combustible.modules.ConfiguracionUsuario.ConfiguracionUsuarioPresenterImpl;
import co.org.ceindetec.combustible.modules.ConfiguracionUsuario.ui.adapters.OnItemClickListener;
import co.org.ceindetec.combustible.modules.ConfiguracionUsuario.ui.adapters.OnUsuarioAddedListener;
import co.org.ceindetec.combustible.modules.ConfiguracionUsuario.ui.adapters.UsuarioListAdapter;

/**
 * Created by Ceindetec02 on 17/08/2016.
 */
public class ConfiguracionUsuarioActivity extends AppCompatActivity implements ConfiguracionUsuarioView, OnItemClickListener, OnUsuarioAddedListener {


    @Bind(R.id.tlbConfiguracionUsuario)
    Toolbar tlbConfiguracionUsuario;
    @Bind(R.id.apblConfiguracionUsuario)
    AppBarLayout apblConfiguracionUsuario;
    @Bind(R.id.rcvRecyclerUsuario)
    RecyclerView rcvRecyclerUsuario;
    @Bind(R.id.fabAgregarUsuario)
    FloatingActionButton fabAgregarUsuario;
    @Bind(R.id.colConfiguracionUsuario)
    CoordinatorLayout colConfiguracionUsuario;

    //Instanciamiento de la interfaz PlayListPresenter
    private ConfiguracionUsuarioPresenter configuracionUsuarioPresenter;

    //Instanciamiento del PlayListAdapter
    private UsuarioListAdapter usuarioListAdapter;

    /**
     * Creacion de la vista
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf_usuario);
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Instanciamiento de la interfaz LoginPresenter
        configuracionUsuarioPresenter = new ConfiguracionUsuarioPresenterImpl(this);

        //Llamado al metodo que inicializa el Adaptador
        setupAdapter();

        //Llamado al metodo que inicializa el RecyclerView
        setupRecyclerView();

        //Llamado al metodo que inicializa el ToolBar
        setupToolbar();

        configuracionUsuarioPresenter.onCreate(this);

        configuracionUsuarioPresenter.obtenerListadoUsuarios();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_close_session, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo sobrecargado de la Actividad para eliminar el Presentador
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Metodo que inicializa y configura el Adaptador
     */
    private void setupAdapter() {
        usuarioListAdapter = new UsuarioListAdapter(new ArrayList<Usuario>(), this, getApplicationContext());
    }

    /**
     * Metodo que inicializa y configura el recyclerView
     */
    private void setupRecyclerView() {
        rcvRecyclerUsuario.setLayoutManager(new LinearLayoutManager(this));
        rcvRecyclerUsuario.setAdapter(usuarioListAdapter);
    }

    private void setupToolbar() {
        tlbConfiguracionUsuario.setTitle(R.string.configuracionUsuario_text_title);
        setSupportActionBar(tlbConfiguracionUsuario);
    }

    @OnClick(R.id.fabAgregarUsuario)
    public void addContact() {
        new AgregarUsuarioFragment().show(getSupportFragmentManager(), getString(R.string.agregarUsuario_text_agregarUsuario));
    }

    @Override
    public void onItemClick(Usuario usuarioItem) {

    }

    @Override
    public void onItemLongClick(Usuario usuarioItem) {
        configuracionUsuarioPresenter.eliminarUsuario(usuarioItem.getUsuarioId());
    }

    @Override
    public void addUsuarioList(List<Usuario> usuarioList) {
        for (int i = 0; i < usuarioList.size(); i++) {
            usuarioListAdapter.add(usuarioList.get(i));
        }
    }

    @Override
    public void errorUsuarioList(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUsuarioAdded(Usuario usuario) {
        usuarioListAdapter.add(usuario);

    }

    @Override
    public void onDelUsuarioSuccess() {
        usuarioListAdapter.clear();
        configuracionUsuarioPresenter.obtenerListadoUsuarios();
        Toast.makeText(this, getResources().getString(R.string.usuarioList_text_eliminarUsuario), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDelUsuarioError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
