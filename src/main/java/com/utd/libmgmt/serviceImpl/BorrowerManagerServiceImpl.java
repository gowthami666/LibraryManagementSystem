package com.utd.libmgmt.serviceImpl;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utd.libmgmt.dao.BorrowerManagerDao;
import com.utd.libmgmt.model.Borrower;
import com.utd.libmgmt.service.BorrowerManagerService;

@Service
public class BorrowerManagerServiceImpl implements BorrowerManagerService {
	@Autowired
	BorrowerManagerDao borrowerManagerDao;

	@Override
	public String createBorrower(Borrower borrower) {
		String ssn = borrower.getSsn();
		StringBuilder builder = new StringBuilder(ssn);
		builder.insert(3, "-");
		builder.insert(6, "-");
		borrower.setSsn(builder.toString());
		String phone = borrower.getPhone();
		if(phone !=null && !phone.isEmpty())
		{
		StringBuilder builder2 = new StringBuilder(phone);
		builder2.insert(0, "(");
		builder2.insert(4, ") ");
		builder2.insert(8,"-");
		borrower.setPhone(builder2.toString());
		}	
		return borrowerManagerDao.createBorrower(borrower);
		
	}

	@Override
	public List<Borrower> getBorrowers() {
		return borrowerManagerDao.getBorrowers();
	}

	@Override
	public Borrower generateCardId() {
		List<String> cardsList = borrowerManagerDao.getCardnoList();
		String randCardno = null;
		do
		{
		int rand = generateRandomNumber();
		String randString =String.valueOf(rand);
		//rand = 10001;
		randCardno = "ID"+StringUtils.leftPad(randString,6,"0" );
		
		}while(cardsList.contains(randCardno));
		Borrower borrower = new Borrower();
		borrower.setCardNo(randCardno);
		return borrower;
	}

	private static int generateRandomNumber()
	{
		Random r = new Random();
		return r.nextInt(999000)+1000;
	}	
	
}
