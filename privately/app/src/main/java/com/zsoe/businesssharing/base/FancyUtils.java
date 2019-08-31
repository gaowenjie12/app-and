package com.zsoe.businesssharing.base;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.reflect.TypeToken;
import com.zsoe.businesssharing.bean.RootHangYe;
import com.zsoe.businesssharing.business.login.LoginUser;

import java.util.List;

/**
 * Fancy 工具类,里面都是公用使用的东西
 * <p>
 * token
 * LoginModule
 * UserModule
 *
 * @author Edwin.Wu edwin.wu05@gmail.com
 * @version 2019-04-27 17:39
 * @since JDK1.8
 */
public final class FancyUtils {

    public final static String LOGIN_MODEL = "fancy_login_model";

    public final static String USER_PHONE = "fancy_user_phone";
    public final static String INDUSTRY_ALLCATE = "industry_allcate";

    public final static String SP_NAME = "fancy_SP";


    public static void setUserPhone(String phone) {
        SPUtils.getInstance(SP_NAME).put(USER_PHONE, phone);
    }

    public static String getUserPhone() {
        String data = SPUtils.getInstance(SP_NAME).getString(USER_PHONE, "");
        return data;
    }

    public static void setLoginUser(LoginUser loginUser) {

        String data = DApplication.getInstance().gson.toJson(loginUser);
        SPUtils.getInstance(SP_NAME).put(LOGIN_MODEL, data);
        DApplication.getInstance().initLoginUser(loginUser);
    }


    @Nullable
    public static LoginUser getLoginUser() {
        String data = SPUtils.getInstance(SP_NAME).getString(LOGIN_MODEL, "");
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        return DApplication.getInstance().gson.fromJson(data, LoginUser.class);
    }


    public static void setRootHangYe(List<RootHangYe> rootHangYe) {
        String data = DApplication.getInstance().gson.toJson(rootHangYe);
        SPUtils.getInstance(SP_NAME).put(INDUSTRY_ALLCATE, data);
    }


    @Nullable
    public static List<RootHangYe> getRootHangYe() {
        String data = SPUtils.getInstance(SP_NAME).getString(INDUSTRY_ALLCATE, "");
        if (TextUtils.isEmpty(data)) {
            return null;
        }

        return DApplication.getInstance().gson.fromJson(data, new TypeToken<List<RootHangYe>>() {
        }.getType());
    }
}
