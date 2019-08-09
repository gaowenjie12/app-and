package com.zsoe.businesssharing.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zsoe.businesssharing.R;

import java.io.Serializable;


/**
 * 一个现实不定式进度条的dialog
 */
public class IProgressDialog extends Dialog {
	private TextView contextHint;
	private ProgressBar progressBar;

	public IProgressDialog(Context context) {
		super(context, R.style.CustomDialog);
		setContentView(R.layout.dialog_upload_layout);
		Window window = getWindow();
		window.getAttributes().gravity = Gravity.CENTER;
		window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(false);
//		dialogLayout = (RelativeLayout) this
//				.findViewById(R.id.dialog_upload_layout);
		this.progressBar = (ProgressBar) this.findViewById(R.id.progress_bar);
		this.contextHint = (TextView) this.findViewById(R.id.context);
	}

	/**
	 * 设置消息提示
	 */
	public void setMessage(String strMessage) {
		contextHint.setText(strMessage);
	}


	/**
	 * 延迟2s消失
	 */
	public void holdDismiss(int sec) {
		this.show();
		new HoldTimer(sec).start();
	}

	private class HoldTimer extends CountDownTimer implements Serializable {
		private static final long serialVersionUID = 1L;

		public HoldTimer(int second) {
			super(second * 1000l, 1000l);
		}

		@Override
		public void onFinish() {
			this.cancel();
			dismiss();
		}

		@Override
		public void onTick(long millisUntilFinished) {
		}
	}
}
