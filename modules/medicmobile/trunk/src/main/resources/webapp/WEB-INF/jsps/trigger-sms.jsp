<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery-1.9.1.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery-ui.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery.dataTables.nightly.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/dialogueBox.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/header.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/sidemenu.js"></script>
<link
	href="${pageContext.request.contextPath}/medicmobile/css/jquery.dataTables.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/medicmobile/css/layout.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/medicmobile/css/jquery-ui-1.10.0.custom.css"
	rel="stylesheet" type="text/css" />

<script
	src="${pageContext.request.contextPath}/medicmobile/js/careGiverTable.js"></script>
<title>View Vaccine</title>
<script>
            $(function(){
                $("#enter-date").dialog({autoOpen:false});
                $("#accordion").accordion({
                    active : 4
              });
                makeLoginNameAppear("${userName}");
                $( "#accordion" ).accordion();
                $("#send-SMS").button();
                
                $("#formAngawadi").bind("submit",function(event){
                         if($("#date").val() == "" || $("#date").val() == null || $("#anganwadiID").val() == "no_AWC_selected") {
                               $("#enter-date").dialog("open");
                               event.preventDefault();
                         }
                     });
            });
            
        </script>
</head>
<body>
	<div id="enter-date" title="">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>select date
		</p>
	</div>
	<div id="content">
		<c:if test="${(previousObject != '') && (not empty previousObject)}">
			<div style="border: 1px solid #000000" id="previousMessageData">
				<div>Messages are queued for "${previousObject}" anganwadi
					center</div>
			</div>
		</c:if>
		<div class="clear"></div>
		<div class="clear"></div>
		<div class="clear"></div>
		<div>
			<form id="formAngawadi"
				action="/motech-platform-server/module/medicmobile/api/triggerSMS"
				method="POST">
				<%--  <c:if test="${previousObject != ''}">
                        <div>ANM visit schedule Saved</div>
                  </c:if> --%>
				<div class="left">Anganwadi Center :</div>
				<select id="anganwadiID" name="anganwadiID">
					<option value="no_AWC_selected">Select AWC</option>
					<c:forEach var="anganwadiCenterView" items="${anganwadiCenterList}">
						<option value="${anganwadiCenterView.anganwadiCenter.anganwadiID}">${anganwadiCenterView.anganwadiCenter.anganwadiCenterName}</option>
					</c:forEach>
				</select>
				<div class="clear"></div>
				<div class="left">ANM visit Date :</div>
				<input class="date" id="date" name="date" />
				<div class="clear"></div>
				<div class="rightButton">
					<button type="submit" id='send-SMS'>Send SMS</button>
				</div>
			</form>
		</div>
	</div>


</body>
</html>

