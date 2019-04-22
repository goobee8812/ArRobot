package com.cloudring.arrobot.gelin.manager;

public interface IObserver {
	public void notify(int iEvent, int iResultCode, Object aData);
}
