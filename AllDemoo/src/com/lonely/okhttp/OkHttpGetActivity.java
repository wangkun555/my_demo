package com.lonely.okhttp;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.lonely.alldemoo.R;

public class OkHttpGetActivity extends Activity {
	private TextView tv;
	private ImageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.okhttpget);
		tv = (TextView) findViewById(R.id.tv_okhttpget);
		mImageView = (ImageView) findViewById(R.id.id_image);
		okHttpGet();
	}

	public void okHttpGet() {
		OkHttpClient okHttpClient = new OkHttpClient();
		Request request = new Request.Builder()
				.url("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=755811473,897559350&fm=58")
				.build();
		// 同步使用
		// try {
		// Response response = okHttpClient.newCall(request).execute();
		// if (response.isSuccessful()) {
		// // 返回成功操作
		// // return response.body().string()；
		// } else {
		// throw new IOException("Unexpected code " + response);
		// }
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// 异步操作
		Call call = okHttpClient.newCall(request);
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response response)
					throws IOException {
				// TODO Auto-generated method stub
				// final String mresponse = response.body().string();
				InputStream iStream = response.body().byteStream();
				final Bitmap bm = BitmapFactory.decodeStream(iStream);
				runOnUiThread(new Runnable() {
					public void run() {
						// tv.setText(mresponse);
						mImageView.setImageBitmap(bm);
					}
				});

			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub

			}
		});

	}
}
