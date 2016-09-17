<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.utd.libmgmt.model.Book"%>
<%@page import="com.utd.libmgmt.model.Borrower"%>
<script>
$(document).ready(function() {
			
		var table = $('#checkout-borrower-table').DataTable();
		$('#checkout-borrower-table').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    } );
	
	$("#checkout_borrower_button").click(function(){
		
		
		var selectedRow = table.row(".selected").data();
		var cardNo = selectedRow[0]; 
		var data = {};
		var isbn = $("#checkout_book_Isbn").val();
		var libId = $("#checkout_book_library_id").val();
		data.cardNo = cardNo;
		
		$.post('/LibManagementSys/rest/checkoutBook', {
            cardNo: cardNo,
            isbn: isbn,
			libraryId:libId
        },
        function(data) {
			console.log(data);
			if(data.messageCode === 1)
			{
			$('#checkout_status').html(data.messageText).show();
			$('#checkout_status').addClass("success");
			$("#hidden_heckout_SuccessText").val(data.messageText);
			$("#hidden_form_checkout").submit();
			
			}
		if(data.messageCode === 0)
			{
			$('#checkout_status').html(data.messageText).show();
			$('#checkout_status').addClass("error");
			}
			
		});
		
});
});
</script>
<div class="row-fluid" id="booksearch-results-row">
	<div id ="Checkout_page" class = "container-fluid">
	<div class = "row-fluid" id="checkout_status"></div>
	<div class="title">CHECKOUT PAGE</div>
	<form id = "hidden_form_checkout" action="/LibManagementSys/rest/welcome" Method ="post" name="success">
	<input type="hidden" id="hidden_heckout_SuccessText" name = "successText">
	</form>
	<form id= "checkout-page-form" class="form-horizontal">
	<div class="form-group">
		<label class="control-label col-md-2" for="checkout_book_Title">Book Title</label>
		<div class="col-md-6">
			<input type="text" class="form-control"id="checkout_book_Title" readOnly name="title" 
			  value="<c:out value='${book.title}'/>">
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-2" for="checkout_book_Isbn">Book ISBN</label>
		<div class="col-md-6">
			<input type="text" class="form-control"id="checkout_book_Isbn" readOnly name="isbn" 
			  value="<c:out value='${book.isbn}'/>">
		</div>
	</div>
	<input type="hidden" id="checkout_book_library_id" readOnly name="libraryId"  value="<c:out value='${book.libraryId}'/>">
	</form>
	<div class="headings">SELECT THE BORRORWER FROM THE BELOW TABLE AND CLICK CHECKOUT</div>
		<table id="checkout-borrower-table" class="display table table-bordered">
			<thead>
				<tr>
					<th>Card No</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>SSN</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty borrowers}">
					<c:forEach items="${borrowers}" var="ob">
						<tr><td><c:out value="${ob.cardNo}"/></td>
						<td><c:out value="${ob.fname}"/></td>
						<td><c:out value="${ob.lname}"/></td>
						<td><c:out value="${ob.ssn}"/></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<div class="row-fluid">
			<button type="button" id="checkout_borrower_button" class="btn btn-primary col-md-1">Checkout</button>
		</div>
	</div>
</div>