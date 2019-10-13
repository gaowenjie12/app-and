package com.zsoe.businesssharing.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.SPUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yayandroid.locationmanager.LocationManager;
import com.zsoe.businesssharing.BuildConfig;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.business.login.LoginActivity;
import com.zsoe.businesssharing.business.login.LoginUser;
import com.zsoe.businesssharing.commonview.imagepicker.ImagePicker;
import com.zsoe.businesssharing.commonview.imagepicker.view.CropImageView;
import com.zsoe.businesssharing.easechat.DemoHelper;
import com.zsoe.businesssharing.utils.CertificateUtils;
import com.zsoe.businesssharing.utils.ImagePipelineConfigUtils;
import com.zsoe.businesssharing.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.Nullable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author 于长亮   & E-mail: yuchl@mail.open.com.cn
 * @create_time 创建时间：2019/4/24 17:59
 * @version:
 * @类说明:
 */
public class DApplication extends Application {
    protected static DApplication instance;
    protected ArrayList<Activity> mList = new ArrayList<>();
    public static Gson gson = new Gson();
    private ImagePicker imagePicker;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        LocationManager.enableLog(true);

        //init demo helper
        DemoHelper.getInstance().init(this);

        // 请确保环信SDK相关方法运行在主进程，子进程不会初始化环信SDK（该逻辑在EaseUI.java中）
//        if (EaseUI.getInstance().isMainProcess(this)) {
//            // 初始化华为 HMS 推送服务, 需要在SDK初始化后执行
//            HMSPushHelper.getInstance().initHMSAgent(instance);
//
//            EMPushHelper.getInstance().setPushListener(new PushListener() {
//                @Override
//                public void onError(EMPushType pushType, long errorCode) {
//                    // TODO: 返回的errorCode仅9xx为环信内部错误，可从EMError中查询，其他错误请根据pushType去相应第三方推送网站查询。
//                    EMLog.e("PushClient", "Push client occur a error: " + pushType + " - " + errorCode);
//                }
//            });
//        }

        //动态配置打印开关
        LogUtil.isDebug = true;
        initLocation();

        //我们建议应用开发者在Activity或Service类中调用个推SDK的初始化方法，确保SDK在各种情况下都能正常运行。
        // 一般情况下可以在主Activity的onCreate()或者onResume()方法中调用，也可以在多个主要界面Activity的onCreate()或onResume()方法中调用。
        // 反复调用SDK初始化并不会有什么副作用。
        // com.getui.demo.DemoPushService 为第三方自定义推送服务
//        PushManager.getInstance().initialize(getApplicationContext(), BPushService.class);
//        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), BIntentService.class);


        /**
         * 数据库框架
         */
//        ActiveAndroid.initialize(this);

        /**
         * fresco图片加载配置
         */
        ImagePipelineConfig config = ImagePipelineConfigUtils.getDefaultImagePipelineConfig(this);
        Fresco.initialize(this, config);

        initLogger();
        /**
         * 友盟统计
         注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，
         也需要在App代码中调用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，
         UMConfigure.init调用中appkey和channel参数请置为null）。

         注意：如果您已经在AndroidManifest.xml中配置过appkey和channel值，可以调用此版本初始化函数。
         */
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);
        //只有正式环境开启友盟错误统计
        MobclickAgent.setCatchUncaughtExceptions(!BuildConfig.DEBUG);

        initHttpClient();

        /**
         * 图片相册
         */
        initImagePicker();
        initImageLoader();


        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);

        UMShareAPI.get(this);
        /**
         * 友盟相关平台配置。注意友盟官方新文档中没有这项配置，但是如果不配置会吊不起来相关平台的授权界面
         */
        PlatformConfig.setWeixin("wx96c57014385a0e0c", "64a432c883355d9a106b9f23cdfb952c");//微信APPID和AppSecret
//        PlatformConfig.setQQZone("1109933226", "jqoB2ONdjIZdsiPj");//QQAPPID和AppSecret
        PlatformConfig.setSinaWeibo("2335429698", "8093274847f1350cabd42fd2bd7806e2", "https://www.baidu.com");//微博
    }


    private void initImageLoader() {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.icon_default) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.icon_default) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.icon_default) // 设置图片加载或解码过程中发生错误显示的图片
//                .resetViewBeforeLoading(false)  // default 设置图片在加载前是否重置、复位
//                .delayBeforeLoading(1000)  // 下载前的延迟时间
                .cacheInMemory(true) // default  设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // default  设置下载的图片是否缓存在SD卡中
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default 设置图片以如何的编码方式显示
//                .bitmapConfig(Bitmap.Config.RGB_565) // default 设置图片的解码类型
                //.decodingOptions(...)  // 图片的解码设置
                .displayer(new SimpleBitmapDisplayer()) // default  还可以设置圆角图片new RoundedBitmapDisplayer(20)

                .build();


        File cacheDir = StorageUtils.getCacheDirectory(this);  //缓存文件夹路径
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)// 开始构建 ,图片加载配置
                .threadPriority(Thread.NORM_PRIORITY - 2)// 设置线程优先级
                .threadPoolSize(3)// 线程池内加载的数量 ;减少配置之中线程池的大小，(.threadPoolSize).推荐1-5；
