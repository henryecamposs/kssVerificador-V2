package kss.kssverificadorv2;

import java.util.Date;

/**
 * Created by KSS on 24/04/2015.
 */
public class inven_tabla {
    public String codigo,barra, descr,ult_venta, ult_compra, desde_o, hasta_o;;
    public  Double precio, precio1, precio2, precio3, precio_e, precio1_e, precio2_e, tcos1, tcos2,precio1_o, precio2_o, precio3_o, precio_o;
    public int  pidepre, pidecanti,tiva2, tiva;


    public  inven_tabla(){
        codigo=null; descr=null; barra=null;ult_venta=null; ult_compra=null; desde_o=null; hasta_o=null;
        precio=0.0; precio1=0.0; precio2=0.0; precio3=0.0; precio_e=0.0; precio1_e=0.0; precio2_e=0.0; tcos1=0.0; tcos2=0.0;precio1_o=0.0; precio2_o=0.0; precio3_o=0.0; precio_o=0.0;
        pidepre=1; pidecanti=1;tiva2=1; tiva=1;

    }

    public inven_tabla(String descr, String codigo, String barra, int tiva, double precio) {
        this.codigo=codigo;
        this.descr=descr;
        this.tiva=tiva;
        this.barra=barra;
        this.precio=precio;
    }
}
