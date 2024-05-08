package kss.kssverificadorv2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import utiles.ToastManager;
import utiles.clsUtil_Net;


public class MainActivity extends FragmentActivity {
    private static Boolean esProductoENC = false;
    private static ImageButton imgbtnbuscar;
    private static EditText txtBuscarProducto;
    private static TextView lblProducto_Desc;
    private static TextView lblProducto_Precio;
    private static TextView lblProducto_PrecioBase;
    private static TextView lblProducto_IVA;
    private static TextView lblProducto_NoExiste;
    private static TextView lblfechaActual;
    private static TextView lblfechaActualizacion;
    private static TextView lblNombreEmpresa;
    private static FrameLayout frmMain;
    private static FrameLayout frmProductoExiste;
    protected Context context;
    private int Esperar = 20;
    private ArrayList<String> data = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0;
    long timeSwapBuff = 0;
    long updatedTime = 0;
    private long startTime = 0;
    private int SegundoTranscurriods = 0;
    private boolean preferenciasGuardadas = false;
    private String dirServerRestAPI;
    private double IVA2;
    private double IVA3;
    private double IVA1;
    private ImageButton imgBtnConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Ocultar barra Notificacion
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        context = this;
        txtBuscarProducto = (EditText) this.findViewById(R.id.txtEscanearProducto);
        imgbtnbuscar = (ImageButton) this.findViewById(R.id.imgbtnBuscar);
        imgBtnConfig = (ImageButton) this.findViewById(R.id.iimgBtnConfig);
        // Okbtn.setOnClickListener(OnClickListener);

        //lblProducto_Barra = (TextView) this.findViewById(R.id.lblProductoNombre);
        lblProducto_Desc = (TextView) this.findViewById(R.id.lblProductoNombre);
        lblProducto_IVA = (TextView) this.findViewById(R.id.lblPrecioIVA);
        lblProducto_Precio = (TextView) this.findViewById(R.id.lblPrecioTotal);
        lblProducto_PrecioBase = (TextView) this.findViewById(R.id.lblPrecioBase);
        lblProducto_NoExiste = (TextView) this.findViewById(R.id.lblProductoNoExiste);
        lblfechaActual = (TextView) this.findViewById(R.id.lblFechaActual);
        lblNombreEmpresa = (TextView) this.findViewById(R.id.lblNombreEmpresa);
        lblfechaActualizacion = (TextView) this.findViewById(R.id.lblFechaActualizada);
        frmMain = (FrameLayout) this.findViewById(R.id.frmMain);
        frmProductoExiste = (FrameLayout) this.findViewById(R.id.frmPrecioExiste);

