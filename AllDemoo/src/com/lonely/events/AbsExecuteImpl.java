package com.lonely.events;

public abstract class AbsExecuteImpl implements IExecute {
	private int nExecuteId;
	private Object refLevelCollection = null;
	private Object refGrandParent = null;
	private boolean bFinishFlag = false;
	protected long mExecuteBeginTime = 0;
	protected long mTimeOutInSeconds = 30;
	protected int eLevel;

	public static class IdBuild {
		private static int mId = 1;
		private static Object mLock = new Object();

		public static int GetNextId() {
			synchronized (mLock) {
				return ++mId;
			}
		}
	}

	public AbsExecuteImpl() {
		int nId = IdBuild.GetNextId();
		SetExecuteId(nId);
		BindEvents();
	}

	public int getLevel() {
		return eLevel;
	}

	public void setLevel(int eLevel) {
		this.eLevel = eLevel;
	}

	public boolean isFinish() {
		return bFinishFlag;
	}

	public void setIsFinishFlag(boolean bFlag) {
		this.bFinishFlag = bFlag;
	}

	public void SetExecuteId(int nId) {
		nExecuteId = nId;
	}

	public int GetExecuteId() {
		return nExecuteId;
	}

	public Object getRefLevelCollection() {
		return refLevelCollection;
	}

	public void setRefLevelCollection(Object refLevelCollection) {
		this.refLevelCollection = refLevelCollection;
	}

	public void SetGrandParent(Object o) {
		refGrandParent = o;
	}

	public Object GetGrandParent() {
		return refGrandParent;
	}

	public long getExecuteBeginTime() {
		return mExecuteBeginTime;
	}

	public void setExecuteBeginTime(long lExecuteBeginTime) {
		this.mExecuteBeginTime = lExecuteBeginTime;
	}

	public long getTimeOutInSeconds() {
		return mTimeOutInSeconds;
	}

	public void setTimeOutInSeconds(long lTimeOutInSeconds) {
		this.mTimeOutInSeconds = lTimeOutInSeconds;
	}

	public boolean isTimeout(long lCurTime) {
		return (doIRealBegin() && lCurTime - mExecuteBeginTime > mTimeOutInSeconds * 1000);
	}

	public void onExecuteTimeout() {
		setIsFinishFlag(true);
	}

	public boolean doIRealBegin() {
		return mExecuteBeginTime > 0;
	}

	public void dispose() {
		UnbindEvents();
	}

	public boolean BindEvents() {
		return true;
	}

	public void UnbindEvents() {
	}

}