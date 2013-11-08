<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Performance Report For Anganwadi Center</title>
<link
	href="${pageContext.request.contextPath}/medicmobile/css/jquery.dataTables.css"
	rel="stylesheet" media="screen">
<link
	href="${pageContext.request.contextPath}/medicmobile/css/layout.css"
	rel="stylesheet" media="screen">
<link
	href="${pageContext.request.contextPath}/medicmobile/css/jquery-ui-1.10.0.custom.css"
	rel="stylesheet" type="text/css" media="screen" />
<link
	href="${pageContext.request.contextPath}/medicmobile/css/print-preview.css"
	rel="stylesheet" type="text/css" media="screen" />
<link
	href="${pageContext.request.contextPath}/medicmobile/css/print.css"
	rel="stylesheet" type="text/css" media="print" />
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery-1.9.1.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery-ui.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery.dataTables.nightly.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery-migrate-1.1.1.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery.print-preview.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/header.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/sidemenu.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/performanceReport.js"></script>
<style>
left {
	height: 20px;
}
</style>
<script>
	$(function() {
	      makeLoginNameAppear("${userName}");
	      $("#accordion").accordion({
              active : 5
        });
		$(".accordion").accordion({
		});
		$( "#dialogMissingDetails" ).dialog({
              autoOpen: false});
		$(".table").dataTable({"sEmptyTable": "no child due for vaccination"});
		$('#print').button().click(function(){
		      $("#performanceReport .ui-accordion-content").css("display", "block");
          });
		$("#print").printPreview();
		$(".paginate_disabled_previous").hide();
		$(".paginate_disabled_next").hide();
		$(".dataTables_info").hide();
		$(".dataTables_length").hide();
		$(".dataTables_filter").hide();

		
		$("#get").button();
		$("#print").button();
	
		
	});
</script>
</head>
<body>
	<div id="dialogMissingDetails" title="Unfilled Fields">

		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span> Please select end
			Date Greater than Start Date
		</p>
	</div>
	<div id="content">
		<div class="title printTrue">
			<h1>Performance Report</h1>
		</div>
		<fieldset class="printFalse">
			<legend>Filter Results</legend>
			<div class="leftinner" style="padding-left: 0">Select Anganwadi
				Center</div>
			<select id="anganwadiID">
				<c:forEach var="anganwadiCenterView" items="${anganwadiCenterList}">
					<option value="${anganwadiCenterView.anganwadiCenter.anganwadiID}">${anganwadiCenterView.anganwadiCenter.anganwadiCenterName}</option>
				</c:forEach>
			</select>
			<div class="clear"></div>

			<div class="leftinner" style="padding-left: 0">Select Date
				Range</div>
			<div style="float: left">
				<input class="date" id="startDate" placeholder="From" /><input
					class="date" id="endDate" style="margin-left: 30px;"
					placeholder="To" />
			</div>
		</fieldset>

		<div class="clear"></div>
		<div class="rightButton printFalse">
			<button class="printFalse" id="get" onclick="callAjax()">Get</button>
			<button class="printFalse" id="print">Print</button>
		</div>
		<div class="clear"></div>
		<div id="performanceReport" class="printTrue"></div>

	</div>
</body>
</html>
