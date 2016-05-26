package com.lonely.alldemoo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.lonely.contest.MyUtilsContest;
import com.lonely.fragment.BaseFragment;
import com.lonely.fragment.OneFragment;
import com.lonely.view.BottomControl;
import com.lonely.view.BottomControl.BottomPanelCallback;

public class MainActivity extends FragmentActivity implements
		BottomPanelCallback {

	private FragmentManager fragmentManager;// v4包出现的较早，

	private FragmentTransaction mFragmentTransaction;

	OneFragment oneFragment;

	private BottomControl mBottomControl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mBottomControl = (BottomControl) findViewById(R.id.id_bottom);
		if (mBottomControl != null) {
			mBottomControl.initBottom();
			mBottomControl.setBottomCallback(this);
			mBottomControl.defaultBtnChecked();
		}

		fragmentManager = MainActivity.this.getSupportFragmentManager();

		setFtagmentTag(MyUtilsContest.BottomTag.Tag_one);
		// mFragmentTransaction.commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBottomPanelClick(int itemId) {
		// TODO Auto-generated method stub
		String tag = "";
		switch (itemId) {
		case R.id.btn_one:
			tag = MyUtilsContest.BottomTag.Tag_one;
			break;
		case R.id.btn_two:
			tag = MyUtilsContest.BottomTag.Tag_two;
			break;
		case R.id.btn_three:
			tag = MyUtilsContest.BottomTag.Tag_three;
			break;
		default:
			break;
		}
		setFtagmentTag(tag);
	}

	// 1
	private void setFtagmentTag(String tag) {
		// TODO Auto-generated method stub
		// 首先去掉fragmentTranscation中的fragment
		mFragmentTransaction = fragmentManager.beginTransaction();
		if (TextUtils.equals(tag, MyUtilsContest.CUR_RFRAGTAG)) {
			return;
		}
		if (MyUtilsContest.CUR_RFRAGTAG != null
				&& !MyUtilsContest.CUR_RFRAGTAG.equals("")) {
			detachFragment(getFragment(MyUtilsContest.CUR_RFRAGTAG));
		}
		attachFragment(R.id.fragment_content, getFragment(tag), tag);
		commitTransactions(tag);
	}

	private Fragment getFragment(String tag) {
		Fragment f = fragmentManager.findFragmentByTag(tag);
		if (f == null) {
			f = BaseFragment.newFragment(getApplicationContext(), tag);
		}

		return f;
	}

	private void attachFragment(int layout, Fragment f, String tag) {
		if (f != null) {
			if (f.isDetached()) {
				ensureTransaction();
				mFragmentTransaction.attach(f);

			} else if (!f.isAdded()) {
				ensureTransaction();
				mFragmentTransaction.add(layout, f, tag);
			}
		}
	}

	private void commitTransactions(String tag) {
		if (mFragmentTransaction != null && !mFragmentTransaction.isEmpty()) {
			mFragmentTransaction.commit();
			MyUtilsContest.CUR_RFRAGTAG = "";
			mFragmentTransaction = null;
		}
	}

	// 去掉fragment 2

	private void detachFragment(Fragment f) {
		if (f != null && !f.isDetached()) {
			ensureTransaction();
			mFragmentTransaction.detach(f);
		}
	}

	private FragmentTransaction ensureTransaction() {
		// TODO Auto-generated method stub
		if (mFragmentTransaction == null) {
			mFragmentTransaction = fragmentManager.beginTransaction();
			mFragmentTransaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		}
		return mFragmentTransaction;
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		MyUtilsContest.CUR_RFRAGTAG = "";
	}

}
