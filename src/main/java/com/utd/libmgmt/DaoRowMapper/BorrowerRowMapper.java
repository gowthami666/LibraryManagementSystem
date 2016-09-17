package com.utd.libmgmt.DaoRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.utd.libmgmt.model.Borrower;

public class BorrowerRowMapper implements RowMapper<Borrower> {

	@Override
	public Borrower mapRow(ResultSet rs, int rowNum) throws SQLException {
		Borrower borrower = new Borrower();
		borrower.setAddress(rs.getString("address"));
		borrower.setCardNo(rs.getString("card_no"));
		borrower.setFname(rs.getString("fname"));
		borrower.setId(rs.getLong("Id"));
		borrower.setLname(rs.getString("lname"));
		borrower.setPhone(rs.getString("phone"));
		borrower.setSsn(rs.getString("ssn"));
		return borrower;
	}

}
