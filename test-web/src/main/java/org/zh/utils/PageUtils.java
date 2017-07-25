package org.zh.utils;

import java.io.Serializable;

public class PageUtils implements Serializable{


	private static final long serialVersionUID = -1029068962837506642L;
	
	private Long totalCount;
	private int currentPage=1;
	private int pageSize=10;
	private int offset;
	private int limit;
	
	public PageUtils() {

	}
	
	public PageUtils(int currentPage) {
		
		this.pageSize=10;
		this.offset = (currentPage-1)*this.pageSize;
		this.limit = pageSize;
		this.currentPage= currentPage;
		this.totalCount =0l;
	}
	
	public PageUtils(int currentPage,int pageSize){
		
		this.pageSize=pageSize;
		this.offset =(currentPage-1)*this.pageSize;
		this.limit = pageSize;
		this.currentPage= currentPage;
		this.totalCount =0l;
	}
	
	
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	
	
	
	
	
}
