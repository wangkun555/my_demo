package com.lonely.events;




public class HolderExecuteQueue {
	
	private  ExecuteQueueManager<AbsExecuteImpl> mWorkExecuteQueue = new ExecuteQueueManager<AbsExecuteImpl>();
	
	public   ExecuteQueueManager<AbsExecuteImpl> getWorkExecuteQueue()
	{
		return mWorkExecuteQueue;
	}
	
	private static HolderExecuteQueue instance = null;
	
	private HolderExecuteQueue(){
		
	}
	
	public synchronized static HolderExecuteQueue getInstance(){
		
		if(instance == null){
			instance = new HolderExecuteQueue();
		}
		return instance;
	}

}
