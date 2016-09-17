package com.utd.libmgmt.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.utd.libmgmt.dto.BookSearchDto;
import com.utd.libmgmt.dto.LibBranchDto;
import com.utd.libmgmt.model.Book;
import com.utd.libmgmt.model.BooksLoanRecord;
import com.utd.libmgmt.model.Borrower;
import com.utd.libmgmt.model.FinesRecordsDetails;
import com.utd.libmgmt.model.Message;
import com.utd.libmgmt.model.OverDueBookSRecord;
import com.utd.libmgmt.service.BooksManagerService;
import com.utd.libmgmt.service.BorrowerManagerService;

@Controller
public class LibMgmtController {

	private static final Logger log = Logger.getLogger(LibMgmtController.class);
	
	@Autowired
	BooksManagerService booksManagerService;
	@Autowired
	BorrowerManagerService borrowerManagerService;
	
	
	@RequestMapping(value={"/welcomePage","/welcome"})
	  public ModelAndView ShowHomePage(@RequestParam(value="successText",required=false) String successText) {  
	    	log.info("In show home page");
	    List<LibBranchDto> branches	= booksManagerService.getLibraryBranches();
	    ModelAndView modelView = new ModelAndView("welcomePage");
	   if(successText !="")
	   {
		   modelView.addObject("successText", successText);
	   }
	    modelView.addObject("libBranches", branches);
	        return modelView;  
	}
	
	@RequestMapping(value={"/BorrowerPage"})
	  public ModelAndView displayBorrowerpage(@RequestParam(value="success",required=false) String success) {  
	    	log.info("In BorrowerPage");
	    ModelAndView modelView = new ModelAndView("BorrowerPage");
	    Borrower borrower = borrowerManagerService.generateCardId();
	    if(success != null && !success.isEmpty())
	    {
	    	modelView.addObject("successmsg", "New Borrower saved successfully");
	    }
	    modelView.addObject("borrower",borrower);
	        return modelView;  
	}
	
	@RequestMapping(value={"/BookCheckinPage"})
	  public ModelAndView bookCheckin(@RequestParam(value="success",required=false) String success) {  
	    	log.info("In BookCheckin");
	    ModelAndView modelView = new ModelAndView("BookCheckin");
	    if(success != null && !success.isEmpty())
	    {
	    	Message message = new Message();
	    	message.setMessageCode(1);
	    	message.setMessageText("Book Check In is successful");
	    	modelView.addObject("message", message);
	    }
	    return modelView;  
	}
	
	@RequestMapping(value={"/updateFineStatus"})
	  public ModelAndView updateFineStatus(@RequestParam("fineId") long fineId) {  
	    	log.info("In updateFineStatus");
	    	Message message = booksManagerService.updateFineStatus(fineId);
	    
	    return new ModelAndView("redirect:/rest/FinesPage?success=CHECKIN_SAVED"); 
	}
	
	@RequestMapping(value={"/checkoutPage"})
	  public ModelAndView displayCheckoutPage(@ModelAttribute("bookDet") Book book) {  
	    	log.info("In checkoutPage");
	    ModelAndView modelView = new ModelAndView("CheckoutPage");
	    List<Borrower> borrowers = borrowerManagerService.getBorrowers();
	    modelView.addObject("borrowers", borrowers);
	    modelView.addObject("book",book);
	        return modelView;  
	}
	
	@RequestMapping(value="/checkoutBook", method= RequestMethod.POST)
	  public @ResponseBody Message bookCheckoutStatus(@RequestParam(value="cardNo") String cardNo,
			  @RequestParam(value="isbn") String isbn,@RequestParam(value="libraryId") String libraryId) {  
	    	log.info("In bookCheckoutStatus");
	    	Message message = booksManagerService.saveBookLoan(isbn,cardNo,Integer.parseInt(libraryId));
		return message;
	     
	}
	
	@RequestMapping(value="/BookLoanRecords", method= RequestMethod.POST)
	  public @ResponseBody List<BooksLoanRecord> bookLoanRecords(@RequestParam(value="borrowerSearchText") String borrowerSearchText) {  
	    	log.info("In bookLoanRecords");
	    	
		return booksManagerService.getBookLoanRecords(borrowerSearchText);
	     
	}
	
	@RequestMapping(value="/finesRecords", method= RequestMethod.POST)
	  public @ResponseBody List<FinesRecordsDetails> getFinesRecords(@RequestParam(value="cardNo") String cardNo) {  
	    	log.info("In getFinesRecords"); 	
		return booksManagerService.getFinesRecords(cardNo);
	}
	
	@RequestMapping(value="/overDueRecords", method= RequestMethod.POST)
	  public @ResponseBody List<OverDueBookSRecord> getOverDueRecords(@RequestParam(value="cardNo") String cardNo) {  
	    	log.info("In getOverDueRecords"); 	
		return booksManagerService.getOverDueRecords(cardNo);
	}
	
	@RequestMapping("/saveCheckIn")
	public ModelAndView saveCheckIn(@ModelAttribute("bookDet") BooksLoanRecord booksLoanRecord) {
		log.info("Inside saveCheckIn");
		booksManagerService.saveCheckIn(booksLoanRecord);
		//ModelAndView model = new ModelAndView("BookCheckin");
		return new ModelAndView("redirect:/rest/BookCheckinPage?success=CHECKIN_SAVED");
	}
	
	@RequestMapping("/searchBooks")
	public ModelAndView searchBooks(@ModelAttribute("searchBooksDto") BookSearchDto bookSearchDto) {
		log.info("Inside searchBooks");
		List<Book> books =booksManagerService.getBooks(bookSearchDto);
		System.out.println(bookSearchDto);
		ModelAndView model = new ModelAndView("bookSearch");
		model.addObject("books", books);
		model.addObject("libLocationId",bookSearchDto.getLibLocationId());
		return model;
	}
	
	
	
	@RequestMapping("/addBorrower")
	public ModelAndView AddBorrower(@ModelAttribute("borrowerdets") Borrower borrower) {
		log.info("Inside searchBooks");
		String message =borrowerManagerService.createBorrower(borrower);
		if(!message.equals("1"))
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("BorrowerPage");
			model.addObject("borrower", borrower);
			model.addObject("Errormsg", message);
			return model;
		}
		return new ModelAndView("redirect:/rest/BorrowerPage?success=BORROWER_SAVED");
	}
	
	@RequestMapping("/FinesPage")
	public ModelAndView displayFinesPage(@RequestParam(value="success",required=false) String successText) {
		log.info("Inside displayFinesPage");
		ModelAndView model = new ModelAndView();
		if(successText != null && !successText.isEmpty())
		{
			Message message = new Message();
	    	message.setMessageCode(1);
	    	message.setMessageText("Fine Status updated successfully");
	    	model.addObject("message", message);
		}
		return model;
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex) {

		ex.printStackTrace();
		ModelAndView model = new ModelAndView("Error");
		model.addObject("errMsg", ex.getMessage());

		return model;

	}


}
