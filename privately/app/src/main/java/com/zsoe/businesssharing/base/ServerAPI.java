package com.zsoe.businesssharing.base;


import com.zsoe.businesssharing.BuildConfig;
import com.zsoe.businesssharing.bean.ChanPinBeanItem;
import com.zsoe.businesssharing.bean.DetailFinanceBean;
import com.zsoe.businesssharing.bean.DetailJoinInvestmentBean;
import com.zsoe.businesssharing.bean.FileDownBean;
import com.zsoe.businesssharing.bean.HomeBean;
import com.zsoe.businesssharing.bean.ItemBankBean;
import com.zsoe.businesssharing.bean.ItemFinancBean;
import com.zsoe.businesssharing.bean.ItemJoinInvestmentBean;
import com.zsoe.businesssharing.business.login.LoginUser;

import java.util.List;

import okhttp3.FormBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface ServerAPI {
    public static final String ENDPOINT = BuildConfig.ENDPOINT;


    /**
     * 获取验证码
     */
    @POST("sms/send")
    Observable<RootResponse> getSms(@Body FormBody body);

    /**
     * 注册会员
     */
    @POST("user/register")
    Observable<RootResponse> register(@Body FormBody body);

    /**
     * 注册会员
     */
    @POST("user/login")
    Observable<RootResponse<LoginUser>> login(@Body FormBody body);

    /**
     * 首页
     */
    @POST("index/index")
    Observable<RootResponse<HomeBean>> index(@Body FormBody body);

    /**
     * 展厅
     */
    @POST("hall/index")
    Observable<RootResponse<HomeBean>> hall(@Body FormBody body);

    /**
     * 产品列表
     */
    @POST("index/product_list")
    Observable<RootResponse<List<ChanPinBeanItem>>> product_list(@Body FormBody body);

    /**
     * 产品列表
     */
    @POST("keyword")
    Observable<RootResponse<List<ChanPinBeanItem>>> keyword(@Body FormBody body);


    /**
     * 加盟列表
     */
    @POST("index/join_merchant_list")
    Observable<RootResponse<List<ItemJoinInvestmentBean>>> join_merchant_list(@Body FormBody body);

    /**
     * 招商加盟-详情
     */
    @POST("index/join_merchant_info")
    Observable<RootResponse<DetailJoinInvestmentBean>> join_merchant_info(@Body FormBody body);

    /**
     * 银行信贷列表
     */
    @POST("index/loan_list")
    Observable<RootResponse<List<ItemBankBean>>> loan_list(@Body FormBody body);

    /**
     * 融资项目列表
     */
    @POST("index/finance_list")
    Observable<RootResponse<List<ItemFinancBean>>> finance_list(@Body FormBody body);

    /**
     * 项目融资-详情
     */
    @POST("index/finance_info")
    Observable<RootResponse<DetailFinanceBean>> finance_info(@Body FormBody body);

    /**
     * 项目融资-详情-获取资料
     */
    @POST("index/finance_file_download")
    Observable<RootResponse<FileDownBean>> finance_file_download(@Body FormBody body);

}
