package utiles;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import kss.kssverificadorv2.R;

import static kss.kssverificadorv2.R.drawable.toast_background_blue;
import static kss.kssverificadorv2.R.drawable.toast_background_red;
import static kss.kssverificadorv2.R.drawable.toast_background_yellow;

/**
 * Created by HENRY on 17/02/2015.
 * Enviar un Mostrar un Toast mensaje en un tiempo determinado
 */
public class ToastManager {

    public static final int INFORMATION = 0;
    public static final int WARNING = 1;
    public static final int ERROR = 2;


    /**
     * Funcion para mostrar Toast en pantalla.
     *
     * @param context   Contexto de la aplicacion
     * @param msj       mensaje de toast
     * @param tipoMSJ   tipo de mensaje
     * @param tiempoMSJ duración del mensaje
     */
    public static void show(Context context, String msj,
                            int tipoMSJ, int tiempoMSJ) {

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.toast_layout, null);

        TextView tv = (TextView) layout.findViewById(R.id.tvTexto);
        tv.setText(msj);

        LinearLayout llRoot =
                (LinearLayout) layout.findViewById(R.id.llRoot);

        Drawable img;
        int bg;

        switch (tipoMSJ) {
            case WARNING:
                img = context.getResources().getDrawable(R.drawable.btn_warning);
                bg = toast_background_yellow;
                break;
            case ERROR:
                img = context.getResources().getDrawable(R.drawable.btn_error);
                bg = toast_background_red;
                break;
            default:
                img = context.getResources().getDrawable(R.drawable.btn_info);
                bg = toast_background_blue;
                break;
        }
        tv.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        llRoot.setBackgroundResource(bg);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(tiempoMSJ);
        toast.setView(layout);
        toast.show();
    }
}