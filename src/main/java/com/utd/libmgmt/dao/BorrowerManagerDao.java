package com.utd.libmgmt.dao;

import java.util.List;

import com.utd.libmgmt.model.Borrower;

public interface BorrowerManagerDao {

	public String createBorrower(Borrower borrower);
	public Borrower getBorrowerDetails(long id);
	public List<String> getCardnoList();
	public List<Borrower> getBorrowers();
}
