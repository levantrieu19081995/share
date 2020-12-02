package com.phamvanthang.thuexehoi.Connection;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.ImageView;

import java.net.URL;
import java.net.URLConnection;

public class Class_bien_duongdan {
    public static final String ip_server = "http://192.168.1.13/Thang";

    public static class DowloadImg extends AsyncTask<String,Void, Bitmap> {
        private ImageView imgHinh;
        public DowloadImg (ImageView img){
            this.imgHinh =img;
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(ip_server+strings[0]);
                URLConnection connection = url.openConnection();
                bitmap = BitmapFactory.decodeStream(connection.getInputStream());

            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imgHinh.setImageBitmap(bitmap);
        }
    }

    public static String getDeviceUniqueID(Activity activity){
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }
    public static String getIMEI(Activity activity) {
        TelephonyManager telephonyManager = (TelephonyManager) activity
                .getSystemService( Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
}
