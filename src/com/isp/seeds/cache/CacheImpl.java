package com.isp.seeds.cache;

import java.util.HashMap;
import java.util.Map;

public class CacheImpl<K,V> implements Cache<K,V> {
	
	String name= null;
	private Map <K, V> _cache = null;
	
	public CacheImpl(String name) {
		this.name= name;
		_cache= new HashMap<K,V>();
	}

	@Override
	public void put (K k, V v) {
		_cache.put(k, v);
		
	}

	@Override
	public V get (K k) {
		return _cache.get(k);
	}

	@Override
	public void clear() {
		_cache.clear();
		
	}

	@Override
	public void remove(K k) {
		_cache.remove(k);
		
	}

	
}
