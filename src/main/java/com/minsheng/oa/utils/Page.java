package com.minsheng.oa.utils;

import java.util.List;

public class Page {
	
	/**
	 * 当前页码  （已知）
	 */
	private int pageNo = 1;
	
	/**
	 * 每页记录数（已知）
	 */
	private int pageSize = 5;
	
	/**
	 * 总记录数
	 */
	private int totalCount = 0;
	
	/**
	 * 总页数
	 */
	private int totalPage = 1;
	
	/**
	 * 开始行号
	 */
	private int startNum = 0;
	
	/**
	 * 结束行号
	 */
	private int endNum = 0;
	
	/**
	 * 结果集
	 */
	private List<?> list;



	public int getPageNo() { return pageNo; }

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}           //页码

	public int getPageSize() {
		return pageSize;
	}                          //页面大小

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}                       //总页数

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * totalCount		pageSize 	 totalPage
	 *     0				10			1
	 * 	   100				10			10
	 * 		92				10			10	
	 * @return
	 */
	public int getTotalPage() {                               //总页码
		totalPage = totalCount/pageSize;
		if(totalCount == 0 || totalCount%pageSize != 0){
			totalPage++;
		}
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getStartNum() {
		                                              //起始行号
		return (pageNo - 1) * pageSize;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	public int getEndNum() {

		return pageNo * pageSize + 1;
	}

	public void setEndNum(int endNum) {
		this.endNum = endNum;
		System.out.println(endNum);
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}


	
	
	

}
