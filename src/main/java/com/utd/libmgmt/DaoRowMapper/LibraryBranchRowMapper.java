package com.utd.libmgmt.DaoRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.utd.libmgmt.model.LibBranch;

public class LibraryBranchRowMapper implements RowMapper<LibBranch> {
	
	private static final Logger log = Logger.getLogger(LibraryBranchRowMapper.class);

	@Override
	public LibBranch mapRow(ResultSet rs, int rowNum) throws SQLException {
		log.info("Inside LibraryBranchRowMapper mapRow method");
		LibBranch branch = new LibBranch();
		branch.setBranchId(rs.getInt("BRANCH_ID"));
		branch.setBranchName(rs.getString("BRANCH_NAME"));
		branch.setAddress(rs.getString("address"));
		return branch;
	}

}
