package com.lonely.okhttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.app.Activity;
import android.os.Bundle;

public class OkHttpPostActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView();
	}

	public void okHttpPost() {
		OkHttpClient okHttpClient = new OkHttpClient();
		RequestBody body = new FormBody.Builder().add("platform", "android")
				.add("name", "bug").add("subject", "XXXXXXXXXXXXXXX").build();

		Request request = new Request.Builder().url("")
				.header("User-Agent", "OkHttp Headers.java")
				.addHeader("Accept", "application/json; q=0.5")
				.addHeader("Accept", "application/vnd.github.v3+json")
				.post(body).build();
		Call call = okHttpClient.newCall(request);
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub

			}
		});
	}

	public String postJson(String url, String json) throws IOException {
		// POST提交Json数据
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		OkHttpClient okHttpClient = new OkHttpClient();
		RequestBody requestBody = RequestBody.create(JSON, json);
		Request request = new Request.Builder().url(url).post(requestBody)
				.build();
		// 同步
		Response response = okHttpClient.newCall(request).execute();
		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			throw new IOException("Unexpected code" + response);
		}

		// 异步
		// Call call = okHttpClient.newCall(request);
		// call.enqueue(new Callback() {
		//
		// @Override
		// public void onResponse(Call arg0, Response arg1) throws IOException {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onFailure(Call arg0, IOException arg1) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
	}

	public String postValue(String url, String json) throws IOException {

		OkHttpClient okHttpClient = new OkHttpClient();
		RequestBody requestBody = new FormBody.Builder().add("na", "")
				.add("", "").build();
		Request request = new Request.Builder().url(url).post(requestBody)
				.build();
		Response response = okHttpClient.newCall(request).execute();
		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			throw new IOException("Unexpected code" + response);
		}
	}
}
