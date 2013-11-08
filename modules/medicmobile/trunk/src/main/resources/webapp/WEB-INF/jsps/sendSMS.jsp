<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery-1.9.1.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery-ui.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery.dataTables.nightly.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/viewPersonnel.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery.dataTables.nightly.js"></script>
<link
	href="${pageContext.request.contextPath}/medicmobile/css/jquery.dataTables.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/medicmobile/js/validator-en.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/validator.js"></script>

<link
	href="${pageContext.request.contextPath}/medicmobile/css/layout.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/medicmobile/css/jquery-ui-1.10.0.custom.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/medicmobile/css/validator.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/medicmobile/js/fetchLocalVolunteer.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/fetchStaff.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/header.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/sidemenu.js"></script>

<script
	src="${pageContext.request.contextPath}/medicmobile/js/pramukhime.js"></script>

<script
	src="${pageContext.request.contextPath}/medicmobile/js/pramukhindic.js"></script>

<script
	src="${pageContext.request.contextPath}/medicmobile/js/sendsms.js"></script>


<script>
            $(function(){
                  makeLoginNameAppear("${userName}");
                  $("#accordion").accordion({
                      active : 4
                });
                $(".notInDataBase").attr("disabled", "disabled");
                $("input[name=toWhom]").bind('change', function(){
                    if(this.value == 'registered'){
                        $(".notInDataBase").attr("disabled", "disabled");
                        $("#phoneNumber").val("");
                        $(".inDataBase").removeAttr("disabled");
                    }
                    else if(this.value == "notRegistered"){
                          $(".careGiverField").hide();
                          $(".staffField").hide();
                          $("#anganwadiID").val("no_AWC_selected");
                        $("input[name=toWhomInAnganwadi]").each(function(){
                              $(this).removeAttr("checked");
                        });
                        $(".inDataBase").attr("disabled", "disabled");
                        $(".notInDataBase").removeAttr("disabled");
                    }
                });
                $(".smsForm").validationEngine("attach");
                $(".staffField").hide();
                 $(".careGiverField").hide();
                $("input[name=toWhomInAnganwadi]").bind('change', function(){
                   
                    if(this.value == 'staff'){
                         $(".careGiverField").hide();
                        $(".staffField").show();
                    }
                    else if(this.value == "careGiver"){
                       bool = false;
                       $(".careGiverField").show();
                       $(".staffField").hide();
                    }
                });
                pramukhIME.addLanguage(PramukhIndic,"hindi");
                $("#dialogMissingAnganwadiId").dialog({
                      autoOpen : false
                });
                $("#dialogSelectRegisteredOrNot").dialog({
                      autoOpen : false
                });
                $("#dialogMissingWhomAnganwadiId").dialog({
                      autoOpen : false
                });
                $("#dialogMissingMessage").dialog({
                      autoOpen : false
                });
                $("#sendSMSForm").bind("submit", function(event){
                      var ph = $("input[name=toWhom]:checked").val();
                      if (ph == 'registered') {
                            var anganwadiId = $("#anganwadiID").val();
                            var whom = $("input[name=toWhomInAnganwadi]:checked").val();
                            if(anganwadiId == "no_AWC_selected") {
                                  $("#dialogMissingAnganwadiId").dialog("open");
                                  event.preventDefault();
                            }
                            else if (whom  != 'staff' && whom != 'careGiver') {
                                  $("#dialogMissingWhomAnganwadiId").dialog("open"); 
                                  event.preventDefault();
                            }
                      }
                });
                $("#previousMessageData").hide();                
                $("#sendSMSButton").bind("click", function(event){
                      var send = true;
                      var ph = $("input[name=toWhom]:checked").val();
                      if (ph == 'registered') {
                            var anganwadiId = $("#anganwadiID").val();
                            var whom = $("input[name=toWhomInAnganwadi]:checked").val();
                            if(anganwadiId == "no_AWC_selected") {
                                  $("#dialogMissingAnganwadiId").dialog("open");
                                  send = false;
                            }
                            else if (whom  != 'staff' && whom != 'careGiver') {
                                  $("#dialogMissingWhomAnganwadiId").dialog("open");
                                  send = false;
                            }
                      }
                      if (ph != "registered" && ph != "notRegistered") {
                            $("#dialogSelectRegisteredOrNot").dialog("open");
                            send = false;
                      } 
                      if($("#message").val() == "" && (send)) {
                            $("#dialogMissingMessage").dialog("open");
                            send = false;
                      } 
                      if(send) {
						   var text = $("#message").val();
						   text.replace("%", "0");
                           sendSMS();
                      }
                });
            });
            function setLanguage() {
                  var lan = $("input[name=language]:checked").val();
                  if (lan == 'hindi') {
                        pramukhIME.enable("message");       
                  }
                  if (lan == 'english') {
                        pramukhIME.disable("message");
                  }
            }
            function enableHindi() {
					   
            }
			function enableEnglish() {
                 pramukhIME.disable("message");
            }
			function focus_phoneNumber() {
			      var ph = $("input[name=toWhom]:checked").val();
                  if (ph == 'registered') {
                        $("#anganwadiID").focus();
                        $("#phoneNumber").removeClass("validate[required,custom[phone]]");
                        $(".phoneNumberformError").hide();
                  }
                  if (ph == 'notRegistered') {
						$("#phoneNumber").focus();
						$("#phoneNumber").addClass("validate[required,custom[phone]]");
						$(".phoneNumberformError").show();
                  }
			}
        </script>
