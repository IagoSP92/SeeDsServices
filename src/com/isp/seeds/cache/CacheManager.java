package com.isp.seeds.cache;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {
	
	static {
		Map <String, Map <String, Object>> caches = new HashMap <String, Map <String, Object> >();
	}

	public void setCache (String nombre, Object key, Object value) {
		
		Map<key,value> esta = new HashMap <key, value>();
		
	}

	public Map <Object, Object> getCache (String nombre) {
		
		return caches.
	}


	public static <K,V> Cache <K,V> getCache (String nombre, Class<K> keyClass, Class<V> valueClass) {

		Cache<K,V> cache = new CacheImpl<K,V>();

		return cache;
	}

}
