package com.utd.libmgmt.DaoRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.utd.libmgmt.model.OverDueBookSRecord;

public class OverDueRecordRowMapper implements RowMapper<OverDueBookSRecord> {

	@Override
	public OverDueBookSRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
		OverDueBookSRecord overDueBookSRecord = new OverDueBookSRecord();
		overDueBookSRecord.setBookId(rs.getLong("book_id"));
		overDueBookSRecord.setLoanId(rs.getLong("loan_id"));
		overDueBookSRecord.setTitle(rs.getString("title"));
		overDueBookSRecord.setIsbn(rs.getString("isbn"));
		overDueBookSRecord.setDateIn(rs.getDate("date_in"));
		overDueBookSRecord.setDateOut(rs.getDate("date_out"));
		overDueBookSRecord.setDueDate(rs.getDate("date_date"));
		return overDueBookSRecord;
	}

}
