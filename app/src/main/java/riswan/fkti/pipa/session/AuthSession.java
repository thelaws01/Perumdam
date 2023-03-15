package riswan.fkti.pipa.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import riswan.fkti.pipa.activity.auth.LoginActivity;

public class AuthSession {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private Context mContext;
    private int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LoginPref";
    private static final String IS_LOGIN = "isLogin";



    public static final String DATAPREF = "riswa.fkti.pengaduan";
    public static final String DATASTREAM = "riswa.fkti.pengaduan";

    private static final String KEY_ID = "id";
    public static final String KEY_AKSES = "akses_id";
    public static final String KEY_NAME = "nama";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_NIK = "nik";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_FOTO = "foto";
    public static final String KEY_BARU = "baru";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_ALAMAT = "alamat";
    public static final String KEY_KEC = "kecamatan";

    public AuthSession(Context mContext){
        this.mContext = mContext;
        preferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void createLogin(String id, String akses_id, String name, String nik, String phone, String foto, String ttl, String alamat, String username, String plain){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_AKSES, akses_id);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_NIK, nik);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_FOTO, foto);
        editor.putString(KEY_KEC,ttl);
        editor.putString(KEY_ALAMAT, alamat);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, plain);
        editor.putString(KEY_BARU, "baru");
        editor.commit();
    }

    public void regisBaru(String status, String username){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_BARU, status);
        editor.putString(KEY_USERNAME, username);
        editor.commit();
    }

    public void updateProfile(String peler, String phone, String alamat, String nama, String nik){
        editor.putString(KEY_BARU, peler);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_ALAMAT, alamat);
        editor.putString(KEY_NAME, nama);
        editor.putString(KEY_NIK, nik);
        editor.commit();
    }

    public void updateFoto(String name, String key){
        editor.putString(KEY_USERNAME, name);
        editor.putString(KEY_FOTO, key);
        editor.commit();
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_ID, preferences.getString(KEY_ID, null));
        user.put(KEY_AKSES, preferences.getString(KEY_AKSES, null));
        user.put(KEY_NAME, preferences.getString(KEY_NAME, null));
        user.put(KEY_USERNAME, preferences.getString(KEY_USERNAME, null));
        user.put(KEY_NIK, preferences.getString(KEY_NIK, null));
        user.put(KEY_PASSWORD, preferences.getString(KEY_PASSWORD, null));
        user.put(KEY_FOTO, preferences.getString(KEY_FOTO, null));
        user.put(KEY_BARU, preferences.getString(KEY_BARU, null));
        user.put(KEY_ALAMAT, preferences.getString(KEY_ALAMAT, null));
        user.put(KEY_PHONE, preferences.getString(KEY_PHONE, null));
        return user;
    }


    public void check_login(){
        if (!this.is_login()){
            Intent aa = new Intent(mContext, LoginActivity.class);
            aa.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            aa.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(aa);
        }
    }

    public void logout()
    {
        editor.clear();
        editor.commit();
        Intent aa = new Intent(mContext, LoginActivity.class);
        aa.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        aa.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(aa);
    }

    public boolean is_login(){
        return preferences.getBoolean(IS_LOGIN, false);
    }

}
