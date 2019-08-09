package com.zsoe.businesssharing.commonview.recyclerview.entity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class MultipleItem implements MultiItemEntity {

    /**
     * 今日签到，全部考勤栏
     */
    public static final int TYPE_ALL_SIGN_LABEL = 100;
    /**
     * 单条今日签到item
     */
    public static final int TYPE_TODAY_SIGN = 103;

    /**
     * 全部签到item
     */
    public static final int TYPE_ALL_SIGN = 106;

    /**
     * 今日课程栏
     */
    public static final int TYPE_COURSES_LABEL = 109;
    /**
     * 今日课程item
     */
    public static final int TYPE_TODAY_COURSES = 112;

    /**
     * 直播item
     */
    public static final int TYPE_LIVE = 113;
    /**
     * 全部课程
     */
    public static final int TYPE_ALL_COURSES = 115;

    /**
     * 评价，反思
     */
    public static final int TYPE_PINGJIA_FANSI = 118;

    //---------------------课程相关------------
    /**
     * 默认类型
     */
    public static final int TYPE_DEFAULT = 0;
    /**
     * 课程资源标签
     */
    public static final int TYPE_RESOURCE_TAG = 119;

    /**
     * 课程里面的直播回放视频
     */
    public static final int TYPE_VIDEO_RECORD = 217;
    /**
     * 课程活动标签
     */
    public static final int TYPE_COURSE_ACTIVITY_TAG = 219;

    /**
     * 课程里面的直播回放视频标签
     */
    public static final int TYPE_VIDEO_RECORD_TAG = 218;


    /**
     * 课程资源
     */
    public static final int TYPE_RESOURCE = 120;

    /**
     * 管理端首页菜单
     */
    public static final int TYPE_MENU = 121;
    /**
     * 管理端首页照片墙推荐
     */
    public static final int TYPE_PIC_WALL = 122;
    /**
     * 无照片时显示拍一张
     */
    public static final int TYPE_PIC_WALL_NONE = 123;

    /**
     * 项目任务item的活动条目
     */
    public static final int TYPE_PROJECT_PHOTOWALL = 124;
    /**
     * 项目任务item的活动条目
     */
    public static final int TYPE_PROJECT_VOTE = 125;
    /**
     * 项目任务item的活动条目
     */
    public static final int TYPE_PROJECT_DISCUSS = 126;
    /**
     * 项目任务item的活动条目
     */
    public static final int TYPE_PROJECT_HOMEWORK = 127;
    /**
     * 项目任务item的活动条目
     */
    public static final int TYPE_PROJECT_RESOURCE = 128;
    /**
     * 项目任务item的活动条目
     */
    public static final int TYPE_PROJECT_QUESTIONNAIRE = 129;

    /**
     * 小组积分相关
     */
    public static final int TYPE_GROUPINTEGRAIL_MORE = 131;
    public static final int TYPE_GROUPINTEGRAIL_HEADTAG = 132;
    public static final int TYPE_GROUPINTEGRAIL_NODATA = 133;
    /**
     * 日志图片类型
     */
    public static final int TYPE_LOG_IMG = 134;
    /**
     * 同学圈日志类型
     */
    public static final int TYPE_LOG_CIRCLE = 135;
    /**
     * 更多
     */
    public static final int TYPE_SPEAK_MORE = 136;

    protected int itemType;

    public MultipleItem() {
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    /*一下是网络课程布局类型*/
    public static final int TYPE_MUST_COURSE = 141;
    public static final int TYPE_ITEM_COURSE = 142;
    public static final int TYPE_SELECT_COURSE = 143;
    public static final int TYPE_NODATA_COURSE = 144;
    public static final int TYPE_ADD_COURSE = 145;
    public static final int TYPE_SEE_ALL_SELECTCOURSE = 146;

    /**
     * 选课中心
     */
    public static final int TYPE_COURSE_TOP = 211;
    public static final int TYPE_COURSE_MIDDLE = 212;
    public static final int TYPE_COURSE_BOTTOM = 213;

    /**
     * 学员端首页选课类型
     */
    public static final int TYPE_INXEX_SELECT_COURSE = 214;

    /**
     * 砖家首页
     */
    public static final int TYPE_INXEX_EXPERT_TAG = 215;
    public static final int TYPE_INXEX_EXPERT_NODATA = 216;


}
