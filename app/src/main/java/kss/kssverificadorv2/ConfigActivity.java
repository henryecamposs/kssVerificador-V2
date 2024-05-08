package kss.kssverificadorv2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import utiles.ToastManager;


public class ConfigActivity extends FragmentActivity {

    private boolean preferenciasGuardadas=false;
    private String dirServerRestAPI;
    private double IVA2;
    private double IVA3;
    private double IVA1;
    static EditText txtDirServer;
    static EditText txtIVA1;
    static EditText txtIVA2;
    static EditText txtIVA3;
    static TextView lblEmpresa;
    protected Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Ocultar barra Notificacion
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_config);
        context = this;
        txtDirServer= (EditText) findViewById(R.id.txtDirServer);
        txtIVA1= (EditText) findViewById(R.id.txtIVA1);
        txtIVA2= (EditText) findViewById(R.id.txtIVA2);
        txtIVA3= (EditText) findViewById(R.id.txtIVA3);
    }
    // al abrir la aplicaci&#xfffd;n, guardamos preferencias
    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    // al abrir la aplicaci&#xfffd;n, cargamos preferencias
    @Override
    protected void onStart() {
        super.onStart();
        cargarPreferencias();
        String mensaje = "";
        if (this.preferenciasGuardadas) {
            mensaje = "Configuraci\ufffdn Guardada";
        } else {
            mensaje = "Configuraci\ufffdn NO Guardada";
        }
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        if (preferenciasGuardadas){
            txtDirServer.setText(dirServerRestAPI);
            txtIVA1.setText(Double.toString(IVA1));
            txtIVA2.setText(Double.toString(IVA2));
            txtIVA3.setText(Double.toString(IVA3));
        }
    }

    //guardar configuraci&#xfffd;n aplicaci&#xfffd;n Android usando SharedPreferences
    public void guardarPreferencias(View v){
        try {

            SharedPreferences prefs = getSharedPreferences("preferenciasMiApp", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("preferenciasGuardadas", true);
            editor.putString("IVA1", txtIVA1.getText().toString());
            editor.putString("IVA2", txtIVA2.getText().toString());
            editor.putString("IVA3", txtIVA3.getText().toString());
            editor.putString("dirServerRestAPI", txtDirServer.getText().toString());
            editor.commit();
            ToastManager.show(this, "guardando preferencias", ToastManager.INFORMATION, 0);
        } catch (Exception ex) {
            ToastManager.show(context, ex.getMessage().toString(), ToastManager.ERROR, 0);
        }
    }

    //cargar configuraci&#xfffd;n aplicaci&#xfffd;n Android usando SharedPreferences
    public void cargarPreferencias(){
        try {
            SharedPreferences prefs = getSharedPreferences("preferenciasMiApp", Context.MODE_PRIVATE);
            this.dirServerRestAPI = prefs.getString("dirServerRestAPI", "http://192.168.0.104/WebAPI_JSON_Retail/handler1.ashx");
            this.IVA1 =Double.parseDouble(prefs.getString("IVA1", "12.0"));
            this.IVA2 = Double.parseDouble(prefs.getString("IVA2", "12.0"));
            this.IVA3 = Double.parseDouble(prefs.getString("IVA3", "12.0"));
            preferenciasGuardadas = prefs.getBoolean("preferenciasGuardadas", false);
        } catch (Exception ex) {
            ToastManager.show(context, ex.getMessage().toString(), ToastManager.ERROR, 0);
        }
    }
    public void Cerrar(View v) {
        //Cerrar
        finish();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
