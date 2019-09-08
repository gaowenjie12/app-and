package com.zsoe.businesssharing.commonview;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.lxj.xpopup.core.BottomPopupView;
import com.zsoe.businesssharing.R;

import io.reactivex.annotations.NonNull;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-09-07 19:06
 * @version:
 * @类说明:
 */
public class CustomEditTextBottomPopup extends BottomPopupView {

    public interface CommentListener {
        void onFinish(String str);
    }

    CommentListener commentListener;

    public BottomPopupView setCommentListener(CommentListener commentListener) {
        this.commentListener = commentListener;
        return this;
    }

    public CustomEditTextBottomPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_edittext_bottom_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onShow() {
        super.onShow();

        EditText et = findViewById(R.id.et_comment);
        TextView tvCount = findViewById(R.id.tv_count);

        int maxTextCount = 200;
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et.removeTextChangedListener(this);//**** 注意的地方
                if (s.length() > maxTextCount) {
                    et.setText(s.toString().substring(0, maxTextCount));
                    et.setSelection(maxTextCount);
                    tvCount.setText(maxTextCount + "");
                } else {
                    tvCount.setText(s.length() + "/" + maxTextCount + "字");
                }
                et.addTextChangedListener(this);//****  注意的地方
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        findViewById(R.id.btn_finish).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (commentListener != null) {
                    String comment = et.getText().toString();
                    if (TextUtils.isEmpty(comment)) {
                        ToastUtils.showShort("请填写留言内容");
                        return;
                    }
                    commentListener.onFinish(comment);
                }
            }
        });
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
    }
}
