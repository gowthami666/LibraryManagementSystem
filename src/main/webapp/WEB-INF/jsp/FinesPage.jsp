<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.utd.libmgmt.dto.LibBranchDto"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.log4j.Logger"%>

<script>
$(document).ready(function() {
		
		$('#fines-results-table').dataTable({
			"columns":[{"title":"ISBN"},
			           {"title":"Title"},
			           {"title":"Fine Amount"},
			           {"title":"Fine Status"},
			           {"title":"Book ID"},
			           {"title":"Loan ID"},
			           {"title":"Id"}
			           ],
			"columnDefs": [
            {
                "targets": [ 4 , 5 , 6 ],
                "visible": false,
                "searchable": false
            }]
		});
		
		$('#overDue-results-table').dataTable({
			"columns":[{"title":"loanId"},
			           {"title":"bookId"},
			           {"title":"ISBN"},
			           {"title":"Title"},
			           {"title":"Date Out"},
			           {"title":"Due DateS"}
			           ],
			"columnDefs": [
            {
                "targets": [ 0 , 1 ],
                "visible": false,
                "searchable": false
            }]
		});
		$('#fines-results-table').parents('div.dataTables_wrapper').first().hide();
		$('#overDue-results-table').parents('div.dataTables_wrapper').first().hide();
		$("#finePaid_button").hide();
		var finesTable = $("#fines-results-table").DataTable();
		var overdueTable = $("#overDue-results-table").DataTable();
		$('#fines-results-table').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            finesTable.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    } );
		$("#common_tableName").hide();
		$("#display_fines_button").click(function(){
			console.log("clicked");
			finesTable.clear().draw();
			var cardNo = $("#fines_cardNo").val();
			if($.trim(cardNo) === "")
			{
				$("#fines-page-status").removeClass("success");
				$("#fines-page-status").html("Card No cannot be empty").addClass("error").show();
				return;
			}
			$('#overDue-results-table').parents('div.dataTables_wrapper').first().hide();
			$('#fines-results-table').parents('div.dataTables_wrapper').first().show();
			$.post('/LibManagementSys/rest/finesRecords', {
				cardNo:cardNo
	        },
	        function(data) {
	        	$.each(data, function( key, value ) {
					var row = [value.isbn,value.title,value.fineAmt,value.paid,value.bookId,value.loanId,value.id];
					finesTable.row.add(row).draw();
					})
			});
			$("#common_tableName").html("FINE STATUS TABLE").show();
			$("#finePaid_button").show();
		});
		$("#display_overdue_button").click(function(){
			console.log("clicked");
			overdueTable.clear().draw();
			var cardNo = $("#fines_cardNo").val();
			if($.trim(cardNo) === "")
			{
				$("#fines-page-status").removeClass("success");
				$("#fines-page-status").html("Card No cannot be empty").addClass("error").show();
				return;
			}
			$('#fines-results-table').parents('div.dataTables_wrapper').first().hide();
			$('#overDue-results-table').parents('div.dataTables_wrapper').first().show();
			$.post('/LibManagementSys/rest/overDueRecords', {
				cardNo:cardNo
	        },
	        function(data) {
	        	$.each(data, function( key, value ) {
					var row = [value.loanId,value.bookId, value.isbn,value.title,value.dateOut,value.dueDate];
					overdueTable.row.add(row).draw();
					})
					$("#common_tableName").html("OVER DUE TABLE").show();
					$("#fines-page-status").hide();
	        	$("#finePaid_button").hide();
			});
			
		})
		
		$("#finePaid_button").click(function(){
			console.log("clicked");
			var selectedRow = finesTable.row(".selected").data();
			if(selectedRow == undefined)
			{
				$("#fines-page-status").removeClass("success");
				$("#fines-page-status").html("select the record to update the fine status").addClass("error").show();
				return;
			}
			if(selectedRow[3] === "Y")
			{
				$("#fines-page-status").removeClass("success");
				$("#fines-page-status").html("Fine is already paid").addClass("error").show();
				return;
			}
			$("#fine_form_bookId_field").val(selectedRow[6])
			$("#fines-page-status").hide();
			$("#fine_hidden_form").submit();
			
		});
	});
</script>



<div class="row-fluid" id="fines-page-row">
<div id ="fines-page-body" class = "container-fluid">
<div class="title">FINES</div>
<div id="fines-page-status" class ="success">
 <c:if test="${not empty message}">
 <c:out value="${message.messageText}"/>
 </c:if>
</div>
	<form id= "fines-page-form" class="form-horizontal">
	
			<div class="form-group">
			<label class="control-label col-md-2" for="fines_cardNo">Enter borrower's Card Number<span class="required">*</span></label>
			<div class= "col-md-6">
			<input type="text" class="form-control"id="fines_cardNo" name="cardNo">
			</div>
		</div>
		<div class="form-group">
			<button type="button" id="display_fines_button" class="btn btn-primary ">Display Fines History</button>
			<div class ="col-md-1"></div>
			<button type="button" id="display_overdue_button" class="btn btn-primary">Display overdue books</button>
		</div>
		<div class ="headings" id="common_tableName"></div>
		<table id="fines-results-table" class="display table table-bordered"></table>
		<table id="overDue-results-table" class="display table table-bordered"></table>
		<div class="form-group">
			<button type="button" id="finePaid_button" class="btn btn-primary">Update Fine Status</button>
		</div>
	</form>
	<form id = "fine_hidden_form" action="/LibManagementSys/rest/updateFineStatus" Method ="post" name="PayFine">
			  <input type ="hidden" id="fine_form_bookId_field" type="hidden" name="fineId">
		</form>
</div>
</div>
