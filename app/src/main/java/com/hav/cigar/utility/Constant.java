package com.hav.cigar.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hav.cigar.R;
import dmax.dialog.SpotsDialog;


public class Constant {

    //   public static String MQTT_BROKER_URL = "a8lnp0dy0uxch-ats.iot.ap-southeast-1.amazonaws.com";
   public static String MQTT_BROKER_URL = "wss://a8lnp0dy0uxch-ats.iot.ap-southeast-1.amazonaws.com/mqtt";
   public static String PREFERENCE_DEVICE_TOKEN = "PREFERENCE_DEVICE_TOKEN";
    public static String MyPREFERENCES;
    public static String pay="";
    public static String ORDER_IDS="";
    static AlertDialog dialog;
    public static boolean isOnline(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static void showDialog(Context context){
        dialog = new SpotsDialog.Builder().setContext(context).build();
        dialog.show();
    }
    public static void hideDialog(){
        if(dialog!=null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    public static void showToast(Context context,String message){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_toast, null);
        TextView tv = (TextView) layout.findViewById(R.id.txtvw);
        tv.setText(message);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
