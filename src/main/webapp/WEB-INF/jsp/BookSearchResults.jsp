<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.utd.libmgmt.model.Book"%>
<script>
$(document).ready(function() {
		$('#booksearch-results-table').dataTable({
			"columnDefs": [
            {
                "targets": [ 1 , 2 , 3 ],
                "visible": false,
                "searchable": false
            }]
		}); 
		var table = $('#booksearch-results-table').DataTable();
		$('#booksearch-results-table tbody').on( 'click', 'tr', function () {
			$("#bookSearch_checkout_errorMsg").html("This book is not available in the current Library branch").hide();
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    } );
	
	$("#book_checkout_button").click(function(){
		var selectedRow = table.row(".selected").data();
		if(selectedRow[2] === "0")
		{
			$("#bookSearch_checkout_errorMsg").html("This book is not available in the current Library branch").show();
			$("#bookSearch_checkout_errorMsg").addClass("error");
		}
		else{
			$("#checkout_form_isbn_field").val(selectedRow[1]);
			$("#checkout_form_title_field").val(selectedRow[3]);
			$("#checkout_hidden_form").submit();
		}
			
		})
		
});
</script>
<div class="row-fluid" id="booksearch-results-row">
	<div id ="booksearch-results-body" class = "container-fluid">
	<div class = "row-fluid" id="bookSearch_checkout_errorMsg"></div>
	<div class="title">BOOK SEARCH RESULTS</div>
		<table id="booksearch-results-table" class="display table table-bordered">
			<thead>
				<tr>
					<th>Search Results</th>
					<th>ISBN</th>
					<th>Copies</th>
					<th>Title</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty books}">
					<c:forEach items="${books}" var="ob">
						<tr><td><b><c:out value="${ob.title}"/></b></br>
							<div class="col-md-2">ISBN : <c:out value="${ob.isbn}"/></div>
							<div class="col-md-4">Author : <c:out value="${ob.author}"/></div>
							<div class="col-md-2">Copies : <c:out value="${ob.copies}"/></div>
						</td>
						<td><c:out value="${ob.isbn}"/></td>
						<td><c:out value="${ob.copies}"/></td>
						<td><c:out value="${ob.title}"/></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<div class="row-fluid">
			<button type="button" id="book_checkout_button" class="btn btn-primary col-md-1">Checkout</button>
		</div>
		<form id = "checkout_hidden_form" action="/LibManagementSys/rest/checkoutPage" Method ="post" name="bookDet">
			  <input id="checkout_form_isbn_field" type="hidden" name="isbn">
			  <input id="checkout_form_title_field" type="hidden" name="title">
			  <input type="hidden" name="LibraryId" value="<c:out value='${libLocationId}'/>"/>
		</form>
		
	</div>
</div>