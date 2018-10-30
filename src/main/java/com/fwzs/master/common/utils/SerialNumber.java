package com.fwzs.master.common.utils;

public abstract class SerialNumber {
	 public synchronized String getSerialNumber() {
	        return process();
	    }
	    protected abstract String process();
}
