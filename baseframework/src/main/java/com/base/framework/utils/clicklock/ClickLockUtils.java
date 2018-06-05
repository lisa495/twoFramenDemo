package com.base.framework.utils.clicklock;

import android.text.TextUtils;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaoyk
 * @ClassName: click lock 实现暴力点击屏蔽
 * @Description: 通过 {@link #isCanClick(String)} 传入一个名字，同名的锁在一定时间能不允许2次点击会返回false
 * @date 2015/7/14
 */
public class ClickLockUtils {
  /**
   * 所有锁的缓存
   */
  private static Map<String,SoftReference<LockCell>> lockCells = new HashMap<String, SoftReference<LockCell>>();

  /**
   * 锁的持续时间
   */
  private final static long DEFAULT_LOCK_TIME = 300;
  
   private static class LockCell{
	   /**
	    * 锁名，唯一标识
	    */
	   String lockName;
	   /**
	    * 锁住，禁止点击时间
	    */
	   long lockDuration = DEFAULT_LOCK_TIME;
	   
	   long lastClickTime = -1;
	   /**
	    * 是否可以点击
	    * @return
	    */
	   public boolean isCanClick(){
		   long cur = System.currentTimeMillis();
		   if(lastClickTime +lockDuration < cur){
			   lastClickTime = cur;
			   return true;
		   }
		   return false;
	   }
   }
   
   /**
    * @param lockName 锁名，同一个锁名在一定时间内{@link LockCell#lockDuration}禁止2此点击会返回false 
    * @return
    *      true:允许点击，调用者可以处理点击事件
    *      false:禁止点击，调用者需要屏蔽点击处理
    */
   public static boolean isCanClick(String lockName){
	   if(TextUtils.isEmpty(lockName)){
		   return true;
	   }
	   return getLock(lockName).isCanClick();
   }
   
   /**
    * 获取一个lock 不存在则创建
    * @param lockName
    * @return
    */
   public static synchronized LockCell getLock(String lockName){
	   if(TextUtils.isEmpty(lockName)){
		   return null;
	   }
	   SoftReference<LockCell> softReference = lockCells.get(lockName);
	   LockCell lockCell ;
	   if(softReference == null){
		   lockCell = null;
	   }else{
		   lockCell = softReference.get();
	   }
	   if(lockCell == null){
		   lockCell = new LockCell();
		   lockCell.lockName = lockName;
		   lockCells.put(lockName, new SoftReference<LockCell>(lockCell));
	   }
	   return lockCell;
   }
   
   /**
    * 从缓存中移除一个lock
    * @param lockName
    */
   public static synchronized void removeLock(String lockName){
	    SoftReference<LockCell> removeLock = lockCells.remove(lockName);
	    if(removeLock != null){
	    	removeLock.clear();
	    }
   }
   
   /**
    * 移除所有的锁
    */
   public static synchronized void clearLock(){
	   lockCells.clear();
   }
}