//                .denyCacheImageMultipleSizesInMemory()// 设置加载的图片有多样的
                .tasksProcessingOrder(QueueProcessingType.LIFO)// 图片加载任务顺序
//                .memoryCache(new WeakMemoryCache())//使用.memoryCache(new WeakMemoryCache())，不要使用.cacheInMemory();
//                .memoryCacheExtraOptions(480, 800) // 即保存的每个缓存文件的最大长宽
                .memoryCacheSizePercentage(60)// 图片内存占应用的60%；
//                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())//使用HASHCODE对UIL进行加密命名
//                .diskCacheFileNameGenerator(new Md5FileNameGenerator())// 将保存的时候的URI名称用MD5 加密
                .diskCacheSize(50 * 1024 * 1024) // 缓存设置大小为50 Mb
                .diskCache(new UnlimitedDiskCache(cacheDir))// 自定义缓存路径
                .diskCacheFileCount(100) // 缓存的文件数量
//                .denyCacheImageMultipleSizesInMemory()// 自动缩放
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
//                .memoryCacheExtraOptions(480, 800)//设置缓存图片时候的宽高最大值，默认为屏幕宽高;保存的每个缓存文件的最大长宽
                .defaultDisplayImageOptions(options)// 如果需要打开缓存机制，需要自己builde一个option,可以是DisplayImageOptions.createSimple()
                .writeDebugLogs() // Remove for release app
                .build();

        ImageLoader.getInstance().init(configuration);

    }

    public String getCity() {
        return SPUtils.getInstance().getString("city");
    }

    public void setCity(String city) {
        SPUtils.getInstance().put("city", city);
    }


    public static DApplication getInstance() {
        return instance;
    }

    public Activity getTopActivity() {
        if (mList.size() > 0) {
            return mList.get(mList.size() - 1);
        }
        return null;
    }

    /**
     * 这个问题由于项目中类太多，方法太多。 Android平台的Java虚拟机Dalvik在执行DEX格式的Java应用程序时，
     * 使用原生类型short来 索引DEX文件中的方法。这意味着单个DEX文件可被引用的方法总数被限制为65536。
     * 通常APK包含一个classes.dex文件，因此Android应用 的方法总数不能超过这个数量，这包括Android框架、类库和你自己开发的代码。
     * 这个问题可以通过将一个DEX文件分拆成多个DEX文件解决。而下面的代码就是将一个DEX文件分拆成多个DEX文件，完美解决NoClassDefFoundError。
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    private LoginUser loginUser;

    /**
     * 登录信息
     */
    public LoginUser getLoginUser() {
        if (null == loginUser) {
            loginUser = FancyUtils.getLoginUser();
        }
        return loginUser;
    }

    public void initLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }


    public void exit() { // 遍历List，退出每一个Activity
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
            mList.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.loginUser = null;
        FancyUtils.setLoginUser(null);
    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public boolean removeActivity(Activity activity) {
        return mList.remove(activity);
    }


    private static ServerAPI serverAPI;


    private void initImagePicker() {
        imagePicker = ImagePicker.getInstance();
        //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    public void changePickerModeAndClear(boolean isMultiMode, int selectLimit) {
        imagePicker.clear();
        imagePicker.setMultiMode(isMultiMode);
        imagePicker.setCrop(!isMultiMode);
        imagePicker.setSelectLimit(selectLimit);
    }

    public OkHttpClient okHttpClient;

    //登陆成功，退出后，都调用此方法，用来刷新token
    public void initHttpClient() {
        okHttpClient = genericClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerAPI.ENDPOINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())//服务器崩溃 会导致解析出错
                .client(okHttpClient)
                .build();

        serverAPI = retrofit.create(ServerAPI.class);

//        loginBean = PreferencesUtils.getInstance().getBean(LoginBean.class);
//
//        if (PreferencesUtils.getInstance().isLogin() && loginBean != null) {
//            try {
//                this.token = loginBean.getToken();
//                this.userId = loginBean.getUser_id();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

    }


    public OkHttpClient genericClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {//测试版本看log
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {//线上版本 看log会影响传图
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //默认10s
        builder.connectTimeout(60, TimeUnit.SECONDS);//设置超时时间
        builder.writeTimeout(60, TimeUnit.SECONDS);//设置读取超时时间
        builder.readTimeout(60, TimeUnit.SECONDS);//设置写入超时时间
//        builder.retryOnConnectionFailure(true);  //失败重试
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request compressedRequest = originalRequest.newBuilder()
//                        .header("Content-Encoding", "gzip")
//                        .removeHeader("Content-Type")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .addHeader("charset", "UTF-8")
                        .addHeader("appVersion", String.valueOf(getVersionCode()))
                        .addHeader("token", getToken())
                        .addHeader("city", URLEncoder.encode(getCity()))
//                        .method(originalRequest.method(), gzip(originalRequest.body()))
                        .build();
                return chain.proceed(compressedRequest);
            }
        }).addInterceptor(logging);
