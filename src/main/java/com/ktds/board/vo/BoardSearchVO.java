package com.ktds.board.vo;

import io.github.seccoding.web.pager.annotations.EndRow;
import io.github.seccoding.web.pager.annotations.StartRow;

public class BoardSearchVO {

	private int pageNo;
	
	// 추가
	private String searchKeyword;
	

	@StartRow
	private int startRow;
	
	@EndRow
	private int endRow;

	
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	
	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	
}
