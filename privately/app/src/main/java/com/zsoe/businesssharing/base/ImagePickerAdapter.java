package com.zsoe.businesssharing.base;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.commonview.imagepicker.ImagePicker;
import com.zsoe.businesssharing.commonview.imagepicker.Utils;
import com.zsoe.businesssharing.commonview.imagepicker.bean.ImageItem;
import com.zsoe.businesssharing.utils.AppImageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ImagePickerAdapter extends RecyclerView.Adapter<ImagePickerAdapter.SelectedPicViewHolder> {
    private int maxImgCount;
    private Context mContext;
    private List<ImageItem> mData;
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener listener;
    private boolean isAdded;   //是否额外添加了最后一个图片

    /**
     * 相册预览为保证内存，加载一个小的图片
     */
    private ResizeOptions reSizeOption;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);

        void onDeleteClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public void setImages(List<ImageItem> data) {
        mData = new ArrayList<>(data);
        if (getItemCount() < maxImgCount) {
            mData.add(new ImageItem());
            isAdded = true;
        } else {
            isAdded = false;
        }
        notifyDataSetChanged();
    }

    public List<ImageItem> getImages() {
        //由于图片未选满时，最后一张显示添加图片，因此这个方法返回真正的已选图片
        if (isAdded) return new ArrayList<>(mData.subList(0, mData.size() - 1));
        else return mData;
    }

    public ImagePickerAdapter(Activity mContext, List<ImageItem> data, int maxImgCount) {
        this.mContext = mContext;
        this.maxImgCount = maxImgCount;
        this.mInflater = LayoutInflater.from(mContext);
        int imageSize = Utils.getImageItemWidth(mContext);
        reSizeOption = new ResizeOptions(imageSize, imageSize);
        setImages(data);
    }

    @Override
    public SelectedPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectedPicViewHolder(mInflater.inflate(R.layout.item_imagelist, parent, false));
    }

    @Override
    public void onBindViewHolder(SelectedPicViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class SelectedPicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SimpleDraweeView iv_img;
        private ImageView deleteBtn;
        private int clickPosition;

        public SelectedPicViewHolder(View itemView) {
            super(itemView);
            iv_img = (SimpleDraweeView) itemView.findViewById(R.id.iv_img);
            deleteBtn = (ImageView) itemView.findViewById(R.id.delete_btn);
        }

        public void bind(final int position) {
            //设置条目的点击事件
            itemView.setOnClickListener(this);
            //根据条目位置设置图片
            final ImageItem item = mData.get(position);
            if (isAdded && position == getItemCount() - 1) {
                iv_img.setImageResource(R.mipmap.add);
                clickPosition = ImgPickWithTxtActivity.IMAGE_ITEM_ADD;
                deleteBtn.setVisibility(View.GONE);
            } else {
                Uri imgUrl;
                if (item.path.startsWith("http")) {
                    imgUrl = Uri.parse(item.path);
                } else {
                    if (!TextUtils.isEmpty(item.url)) {
                        imgUrl = Uri.parse(item.url);
                    } else {
                        imgUrl = Uri.fromFile(new File(item.path));
                    }
                }
                AppImageUtils.displayImage(mContext, iv_img, imgUrl, reSizeOption);

                clickPosition = position;
                deleteBtn.setVisibility(View.VISIBLE);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mData.remove(position);
                        if (getItemCount() < maxImgCount) {
                            if (!isAdded) {
                                mData.add(new ImageItem());
                                isAdded = true;
                            }
                        } else {
                            isAdded = false;
                        }
                        ArrayList<ImageItem> selectImg = ImagePicker.getInstance().getSelectedImages();
                        if (selectImg != null) {
                            selectImg.remove(item);
                        }
                        notifyDataSetChanged();
                        listener.onDeleteClick(v, position);
                    }
                });
            }
        }

        @Override
        public void onClick(View v) {
            if (listener != null) listener.onItemClick(v, clickPosition);
        }
    }
}