//        try {
//            setCertificates(builder, getAssets().open("server.crt"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        CertificateUtils.setCertificates(builder);
        return builder.build();
    }


    public int getVersionCode() {
        PackageManager manager = this.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        return 1;
    }


    private String token = "";

    /**
     * 获取全局token
     *
     * @return
     */

    public String getToken() {
        if (TextUtils.isEmpty(token)) {
            LoginUser loginUser = getLoginUser();
            if (null != loginUser) {
                token = getLoginUser().getToken();
            } else {
                token = "";
            }
        }
        return token;
    }

    private int role;

    /**
     * 获取全局身份
     *
     * @return
     */

    public int getRole() {
        if (role == 0) {
//            role = Integer.parseInt(PreferencesUtils.getInstance().getRole());
        }
        return role;
    }

    private long userId;

    /**
     * 获取全局userId
     *
     * @return
     */

//    public long getUserId() {
//        if (userId == 0) {
////            userId = PreferencesUtils.getInstance().getUserId();
//        }
//        return userId;
//    }


//    private LoginBean loginBean;
//
//    /**
//     * 获取全局LoginBean
//     *
//     * @return
//     */
//
//    public LoginBean getLoginBean() {
//        if (null == loginBean) {
//            loginBean = PreferencesUtils.getInstance().getBean(LoginBean.class);
//        }
//        return loginBean;
//    }
    public static ServerAPI getServerAPI() {
        return serverAPI;
    }

//    public void initLoginUser(LoginBean loginBean) {
//
//        this.token = loginBean.getToken();
//        this.loginBean = loginBean;
//        this.userId = loginBean.getUser_id();
//        this.role = loginBean.getRole();
//
//        PreferencesUtils.getInstance().saveToken(this.token);
//        PreferencesUtils.getInstance().saveUserId(userId);
//        PreferencesUtils.getInstance().saveBean(this.loginBean);
//        PreferencesUtils.getInstance().saveLogin(true);
//        PreferencesUtils.getInstance().saveRole(String.valueOf(role));
//
//    }


    /**
     * 获取版本名称
     *
     * @param context 上下文
     * @return 版本名称
     */
    public String getVersionName() {

        //获取包管理器
        PackageManager pm = this.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(this.getPackageName(), 0);
            //返回版本号
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void startLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    private void initLogger() {
        PrettyFormatStrategy alishan = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  //（可选）是否显示线程信息。默认值true
                .methodCount(1)        //（可选）要显示的方法行数。默认值2
//                .methodOffset(7)     //（可选）隐藏内部方法调用到偏移量。默认值5
//                .logStrategy()       //（可选）更改要打印的日志策略。默认LogCat
                .tag("open")        //（可选）每个日志的全局标记。默认PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(alishan) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
//                return super.isLoggable(priority, tag);
                return !BuildConfig.DEBUG;
            }
        });
    }


    private void initLocation() {
        // 初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        // 设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        // 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Transport);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        // 时间间隔1分钟
        mLocationOption.setInterval(1000 * 60);
        // 设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);
        mLocationClient.setLocationOption(mLocationOption);

        //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
        mLocationClient.stopLocation();
        mLocationClient.startLocation();

    }


    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {

            amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
            double latitude = amapLocation.getLatitude();//获取纬度
            double longitude = amapLocation.getLongitude();//获取经度
            amapLocation.getAccuracy();//获取精度信息
            String positionName = amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
            amapLocation.getCountry();//国家信息
            amapLocation.getProvince();//省信息
            amapLocation.getCity();//城市信息
            amapLocation.getDistrict();//城区信息
            amapLocation.getStreet();//街道信息
            amapLocation.getStreetNum();//街道门牌号信息
            amapLocation.getCityCode();//城市编码
            amapLocation.getAdCode();//地区编码
            amapLocation.getAoiName();//获取当前定位点的AOI信息
            amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
            amapLocation.getFloor();//获取当前室内定位的楼层
            amapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
            // 获取定位时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(amapLocation.getTime());
            df.format(date);
            locationName = amapLocation.getCity();
            Log.e("open", "城市名称==" + amapLocation.getCity());

        }
    };

    public String locationName;

    private AMapLocationClientOption mLocationOption = new AMapLocationClientOption();


    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        mLocationClient.stopLocation();
        mLocationClient.onDestroy();
        super.onTerminate();
    }

}
