<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Library Management System</title>
        <link href="<c:url value='/resources/css/bootstrap.min.css'  />" rel="stylesheet"/>
        <script src="<c:url value='/resources/js/jquery-2.2.4.js' />"></script>
        <script src="<c:url value='/resources/js/bootstrap.js' />"></script>
        <link href="<c:url value='/resources/DataTables/datatables.min.css' /> rel="stylesheet"/>
		<script src="<c:url value='/resources/DataTables/datatables.min.js' />" ></script>
		<link href="<c:url value='/resources/css/project_style.css'  />" rel="stylesheet"/>
        <meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<body>
		<div class="container-fluid">
				<div class="border">
            <tiles:insertAttribute name="header" />
</div>
<div style="padding-top:20px">
            <tiles:insertAttribute name="body" />
	</div>	
            <tiles:insertAttribute name="footer" />
        </div>
	</body>
</html>