<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.utd.libmgmt.dto.LibBranchDto"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.log4j.Logger"%>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$('#searchText_Id').hide();
		$("#welcome_search_button").click(function(){
			if($.trim($('#welcome_inputsearchID').val()) === "")
			{
				$('#searchText_Id').show();
				return;
			}
			var selection = $( "input[name='searchCombo']:checked" );
			if(selection.length != 0)
			{
				var searchSelection =[];
				for(var i =0;i<selection.length;i++)
				{
					searchSelection[i] = selection[i].value; 
				}
				$("#welcome_searchSelection").val(searchSelection);
			}
			
			$("#welcome-page-form").submit();
		})
	});
</script>



<div class="row-fluid" id="welcome-page-row">
<div id ="welcome-page-body" class = "container-fluid">
<div id="welcome-page-status" class="success">
 <c:if test="${not empty successText}">
 <c:out value="${successText}"/>
 </c:if>
</div>
	<form id= "welcome-page-form" class="form-horizontal" action="/LibManagementSys/rest/searchBooks" Method ="post" name="bookSearchDto">
		<div class="title">SEARCH PAGE</div>
		<div class="form-group">
			<label class="control-label col-md-2" for="welcome_libLocationId">Select Library Location<span class="required">*</span></label>
			<div class="col-md-6">
			  <select id="welcome_libLocationId" name="libLocationId" class="form-control">
			  <c:if test="${not empty libBranches}">
					<c:forEach items="${libBranches}" var="ob">
						<div><c:out value="${ob.branchId}"/></div>
						<option value=<c:out value="${ob.branchId}"/>><c:out value="${ob.branchName}"/></option>
					</c:forEach>
				</c:if>
			  </select>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-2" for="searchID">Enter Search<span class="required">*</span></label>
			<div class= "col-md-6">
			<input type="text" class="form-control"id="welcome_inputsearchID" name="searchText"><span id="searchText_Id" class="error">Search can't be empty</span>
			</div>
		</div>
		<div class = "form-group">
			<label class="control-label col-md-2">Search Filter</label>
			<label class="checkbox-inline control-label"><input type="checkbox"  value="1" name = "searchCombo"><b>Title</b></label>
			<label class="checkbox-inline control-label"><input type="checkbox"  value="3" name = "searchCombo"><b>ISBN</b></label>
			<label class="checkbox-inline control-label"><input type="checkbox"  value="5" name = "searchCombo"><b>Author</b></label>
		</div>
		<input type="hidden" id="welcome_searchSelection" name="searchSelection">
		<div class="form-group">
			<button type="button" id="welcome_search_button" class="btn btn-primary col-md-1">Search Books</button>
		</div>
	</form>
</div>
</div>

