<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%@ page import="org.medicmobile.model.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html lang="en">

<head>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery-1.9.1.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery-ui.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery.dataTables.nightly.js"></script>
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
	src="${pageContext.request.contextPath}/medicmobile/js/validator-en.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery.multiselect.js"></script>
<link
	href="${pageContext.request.contextPath}/medicmobile/css/jquery.multiselect.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/medicmobile/js/validator.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/header.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/dialogueBox.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/sidemenu.js"></script>
<link
	href="${pageContext.request.contextPath}/medicmobile/css/validator.css"
	rel="stylesheet">
<script>
      $(function() {
            makeLoginNameAppear("${userName}");
            $("#accordion").accordion({
                  active : 1
            });
            $(".ui-datepicker-trigger").addClass("inputField");
            $(".inputField").hide();
            $(".label").hide();
            $("#" + "${staff.gender}").prop('checked', true);
            $("#address").html("${staff.address}");
            $("#" + "${staff.whetherToReceiveSMS}").prop('checked', true);
            $("#role").val('${staff.role}');
            $(".multiselect").multiselect({
                  noneSelectedText : 'Select ',
                  selectedList : 4
            });
            $("#staffCenterId").multiselect({
                  noneSelectedText : 'Select ',
                  selectedList : 4
            });
            $("#language").multiselect({
                  noneSelectedText : 'Select ',
                  selectedList : 4
            });
            $(".buttonDropDown").hide();
            $(".addInput").hide();
            $(".editInput").hide();
            $("#delete").hide();
            $("#dialogMissingDetails").dialog({
                  autoOpen : false
            });
            $("#dialogDeletingLVHavingCareGivers").dialog({
                  autoOpen : false
            });
            $("#hiddenConfirmAnchor").hide();
            var bool;
            if ("${staff.name}" == '') {
                  $(".inputField").show();
                  $(".buttonDropDown").show();
                  bool = false;
                  $(".addInput").show();
                  $("#editButton").hide();
            } else {
                  $("#delete").show();
                  $(".addInput").hide();
                  $(".label").show();
                  if("${staff.whetherToReceiveSMS}".toLowerCase() == "true"){
                        $("#whetherToReceiveSMSLabel").text("Yes");
                  } else if("${staff.whetherToReceiveSMS}".toLowerCase() == "false") {
                        $("#whetherToReceiveSMSLabel").text("No");
                  }
                  $("#savePersonnel").hide();
                  var anganwadi = [];
                  <c:forEach var="anganwadiCenterView" items="${anganwadiCenterList}">
                  anganwadi
                              .push("${anganwadiCenterView.anganwadiCenter.anganwadiID}");
                  </c:forEach>
                  $("#staffCenterId").val(anganwadi);
                  $(".multiselect").multiselect("refresh");
                  var languages = [];
                  <c:forEach var="language" items="${staff.languages}">
                  //                        var lang = ;
                  languages.push("${language}");
                  </c:forEach>
                  $("#languagesSpoken").val(languages);
                  $widget = $("#staffCenterId").multiselect("refresh");
                  $("#language").multiselect("refresh");
                  $(".buttonDropDown").hide();
            }
            $("#editButton")
                        .bind(
                                    "click",
                                    function() {

                                          var role = $("#role").val();
                                          sendAjaxForLocalVolunteer("${staff.staffID}", false);
                                          if (!(role == "AUXILIARY_NURSING_MOTHER")) {
                                                $("#staffCenterId")
                                                            .multiselect(
                                                                        {
                                                                              multiple : false,
                                                                              noneSelectedText : 'Select ',
                                                                              selectedList : 3
                                                                        });

                                          } else if (role == "AUXILIARY_NURSING_MOTHER") {
                                                $("#staffCenterId")
                                                            .multiselect(
                                                                        {
                                                                              multiple : true
                                                                        });
                                          }
                                          $(".label").hide();
                                          $(".editInput").show();
                                          $("#editButton").hide();
                                          $("#delete").hide();
                                          bool = true;
                                          $(".inputField").show();
                                          $("#languagesSpoken").val(languages);
                                          $("#role").val("${staff.role}");
                                          var anganwadiCenterMultiSelect = $("#staffCenterId");
                                          var anganwadiIDArray = new Array();
                                          <c:forEach var="anganwadiID" items="${staff.anganwadiID}">
                                          anganwadiIDArray
                                                      .push("${anganwadiID}");
                                          </c:forEach>
                                          anganwadiCenterMultiSelect
                                                      .val(anganwadiIDArray);
                                          anganwadiCenterMultiSelect
                                                      .multiselect("refresh");

                                          var languagesMultiSelect = $("#languagesSpoken");
                                          var languagesArray = new Array();
                                          <c:forEach var="language" items="${staff.languages}">
                                          languagesArray.push("${language}");
                                          </c:forEach>
                                          languagesMultiSelect
                                                      .val(languagesArray);
                                          languagesMultiSelect
                                                      .multiselect("refresh");
                                          //var anganwadiCenterMultiSelect = $("#staffCenterId") */
                                          $("#staffCenterId").multiselect(
                                                      "refresh");
                                          $("#language").multiselect("refresh");
                                          $(".buttonDropDown").show();
                                          $("#savePersonnel").show();

                                    });

            
            $("#role").change(function() {
                  var role = $("#role").val();
                  if (!(role == "AUXILIARY_NURSING_MOTHER")) {
                        $("#staffCenterId").multiselect({
                              multiple : false,
                              noneSelectedText : 'Select ',
                              selectedList : 3
                        });

                  } else if (role == "AUXILIARY_NURSING_MOTHER") {
                        $("#staffCenterId").multiselect({
                              multiple : true
                        });
                  }
            });
            $(".basicForm").validationEngine("attach");

            $(".basicForm").bind(
                        "submit",
                        function(event) {
                              if ($("#staffCenterId").val() == null) {
                                    $("#dialogMissingDetails").dialog("open");
                                    event.preventDefault();
                              }

                        });
            var link = '/motech-platform-server/module/medicmobile/api/deleteStaff/';
            dialogueBoxDeleteData(link, {"staffID":'${staff.staffID}'}, "/motech-platform-server/module/medicmobile/api/viewPersonnel");
            $('#delete').bind('click',function() {
                  sendAjaxForLocalVolunteer("${staff.staffID}", true);
            });
            dialogueBoxIfNotSaved();
            $(".changeWindow").bind(
                        "click",
                        function(event) {
                              var id = $(this).attr("id");
                              $("#hiddenConfirmAnchor").text(
                                          $("#" + id + " a").attr("href"));
                              if (bool) {
                                    event.preventDefault();
                                    $("#dialog-confirm").dialog("open");

                              }
                        });
            $('.left').click(function() {
                  $(".buttonDropDown").multiselect('disable');
            });
            $('.basicForm').submit(function() {
                  $('[disabled]').each(function(i) {
                      $(this).attr("disabled", false);
                  });
                  return true;
              });
           
      });
