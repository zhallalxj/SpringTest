package org.zh.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 
 * @Description:TODO(请求  PageRequest 封装)   
 * @author: level.meng 
 * @date:   2017年2月16日 下午4:49:46   
 *     
 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class PageRequest implements Serializable {

	private static final long serialVersionUID = 6337062797643232434L;
	
	
	protected String orderByClause;
	protected String orderWay = "ASC";
	protected String searchValue = null;
	protected int currentPage = 1;
	protected int pageCount = 10;
	protected int limit = 10;
	protected int offset= 0;
	protected int draw;
	
	
	public PageRequest() {

	}
	public PageRequest(HttpServletRequest request) {
		
		String orderColumnIndex = request.getParameter("order[0][column]");
		StringBuffer sortPropertie =  new StringBuffer().append("columns[").append(orderColumnIndex).append("][data]");
		String orderColumn = request.getParameter(sortPropertie.toString());
	
		this.orderWay = request.getParameter("order[0][dir]").toUpperCase();
		this.orderByClause = orderColumn + " " + orderWay;
		if(!StringUtils.isBlank(request.getParameter("search[value]"))){
			this.searchValue ="%"+request.getParameter("search[value]")+"%";
		}else{
			this.searchValue="%%";
		}
		
		
		this.currentPage = (Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")));
		this.offset = Integer.parseInt(request.getParameter("start"));
		this.pageCount = this.limit = Integer.parseInt(request.getParameter("length"));
		this.draw = Integer.parseInt(request.getParameter("draw"));
		draw++;
		currentPage++;
	}


	public String getOrderByClause() {
		return orderByClause;
	}


	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}


	public String getOrderWay() {
		return orderWay;
	}


	public void setOrderWay(String orderWay) {
		this.orderWay = orderWay;
	}


	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public int getPageCount() {
		return pageCount;
	}


	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}


	public int getDraw() {
		return draw;
	}


	public void setDraw(int draw) {
		this.draw = draw;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	
	

	
	
	
   
	
}
