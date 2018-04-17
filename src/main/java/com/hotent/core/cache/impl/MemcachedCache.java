package com.hotent.core.cache.impl;

import com.hotent.core.cache.ICache;
import java.util.concurrent.TimeoutException;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

public class MemcachedCache implements ICache {
	private int timeOut = 0;
	private MemcachedClient memcachedClient;

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public void setMemcachedClient(MemcachedClient tmp) {
		this.memcachedClient = tmp;
	}

	public synchronized void add(String key, Object obj, int timeout) {
		try {
			this.memcachedClient.set(key, timeout, obj);
		} catch (TimeoutException arg4) {
			arg4.printStackTrace();
		} catch (InterruptedException arg5) {
			arg5.printStackTrace();
		} catch (MemcachedException arg6) {
			arg6.printStackTrace();
		}

	}

	public synchronized void delByKey(String key) {
		try {
			this.memcachedClient.delete(key);
		} catch (TimeoutException arg2) {
			arg2.printStackTrace();
		} catch (InterruptedException arg3) {
			arg3.printStackTrace();
		} catch (MemcachedException arg4) {
			arg4.printStackTrace();
		}

	}

	public void clearAll() {
		try {
			this.memcachedClient.flushAll();
		} catch (TimeoutException arg1) {
			arg1.printStackTrace();
		} catch (InterruptedException arg2) {
			arg2.printStackTrace();
		} catch (MemcachedException arg3) {
			arg3.printStackTrace();
		}

	}

	public synchronized Object getByKey(String key) {
		try {
			return this.memcachedClient.get(key);
		} catch (TimeoutException arg2) {
			arg2.printStackTrace();
		} catch (InterruptedException arg3) {
			arg3.printStackTrace();
		} catch (MemcachedException arg4) {
			arg4.printStackTrace();
		}

		return null;
	}

	public boolean containKey(String key) {
		Object obj = this.getByKey(key);
		return obj != null;
	}

	public synchronized void add(String key, Object obj) {
		try {
			this.memcachedClient.set(key, 0, obj);
		} catch (TimeoutException arg3) {
			arg3.printStackTrace();
		} catch (InterruptedException arg4) {
			arg4.printStackTrace();
		} catch (MemcachedException arg5) {
			arg5.printStackTrace();
		}

	}
}