package com.zsoe.businesssharing.base;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019/4/28 16:21
 * @version:
 * @类说明:
 */
public class Config {

    public static final String INTENT_PARAMS1 = "params1";
    public static final String INTENT_PARAMS2 = "params2";
    public static final String INTENT_PARAMS3 = "params3";
    public static final String INTENT_PARAMS4 = "params4";
    public static final String INTENT_PARAMS5 = "params5";
    public static final String INTENT_PARAMS6 = "params6";
    public static final String INTENT_PARAMS7 = "params7";

    public final static int IMAGE_PICKER = 99;
    public static final String BASE_IMAGE_CACHE = "ONION";
    public static final String CHAT_IMAGE_CACHE = "easechat";


    /**
     * role : 0=无角色1=劳务人员2=劳务经理4=劳务端管理员8=酒店安保16=酒店经理32=酒店管理员
     */
    public static final String MANAGER = "16";
    public static final String ANBAO = "8";
    public static final int pageNum = 10;

    //状态 1=全部 2=未接单 3=已接单 4=待结算 5=已完成

    public static final int all = 1;
    public static final int haveorder = 3;
    public static final int notreceived = 2;
    public static final int forthe = 4;
    public static final int finish = 5;

    public static final String changecode = "5";

    public static final String LNS = "https://www.bbyonggong.com/policy/yonggong/privacy.html";
    public static final String ZG = "https://www.bbyonggong.com/policy/zhigong/privacy.html";

    public static final int REQUEST_CODE_TOKEPHOTO = 100;

    public static final int ICON_DEFAULT = 0;

}
