package com.utd.libmgmt.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utd.libmgmt.dao.BooksManagerDao;
import com.utd.libmgmt.dto.BookSearchDto;
import com.utd.libmgmt.dto.LibBranchDto;
import com.utd.libmgmt.model.Book;
import com.utd.libmgmt.model.BooksLoanRecord;
import com.utd.libmgmt.model.FinesRecordsDetails;
import com.utd.libmgmt.model.LibBranch;
import com.utd.libmgmt.model.Message;
import com.utd.libmgmt.model.OverDueBookSRecord;
import com.utd.libmgmt.service.BooksManagerService;

@Service
public class BooksMangerServiceImpl implements BooksManagerService {
	
	private static final Logger log = Logger.getLogger(BooksMangerServiceImpl.class);
	@Autowired
	BooksManagerDao booksManagerDao;

	@Override
	public List<LibBranchDto> getLibraryBranches() {
		List<LibBranch> branches = booksManagerDao.getLibBranches();
		List<LibBranchDto> branchesDto = new ArrayList<LibBranchDto>();
		for(LibBranch branch : branches)
		{
			LibBranchDto branchDto = new LibBranchDto();
			branchDto.setBranchId(branch.getBranchId());
			branchDto.setBranchName(branch.getBranchName());
			branchesDto.add(branchDto);
		}
		
		return branchesDto;
	}

	@Override
	public List<Book> getBooks(BookSearchDto bookSearchDto) {
		return booksManagerDao.searchForBooks(bookSearchDto);
	}

	@Override
	public Message saveBookLoan(String isbn, String cardNo,int libraryId) {
		
	return booksManagerDao.saveBookLoan(isbn,cardNo,libraryId);
	}

	@Override
	public List<BooksLoanRecord> getBookLoanRecords(String borrowerSearchText) {
		return booksManagerDao.getBookLoanRecords(borrowerSearchText);
		
	}

	@Override
	public void saveCheckIn(BooksLoanRecord booksLoanRecord) {
		 booksManagerDao.saveCheckIn(booksLoanRecord);
		
	}

	@Override
	public List<FinesRecordsDetails> getFinesRecords(String cardNo) {
		return booksManagerDao.getFineRecords(cardNo);
	}

	@Override
	public List<OverDueBookSRecord> getOverDueRecords(String cardNo) {
		// TODO Auto-generated method stub
		return booksManagerDao.getOverDueRecords(cardNo);
	}

	@Override
	public Message updateFineStatus(long fineId) {
		return booksManagerDao.updateFineStatus(fineId);
	}


}
