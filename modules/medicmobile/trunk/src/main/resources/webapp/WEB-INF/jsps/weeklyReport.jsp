<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Weekly Report</title>
<link
	href="${pageContext.request.contextPath}/medicmobile/css/jquery.dataTables.css"
	type="text/css" rel="stylesheet" media="screen">
<link
	href="${pageContext.request.contextPath}/medicmobile/css/layout.css"
	rel="stylesheet" type="text/css" media="screen">
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
	src="${pageContext.request.contextPath}/medicmobile/js/generateReport.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/dialogueBox.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/header.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/sidemenu.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery-migrate-1.1.1.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery.print-preview.js"></script>
<script>
var bool = true;
var selectedIndex = 0;
function setBool(value)
{
	bool = value;
}
            $(function() {
                  makeLoginNameAppear("${userName}");
                  $("#accordion").accordion({
                      active : 5
                });
                  $("#ui-datepicker-div").addClass("printFalse");
                  if('${visitDate}'!='')
                 	{
              	     var visitDate = new Date('${visitDate}');
              	     if(visitDate > new Date()) {
              	           $('#visitDate').datepicker("setDate",new Date());        	           
  	           	     } else {
  	           	        $('#visitDate').datepicker("setDate",visitDate);
  	           	     }
                 	} else {
                 	   $('#visitDate').datepicker("setDate", new Date());
                 	}
                $("#accordion").accordion({
					active : 5
				});
                $(".accordion").accordion();
                $( "#dialogMissingDetails" ).dialog({
                    autoOpen: false
                });
                
                $( "#dateFutureDate" ).dialog({
                      autoOpen: false
                  });
                
                $(".paginate_disabled_previous").hide();
                $(".paginate_disabled_next").hide();
                $(".dataTables_info").hide();
                $(".dataTables_length").hide();
                $(".dataTables_filter").hide();
                $("#hide").hide();
                
                $('.print').printPreview();
                $('.print').button().click(function(){
                	var id = this.id;
					selectedIndex = $(".reportAccordion").accordion( "option", "active");
                    $('.'+id).removeClass('printFalse');
                    fullShowDatatable();
                });
                $("#loadingImage").hide();
                dialogueBoxIfNotSaved();
            $("#hiddenConfirmAnchor").hide();
            $(".hideBefore").hide();
                $('#getChildren').button().click(function(){
                    $("#updateDetails").hide();
                	callAjaxForReport();
                });
                $(".changeWindow").bind("click" , function(event){
                        var id = $(this).attr("id");
                        $("#hiddenConfirmAnchor").text($("#"+id+" a").attr("href"));
                        if(!bool){
                            event.preventDefault();
                            $("#dialog-confirm").dialog("open");
                        }
                    });
                if($("#reportDate").val()!='')
               	{
                	$("#updateDetails").hide();
                	callAjaxForReport();
               	}
                
                $("#updateDetails").bind("submit",function(event){
                	if(new Date($("#visitDate").val()) > new Date()){
                		event.preventDefault();
                		$("#dateFutureDate").dialog("open");
                	}
                	
                		
                });
                    
                });
            function setVisitDate()
            {
            	if('${visitDate}'!='')
               	{
               		$('#visitDate').val('${visitDate}');
               	}
            }
            function getSelectedIndex(){
                  return selectedIndex;
            }
        </script>
<title>Weekly Reports</title>
</head>
<body>
	<div id="dialog-confirm" title="Unsaved Date">
		<div id="hiddenConfirmAnchor"></div>
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>Form Contains
			unsaved Data. Sure you want to continue ?
		</p>
	</div>
	<div id="dialogMissingDetails" title="Unfilled Fields">

		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>Select Date of ANM
			visit
		</p>
	</div>
	<div id="dateFutureDate" title="Date Conflict">

		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>Future Date
			Selected Change it to present Date or past Date
		</p>
	</div>
	<div id="content">
		<div id="getScheduledChildren">
			<div class="title printFalse">
				<h1>Weekly Reports</h1>
			</div>
			<fieldset class="printFalse">
				<legend>Filter Results</legend>
				<div class="left">Select Anganwadi Center</div>
				<select id="anganwadiID">
					<c:forEach var="anganwadiCenterView" items="${anganwadiCenterList}">
						<c:choose>
							<c:when
								test="${anganwadiID[0] == anganwadiCenterView.anganwadiCenter.anganwadiID}">
								<option
									value="${anganwadiCenterView.anganwadiCenter.anganwadiID}"
									selected="selected">${anganwadiCenterView.anganwadiCenter.anganwadiCenterName}</option>
							</c:when>
							<c:otherwise>
								<option
									value="${anganwadiCenterView.anganwadiCenter.anganwadiID}">${anganwadiCenterView.anganwadiCenter.anganwadiCenterName}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
				<div class="clear"></div>
				<div class="left">Select the Date Of Visit</div>
				<input class="date" id="reportDate" style="height: 22px;"
					value="${reportsDate[0]}">
			</fieldset>
			<div class="clear"></div>
			<div style="float: right">
				<button type="button" id="getChildren" class="printFalse">Get</button>
			</div>
			<div class="clear"></div>
			<div id="showEmptyContent"></div>
			<div style="float: right">
				<button id="everyPrint" class="print printFalse hideBefore"
					onclick="removeFullPrint('everyPrint');return false;">Print
					All</button>
			</div>
			<div class="clear"></div>
			<img
				src="${pageContext.request.contextPath}/medicmobile/images/loading.gif"
				id="loadingImage" alt="Loading">
			<form id="updateDetails"
				action="/motech-platform-server/module/medicmobile/api/getWeeklyReport"
				method="POST">
				<div class="hideBefore">
					<label style="margin-right: 30px;">ANM Visit Date</label> <input
						id="visitDate" name="visitDate"
						class="date visitDate printFalse hideBefore" style="height: 22px;">
					<input id="visitDateTextBox" style="height: 22px;">
				</div>
				<div class="clear"></div>

				<input id="reportsDate" name="reportsDate" style="display: none">
				<input id="anganwadi" name="anganwadiID" style="display: none">
				<div id="reportAccordion"></div>
				<div>
					<button id="save" type="submit"
						class="button rightButton printFalse hideBefore">save</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
