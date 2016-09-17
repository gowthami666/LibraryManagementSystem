package com.utd.libmgmt.daoImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.utd.libmgmt.DaoRowMapper.AuthorRowMapper;
import com.utd.libmgmt.DaoRowMapper.BookCopiesRowMapper;
import com.utd.libmgmt.DaoRowMapper.BookLoansRowMapper;
import com.utd.libmgmt.DaoRowMapper.BookRowMapper;
import com.utd.libmgmt.DaoRowMapper.FinesDisplayRowMapper;
import com.utd.libmgmt.DaoRowMapper.LibraryBranchRowMapper;
import com.utd.libmgmt.DaoRowMapper.OverDueRecordRowMapper;
import com.utd.libmgmt.dao.BooksManagerDao;
import com.utd.libmgmt.dto.BookSearchDto;
import com.utd.libmgmt.model.Author;
import com.utd.libmgmt.model.Book;
import com.utd.libmgmt.model.BookLoans;
import com.utd.libmgmt.model.BooksLoanRecord;
import com.utd.libmgmt.model.FinesRecordsDetails;
import com.utd.libmgmt.model.LibBranch;
import com.utd.libmgmt.model.Message;
import com.utd.libmgmt.model.OverDueBookSRecord;

@Repository
public class BooksManagerDaoImpl implements BooksManagerDao {
	private static final Logger log = Logger
			.getLogger(BooksManagerDaoImpl.class);
	private static final int PARAM_SIZE = 500; 
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private static final String BOOKS_SEARCH_SELECT_STATEMENT = "select distinct books.isbn,books.title from books";
	private static final String ISBN_WHERE_CLAUSE = "books.isbn like :isbn";
	private static final String TITLE_WHERE_CLAUSE = "books.title like :title";
	private static final String AUTHORS_JOIN_CLAUSE = "left join book_authors on books.isbn = book_authors.isbn "
			+ "join authors on authors.author_id = book_authors.author_id";
	private static final String AUTHORS_WHERE_CLAUSE = "authors.name like :author";
	
	
	private static final String BOOK_SEARCH_SQL =" left join book_authors on books.isbn = book_authors.isbn"
			+ " join authors on authors.author_id = book_authors.author_id"
			+ " where (books.isbn like :isbn OR books.title like :title OR authors.name like :author );";
	
	private static final String LIB_BRANCHES_SQL="SELECT * from library_branch";
	
	private static final String LIST_AUTHORS ="SELECT book_authors.isbn, authors.name from authors "
			+ " JOIN book_authors ON book_authors.author_id = authors.author_id "
			+ " WHERE BOOK_AUTHORS.ISBN IN (:isbn) ;";
	private static final String NUMBER_OF_COPIES = "Select isbn, count(isbn) as copies from book_copies where isbn IN (:isbn) and branch_id = :branchId "
			+ " and book_id NOT IN( Select book_id from book_loans where date_in IS NULL) group by isbn";

	private static final String CHECK_OVERDUE_BORROWER = "select * from book_loans where card_no = :cardno and date_date < curDate() and date_in is null;";
	private static final String CHECK_FINES_UNPAID = "select count(*) from fines join book_loans on fines.loan_id = book_loans.loan_id "
			+ " where book_loans.card_no  =:cardno and paid = 'N';";
	private static final String CHECK_COPIES_BORROWER ="select count(*) from book_loans where card_no =:cardno and date_in is null";
	private static final String BOOK_ID_SQL = "select BOOK_ID from book_copies where isbn = :isbn and branch_id =:branch_id";
	private static final String SAVE_BOOK_LOANS_SQL = "Insert into book_loans(book_id,card_no,date_out,date_date)"
			+ " values ( :bookId, :cardNo, :dateOut, :dueDate);";
	
