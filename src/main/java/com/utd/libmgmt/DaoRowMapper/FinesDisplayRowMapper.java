package com.utd.libmgmt.DaoRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.utd.libmgmt.model.FinesRecordsDetails;

public class FinesDisplayRowMapper implements RowMapper<FinesRecordsDetails> {

	@Override
	public FinesRecordsDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		FinesRecordsDetails details = new FinesRecordsDetails();
		details.setBookId(rs.getLong("book_id"));
		details.setFineAmt(rs.getDouble("fine_amt"));
		details.setLoanId(rs.getLong("loan_id"));
		details.setId(rs.getLong("id"));
		details.setIsbn(rs.getString("isbn"));
		details.setTitle(rs.getString("title"));
		details.setPaid(rs.getString("paid"));
		
		return details;
	}

}
