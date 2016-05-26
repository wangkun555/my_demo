package com.lonely.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.lonely.alldemoo.R;
import com.lonely.contest.MyUtilsContest;

public class BottomControl extends RelativeLayout implements
		View.OnClickListener {

	private Context mContext;

	private ImageText btImageText_one;

	private ImageText btImageText_two;

	private ImageText btImageText_three;

	private int DEFALUT_BACKGROUND_COLOR = Color.rgb(252, 252, 252);

	private int phoneWidth = 0;

	private List<ImageText> viewList = new ArrayList<ImageText>();

	private BottomPanelCallback mBottomCallback = null;

	public void setBottomCallback(BottomPanelCallback bottomCallback) {
		mBottomCallback = bottomCallback;
	}

	public interface BottomPanelCallback {
		public void onBottomPanelClick(int itemId);
	}

	public BottomControl(Context context) {
		super(context);
		this.mContext = context;
	}

	public BottomControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		btImageText_one = (ImageText) findViewById(R.id.btn_one);
		btImageText_two = (ImageText) findViewById(R.id.btn_two);
		btImageText_three = (ImageText) findViewById(R.id.btn_three);
		setBackgroundColor(DEFALUT_BACKGROUND_COLOR);
		viewList.add(btImageText_one);
		viewList.add(btImageText_two);
		viewList.add(btImageText_three);
	}

	public void initBottom() {
		// TODO Auto-generated method stub
		if (btImageText_one != null) {
			btImageText_one.setImage(R.drawable.chat_unselected);
			btImageText_one.setText(MyUtilsContest.BottomTag.Tag_one);
		}
		if (btImageText_two != null) {
			btImageText_two.setImage(R.drawable.history_unselected);
			btImageText_two.setText(MyUtilsContest.BottomTag.Tag_two);
		}
		if (btImageText_three != null) {
			btImageText_three.setImage(R.drawable.myprofile_unselected);
			btImageText_three.setText(MyUtilsContest.BottomTag.Tag_three);
		}
		setViewOnclick();

	}

	public void defaultBtnChecked() {
		if (btImageText_one != null) {
			btImageText_one.setChecked(R.drawable.chat_selected);
		}
	}

	private void initView() {
		// TODO Auto-generated method stub

	}

	private void setViewOnclick() {
		// TODO Auto-generated method stub
		int num = getChildCount();
		for (int i = 0; i <= num; i++) {
			View view = getChildAt(i);
			if (view != null) {
				view.setOnClickListener(this);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		initBottom();
		switch (v.getId()) {
		case R.id.btn_one:
			btImageText_one.setChecked(R.drawable.chat_selected);
			break;
		case R.id.btn_two:
			btImageText_two.setChecked(R.drawable.history_selected);
			break;
		case R.id.btn_three:
			btImageText_three.setChecked(R.drawable.myprofile_selected);
			break;
		default:
			break;
		}
		mBottomCallback.onBottomPanelClick(v.getId());

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		phoneWidth = r - l;
		setLayoutParems(l, t, r, b);
	}

	public void setLayoutParems(int left, int top, int right, int bottom) {

		int n = getChildCount();
		if (n == 0) {
			return;
		}
		int width = right - left;
		int viewWidth = width / n;
		for (int i = 0; i < n; i++) {
			setViewLocal(i, viewWidth);
		}

	}

	private void setViewLocal(int i, int width) {
		// TODO Auto-generated method stub
		if (viewList.get(i) != null) {
			LayoutParams params = (LayoutParams) viewList.get(i)
					.getLayoutParams();
			params.width = width;
			viewList.get(i).setLayoutParams(params);
		}

	}
}
