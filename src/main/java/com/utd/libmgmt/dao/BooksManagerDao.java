package com.utd.libmgmt.dao;

import java.util.List;

import com.utd.libmgmt.dto.BookSearchDto;
import com.utd.libmgmt.model.Author;
import com.utd.libmgmt.model.Book;
import com.utd.libmgmt.model.BooksLoanRecord;
import com.utd.libmgmt.model.FinesRecordsDetails;
import com.utd.libmgmt.model.LibBranch;
import com.utd.libmgmt.model.Message;
import com.utd.libmgmt.model.OverDueBookSRecord;

public interface BooksManagerDao {

	public List<Book> searchForBooks(BookSearchDto bookSearchDto);
	public Author searchAuthor(String searchText);
	public List<LibBranch> getLibBranches();
	public Message saveBookLoan(String isbn, String cardNo, int libraryId);
	public List<BooksLoanRecord> getBookLoanRecords(String borrowerSearchText);
	public void saveCheckIn(BooksLoanRecord booksLoanRecord);
	public List<FinesRecordsDetails> getFineRecords(String cardNo);
	public List<OverDueBookSRecord> getOverDueRecords(String cardNo);
	public Message updateFineStatus(long fineId);
	
}
