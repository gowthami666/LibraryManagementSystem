package com.utd.libmgmt.daoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.utd.libmgmt.DaoRowMapper.BorrowerRowMapper;
import com.utd.libmgmt.dao.BorrowerManagerDao;
import com.utd.libmgmt.model.Borrower;

@Repository
public class BorrowerManagerDaoImpl implements BorrowerManagerDao {

	private static final Logger log = Logger
			.getLogger(BorrowerManagerDaoImpl.class);
	private static final String CREATE_BORROWER_SQL = "Insert into borrower (card_no,ssn,fname,lname,address,phone)"
			+ " values (:cardNo,:ssn,:fname,:lname,:address,:phone);";
	
	private static final String CARD_LIST_SQL ="select card_no from borrower";
	private static final String BORROWER_LIST_SQL = "select * from borrower";
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	

	@Override
	public String createBorrower(Borrower borrower) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("cardNo", borrower.getCardNo());
		paramMap.put("ssn", borrower.getSsn());
		paramMap.put("fname", borrower.getFname());
		paramMap.put("lname", borrower.getLname());
		paramMap.put("address", borrower.getAddress());
		paramMap.put("phone", borrower.getPhone());
		int success =0;
		try
		{
			success = namedParameterJdbcTemplate.update(CREATE_BORROWER_SQL, paramMap);
		
		}catch(Exception e)
		{
			e.printStackTrace();
			return e.getCause().getMessage();
		}
		return Integer.toString(success);
	}

	@Override
	public Borrower getBorrowerDetails(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getCardnoList() {
		List<String> cardList = namedParameterJdbcTemplate.queryForList(CARD_LIST_SQL, new HashMap(), String.class);
		return cardList;
	}

	@Override
	public List<Borrower> getBorrowers() {
		List<Borrower> borrower = namedParameterJdbcTemplate.query(BORROWER_LIST_SQL, new HashMap(), new BorrowerRowMapper());
		return borrower;
	}

}