</script>
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
				style="float: left; margin: 0 7px 20px 0;"></span>Select Anganwadi
			Center
		</p>
	</div>
	<div id="dialogDeletingLVHavingCareGivers" title="Unfilled Fields">

		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>Can't delete have
			care givers under him/her
		</p>
	</div>
	<div id="dialog-delete" title="Delete">

		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>Sure you want to
			delete the data.
		</p>
	</div>

	<div id="content">
		<div id="Personnel">
			<form class="basicForm"
				action="/motech-platform-server/module/medicmobile/api/staff"
				method="POST">
				<div class="title">
					<h1 style="float: left; margin-top: 0" class="addInput">Add
						Personnel</h1>
					<h1 style="float: left; margin-top: 0" class="label">View
						Personnel</h1>
					<h1 style="float: left; margin-top: 0" class="editInput">Edit
						Personnel</h1>

					<button class="rightButton button" type="button" id="editButton">Edit</button>
					<button type="button" class="rightButton button" id="delete">Delete</button>
					<div class="clear"></div>
				</div>
				<div>
					<input type="hidden" name="staffID" value="${staff.staffID}" />
					<div class="left">
						<label for="name">Name<em style="color: red"> *</em></label>
					</div>
					<input id="name"
						class="inputField validate[required,custom[onlyLetterSp]]"
						name="name" value="${staff.name}"> <label class="label">${staff.name}</label>
					<div class="clear"></div>
					<div class="left">
						<label for="dateOfBirth">Date Of Birth </label>
					</div>
					<input size="20" id="dateOfBirth" class="date inputField "
						name="dateOfBirthString"
						value="<fmt:formatDate value='${staff.dateOfBirth}' pattern='dd MMM yyyy' />">
					<label class="label"><fmt:formatDate
							value='${staff.dateOfBirth}' pattern='dd MMM yyyy' /></label>
					<div class="clear"></div>
					<div class="left">Gender</div>
					<div class="inputField">
						<input type="radio" id="MALE" name="gender" value="MALE"
							class="radio" checked>Male<input class="radio"
							type="radio" id="FEMALE" name="gender" value="FEMALE">Female
					</div>
					<label class="label">${staff.gender.value}</label>
					<div class="clear"></div>
					<div class="left">Employee ID</div>
					<input size="20" name="employeeID"
						class="inputField validate[custom[onlyLetterNumber]]"
						value="${staff.employeeID}"> <label class="label">${staff.employeeID}</label>
					<div class="clear"></div>
					<div class="left">Role</div>
					<select name="role" class="inputField validate[required]" id="role"><c:set
							var="role" value="${allroles}" />
						<c:forEach var="role" items="${role}">
							<option value="${role}">${role.value}</option>
						</c:forEach></select> <label class="label">${staff.role.value}</label>
					<div class="clear"></div>
					<div class="left">
						Anganwadi Center<em style="color: red"> *</em>
					</div>
					<div>
						<select multiple id="staffCenterId"
							class="multiselect validate[required]" name="anganwadiID"
							style="margin-right: 20px">
							<c:forEach var="anganwadiCenterView"
								items="${anganwadiCenterList}">
								<option
									value="${anganwadiCenterView.anganwadiCenter.anganwadiID}">${anganwadiCenterView.anganwadiCenter.anganwadiCenterName}</option>
							</c:forEach>
						</select> <label class="label" id="anganwadiCenterLabel"> <c:forEach
								var="anganwadiID" items="${staff.anganwadiID}"
								varStatus="status">
								<c:forEach var="anganwadiCenterView"
									items="${anganwadiCenterList}">
									<c:if
										test="${anganwadiID == anganwadiCenterView.anganwadiCenter.anganwadiID}">
										<c:out
											value="${anganwadiCenterView.anganwadiCenter.anganwadiCenterName}" />
										<span>${status.last ? "" : ","}</span>
									</c:if>
								</c:forEach>
							</c:forEach>
						</label>
						<script>$(function(){
                        var name = $( "#anganwadiCenterLabel" ).text().replace(/\s+/g, ' ');
                        if(name.length >= 30){
                            var temp = name.substring(0,27) +"...";
                            $("#anganwadiCenterLabel").attr("title",name);
                            $("#anganwadiCenterLabel").text(temp);
                        }
                        else{
                              $("#anganwadiCenterLabel").text(name);
                            $("#anganwadiCenterLabel").attr("title","");
                        }
                    });
                        </script>
					</div>
					<div class="clear"></div>
					<div class="left">Languages Spoken</div>
					<select multiple="multiple" class="multiselect" name="languages"
						id="languagesSpoken"><c:set var="language"
							value="${alllanguages}" />
						<c:forEach var="language" items="${language}">
							<option value="${language}">${language.value}</option>
						</c:forEach>
					</select> <label class="label"><c:forEach var="language"
							items="${staff.languages}" varStatus="status">
							<c:out value="${language.value}" />
							<span>${status.last ? "" : ","}</span>
						</c:forEach></label>
					<div class="clear"></div>
					<div class="left">
						Phone Number<em style="color: red"> *</em>
					</div>
					<input size="20" name="phoneNumber"
						class="inputField validate[required,custom[phone]]"
						value="${staff.phoneNumber}"> <label class="label">${staff.phoneNumber}</label>
					<div class="clear"></div>
					<div class="left">Recieve SMS</div>
					<div class="inputField">
						<input type="radio" name="whetherToReceiveSMS" class="radio"
							id="true" value="true">Yes<input class="radio" id="false"
							type="radio" name="whetherToReceiveSMS" value="false">No
					</div>
					<label class="label" id="whetherToReceiveSMSLabel"></label>
				</div>
				<button type="submit" class="button rightButton" id="savePersonnel">Save</button>
			</form>

		</div>
	</div>