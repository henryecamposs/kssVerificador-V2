package kss.kssverificadorv2;

/**
 * Created by KSS on 24/04/2015.
 */
import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class JSONParser {

    public JSONParser() {
        super();
    }
    //******************IVA
    public inven_tabla parserInven(JSONObject object) {
        inven_tabla result = new inven_tabla();

        try {
            JSONObject jsonObj = object.getJSONArray("Value").getJSONObject(0);
            result.barra = (jsonObj.getString("barra"));
            result.codigo = (jsonObj.getString("codigo"));
            result.descr = (jsonObj.getString("descr"));
            result.desde_o = (jsonObj.getString("desde_o"));
            result.hasta_o = (jsonObj.getString("hasta_o"));
            result.pidecanti = (jsonObj.getInt("pidecanti"));
            result.pidepre = (jsonObj.getInt("pidepre"));
            result.precio = (jsonObj.getDouble("precio"));
            result.precio1 = (jsonObj.getDouble("precio1"));
            result.precio1_e = (jsonObj.getDouble("precio1_e"));
            result.precio1_o = (jsonObj.getDouble("precio1_o"));
            result.tiva = (jsonObj.getInt("tiva"));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser=>parserInven", e.getMessage());
        }
        return result;
    }

    public ArrayList<inven_tabla> parseInven_array(JSONObject object) {
        ArrayList<inven_tabla> arrayList = new ArrayList<inven_tabla>();
        try {
            JSONArray jsonArray = object.getJSONArray("Value");
            JSONObject jsonObj = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObj = jsonArray.getJSONObject(i);
                arrayList.add(new inven_tabla(
                        jsonObj.getString("descr"),
                        jsonObj.getString("codigo"),
                        jsonObj.getString("barra"),
                        jsonObj.getInt("tiva"),
                        jsonObj.getDouble("precio")
                ));
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser=>InvenArr", e.getMessage());
        }
        return arrayList;

    }
    public String parseFechaAcutalizacion(JSONObject object)
    {     String FechaAcutalizacion="";
        try {
            FechaAcutalizacion= object.getString("Value");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => FecActu", e.getMessage());
        }
        return FechaAcutalizacion;
    }
    public String parseNombreEmpresa(JSONObject object)
    {     String NombreEMpresa="";
        try {
            NombreEMpresa= object.getString("Value");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => FecActu", e.getMessage());
        }
        return NombreEMpresa;
    }

}
