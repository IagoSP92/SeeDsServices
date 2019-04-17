package com.isp.seeds.service.util;

import java.util.Iterator;
import java.util.List;

public class Results<T> implements Iterable{
	
	private List<T> page = null;
	private int startIndex = 0;
	private int total = 0;
	
	public Results(List<T> page, int startIndex, int total) {
		setPage(page);
		setStartIndex(startIndex);
		setTotal(total);		
	}

	public List<T> getPage() {
		return page;
	}

	public void setPage(List<T> page) {
		this.page = page;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