	private static final String GET_BOOKID_IN_BOOK_LOANS="select book_loans.book_id from book_loans join "
			+ "book_copies on book_loans.book_id = book_copies.book_id "
			+ "join books on books.isbn = book_copies.isbn where books.isbn =:isbn and book_loans.date_in is null;";
	private static final String BOOK_LOAN_SEARCH_SQL = "select book_loans.* , borrower.fname,borrower.lname, books.title,books.isbn from book_loans "
			+ " join borrower on borrower.card_no = book_loans.card_no "
			+ "join book_copies on book_copies.book_id = book_loans.book_id "
			+ "join books on books.isbn = book_copies.isbn "
			+ " where (borrower.card_no like :cardNO OR borrower.fname like :fname "
			+ " OR borrower.lname like :lname ) AND book_loans.date_in is null;";
	
	private static final String INSERT_FINES = "Insert into fines (loan_id, fine_amt,paid) "
			+ "values ( :loanId, datediff(sysDate(), :dueDate)/4, :paid)";
	
	private static final String UPDATE_BOOK_LOANS ="update book_loans set date_in = :date where loan_id =:loanId";
	
	private static final String FINE_RECORDS_SQL ="select fines.*,book_loans.book_id , books.title, books.isbn from fines "
			+ "join book_loans on book_loans.loan_id = fines.loan_id  "
			+ "join book_copies on book_loans.book_id = book_copies.book_id "
			+ "join books on books.isbn = book_copies.isbn "
			+ "where book_loans.card_no =:cardNo";
	private static final String OVERDUE_RECORDS_SQL = "select bl.*,books.isbn,books.title from book_loans bl"
			+ " join book_copies on book_copies.book_id = bl.book_id "
			+ "join books on book_copies.isbn = books.isbn "
			+ " where  card_no =:cardno and date_in is null and date_date < CURDATE()";
	
	private static final String UPDATE_FINES_SQL = "update fines set paid = 'Y' where id =:fineId;";
	@Override
	public List<Book> searchForBooks(BookSearchDto bookSearchDto) {
		log.info("In SearchForBooks DAo method");
		int selectionAttribute = 0;
		String[] searchCriteria = bookSearchDto.getSearchSelection(); 
		String queryParam = "%"+bookSearchDto.getSearchText()+"%";
		Map<String,String> paramMap = new HashMap<String, String>();
		int condition =0;
		if(!(searchCriteria == null || searchCriteria.length == 0))
		{
			for(int i =0 ;i <searchCriteria.length;i++)
			{
				condition += Integer.parseInt(searchCriteria[i]);
			}
		}
		StringBuilder sqlQuery = new StringBuilder(BOOKS_SEARCH_SELECT_STATEMENT);
		switch(condition)
		{
		case 1 : {
			
			sqlQuery.append(" where ").append(TITLE_WHERE_CLAUSE);
			break;
		}
		case 3 : {
			sqlQuery.append(" where ").append(ISBN_WHERE_CLAUSE);
			break;
		}
		case 5:
		{
			sqlQuery.append(" ").append(AUTHORS_JOIN_CLAUSE).append(" where ").append(AUTHORS_WHERE_CLAUSE);
			break;
		}
		case 4:
		{
			sqlQuery.append(" where ").append(TITLE_WHERE_CLAUSE).append(" OR ").append(ISBN_WHERE_CLAUSE);
			break;
		}
		case 6:
		{
			sqlQuery.append(" ").append(AUTHORS_JOIN_CLAUSE).append(" Where ").append(TITLE_WHERE_CLAUSE)
			.append(" OR ").append(AUTHORS_WHERE_CLAUSE);
			break;
		}
		case 8:
		{
			sqlQuery.append(" ").append(AUTHORS_JOIN_CLAUSE).append(" where ").append(ISBN_WHERE_CLAUSE)
			.append(" OR ").append(AUTHORS_WHERE_CLAUSE);
			break;
		}
		default :
		{
			sqlQuery.append(BOOK_SEARCH_SQL);
		}
		}
		
		
		paramMap.put("isbn", queryParam);
		paramMap.put("title", queryParam);
		paramMap.put("author", queryParam);
		
		List<Book> books = namedParameterJdbcTemplate.query (sqlQuery.toString(), paramMap, new BookRowMapper());
		
		Map<String,Book> bookMap = new HashMap<String,Book>();
		if(books == null || !books.isEmpty())
		{
			
			List<String> isbnList = new ArrayList<String>();
			for(Book book : books)
			{
				bookMap.put(book.getIsbn(), book);
				isbnList.add(book.getIsbn());
			}

			for(int j =0,k=0; k<=isbnList.size()/PARAM_SIZE;k++)
			{
			List<String> tempIsbnList = new ArrayList<String>();
			for(int i=j;(i-j< PARAM_SIZE && i< isbnList.size());i++)
			{
				tempIsbnList.add(isbnList.get(i));
			}
			j+=PARAM_SIZE;
			Map<String,Object> paramMap1 = new HashMap<String, Object>();
			paramMap1.put("isbn", tempIsbnList);
				List<Author> authors = namedParameterJdbcTemplate.query(LIST_AUTHORS, paramMap1, new AuthorRowMapper());
				for(Author author : authors)
				{
					Book book = bookMap.get(author.getIsbn());
					
					if(book.getAuthor() == null || book.getAuthor().isEmpty())
					{
						book.setAuthor(author.getAuthorName());
					}
					else
					{
						book.setAuthor(book.getAuthor()+" , "+author.getAuthorName());
					}
					bookMap.put(author.getIsbn(), book);
					
				}
				Map<String,Object> paramMap2 = new HashMap<String, Object>();
				paramMap2.put("isbn", tempIsbnList);
				paramMap2.put("branchId", bookSearchDto.getLibLocationId());
				List<Book> book_copies = namedParameterJdbcTemplate.query(NUMBER_OF_COPIES, paramMap2, new BookCopiesRowMapper());
				for(Book copy:book_copies)
				{
					Book book = bookMap.get(copy.getIsbn());
					book.setCopies(copy.getCopies());
					bookMap.put(copy.getIsbn(), book);
				}
				
			}

		}
		List<Book> booksList = new ArrayList<Book>(bookMap.values());
		return  booksList;
	}

