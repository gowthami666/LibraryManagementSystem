package com.utd.libmgmt.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.utd.libmgmt.model.BooksLoanRecord;

public class BookLoanRecordRowMapper implements RowMapper<BooksLoanRecord> {

	@Override
	public BooksLoanRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
		BooksLoanRecord booksLoanRecord = new BooksLoanRecord();
		booksLoanRecord.setBookId(rs.getLong("book_id"));
		booksLoanRecord.setCardNo(rs.getString("card_no"));
		booksLoanRecord.setDateIn(rs.getDate("date_in"));
		booksLoanRecord.setDateOut(rs.getDate("date_out"));
		booksLoanRecord.setDueDate(rs.getDate("date_date"));
		booksLoanRecord.setFname(rs.getString("fname"));
		booksLoanRecord.setIsbn(rs.getString("isbn"));
		booksLoanRecord.setTitle(rs.getString("title"));
		booksLoanRecord.setLoanId(rs.getLong("loan_id"));
		booksLoanRecord.setLname(rs.getString("lname"));
		
		return booksLoanRecord;
	}

}
