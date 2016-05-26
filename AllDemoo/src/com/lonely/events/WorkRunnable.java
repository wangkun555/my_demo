package com.lonely.events;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class WorkRunnable  implements Runnable{

	private ReentrantLock mLock = new ReentrantLock();
	private Condition mCondition = mLock.newCondition();
	private volatile boolean isExit = false;
	private volatile AbsExecuteImpl mLastExecute = null;
	private volatile long lastTime = 0;
	private static long mMinWaitTime = 10;
	
	
	public void notifyWork(){
		
		try {
			mLock.lock();
			mCondition.signalAll();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			mLock.unlock();
		}
	}
	
	public void notifyStop(){
		isExit = true;
		notifyWork();
	}
	
	@Override
	public void run() {
		
		while(!isExit){
			mLock.lock();
			
			try {
				while(!isExit && HolderExecuteQueue.getInstance().getWorkExecuteQueue().isEmpty()){
					mCondition.awaitNanos(1500000000L);
					HolderExecuteQueue.getInstance().getWorkExecuteQueue().checkSleepExecuteTimeOut(System.currentTimeMillis());
				}
				if(isExit){
					return;
				}
				if(mLock.getHoldCount() > 0){
					mLock.unlock();
				}
				
				AbsExecuteImpl mExecute = HolderExecuteQueue.getInstance().getWorkExecuteQueue().getExecute();
				if(mExecute != null){
					long curTime = System.currentTimeMillis();
					if(mExecute.isFinish()){
						HolderExecuteQueue.getInstance().getWorkExecuteQueue().removeExecute(mExecute);
						mExecute.dispose();
						mLastExecute = null;
						continue;
					}
					
					if(mExecute.doIRealBegin() && mExecute.isTimeout(curTime)){
						mExecute.onExecuteTimeout();
						HolderExecuteQueue.getInstance().getWorkExecuteQueue().removeExecute(mExecute);
						mExecute.dispose();
						mLastExecute = null;
						continue;
					}
					
					if(mExecute != mLastExecute && ((curTime - lastTime) > mMinWaitTime)){
						lastTime = curTime;
						try {
							mExecute.execute();
						} catch (Exception e) {
							e.printStackTrace();
							mExecute.setIsFinishFlag(true);
						}
						if(mExecute.isFinish()){
							HolderExecuteQueue.getInstance().getWorkExecuteQueue().removeExecute(mExecute);
							mExecute.dispose();
							mLastExecute = null;
						}else{
							mLastExecute = mExecute ;
						}
					}else{
						Thread.sleep(mMinWaitTime);
					}
					
				}else{
					mLastExecute = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(mLock.getHoldCount() > 0){
					mLock.unlock();
				}
			}
		}
		
	}

}
