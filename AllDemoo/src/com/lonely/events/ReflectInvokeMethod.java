package com.lonely.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;

public class ReflectInvokeMethod {
	/**
	 * java 泛型 泛型类，是在实例化类的时候指明泛型的具体类型；泛型方法，是在调用方法的时候指明泛型的具体类型。 Class<?>
	 * c表示这个Class可以放任意的类,?表示object(所有类都隐性从Object继承的)
	 * Class<T>的作用就是指明泛型的具体类型，而Class<T>类型的变量c，可以用来创建泛型类的对象。
	 */

	private Class<?>[] mParameterType = null;

	private class ReflectStruct<T> {

		public Method mMethod = null;
		public Class<?> mClass = null;
		public T mObjectRef = null;

		public ReflectStruct(T object, Method method) {
			this.mMethod = method;
			this.mObjectRef = object;
			this.mClass = object.getClass();
		}

	}

	private LinkedList<ReflectStruct<?>> mInvokeObservers = new LinkedList<ReflectStruct<?>>();

	public ReflectInvokeMethod(Class<?>[] parameterType) {
		this.mParameterType = parameterType;

	}

	public <T> boolean bind(T t, String methodName) {

		assert (mParameterType != null);
		if (isBind(t)) {
			return true;
		}
		Method m = null;
		try {
			m = t.getClass().getMethod(methodName, mParameterType);
		} catch (NoSuchMethodException e) {
			return false;
		}

		ReflectStruct<?> reflectStruct = new ReflectStruct<T>(t, m);
		synchronized (this) {
			mInvokeObservers.add(reflectStruct);
		}

		return true;
	}

	public void unBind(Object object) {
		synchronized (this) {
			Iterator<ReflectStruct<?>> iterator = mInvokeObservers.iterator();
			while (iterator.hasNext()) {
				if (iterator.next().mObjectRef == object)
					;
				iterator.remove();
				return;
			}
		}
	}

	public boolean isBind(Object object) {
		synchronized (this) {
			Iterator<ReflectStruct<?>> iterator = mInvokeObservers.iterator();
			while (iterator.hasNext()) {
				if (iterator.next().mObjectRef == object) {
					return true;
				}
			}
			return false;
		}
	}

	public void clear() {
		synchronized (this) {
			mInvokeObservers.clear();
		}
	}

	public void invoke(Object... params) {

		if (arrayContentIsEqual(mParameterType, params, false)) {
			checkParamsErrors(params);
		}
		try {
			callObservers(params);
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}

	}

	public void invokeWithAcceptSuberClass(Object... params) {
		if (arrayContentIsEqual(mParameterType, params, true)) {
			checkParamsErrors(params);
		}

		try {
			callObservers(params);
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
	}

	private void checkParamsErrors(Object... params) {

		StringBuilder builder = new StringBuilder("参数类型不匹配, 期望的是:(");
		for (int i = 0; i < mParameterType.length; ++i) {
			builder.append(mParameterType[i].getName()).append(",");
		}
		builder.append("), 实际上传的是:(");
		for (int i = 0; i < params.length; ++i) {
			builder.append(params[i].getClass().getName()).append(",");
		}
		builder.append(")");
		throw new IllegalArgumentException(builder.toString());
	}

	private void callObservers(Object... params)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		LinkedList<ReflectStruct<?>> tempList = null;
		synchronized (this) {
			tempList = new LinkedList<ReflectStruct<?>>(mInvokeObservers);
		}
		for (ReflectStruct<?> reflectStruct : tempList) {
			reflectStruct.mMethod.invoke(reflectStruct.mObjectRef, params);
		}
		tempList.clear();
		tempList = null;
	}

	public boolean arrayContentIsEqual(Class<?>[] a1, Object[] a2,
			boolean isAcceptSuperClass) {

		if (a1 == null) {
			return a2 == null || a2.length == 0;
		}

		if (a2 == null) {
			return a1.length == 0;
		}

		if (a1.length != a2.length) {
			return false;
		}

		for (int i = 0; i < a1.length; i++) {
			if (isAcceptSuperClass) {
				if (a2[i] != null && !a1[i].isInstance(a2[i])) {
					return false;
				}
			} else {
				if (a2[i] != null && a1[i] != a2.getClass()) {
					return false;
				}
			}
		}

		return true;
	}
}
