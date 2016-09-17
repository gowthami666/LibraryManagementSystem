package com.utd.libmgmt.DaoRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.utd.libmgmt.model.BookLoans;

public class BookLoansRowMapper implements RowMapper<BookLoans> {

	@Override
	public BookLoans mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		BookLoans bookLoans = new BookLoans();
		bookLoans.setLoanId(rs.getLong("loan_Id"));
		bookLoans.setBookId(rs.getLong("book_id"));
		bookLoans.setDateOut(rs.getDate("date_out"));
		bookLoans.setDateIN(rs.getDate("date_in"));
		bookLoans.setDuedate(rs.getDate("date_date"));
		return bookLoans;
	}

}