        //Valores predeterminados
        isSetProductoENC(false);
        lblfechaActualizacion.setVisibility(View.INVISIBLE);
        FechaActual();
        txtBuscarProducto.setOnFocusChangeListener(myEditTextFocus);
        txtBuscarProducto.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    BuscarProducto(imgbtnbuscar);
                    return true;
                }
                return false;
            }
        });
    }

    // al abrir la aplicación, guardamos preferencias
    @Override
    public void onDestroy() {
        super.onDestroy();
        // guardarPreferencias();
    }

    // al abrir la aplicación, cargamos preferencias
    @Override
    protected void onStart() {
        super.onStart();
        cargarPreferencias();
        String mensaje = "";
        if (this.preferenciasGuardadas) {
            mensaje = "Configuración Guardada";
        } else {
            mensaje = "Configuración NO Guardada";
        }
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    //guardar configuración aplicación Android usando SharedPreferences
    public void guardarPreferencias() {

        try {
            SharedPreferences prefs = getSharedPreferences("preferenciasMiApp", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("preferenciasGuardadas", true);
            editor.putString("IVA1", "12.0");
            editor.putString("IVA2", "8.0");
            editor.putString("IVA3", "22.0");
            editor.putString("dirServerRestAPI", "http://server/WebAPI_JSON_Retail/handler1.ashx");
            editor.commit();
            ToastManager.show(this, "guardando preferencias", ToastManager.INFORMATION, 0);
        } catch (Exception ex) {
            ToastManager.show(context, ex.getMessage().toString(), ToastManager.ERROR, 0);
        }

    }

    //cargar configuración aplicación Android usando SharedPreferences
    public void cargarPreferencias() {

        try {
            SharedPreferences prefs = getSharedPreferences("preferenciasMiApp", Context.MODE_PRIVATE);
            this.dirServerRestAPI = prefs.getString("dirServerRestAPI", "http://server/WebAPI_JSON_Retail/handler1.ashx");
            this.IVA1 = Double.parseDouble(prefs.getString("IVA1", "12.0"));
            this.IVA2 = Double.parseDouble(prefs.getString("IVA2", "8.0"));
            this.IVA3 = Double.parseDouble(prefs.getString("IVA3", "22.0"));
            preferenciasGuardadas = prefs.getBoolean("preferenciasGuardadas", false);
        } catch (Exception ex) {
            ToastManager.show(context, ex.getMessage().toString(), ToastManager.ERROR, 0);
        }
    }

    public boolean isGetProductoENC() {
        return this.esProductoENC;
    }

    public void isSetProductoENC(boolean value) {
        this.esProductoENC = value;
        if (esProductoENC) {
            //Mostrar Etiquetas
            frmMain.setVisibility(View.GONE);
            frmProductoExiste.setVisibility(View.VISIBLE);
        } else {
            frmMain.setVisibility(View.VISIBLE);
            frmProductoExiste.setVisibility(View.GONE);
        }
        lblProducto_NoExiste.setVisibility(View.INVISIBLE);
        txtBuscarProducto.requestFocus();
    }

    private View.OnFocusChangeListener myEditTextFocus = new View.OnFocusChangeListener() {
        public void onFocusChange(View view, boolean gainFocus) {
            //onFocus
            if (gainFocus) {
                //set the text
                ((EditText) view).selectAll();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            //onBlur
            else {
                //clear the text
                ((EditText) view).setText("");
            }
        }

        ;
    };

    //Da formato a Numeros Decimales
    private String customFormat(String pattern, double value) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        return output;
       /* static public void main(String[] args) {

            customFormat("###,###.###", 123456.789);
            customFormat("###.##", 123456.789);
            customFormat("000000.000", 123.78);
            customFormat("$###,###.###", 12345.67);
        }*/
    }

    //Devuelve Fecha Actual
    private void FechaActual() {
        Date dFecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String sFecha = sdf.format(dFecha);
        lblfechaActual.setText(sFecha);
    }

    public void MostrarConfig(View v) {
        //Define la actividad
        Intent i = new Intent(this, ConfigActivity.class);

        //Inicia la actividad
        startActivity(i);
    }

    public void BuscarProducto(View v) {
        try {
            String sBuscar = txtBuscarProducto.getText().toString().trim();
            if (sBuscar.length() > 0) {
                cargardatosUsuarios(sBuscar);
                DetenerTiempo();
                Esperar = 20;
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
            } else {
                ToastManager.show(context, "Debe indicar el texto a buscar :-/", ToastManager.INFORMATION, 1);
            }
        } catch (Exception ex) {
            ToastManager.show(context, ex.getMessage().toString(), ToastManager.ERROR, 0);
        }
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            if (SegundoTranscurriods < Esperar) {
                timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
                updatedTime = timeSwapBuff + timeInMilliseconds;
                int secs = (int) (updatedTime / 1000);
//                int mins = secs / 60;
                secs = secs % 60;
//                int milliseconds = (int) (updatedTime % 1000);
                SegundoTranscurriods = secs;
                customHandler.postDelayed(this, 0);
            } else {
                DetenerTiempo();
            }
        }
    };

    private void DetenerTiempo() {
        timeSwapBuff = 0;
        customHandler.removeCallbacks(updateTimerThread);
        SegundoTranscurriods = 0;
        startTime = 0;
        isSetProductoENC(false);
    }

    private void cargardatosUsuarios(String sBuscar) {

        //Mostrar lista Productos
        data = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, data);
        //  txtBuscarProducto.setAdapter(adapter);
        if (clsUtil_Net.checkWifiState(context)) {
            new AsyncLoadProductos().execute(sBuscar.toUpperCase());
        }

    }

    protected class AsyncLoadProductos extends AsyncTask<String, JSONObject, ArrayList<inven_tabla>>
            //AsyncTask<String,Void,Tabla>
    {
        private String sAccion = "Cargando datos...";
        private ProgressDialog dialogo;
        private String FechaActualizacion = "";
        private String NombreEmpresa = "[NOMBRE EMPRESA]";

        public AsyncLoadProductos() {
        }

        @Override
        protected void onPreExecute() {
            dialogo = new ProgressDialog(context);
            dialogo.setIcon(R.drawable.ksslogo);
            dialogo.setMessage(sAccion);
            dialogo.setIndeterminate(false);
            dialogo.setCancelable(false);
            dialogo.show();
            dialogo.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    AsyncLoadProductos.this.cancel(true);
                }
            });
        }

        @Override
        protected void onCancelled() {
            dialogo.dismiss();
        }

        @Override
        protected ArrayList<inven_tabla> doInBackground(String... params) {
            // hacer validacion si devuelve Error o se ejecuta
            // cancel(esWSActivo = clsUtil_Net.esWSActivo(context, RestAPI.urlString));
            ArrayList<inven_tabla> Productos;
            RestAPI api = new RestAPI(context, dirServerRestAPI);
            try {
                sAccion = "Buscando... " + params[0].toString().toUpperCase() +
                        "\n Esto puede tardar varios segundos :-}";
                publishProgress();
                JSONObject jsonObj = api.GetProductoPorBarra(params[0]);
                JSONParser parser = new JSONParser();
                Productos = parser.parseInven_array(jsonObj);
                //Fecha Actualizacion
                jsonObj = api.GetFechaArchivo("inven.dbf");
                FechaActualizacion = parser.parseFechaAcutalizacion(jsonObj);
                //Nombre Empresa
                jsonObj = api.GetNombreEmpresa();
                NombreEmpresa = parser.parseNombreEmpresa(jsonObj);
                sAccion = "Se encontraron: " + String.valueOf(Productos.size()) + " Registros.";
                publishProgress();
                return Productos;
            } catch (Exception e) {
                sAccion = "Algo ha salido mal :-/ \n" + e.getLocalizedMessage();
                publishProgress();
                try {
                    Thread.sleep((long) (2000));
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                cancel(true);
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(JSONObject... values) {
            dialogo.setMessage(sAccion);
        }

        @Override
        protected void onPostExecute(ArrayList<inven_tabla> Producto) {
            try {
                //Producto existe
                imgBtnConfig.requestFocus();
                if (Producto != null) {
                    if (Producto.size() > 0) {
                        isSetProductoENC(true);
                        Double IVA;
                        Double PrecioBase;
                        //Etiquetas
                        if (Producto.get(0).tiva > 0) {
                            PrecioBase = Producto.get(0).precio / 1.12;
                            IVA = Producto.get(0).precio - PrecioBase;
                            lblProducto_IVA.setText(customFormat("###,##0.00", IVA));
                        } else {
                            PrecioBase = Producto.get(0).precio;
                            lblProducto_IVA.setText("*EXCENTO*");
                        }
                        lblProducto_PrecioBase.setText(customFormat("###,##0.00", PrecioBase));
                        lblProducto_Precio.setText(customFormat("###,##0.00", Producto.get(0).precio));
                        lblProducto_Desc.setText(Producto.get(0).descr.toString());
                    } else {
                        lblProducto_NoExiste.setVisibility(View.VISIBLE);
                        //Retardar Ejecucion 5 segundos
                        final Handler mHandler = new Handler();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isSetProductoENC(false);
                            }
                        }, 5000);
                    }
                    lblfechaActualizacion.setVisibility(View.VISIBLE);
                    lblfechaActualizacion.setText(FechaActualizacion);
                    lblNombreEmpresa.setText(NombreEmpresa);
                } else {
                    //Ocurrio Error
                    ToastManager.show(context, "No se localizaron datos. Revise la conexión al servidor :-/", ToastManager.WARNING, 0);
                    cancel(true);
                }
            } catch (Exception ex) {
                ToastManager.show(context, ex.getMessage().toString(), ToastManager.ERROR, 0);
                cancel(true);
            }
            dialogo.dismiss();
            txtBuscarProducto.requestFocus();
            FechaActual();
        }
    }
/* Trae Preferencias directamente
    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("double", "0.01").commit();
    and then to retrieve the double, simply use Double.parseDouble:
   Double.parseDouble(getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("double", "0.01"));
   */
}
