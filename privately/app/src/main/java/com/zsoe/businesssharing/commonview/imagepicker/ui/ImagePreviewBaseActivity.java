package com.zsoe.businesssharing.commonview.imagepicker.ui;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.commonview.imagepicker.ImagePicker;
import com.zsoe.businesssharing.commonview.imagepicker.adapter.ImagePageAdapter;
import com.zsoe.businesssharing.commonview.imagepicker.bean.ImageItem;
import com.zsoe.businesssharing.commonview.imagepicker.view.ViewPagerFixed;

import java.util.ArrayList;

/**
 * Created by onion on 2016/8/2.
 */
public abstract class ImagePreviewBaseActivity extends BaseActivity {

    protected ImagePicker imagePicker;
    protected ArrayList<ImageItem> mImageItems;      //跳转进ImagePreviewFragment的图片文件夹
    protected int mCurrentPosition = 0;              //跳转进ImagePreviewFragment时的序号，第几个图片
    protected TextView mTitleCount;                  //显示当前图片的位置  例如  5/31
    protected ArrayList<ImageItem> selectedImages;   //所有已经选中的图片
    protected View content;
    protected View topBar;
    protected ViewPagerFixed mViewPager;
    protected ImagePageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_image_preview);
        mCurrentPosition = getIntent().getIntExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
        mImageItems = (ArrayList<ImageItem>) getIntent().getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
        imagePicker = ImagePicker.getInstance();
        //已选预览是有图的
        if(mImageItems==null||mImageItems.isEmpty()) {
            mImageItems = imagePicker.getCurrentImageFolderItems();
        }
        selectedImages = imagePicker.getSelectedImages();
        //初始化控件
        content = findViewById(R.id.content);

        //因为状态栏透明后，布局整体会上移，所以给头部加上状态栏的margin值，保证头部不会被覆盖
        topBar = findViewById(R.id.top_bar);
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) topBar.getLayoutParams();
//        params.topMargin = NinegridUtils.getStatusHeight(this);
//        topBar.setLayoutParams(params);
        topBar.findViewById(R.id.btn_ok).setVisibility(View.GONE);
        topBar.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitleCount = (TextView) findViewById(R.id.tv_des);

        mViewPager = (ViewPagerFixed) findViewById(R.id.viewpager);
        mViewPager.setPageMargin(40);
        mAdapter = new ImagePageAdapter(this, mImageItems);
        mAdapter.setPhotoViewClickListener(new ImagePageAdapter.PhotoViewClickListener() {
            @Override
            public void OnPhotoTapListener(View view, float v, float v1) {
                onImageSingleTap();
            }
        });
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mCurrentPosition, false);

        //初始化当前页面的状态
        mTitleCount.setText(getString(R.string.preview_image_count, mCurrentPosition + 1, mImageItems.size()));
    }


    /** 单击时，隐藏头和尾 */
    public abstract void onImageSingleTap();


    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    protected boolean isFitSystemEnabled() {
        return false;
    }
}