	@Override
	public Author searchAuthor(String searchText) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LibBranch> getLibBranches() {
	
		List<LibBranch> branches = namedParameterJdbcTemplate.query(LIB_BRANCHES_SQL, new LibraryBranchRowMapper());
		return branches;
	}

	@Override
	public Message saveBookLoan(String isbn, String cardNo,int libraryId) {
		Message message= new Message();
		Map<String,String> paramMap = new HashMap<String, String>();
		StringBuilder builder = new StringBuilder();
		paramMap.put("cardno", cardNo);
		 
		 int fines = namedParameterJdbcTemplate.queryForObject(CHECK_FINES_UNPAID,paramMap ,Integer.class);
		 if(fines > 0)
		 {
			 message.setMessageCode(0);
			 message.setMessageText("Can't borrow until fines are cleared");
		 }
		 else
		 {
			 List<BookLoans> bookList = namedParameterJdbcTemplate.query(CHECK_OVERDUE_BORROWER,paramMap , new BookLoansRowMapper());
			 if(!bookList.isEmpty())
			 {
			 for(BookLoans bookLoans:bookList)
				{
				 builder.append(bookLoans.getBookId()+ "is over due \n");
				}
			 	builder.append("Borrower can't borrow more books until he returns the overdue books");
				message.setMessageCode(0);
				message.setMessageText(builder.toString());
			 }
			 else
			 {
				 int bookCount = namedParameterJdbcTemplate.queryForObject(CHECK_COPIES_BORROWER,paramMap ,Integer.class);
				 if(bookCount > 2)
				 {
					 message.setMessageCode(0);
					 message.setMessageText("The selected borrower has already borrowed 3 books");
				 }
				 else
				 {

						Map<String,Object> paramMap2 = new HashMap<String,Object>();
						paramMap2.put("isbn", isbn);
						List<Long> booksListInBookLoans = namedParameterJdbcTemplate.queryForList(GET_BOOKID_IN_BOOK_LOANS,paramMap2,Long.class);
						paramMap2.put("branch_id",libraryId);
						List<Long> bookIdsInBookCopies = namedParameterJdbcTemplate.queryForList(BOOK_ID_SQL,paramMap2,Long.class);
						Long bookId = 0L;
						if(!booksListInBookLoans.isEmpty())
						{
							for(Long id:bookIdsInBookCopies)
							{
								if(!booksListInBookLoans.contains(id))
									{
									bookId = id;
									break;
									}
							}
						}
						else
						{
							bookId = bookIdsInBookCopies.get(0);
						}
						Date today = new Date();
						Map<String,Object> paramMap3 = new HashMap<String,Object>();
						paramMap3.put("bookId", bookId);
						paramMap3.put("cardNo",cardNo);
						paramMap3.put("dateOut",today);
						paramMap3.put("dueDate",DateUtils.addDays(today,14));
						namedParameterJdbcTemplate.update(SAVE_BOOK_LOANS_SQL, paramMap3);
						message.setMessageCode(1);
						message.setMessageText("Book borrowed successfully");
								
					
				 }
			 }
		 
		 }
		return message;
		
	}

