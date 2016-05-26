package com.lonely.events;

public class InvokeEventContainer {
	private static InvokeEventContainer instance = null;

	public ReflectInvokeMethod event_login = new ReflectInvokeMethod(
			new Class<?>[] { Integer.class, String.class });

	private InvokeEventContainer() {

	}

	public static final InvokeEventContainer getInstance() {
		if (instance == null) {
			synchronized (InvokeEventContainer.class) {
				if (instance == null) {
					instance = new InvokeEventContainer();
				}
			}
		}
		return instance;
	}

	public void cleanAndStart() {

		if (instance != null) {
			instance.clean();
			instance = null;
		}
		instance = new InvokeEventContainer();
	}

	private void clean() {
		event_login.clear();
		event_login = null;
	}

}
