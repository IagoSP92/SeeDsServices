package com.isp.seeds.cache;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CacheManager {
	
	private static Map <String , Cache> caches = null;
	private static Logger logger = LogManager.getLogger(CacheManager.class);
	
	 
	public static <K,V> Cache <K,V> createCache (String nombre, Class<K> keyClass, Class<V> valueClass){
		
		Cache <K,V> newCache = new CacheImpl<K,V>(nombre);
		caches.put(nombre, newCache);
		
		if(logger.isInfoEnabled()) {
			logger.info("Cache {} <{},{}>" , nombre, keyClass, valueClass);
		}
		return newCache;
	 }
	
	
	public static <K,V> Cache <K,V>  getCache(String nombre, Class<K> keyClass, Class<V> valueClass, boolean autocreate){
	
		Cache <K,V> cache = (Cache<K,V>) caches.get(nombre);
		
		if(cache==null) {
			if (autocreate) {
				//if logger
				cache= createCache (nombre, keyClass, valueClass );
			}
		}
		return cache;
	}


	public static <K,V> Cache <K,V> getCache (String nombre, Class<K> keyClass, Class<V> valueClass) {

		return getCache(nombre, keyClass, valueClass,true);
	}

}
