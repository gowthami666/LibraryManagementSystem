package com.utd.libmgmt.service;

import java.util.List;

import com.utd.libmgmt.model.Borrower;

public interface BorrowerManagerService {
	public String createBorrower(Borrower borrower);
	public List<Borrower> getBorrowers();
	public Borrower generateCardId();
}
