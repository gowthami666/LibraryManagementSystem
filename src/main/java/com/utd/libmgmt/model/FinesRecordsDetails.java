package com.utd.libmgmt.model;

public class FinesRecordsDetails {
	private long id;
	private long loanId;
	private double fineAmt;
	private String paid;
	private String isbn;
	private String title;
	private long bookId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getLoanId() {
		return loanId;
	}
	public void setLoanId(long loanId) {
		this.loanId = loanId;
	}
	public double getFineAmt() {
		return fineAmt;
	}
	public void setFineAmt(double fineAmt) {
		this.fineAmt = fineAmt;
	}
	public String getPaid() {
		return paid;
	}
	public void setPaid(String paid) {
		this.paid = paid;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getBookId() {
		return bookId;
	}
	public void setBookId(long bookId) {
		this.bookId = bookId;
	}
	
	
	
	

}
