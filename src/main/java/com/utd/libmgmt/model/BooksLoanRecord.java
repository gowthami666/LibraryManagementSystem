package com.utd.libmgmt.model;

import java.util.Date;

public class BooksLoanRecord {

	private long loanId;
	private long bookId;
	private String cardNo;
	private Date dateOut;
	private Date dueDate;
	private Date dateIn;
	private String fname;
	private String lname;
	private String title;
	private String isbn;
	private String stdueDate;
	private String stDateOut;
	
	
	
	public String getStdueDate() {
		return stdueDate;
	}
	public void setStdueDate(String stdueDate) {
		this.stdueDate = stdueDate;
	}
	public String getStDateOut() {
		return stDateOut;
	}
	public void setStDateOut(String stDateOut) {
		this.stDateOut = stDateOut;
	}
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
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Date getDateIn() {
		return dateIn;
	}
	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	
	
}
