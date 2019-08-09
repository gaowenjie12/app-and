package com.zsoe.businesssharing.utils;

import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by onion on 2017/3/22.
 */
public class SpannableHelper extends SpannableString {
    String target = "";

    public SpannableHelper(String source) {
        super(source);
        target = source;
    }

    /**
     * textview 部分文字加颜色
     *
     * @param colorStr 要加色的String
     *                 <p>
     *                 注意这个方法有可能出现，满足“colorStr”的所有字符都会变色
     */
    public SpannableHelper partTextViewColor(String colorStr, int color) {
        if (target == null || !target.contains(colorStr)) {
            return this;
        }
        // 起始位置
        int startIndex = target.indexOf(colorStr);
        // 终止位置
        int endIndex = startIndex + colorStr.length();
        // 文字编排


        // 指定位置上色
        setSpan(new ForegroundColorSpan(color), startIndex, endIndex,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

//        textview.setMovementMethod(LinkMovementMethod.getInstance());
        return this;
    }

    /**
     * textview 部分文字加颜色
     *
     * @param colorStr 要加色的String
     *                 <p>
     *                 注意这个方法是满足最后一个“colorStr”的字符都会变色
     */
    public SpannableHelper partLastTextViewColor(String colorStr, int color) {
        if (target == null || !target.contains(colorStr)) {
            return this;
        }
        //  最后一个匹配字符的起始位置
        int startIndex = target.lastIndexOf(colorStr);
        // 终止位置
        int endIndex = startIndex + colorStr.length();
        // 文字编排


        // 指定位置上色
        setSpan(new ForegroundColorSpan(color), startIndex, endIndex,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

//        textview.setMovementMethod(LinkMovementMethod.getInstance());
        return this;
    }

    /**
     * textview 部分文字加颜色
     *
     * @param colorStr 要加色的String
     */
    public SpannableHelper partTextViewBoldColor(String colorStr, int color) {
        if (target == null || !target.contains(colorStr)) {
            return this;
        }
        // 起始位置
        int startIndex = target.indexOf(colorStr);
        // 终止位置
        int endIndex = startIndex + colorStr.length();
        // 文字编排


        // 指定位置上色
        setSpan(new ForegroundColorSpan(color), startIndex, endIndex,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗体
//        textview.setMovementMethod(LinkMovementMethod.getInstance());
        return this;
    }


    public SpannableHelper partTextViewClick(String clickStr, View.OnClickListener onClickListener) {

        if (target == null || !target.contains(clickStr)) {
            return this;
        }

        // 起始位置
        int startIndex = target.indexOf(clickStr);
        // 终止位置
        int endIndex = startIndex + clickStr.length();

        setSpan(new ClickSpannable(onClickListener), startIndex,
                endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 部分文字设置字体大小
     *
     * @param sizeStr
     * @param sizePx
     * @return 注意这个方法有可能出现，满足“sizeStr”的所有字符都会设置新的字体大小
     */
    public SpannableHelper partTextSize(String sizeStr, int sizePx) {
        if (target == null || !target.contains(sizeStr)) {
            return this;
        }
        // 起始位置
        int startIndex = target.indexOf(sizeStr);
        // 终止位置
        int endIndex = startIndex + sizeStr.length();

        setSpan(new AbsoluteSizeSpan(sizePx), startIndex,
                endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 部分文字设置字体大小
     *
     * @param sizeStr
     * @param sizePx
     * @return 注意这个方法是最后一个匹配的字符“sizeStr”会设置新的字体大小
     */
    public SpannableHelper partLastTextSize(String sizeStr, int sizePx) {
        if (target == null || !target.contains(sizeStr)) {
            return this;
        }
        // 最后一个匹配字符的起始位置
        int startIndex = target.lastIndexOf(sizeStr);
        // 终止位置
        int endIndex = startIndex + sizeStr.length();

        setSpan(new AbsoluteSizeSpan(sizePx), startIndex,
                endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableHelper partTextBold(String sizeStr) {
        if (target == null || !target.contains(sizeStr)) {
            return this;
        }
        // 起始位置
        int startIndex = target.indexOf(sizeStr);
        // 终止位置
        int endIndex = startIndex + sizeStr.length();

        setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗体

        return this;
    }

    public SpannableHelper partImg(String sizeStr, Bitmap bitmap) {
        if (target == null || !target.contains(sizeStr)) {
            return this;
        }
        // 起始位置
        int startIndex = target.indexOf(sizeStr);
        // 终止位置
        int endIndex = startIndex + sizeStr.length();

        setSpan(new ImageSpan(bitmap), startIndex,
                endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * replace  number  color
     *
     * @return
     */
    public SpannableHelper partNumColor(int color) {
        Pattern p = Pattern
                .compile("[0-9]*");
        Matcher matcher = p.matcher(target);
        while (matcher.find()) {
            setSpan(new ForegroundColorSpan(color), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return this;
    }

    public static class ClickSpannable extends ClickableSpan implements View.OnClickListener {

        private View.OnClickListener onClickListener;

        public ClickSpannable(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        @Override
        public void onClick(View widget) {
            onClickListener.onClick(widget);
        }
    }
}
