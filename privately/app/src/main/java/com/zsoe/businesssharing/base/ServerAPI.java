package com.zsoe.businesssharing.base;


import com.zsoe.businesssharing.BuildConfig;
import com.zsoe.businesssharing.bean.CaiGouDetail;
import com.zsoe.businesssharing.bean.ChanPinBeanItem;
import com.zsoe.businesssharing.bean.CompanyInfo;
import com.zsoe.businesssharing.bean.DetailFinanceBean;
import com.zsoe.businesssharing.bean.DetailJiaoLiuBean;
import com.zsoe.businesssharing.bean.DetailJoinInvestmentBean;
import com.zsoe.businesssharing.bean.FenHuiBean;
import com.zsoe.businesssharing.bean.FileDownBean;
import com.zsoe.businesssharing.bean.GalleryRoomBean;
import com.zsoe.businesssharing.bean.GongYouBean;
import com.zsoe.businesssharing.bean.HaoYouBean;
import com.zsoe.businesssharing.bean.HomeBean;
import com.zsoe.businesssharing.bean.IndustryRoot;
import com.zsoe.businesssharing.bean.ItemBankBean;
import com.zsoe.businesssharing.bean.ItemBuZhangXinxBean;
import com.zsoe.businesssharing.bean.ItemCollectBean;
import com.zsoe.businesssharing.bean.ItemCompany;
import com.zsoe.businesssharing.bean.ItemFinancBean;
import com.zsoe.businesssharing.bean.ItemInsdustry;
import com.zsoe.businesssharing.bean.ItemJoinInvestmentBean;
import com.zsoe.businesssharing.bean.ItemMailBox;
import com.zsoe.businesssharing.bean.ItemWenZhangBean;
import com.zsoe.businesssharing.bean.JiaoLiuBean;
import com.zsoe.businesssharing.bean.MasterBean;
import com.zsoe.businesssharing.bean.MessageReturnBean;
import com.zsoe.businesssharing.bean.MySignBean;
import com.zsoe.businesssharing.bean.ProductDetail;
import com.zsoe.businesssharing.bean.RenCompanyBean;
import com.zsoe.businesssharing.bean.RootEventBean;
import com.zsoe.businesssharing.bean.RootSearchBean;
import com.zsoe.businesssharing.bean.SearchBean;
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
     * 重置密码 /api/v1/user/resetpwd
     */
    @POST("user/resetpwd")
    Observable<RootResponse> resetpwd(@Body FormBody body);

    /**
     * 登录会员
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
     * POST
     */
    @POST("index/stock_purchase_list")
    Observable<RootResponse<List<StockBean>>> stock_purchase_list(@Body FormBody body);

    /**
     * 采购/库存删除 /api/v1/my/stockpurchase_del 可以使用
     */
    @POST("my/stockpurchase_del")
    Observable<RootResponse> stockpurchase_del(@Body FormBody body);

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
     * 部长列表 大会长列表 /api/v1/attention/minister_and_associator_list 可以使用
     */
    @POST("attention/minister_and_associator_list")
    Observable<RootResponse<List<ItemBuZhangXinxBean>>> minister_list(@Body FormBody body);


    /**
     * 平台文章列表(5种类型)；1事件2帮助中心3系统公告4行业资讯5头条快讯 /api/v1/msg/article_list 可以使用
     */
    @POST("msg/article_list")
    Observable<RootResponse<List<ItemWenZhangBean>>> article_list(@Body FormBody body);


    /**
     * 事件列表 /api/v1/attention/event 可以使用
     */
    @POST("attention/event")
    Observable<RootResponse<RootEventBean>> attentionEvent(@Body FormBody body);

    /**
     * 信箱提交留言 /api/v1/attention/save_mailbox_msg 可以使用
     */
    @POST("attention/save_mailbox_msg")
    Observable<RootResponse> save_mailbox_msg(@Body FormBody body);


    /**
     * 普通用户 身份 领导回信列表 or 领导身份 信箱页面 列表 /api/v1/msg/mailbox_list 可以使用
     */
    @POST("msg/mailbox_list")
    Observable<RootResponse<List<MessageReturnBean>>> mailbox_list(@Body FormBody body);

    /**
     * 普通用户 身份 领导回信列表 查看 or 领导身份 信箱页面 列表-》查看 /api/v1/msg/mailbox_infolist 可以使用
     */
    @POST("msg/mailbox_infolist")
    Observable<RootResponse<List<ItemMailBox>>> mailbox_infolist(@Body FormBody body);


    /**
     * 我收到的留言列表-》查看 or 我收到的回复列表-》查看 /api/v1/msg/user_companymsg_infolist 可以使用
     */
    @POST("msg/user_companymsg_infolist")
    Observable<RootResponse<List<ItemMailBox>>> user_companymsg_infolist(@Body FormBody body);

    /**
     * 普通用户 身份 领导回信 列表 查看-》 回复 or 领导身份 领信箱页面 列表-》查看-》 回复 /api/v1/msg/mailbox_inforeply 可以使
     */
    @POST("msg/mailbox_inforeply")
    Observable<RootResponse> mailbox_inforeply(@Body FormBody body);

    /**
     * 我收到的留言列表 列表-》查看-》 回复 or 我收到的回复列表 列表-》查看-》 留言 /api/v1/msg/user_companymsg_inforeply 可以使用
     */
    @POST("msg/user_companymsg_inforeply")
    Observable<RootResponse> user_companymsg_inforeply(@Body FormBody body);


    /**
     * 我收到的留言列表 or 我收到的回复列表 /api/v1/msg/user_companymsg_list 可以使用
     */
    @POST("msg/user_companymsg_list")
    Observable<RootResponse<List<MessageReturnBean>>> user_companymsg_list(@Body FormBody body);


    /**
     * 收藏 or 取消收藏 /api/v1/my/collect 可以使用
     */
    @POST("my/collect")
    Observable<RootResponse> collect(@Body FormBody body);

    /**
     * 我的收藏列表 /api/v1/my/collect_list 可以使用
     */
    @POST("my/collect_list")
    Observable<RootResponse<ItemCollectBean>> collect_list(@Body FormBody body);

    /**
     * 获取认证公司信息 /api/v1/my/auth_companyinfo 可以使用
     */
    @POST("my/auth_companyinfo")
    Observable<RootResponse<RenCompanyBean>> auth_companyinfo(@Body FormBody body);


    /**
     * 我的认证公司提交 /api/v1/my/auth_company 可以使用
     */
    @POST("my/auth_company")
    Observable<RootResponse> auth_company(@Body FormBody body);

    /**
     * 我的收藏列表 -》查看全部 /api/v1/my/collect_infolist 可以使用
     */
    @POST("my/collect_infolist")
    Observable<RootResponse<List<ItemInsdustry>>> collect_infolist(@Body FormBody body);


    /**
     * 关于我们 /api/v1/my/about_us 可以使用
     */
    @POST("my/about_us")
    Observable<RootResponse<String>> about_us(@Body FormBody body);


    /**
     * 服务站列表 /api/v1/my/service_station 可以使用
     */
    @POST("my/service_station")
    Observable<RootResponse<List<ItemInsdustry>>> service_station(@Body FormBody body);


    /**
     * my/cardticket_list 可以使用
     */
    @POST("my/cardticket_list")
    Observable<RootResponse<List<ItemInsdustry>>> cardticket_list(@Body FormBody body);


    /**
     * 领取卡券 /api/v1/my/getcardticket 可以使用
     */
    @POST("my/getcardticket")
    Observable<RootResponse> getcardticket(@Body FormBody body);


    /**
     * 我领取的卡券列表 /api/v1/my/mycardticket_list 可以使用
     */
    @POST("my/mycardticket_list")
    Observable<RootResponse<List<ItemInsdustry>>> mycardticket_list(@Body FormBody body);

    /**
     * 删除我领取的卡券 /api/v1/my/mycardticket_del 可以使用
     */
    @POST("my/mycardticket_del")
    Observable<RootResponse> mycardticket_del(@Body FormBody body);


    /**
     * my/mysign 可以使用
     */
    @POST("my/mysign")
    Observable<RootResponse<MySignBean>> mysign(@Body FormBody body);

    /**
     * 排名券立即使用 /api/v1/my/use_rankticket 可以使用
     */
    @POST("my/mysign")
    Observable<RootResponse> use_rankticket(@Body FormBody body);


    /**
     * 签到 /api/v1/my/sign 可以使用
     */
    @POST("my/sign")
    Observable<RootResponse> sign(@Body FormBody body);

    /**
     * 修改会员个人信息 /api/v1/user/profile 可以使用
     */
    @POST("user/profile")
    Observable<RootResponse<LoginUser>> userProfile(@Body FormBody body);

    /**
     * 采购&需求-详情 /api/v1/index/stock_purchase_info
     */
    @POST("index/stock_purchase_info")
    Observable<RootResponse<CaiGouDetail>> stock_purchase_info(@Body FormBody body);

    /**
     * 我的-》推广 /api/v1/my/extenactivity_list 可以使用
     */
    @POST("my/extenactivity_list")
    Observable<RootResponse<List<JiaoLiuBean>>> extenactivity_list(@Body FormBody body);


    /**
     * 搜索页面 /api/v1/index/searchpage 可以使用
     */
    @POST("index/searchpage")
    Observable<RootResponse<RootSearchBean>> searchpage(@Body FormBody body);

    /**
     * 删除 历史搜索 /api/v1/index/historysearch_del 可以使用
     */
    @POST("index/historysearch_del")
    Observable<RootResponse> historysearch_del(@Body FormBody body);

    /**
     * 注销登录 /api/v1/user/logout
     */
    @POST("user/logout")
    Observable<RootResponse> logout(@Body FormBody body);

    /**
     * 添加好友 /api/v1/msg/add_friend 可以使用
     */
    @POST("msg/add_friend")
    Observable<RootResponse> add_friend(@Body FormBody body);

    /**
     * 我的好友列表 /api/v1/msg/myfriend_list 可以使用
     */
    @POST("msg/myfriend_list")
    Observable<RootResponse<List<HaoYouBean>>> myfriend_list(@Body FormBody body);

    /**
     * 第三方登录 /api/v1/user/third 可以使用
     */
    @POST("user/third")
    Observable<RootResponse<LoginUser>> third(@Body FormBody body);


    /**
     * 搜索 /api/v1/index/search 可以使用 ----------
     */
    @POST("index/search")
    Observable<RootResponse<List<SearchBean>>> search(@Body FormBody body);


    /**
     * 移除好友 /api/v1/msg/remove_friend 可以使用
     */
    @POST("msg/remove_friend")
    Observable<RootResponse> remove_friend(@Body FormBody body);

}
