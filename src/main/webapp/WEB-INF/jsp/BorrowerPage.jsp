<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.utd.libmgmt.model.Borrower"%>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$("#borrower_ssn").keypress(function (e) {
     //if the letter is not digit then display error and don't type anything
     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
        //display error message
        $("#errmsgssn").html("Digits Only").show();
               return false;
    }
	$("#errmsgssn").html("Digits Only").hide();
		});
	$("#borrower_phone").keypress(function (e) {
     //if the letter is not digit then display error and don't type anything
     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
        //display error message
        $("#errmsgphone").html("Digits Only").show();
               return false;
    }
	
	 $("#errmsgphone").html("Digits Only").hide();
   });
		$.fn.checkEmpty = function(){
			console.log("inside checkEmpty")
			if($(this).val() === undefined || $.trim($(this).val()) === "")
			{
				$(this).siblings('.borrower_error').html("can't be empty").show();
				
				return false;
			}
			return true;
		};
	
	$("#borrower_add_button").click(function(){
			if($("#borrower_ssn").checkEmpty() &  $("#borrower_fname").checkEmpty() & $("#borrower_lname").checkEmpty() & $("#borrower_address").checkEmpty())
		{
			if($("#borrower_ssn").val().length != 9)
			{
				$("#errmsgssn").html("should be of 9 digits long").show();
				return;
			}
			else if ($("#borrower_phone").val() != "" && $("#borrower_phone").val().length != 10)
			{
				$("#errmsgphone").html("should be of 10 digits long").show();
				return;
			}
			$("#borrower-page-form").submit();
			
		}
		})
	});
</script>

<div class="row-fluid" id="welcome-page-row">
<div id ="borrower-page" class = "container-fluid">
<div class="title">NEW BORROWER</div>
	<c:if test="${not empty successmsg}">
		<h4 class="Success">${successmsg}</h4>
		</c:if>
		<c:if test="${not empty Errormsg}">
		<h4 class="Error">${Errormsg}</h4>
		</c:if>
	<form id= "borrower-page-form" class="form-horizontal" action="/LibManagementSys/rest/addBorrower" Method ="post" name="borrowerdets">
		<div class="form-group">
			<label class="control-label col-md-2" for="borrower_cardno">Card Number</label>
			<div class="col-md-6">
			  <input type="text" class="form-control"id="borrower_cardno" readOnly name="cardNo" 
			  value="<c:out value='${borrower.cardNo}'/>">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-2 digitsOnly" for="borrower_ssn">SSN<span class="required">*</span></label>
			<div class="col-md-4">
			  <input type="number" required class="form-control"id="borrower_ssn" name="ssn"><span class="error borrower_error" id="errmsgssn"/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-2" for="borrower_fname">First Name<span class="required">*</span></label>
			<div class="col-md-4">
			  <input type="text" required class="form-control"id="borrower_fname" name="fname"><span class="error borrower_error" id="errmsgfname"/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-2" for="borrower_lname">Last Name<span class="required">*</span></label>
			<div class="col-md-4">
			  <input type="text" required class="form-control"id="borrower_lname" name="lname"><span class="error borrower_error" id="errmsglname"/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-2" for="borrower_address">Address<span class="required">*</span></label>
			<div class="col-md-6">
			  <textarea class="form-control"id="borrower_address" required name="address" maxLength = 100></textarea><span class="error borrower_error" id="errmsgaddress">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-2 digitsOnly" for="borrower_phone">Phone Number</label>
			<div class="col-md-6">
			  <input type="text" class="form-control"id="borrower_phone" name="phone"><span class="error" id="errmsgphone">
			</div>
		</div>
		<div class="form-group">
		
			<button type="button" id="borrower_add_button" class="btn btn-primary col-md-2">Add Borrower</button>
		</div>
	</form>
</div>
</div>

