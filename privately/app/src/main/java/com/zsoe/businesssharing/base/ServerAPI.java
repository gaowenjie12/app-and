package com.zsoe.businesssharing.base;


import com.zsoe.businesssharing.BuildConfig;
import com.zsoe.businesssharing.bean.ChanPinBeanItem;
import com.zsoe.businesssharing.bean.CompanyInfo;
import com.zsoe.businesssharing.bean.DetailFinanceBean;
import com.zsoe.businesssharing.bean.DetailJiaoLiuBean;
import com.zsoe.businesssharing.bean.DetailJoinInvestmentBean;
import com.zsoe.businesssharing.bean.FenHuiBean;
import com.zsoe.businesssharing.bean.FileDownBean;
import com.zsoe.businesssharing.bean.GalleryRoomBean;
import com.zsoe.businesssharing.bean.GongYouBean;
import com.zsoe.businesssharing.bean.HomeBean;
import com.zsoe.businesssharing.bean.IndustryRoot;
import com.zsoe.businesssharing.bean.ItemBankBean;
import com.zsoe.businesssharing.bean.ItemBuZhangXinxBean;
import com.zsoe.businesssharing.bean.ItemCompany;
import com.zsoe.businesssharing.bean.ItemFinancBean;
import com.zsoe.businesssharing.bean.ItemJoinInvestmentBean;
import com.zsoe.businesssharing.bean.ItemWenZhangBean;
import com.zsoe.businesssharing.bean.JiaoLiuBean;
import com.zsoe.businesssharing.bean.MasterBean;
import com.zsoe.businesssharing.bean.ProductDetail;
import com.zsoe.businesssharing.bean.StockBean;
import com.zsoe.businesssharing.bean.XinXiBena;
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
     * 产品详情
     */
    @POST("index/product_info")
    Observable<RootResponse<ProductDetail>> product_info(@Body FormBody body);

    /**
     *
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

    /**
     * 展厅页接口
     */
    @POST("hall/index")
    Observable<RootResponse<GalleryRoomBean>> hallIndex(@Body FormBody body);

    /**
     * 行业详情
     */
    @POST("hall/industry_info")
    Observable<RootResponse<IndustryRoot>> industry_info(@Body FormBody body);

    /**
     * 企业详情
     */
    @POST("hall/company_info")
    Observable<RootResponse<CompanyInfo>> company_info(@Body FormBody body);

    /**
     * POST 企业列表
     */
    @POST("hall/company_list")
    Observable<RootResponse<List<ItemCompany>>> company_list(@Body FormBody body);

    /**
     * POST 行业人物列表
     */
    @POST("hall/industry_person_list")
    Observable<RootResponse<List<MasterBean>>> industry_person_list(@Body FormBody body);

    /**
     * POST 人物详情
     */
    @POST("hall/industry_person_info")
    Observable<RootResponse<MasterBean>> industry_person_info(@Body FormBody body);

    /**
     * POST 全部行业分类
     */
    @POST("hall/industry_allcate")
    Observable<RootResponse<GongYouBean>> industry_allcate(@Body FormBody body);

    /**
     * POST 全部行业分类
     */
    @POST("index/stock_purchase_list")
    Observable<RootResponse<List<StockBean>>> stock_purchase_list(@Body FormBody body);

    /**
     * POST 交流会列表 /api/v1/hall/communication_list
     */
    @POST("hall/communication_list")
    Observable<RootResponse<List<JiaoLiuBean>>> communication_list(@Body FormBody body);

    /**
     * POST 交流会详情 /api/v1/hall/communication_info
     */
    @POST("hall/communication_info")
    Observable<RootResponse<DetailJiaoLiuBean>> communication_info(@Body FormBody body);

    /**
     * 分会介绍 /api/v1/attention/index
     */
    @POST("attention/index")
    Observable<RootResponse<FenHuiBean>> attentionIndex(@Body FormBody body);

    /**
     * 架构文化 /api/v1/attention/frame
     */
    @POST("attention/frame")
    Observable<RootResponse<FenHuiBean>> attentionFrame(@Body FormBody body);

    /**
     * 信箱 /api/v1/attention/mailbox 可以使用
     */
    @POST("attention/mailbox")
    Observable<RootResponse<List<XinXiBena>>> attentionMailbox(@Body FormBody body);


    /**
     * 部长列表 /api/v1/attention/minister_list 可以使用
     */
    @POST("attention/minister_list")
    Observable<RootResponse<List<ItemBuZhangXinxBean>>> minister_list(@Body FormBody body);


    /**
     * 平台文章列表(5种类型)；1事件2帮助中心3系统公告4行业资讯5头条快讯 /api/v1/msg/article_list 可以使用
     */
    @POST("msg/article_list")
    Observable<RootResponse<List<ItemWenZhangBean>>> article_list(@Body FormBody body);

}
