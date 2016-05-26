package com.lonely.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lonely.alldemoo.R;

public class ImageText extends LinearLayout {

	private ImageView imageView;

	private TextView textView;
	private int CHECKED_COLOR = Color.rgb(29, 118, 199);
	private int UNCHECKED_COLOR = Color.rgb(86, 86, 86);

	public ImageText(Context context) {
		super(context);
		initView(context);
	}

	public ImageText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	private void initView(Context context) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// LayoutInflater iiInflater = LayoutInflater.from(context);//初始化方法2
		// LayoutInflater iiInflater = getLayoutInflater();//初始化3
		View view = inflater.inflate(R.layout.image_text_layout, this, true);
		imageView = (ImageView) view.findViewById(R.id.image_iamge_text);
		textView = (TextView) view.findViewById(R.id.text_iamge_text);
	}

	// 设置图片

	public void setImage(int imageId) {
		if (imageView != null) {
			imageView.setImageResource(imageId);
		}
	}

	// 初始化字体

	public void setText(String text) {

		if (textView != null) {
			textView.setText(text);
			textView.setTextColor(UNCHECKED_COLOR);
		}
	}

	// 选中时的效果

	public void setChecked(int imageId) {
		if (imageView != null) {
			imageView.setImageResource(imageId);
			textView.setTextColor(CHECKED_COLOR);
		}

	}

}
