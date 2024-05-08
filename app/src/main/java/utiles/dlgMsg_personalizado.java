package utiles;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import kss.kssverificadorv2.R;

/**
 * Created by HENRY on 12/02/2015.
 */
public class dlgMsg_personalizado {
    Context context;

    public dlgMsg_personalizado(Context context) {
        this.context = context;
    }

    public void crearDialog(String Mensaje) {
        new AlertDialog.Builder(this.context)
                .setTitle(this.context.getString(R.string.KssHaOcurridoError))
                .setMessage(Mensaje)
                .setIcon(R.drawable.ksslogo)
                        //	.setIcon(R.drawable.ICONO) //añadir icono
                .setPositiveButton(this.context.getString(R.string.KssAceptar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("AlertDialog", "Aceptar");
                        return; //cierra el diálogo. Siempre puedes hacerle hacer algo.
                    }
                })
                .show();
    }
}
