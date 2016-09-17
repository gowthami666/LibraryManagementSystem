package com.utd.libmgmt.DaoRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.utd.libmgmt.model.Fines;

public class FinesRowMapper implements RowMapper<Fines> {

	@Override
	public Fines mapRow(ResultSet rs, int rowNum) throws SQLException {
		Fines fines = new Fines();
		fines.setId(rs.getLong("id"));
		fines.setLoanId(rs.getLong("loan_id"));
		fines.setFineAmount(rs.getDouble("fine_amt"));
		if(rs.getString("paid").equalsIgnoreCase("Y"))
		{
			fines.setPaid(true);
		}
		else
		{
		fines.setPaid(false);
		}
		return fines;
	}

}
