package com.utd.libmgmt.model;

import java.util.Date;

public class BookLoans {
	
	private long loanId;
	private long bookId;
	private String cardNo;
	private Date dateOut;
	private Date duedate;
	private Date dateIN;
	
	public long getLoanId() {
		return loanId;
	}
	public void setLoanId(long loanId) {
		this.loanId = loanId;
	}
	public long getBookId() {
		return bookId;
	}
	public void setBookId(long bookId) {
		this.bookId = bookId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Date getDateOut() {
		return dateOut;
	}
	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}
	public Date getDuedate() {
		return duedate;
	}
	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}
	public Date getDateIN() {
		return dateIN;
	}
	public void setDateIN(Date dateIN) {
		this.dateIN = dateIN;
	}
	
	

}
