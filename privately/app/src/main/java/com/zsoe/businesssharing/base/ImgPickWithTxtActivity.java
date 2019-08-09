package com.zsoe.businesssharing.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.presenter.MPresenter;
import com.zsoe.businesssharing.commonview.imagepicker.ImagePicker;
import com.zsoe.businesssharing.commonview.imagepicker.bean.ImageItem;
import com.zsoe.businesssharing.commonview.imagepicker.ui.ImageGridActivity;
import com.zsoe.businesssharing.commonview.imagepicker.ui.ImagePreviewDelActivity;

import java.util.ArrayList;

import rx.functions.Action1;

/**
 * Created by onion on 2016/8/2.
 */
public abstract class ImgPickWithTxtActivity<V extends MPresenter> extends BaseActivity<V> implements ImagePickerAdapter.OnRecyclerViewItemClickListener {

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    protected ImagePickerAdapter adapter;
    //private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    protected int maxImgCount = 3;               //允许选择图片最大数
    protected ImagePicker imagePicker;

    /**
     * 选择图片九宫格
     */
    protected RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getCotentViewId());
        //最好放到 Application oncreate执行
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initImagePicker();
        initWidget();
    }

    protected abstract int getCotentViewId();

    protected void initImagePicker() {
        /*ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素*/
        imagePicker = ImagePicker.getInstance();
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        DApplication.getInstance().changePickerModeAndClear(true, maxImgCount);
    }

    protected Action1<MenuItem> menuClick = null;
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (menuClick != null) {
                menuClick.call(menuItem);
            }
            return true;
        }
    };


    private void initWidget() {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, imagePicker.getSelectedImages(), maxImgCount);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
//                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());

                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    /**
     * 点击叉号删除了图片
     *
     * @param view
     * @param position
     */
    @Override
    public void onDeleteClick(View view, int position) {
//        setImgViewVisible();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                selImageList.addAll(images);
//                imagePicker.getSelectedImages().addAll(images);
                adapter.setImages(imagePicker.getSelectedImages());
            }

        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
//                selImageList.clear();
//                selImageList.addAll(images);
                imagePicker.clearSelectedImages();
                imagePicker.getSelectedImages().addAll(images);
                adapter.setImages(imagePicker.getSelectedImages());
            }
        }
    }

    /**
     * 设置九宫格图片选择器的显示与影藏
     */
    protected void setImgViewVisible() {
        if (adapter.getImages().size() == 0) {
            if (recyclerView != null) {
                recyclerView.setVisibility(View.GONE);
            }
        } else {
            if (recyclerView != null) {
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
    }
}
