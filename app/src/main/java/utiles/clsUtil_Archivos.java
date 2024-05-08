package utiles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by HENRY on 27/02/2015.
 */
public class clsUtil_Archivos {
    /**
     * Funcion para Colocar Imagen a un ImageView desde un SD
     *
     * @param fileName Nombre del Archivo
     * @param pic      Objeto Imageview al que se le coloca la imagen
     */
    public static void ShowPicture(String fileName, ImageView pic) {
        File f = new File(Environment.getExternalStorageDirectory(), fileName);
        FileInputStream is = null;
        try {
            is = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            Log.d("error: ", String.format("ShowPicture.java file[%s]Not Found", fileName));
            return;
        }
        Bitmap bm = BitmapFactory.decodeStream(is, null, null);
        pic.setImageBitmap(bm);
    }
}
