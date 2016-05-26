package com.lonely.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class RemoveActivities {

	public static List<Activity> activities = new ArrayList<Activity>();

	public static void removeActivity(Activity activity) {
		if (activity != null) {
			activities.remove(activity);
		}

	}

	public static void addActivity(Activity activity) {
		if (activity != null) {
			activities.add(activity);
		}

	}

	public static void finishAll() {
		for (Activity activity : activities) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
	}

}