	@Override
	public List<BooksLoanRecord> getBookLoanRecords(String borrowerSearchText) {
		String searchString = "%"+borrowerSearchText+"%";
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("cardNO", searchString);
		paramMap.put("fname", searchString);
		paramMap.put("lname", searchString);
		List<BooksLoanRecord> records = namedParameterJdbcTemplate.query(BOOK_LOAN_SEARCH_SQL, paramMap , new BookLoanRecordRowMapper());
		return records;
		
	}

	@Override
	public void saveCheckIn(BooksLoanRecord booksLoanRecord) {
		Date date = new Date();
		//DateUtils.parseDate(str, parsePatterns)
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString = booksLoanRecord.getStdueDate();
		
		

			Date dueDate = null;
			try {
				
				dueDate = formatter.parse(dateInString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(dueDate.compareTo(date) < 0)
		{
			double fineAmt = (date.getTime() - dueDate.getTime())/(24*60*60*1000*4);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("loanId", booksLoanRecord.getLoanId());
			paramMap.put("dueDate", booksLoanRecord.getStdueDate());
			paramMap.put("paid", "N");
			namedParameterJdbcTemplate.update(INSERT_FINES, paramMap);
			
		}
		Map<String, Object> paramMap2= new HashMap<String, Object>();
		paramMap2.put("date", new Date());
		paramMap2.put("loanId", booksLoanRecord.getLoanId());
		namedParameterJdbcTemplate.update(UPDATE_BOOK_LOANS,paramMap2);
	}

	@Override
	public List<FinesRecordsDetails> getFineRecords(String cardNo) {
		// TODO Auto-generated method stub
		Map<String,String> paramMap = new HashMap<String, String>();
		log.info(cardNo);
		paramMap.put("cardNo", cardNo);
		List<FinesRecordsDetails> fineDetails = namedParameterJdbcTemplate.query(FINE_RECORDS_SQL, paramMap , new FinesDisplayRowMapper());
		
		return fineDetails;
	}

	@Override
	public List<OverDueBookSRecord> getOverDueRecords(String cardNo) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("cardno", cardNo);
		List<OverDueBookSRecord> overDueBookRecord = namedParameterJdbcTemplate.query(OVERDUE_RECORDS_SQL, paramMap , new OverDueRecordRowMapper());
		return overDueBookRecord;
	}

	@Override
	public Message updateFineStatus(long fineId) {
		Map<String,Long> paramMap = new HashMap<String, Long>();
		paramMap.put("fineId", fineId);
		namedParameterJdbcTemplate.update(UPDATE_FINES_SQL, paramMap);
		Message message = new  Message();
		message.setMessageCode(1);
		message.setMessageText("Fines status updated successfully");
		return message;
		
	}

	
}
