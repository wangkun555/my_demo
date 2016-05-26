package com.lonely.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.util.Log;

public class ExecuteQueueManager <T extends AbsExecuteImpl>{
	
	//清理废弃的队列和任务 暂不考虑
	// 任务的高中低级别暂不考虑
	//是否重复的任务需要考虑
	//取消某任务
	private final String TAG = "ExecuteQueueManager"; 
	
	public static class ExecuteLevel{
		public static final int low = 0;
		public static final int mid = 1;
		public static final int heigh = 2;
	}
	
	/**
	 * 存放不同级别的任务队列
	 */
	private ArrayList<List<T>> arrQueue = new ArrayList<List<T>>();
	
	/**
	 * 睡眠队列
	 */
	private Set<T> sleepQueue = new HashSet<T>();
	
	/**
	 * 根据ExecuteId 缓存任务，可以通过ExecuteId 获取指定的任务
	 */
	private Map<Integer, T> idToExecute = new HashMap<Integer, T>();
	
	
	public ExecuteQueueManager(){
		for(int i = 0; i<= ExecuteLevel.heigh ; i++ ){
			arrQueue.add(new LinkedList<T>());
		}
	}
	
	
	
	public boolean isEmpty(){
		synchronized (this) {
			
			if(arrQueue.isEmpty()){
				return true;
			}else{
				for(int i = 0 ; i<arrQueue.size();i++){
					
					if(!arrQueue.get(ExecuteLevel.heigh-i).isEmpty()){
						return false;
					}
				}
				return true;
			}
		}
	}
	
	public void putExecute(T execute , boolean isAddTail , int level ){
		if(level > ExecuteLevel.heigh || level < ExecuteLevel.low)
		{
			Log.e(TAG, "PutExecute, error level"+level);
			return;
		}
		execute.setLevel(level);
		synchronized (this) {
			List<T> levelList = arrQueue.get(level);
			if(levelList == null) 
			{
				Log.e(TAG, "null Execute queue");
			}
			execute.setRefLevelCollection(levelList);
			execute.SetGrandParent(this);
			if(isAddTail){
				levelList.add(execute);
			}else{
				levelList.add(0, execute);
			}
			idToExecute.put(execute.GetExecuteId(), execute);
		}
	}
	/**
	 * 
	 * @param Execute
	 */
	public void removeExecute(T execute){
		
		synchronized (this) {
			removeExecuteNoLock(execute);
		}
	}

	private void removeExecuteNoLock(T execute) {
		
		List<T> levelList = (List<T>) execute.getRefLevelCollection();
		if(levelList != null){
			boolean isRemove = levelList.remove(execute);
			if(!isRemove)
			{
				Log.e(TAG, "RemoveExecute_NoLock, remove Execute fail");
			}
			else
			{
				execute.setRefLevelCollection(null);
			}
		}
		idToExecute.remove(execute.GetExecuteId());
		sleepQueue.remove(execute);
	}
	
	public void addToSleepQueue(T execute){
		synchronized (this) {
			List<T> levelList = (List<T>) execute.getRefLevelCollection();
			if(levelList != null){
				if(!levelList.remove(execute))
				{
					Log.e(TAG, "MoveToSleepQueue, remove Execute fail");
				}
			}
			sleepQueue.add(execute);
		}
	}
	
	public void addRunningQueue(T execute){
		
		synchronized (this) {
			addRunningQueue(sleepQueue , execute);
		}
	}

	private void addRunningQueue(Set<T> sleeps, T execute) {
		synchronized (this) {
			if(sleeps.contains(execute)){
				if(((List<T>) execute.getRefLevelCollection()) != null){
					((List<T>) execute.getRefLevelCollection()).add(0,execute);
				}
			}else
			{
				Log.e(TAG, "MoveToRunningQueue, parent is null");
			}
			sleeps.remove(execute);
		}
	}
	
	
	public int getSleepExecuteConut(){
		
		synchronized (this) {
			return sleepQueue.size();
		}
	}
	
	public void checkSleepExecuteTimeOut(long currentTime){
		
		synchronized (this) {
			checkSleepExecuteTimeOut(sleepQueue,currentTime);
		}
		
	}

	private void checkSleepExecuteTimeOut(Set<T> sleepqueue, long currentTime) {
		
		synchronized (this) {
			
			if(sleepqueue.size() <= 0){
				return;
			}
			for(T execute : sleepqueue){
				if(execute.isTimeout(currentTime)){
					execute.onExecuteTimeout();
					execute.dispose();
					idToExecute.remove(execute.GetExecuteId());
					sleepqueue.remove(execute);
				}else if(execute.isFinish()){
					this.addRunningQueue(execute);
				}
			}
		}
		
	}
	
	public void cancleExecute( int executeId){
		synchronized (this) {
			T execute = idToExecute.get(executeId);
			if(execute != null){
				execute.setIsFinishFlag(true);;
				addRunningQueue(execute);
			}
		}
		
	}
	
	public T getExecute(){
		
		synchronized (this) {
			
			for(int i=0; i<arrQueue.size(); i++){
				if( !arrQueue.get(ExecuteLevel.heigh - i).isEmpty()){
					return arrQueue.get(ExecuteLevel.heigh -i).get(0);
				}
			}
			return null;
		}
		
	}
	
	public void dispose()
	{
		synchronized(this)
		{
			for(int i=0; i< arrQueue.size(); ++i)
			{
				if(arrQueue.get(i) != null)
				{
					for(T t:arrQueue.get(i))
					{
						t.onExecuteTimeout();
						t.dispose();
					}
				}
				
				arrQueue.get(i).clear();
			}
	
			for(T t:sleepQueue)
			{
				t.onExecuteTimeout();
				t.dispose();
			}
			sleepQueue.clear();
			idToExecute.clear();
		}
	}
	

}
