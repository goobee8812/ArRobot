package com.cloudring.arrobot.gelin.manager;

public interface PRListener {

	/**
	 * 通知所有观察者
	 * @param iEvent
	 * @param iResultCode
	 * @param aData
	 */
	public void notifyObservers(int iEvent, int iResultCode, Object aData);


}