</head>

<body>
	<div id="content">
		<div class="title">
			<h1>Send SMS</h1>
		</div>
		<div id="dialogMissingAnganwadiId" title="Unfilled Fields">
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin: 0 7px 20px 0;"></span>Please select
				Anganwadi Center from drop down
			</p>
		</div>
		<div id="dialogMissingMessage" title="Unfilled Fields">
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin: 0 7px 20px 0;"></span>Message can't be
				empty
			</p>
		</div>
		<div id="dialogSelectRegisteredOrNot" title="Unfilled Fields">
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin: 0 7px 20px 0;"></span>Please select
				registered user or not
			</p>
		</div>
		<div id="dialogMissingWhomAnganwadiId" title="Unfilled Fields">
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin: 0 7px 20px 0;"></span>Please select
				whether to send sms to care giver or staff
			</p>
		</div>
		<form method="post" action="sendSMS" class="smsForm" id="sendSMSForm"
			accept-charset="UTF-8">
			<div style="border: 1px solid #000000" id="previousMessageData">
				<div>Message Sent With following parameters</div>
				<div id="previousMessage">Message:</div>
				<div id="previousRecipients">Recipients:</div>
			</div>
			<div class="clear"></div>
			<div class="left"></div>
			<input type="radio" name="toWhom" onchange="focus_phoneNumber()"
				value="registered" checked="checked" /> Registered Number
			<div class="clear"></div>

			<div class="left">Anganwadi Center</div>
			<select id="anganwadiID" name="anganwadiID" onchange="checkRadio()"
				class="inDataBase">
				<option value="no_AWC_selected">Select AWC</option>
				<c:forEach var="anganwadiCenterView" items="${anganwadiCenterList}">
					<option value="${anganwadiCenterView.anganwadiCenter.anganwadiID}">${anganwadiCenterView.anganwadiCenter.anganwadiCenterName}</option>
				</c:forEach>
			</select>
			<div class="clear"></div>
			<div class="left">Type</div>
			<input type="radio" id="staffCheck" class="whomClass"
				onchange="checkRadio()" name="toWhomInAnganwadi" value="staff" />Staff
			<input type="radio" id="careGiverCheck" name="toWhomInAnganwadi"
				class="whomClass" onchange="checkRadio()" value="careGiver" />CareGiver

			<div class="clear"></div>

			<div class="left staffField">Role</div>
			<select name="staffRole"
				class="inputField validate[required] inDataBase staffField"
				id="staffRole" onchange="fetchStaffWithStaffRole()">
				<option value="all">All</option>
				<c:set var="role" value="${allroles}" />
				<c:forEach var="role" items="${role}">
					<option value="${role}">${role.value}</option>
				</c:forEach>
			</select>
			<div class="clear"></div>
			<div class="left staffField">Staff</div>
			<select id="staffID" name="staffID" onchange="changePhoneNumber()"
				class="inDataBase staffField">

			</select>

			<div class="clear"></div>

			<div class="left careGiverField">CareGiver</div>
			<select id="careGiverID" name="careGiverID"
				class="inDataBase careGiverField">
				<option value="all">All</option>

			</select>
			<div class="clear"></div>
			<div class="left"></div>
			<input type="radio" name="toWhom" value="notRegistered"
				onchange="focus_phoneNumber()" /><label for="phoneNumber">Not
				Registered Number</label>
			<div class="clear"></div>
			<div class="left">Phone Number</div>
			<input type="text" id="phoneNumber" name="newPhoneNumber"
				class="inputField validate[required,custom[phone]] notInDataBase" />
			<div class="clear"></div>
			<input type="radio" name="language" class="whomClass" value="english"
				checked="checked" onchange='setLanguage()' /> English <input
				type="radio" onchange="setLanguage()" name="language"
				class="whomClass" value="hindi" />Hindi
			<div class="left" style="padding-left: 20px; width: 230px">Message</div>
			<div class="inputField" style="font-size: xx-small;">
				<em style="color: red">*</em> can only send sms in English/Hindi
			</div>
			<textarea rows="4" cols="40" name="message" id="message"
				name="message" class="inputField"
				style="float: right; margin-right: 12%;"></textarea>
			<div class="clear"></div>
			<div style="float: right; margin-right: 12%;">
				<button type="button" id="sendSMSButton" class="button margin-class">Send</button>
			</div>
		</form>
	</div>
</body>

</html>
