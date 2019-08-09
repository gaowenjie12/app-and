package com.zsoe.businesssharing.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.zsoe.businesssharing.base.DApplication;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/20.
 */
public class PreferencesUtils {
    final static String SPNAME = "ONION2015720";
    public final static String SPAPPINFO = "appinfo";
    final static String THROWABLE_KEY = "throwable_key";
    final static Gson gson = new Gson();
    private static PreferencesUtils instance = new PreferencesUtils();

    private PreferencesUtils() {
    }

    public static PreferencesUtils getInstance() {
        return instance;
    }


    public void saveUserId(long clazzId) {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPAPPINFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putLong("userId", clazzId);
        editor.commit();
    }

    public long getUserId() {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPAPPINFO, Context.MODE_PRIVATE);
        return spf.getLong("userId", 0);
    }

    public void saveRole(String role) {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPAPPINFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("role", role);
        editor.commit();
    }

    public String getRole() {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPAPPINFO, Context.MODE_PRIVATE);
        return spf.getString("role", "0");
    }


    public void saveToken(String token) {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPAPPINFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("token", token);
        editor.commit();
    }

    public void savePhoneNum(String name) {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPAPPINFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("phoneNum", name);
        editor.commit();
    }

    public String getPhoneNum() {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPAPPINFO, Context.MODE_PRIVATE);
        return spf.getString("phoneNum", "");
    }


    public String getToken() {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPAPPINFO, Context.MODE_PRIVATE);
        return spf.getString("token", "");
    }

    public <T> void saveList(String tag, List<T> datalist) {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPAPPINFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);

        editor.putString(tag, strJson);
        editor.commit();

    }

    public <T> void clearList(String tag) {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPAPPINFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();


        editor.putString(tag, "");
        editor.commit();

    }

    /**
     * 获取List
     *
     * @param tag
     * @return
     */
    public <T> List<T> getDataList(String tag, Type typeToken) {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPAPPINFO, Context.MODE_PRIVATE);
        List<T> datalist = new ArrayList<T>();
        String strJson = spf.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, typeToken);
        return datalist;

    }

    //取bean
    public <T> T getBean(Class<T> c) {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPNAME, Context.MODE_PRIVATE);
        return gson.fromJson(spf.getString(c.getName(), ""), c);
    }

    //存bean
    public void saveBean(Object p) {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = spf.edit();
        edit.putString(p.getClass().getName(), gson.toJson(p));
        edit.commit();
    }

    public void saveLogin(boolean login) {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPAPPINFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putBoolean("login", login);
        editor.commit();
    }

    public boolean isLogin() {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPAPPINFO, Context.MODE_PRIVATE);
        return spf.getBoolean("login", false);
    }

    public void saveDU(boolean du) {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPAPPINFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putBoolean(DApplication.getInstance().getUserId() + "du", du);
        editor.commit();
    }

    public boolean isDU() {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPAPPINFO, Context.MODE_PRIVATE);
        return spf.getBoolean(DApplication.getInstance().getUserId() + "du", false);
    }


    public void saveST(boolean st) {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPAPPINFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putBoolean(DApplication.getInstance().getUserId() + "st", st);
        editor.commit();
    }

    public boolean isST() {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPAPPINFO, Context.MODE_PRIVATE);
        return spf.getBoolean(DApplication.getInstance().getUserId() + "st", true);
    }


    public void clearBean(Class c) {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = spf.edit();
        edit.putString(c.getName(), "");
        edit.commit();
    }


    public void saveWel(boolean login) {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPAPPINFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putBoolean("wel", login);
        editor.commit();
    }

    public boolean isWel() {
        final SharedPreferences spf = DApplication.getInstance().getSharedPreferences(
                SPAPPINFO, Context.MODE_PRIVATE);
        return spf.getBoolean("wel", false);
    }
}
