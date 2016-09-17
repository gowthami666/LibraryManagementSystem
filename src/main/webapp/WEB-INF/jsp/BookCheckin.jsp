<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.utd.libmgmt.dto.LibBranchDto"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.log4j.Logger"%>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$("#checkin_button").hide();
		$('#bookcheckin-status-message').addClass("success");
		if($.trim($("#bookcheckin-status-message").val()) != "")
			{
			$("#bookcheckin-status-message").addClass("success");
			}
		$('#LoanBookRecords-results-table').dataTable({
			"columns":[{"title":"Book ID"},
			           {"title":"ISBN"},
			           {"title":"TITLE"},
			           {"title":"FIRST NAME"},
			           {"title":"LAST NAME"},
			           {"title":"CARD NO"},
			           {"title":"LOAN ID"},
					   {"title":"DUE DATE"},
					   {"title":"DATE OUT"}
			           ],
			"columnDefs": [
            {
                "targets": [ 0 , 6 ],
                "visible": false,
                "searchable": false
            }]
		}); 
		var table =$("#LoanBookRecords-results-table").DataTable();
		
		$('#LoanBookRecords-results-table tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    } );
	$('#LoanBookRecords-results-table').parents('div.dataTables_wrapper').first().hide();
		$("#bookcheckin_search_button").click(function(){
			
			table.clear().draw();
			var borrowerSearchText= $("#bookcheckin_borrowerID").val();
			if($.trim(borrowerSearchText) === "")
			{
				$('#bookcheckin-status-message').removeClass("success");
				$('#bookcheckin-status-message').html("Borrower details shouldnot be empty").addClass("error").show();
				return;
			}
			$.post('/LibManagementSys/rest/BookLoanRecords', {
				borrowerSearchText:borrowerSearchText
	        },
	        function(data) {
				if(data.length === 0)
				{
					$('#bookcheckin-status-message').removeClass("success");
					$('#bookcheckin-status-message').html("No records found for the borrower").addClass("error").show();
					return;
				}
				
				$('#bookcheckin-status-message').hide();
				$.each(data, function( key, value ) {
				var row = [value.bookId, value.isbn,value.title,value.fname,value.lname,value.cardNo,value.loanId,value.dueDate,value.dateOut];
				table.row.add(row).draw();
				$('#LoanBookRecords-results-table').parents('div.dataTables_wrapper').first().show();
				$("#checkin_button").show();
				}
				);
				
			});
		})
		
		$("#checkin_button").click(function(){
			$('#bookcheckin-status-message').hide();
			var selectedRow = table.row(".selected").data();
			if(selectedRow == undefined)
			{	$('#bookcheckin-status-message').removeClass("success");
				$('#bookcheckin-status-message').html("select a book record to checkin").addClass("error").show();
				return;
			}
			
			$("#checkin_form_bookId_field").val(selectedRow[0]);
			$("#checkin_form_isbn_field").val(selectedRow[1]);
			$("#checkin_form_title_field").val(selectedRow[2]);
			$("#checkin_form_fname_field").val(selectedRow[3]);
			$("#checkin_form_lname_field").val(selectedRow[4]);
			$("#checkin_form_cardno_field").val(selectedRow[5]);
			$("#checkin_form_loan_field").val(selectedRow[6]);
			$("#checkin_form_duedate_field").val(selectedRow[7]);
			$("#checkin_form_dateout_field").val(selectedRow[8]);
			$("#checkin_hidden_form").submit();
		})
	});
</script>



<div class="row-fluid" id="bookcheckin-page-row">
<div id ="bookcheckin-page-body" class = "container-fluid">
<div class="title">CHECK IN PAGE</div>
<div id= "bookcheckin-status-message">
<c:if test="${not empty message}">
 <c:out value="${message.messageText}"/>
 </c:if></div>
 
	<form id= "bookcheckin-page-form" class="form-horizontal">
			<div class="form-group">
			<label class="control-label col-md-2" for="bookcheckin_borrowerID" >Enter borrower details<span class="required">*</span></label>
			<div class= "col-md-6">
			<input type="text" class="form-control"id="bookcheckin_borrowerID" name="borrowerSearch" placeholder="search with first name or last name or card number">
			</div>
		</div>
		<div class="form-group">
			<button type="button" id="bookcheckin_search_button" class="btn btn-primary">Search Books Loans</button>
		</div>
		<table id="LoanBookRecords-results-table" class="display table table-bordered">
		</table>
		<div class="form-group">
			<button type="button" id="checkin_button" class="btn btn-primary col-md-1">Checkin Book</button>
		</div>
	</form>
	<form id = "checkin_hidden_form" action="/LibManagementSys/rest/saveCheckIn" Method ="post" name="bookDet">
			  <input type ="hidden" id="checkin_form_bookId_field" type="hidden" name="bookId">
			  <input type ="hidden" id="checkin_form_isbn_field" type="hidden" name="isbn">
			  <input type ="hidden" id="checkin_form_title_field" type="hidden" name="title">
			  <input type ="hidden" id="checkin_form_fname_field" type="hidden" name="fname">
			  <input type ="hidden" id="checkin_form_lname_field" type="hidden" name="lname">
			  <input type ="hidden" id="checkin_form_cardno_field" type="hidden" name="cardNo">
			  <input type ="hidden" id="checkin_form_loan_field" type="hidden" name="loanId">
			  <input type ="hidden" id="checkin_form_duedate_field" type="hidden" name="stdueDate">
			  <input type ="hidden" id="checkin_form_dateout_field" type="hidden" name="stDateOut">
		</form>
</div>
</div>
