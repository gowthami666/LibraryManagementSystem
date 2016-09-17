package com.utd.libmgmt.dto;

public class BookSearchDto {
	
	private String searchText;
	private int libLocationId;
	private String[] searchSelection;
	
	
	public String[] getSearchSelection() {
		return searchSelection;
	}
	public void setSearchSelection(String[] searchSelection) {
		this.searchSelection = searchSelection;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public int getLibLocationId() {
		return libLocationId;
	}
	public void setLibLocationId(int libLocationId) {
		this.libLocationId = libLocationId;
	}
	@Override
	public String toString() {
		return "BookSearchDto [searchText=" + searchText + ", libLocationId=" + libLocationId + "]";
	}
	
	

}
