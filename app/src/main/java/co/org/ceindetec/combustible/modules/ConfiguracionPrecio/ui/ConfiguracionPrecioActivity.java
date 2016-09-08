package co.org.ceindetec.combustible.modules.ConfiguracionPrecio.ui;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.org.ceindetec.combustible.R;
import co.org.ceindetec.combustible.modules.ConfiguracionPrecio.ConfiguracionPrecioPresenter;

/**
 * Created by Ceindetec02 on 17/08/2016.
 */
public class ConfiguracionPrecioActivity extends AppCompatActivity implements ConfiguracionPrecioView {

    WebSocketClient mWebSocketClient;

    JSONObject messageJson;
    JSONObject messageInicial;

    @Bind(R.id.tlbConfiguracionPrecio)
    Toolbar tlbConfiguracionPrecio;
    @Bind(R.id.edtLoginPrecioUno)
    EditText edtLoginPrecioUno;
    @Bind(R.id.wrpEdtLoginPrecioUno)
    TextInputLayout wrpEdtLoginPrecioUno;
    @Bind(R.id.edtLoginPrecioDos)
    EditText edtLoginPrecioDos;
    @Bind(R.id.edtLoginPrecioTres)
    EditText edtLoginPrecioTres;
    @Bind(R.id.edtLoginPrecioCuatro)
    EditText edtLoginPrecioCuatro;
    @Bind(R.id.btnConfirmarPrecios)
    Button btnConfirmarPrecios;
    @Bind(R.id.txvTextoError)
    TextView txvTextoError;

    //Instanciamiento de la interfaz PlayListPresenter
    private ConfiguracionPrecioPresenter configuracionPrecioPresenter;

    /**
     * Creacion de la vista
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf_precio);
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Llamado al metodo que inicializa el ToolBar
        setupToolbar();

        connectWebSocket();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_close_precio, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            finish();
        } else if (item.getItemId() == R.id.action_connect_socket) {
            connectWebSocket();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        tlbConfiguracionPrecio.setTitle(R.string.configuracionPrecio_text_title);
        setSupportActionBar(tlbConfiguracionPrecio);
    }


    @OnClick(R.id.btnConfirmarPrecios)
    public void registrarPrecios() {
        mWebSocketClient.send("{\"accion\":\"UPD\",\"valores\":[" +
                edtLoginPrecioUno.getText().toString() + "," +
                edtLoginPrecioDos.getText().toString() + "," +
                edtLoginPrecioTres.getText().toString() + "," +
                edtLoginPrecioCuatro.getText().toString() + "]}"
        );
    }


    private void habilitarRegistro(boolean status) {
        btnConfirmarPrecios.setEnabled(status);
        String cadenaMensaje;
        if (status) {
            txvTextoError.setVisibility(View.GONE);
            cadenaMensaje = getResources().getString(R.string.configuracionPrecio_text_conexionEstablecida);
        } else {
            txvTextoError.setVisibility(View.VISIBLE);
            cadenaMensaje = getResources().getString(R.string.configuracionPrecio_text_conexionNoEstablecida);
        }
        Toast.makeText(this, cadenaMensaje, Toast.LENGTH_SHORT).show();
    }


    private void confirmacionRegistro(){
        Toast.makeText(this, "Valores registrados en el dispositivo", Toast.LENGTH_SHORT).show();
    }
    private void mostrarError(String errorMessage) {
        Toast.makeText(this, errorMessage+", Revisa tu conexion WiFi al dispositivo", Toast.LENGTH_SHORT).show();
    }
    public void connectWebSocket() {
        URI uri;
        try {
            uri = new URI("ws://192.168.4.1:81");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri, new Draft_17()) {

            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                //mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
                thread(1, null);
            }

            @Override
            public void onMessage(String message) {
                Log.i("Websocket", "Message " + message);
                try {

                    JSONObject jsonObject = new JSONObject(message);
                    Log.i("estado", mWebSocketClient.getReadyState().toString());

                    if(message.equals("Hola mundo"))
                    thread(2, null);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
                thread(3, null);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
                thread(4, e.getMessage());
            }

            public void thread(final int event, final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (event) {
                            case 1:
                                habilitarRegistro(true);
                                break;
                            case 2:
                                confirmacionRegistro();
                                break;
                            case 3:
                                habilitarRegistro(false);
                                break;
                            case 4:
                                break;
                            case 5:
                                break;
                        }
                    }
                });
            }

        };
        mWebSocketClient.connect();
    }


}
