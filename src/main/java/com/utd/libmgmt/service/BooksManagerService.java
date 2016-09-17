package com.utd.libmgmt.service;

import java.util.List;

import com.utd.libmgmt.dto.BookSearchDto;
import com.utd.libmgmt.dto.LibBranchDto;
import com.utd.libmgmt.model.Book;
import com.utd.libmgmt.model.BooksLoanRecord;
import com.utd.libmgmt.model.FinesRecordsDetails;
import com.utd.libmgmt.model.Message;
import com.utd.libmgmt.model.OverDueBookSRecord;

public interface BooksManagerService {
	public List<LibBranchDto> getLibraryBranches();
	public List<Book> getBooks(BookSearchDto bookSearchDto);
	public Message saveBookLoan(String isbn, String cardNo, int libraryId);
	public List<BooksLoanRecord> getBookLoanRecords(String searchTextbookSearchText);
	public void saveCheckIn(BooksLoanRecord booksLoanRecord);
	public List<FinesRecordsDetails> getFinesRecords(String cardNo);
	public List<OverDueBookSRecord> getOverDueRecords(String cardNo);
	public Message updateFineStatus(long fineId);
	

}
