package com.thumann.server.service.helper;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserThreadHelper {

	private static ConcurrentMap<Long, Long> userMap = new ConcurrentHashMap<Long, Long>();

	public static void addUser(long userId)
	{
		userMap.put(Thread.currentThread().getId(), userId);
	}

	public static long getUser()
	{
		return userMap.get(Thread.currentThread().getId());
	}

	public static void removeUser()
	{
		userMap.remove(Thread.currentThread().getId());
	}

}
