
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	src="${pageContext.request.contextPath}/medicmobile/js/careGiver.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/fetchLocalVolunteer.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/header.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/sidemenu.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/dialogueBox.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/validator-en.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/validator.js"></script>

<script
	src="${pageContext.request.contextPath}/medicmobile/js/pramukhime.js"></script>

<script
	src="${pageContext.request.contextPath}/medicmobile/js/pramukhindic.js"></script>
<link
	href="${pageContext.request.contextPath}/medicmobile/css/validator.css"
	rel="stylesheet">
<script>
      $(function() {
            makeLoginNameAppear("${userName}");
            pramukhIME.addLanguage(PramukhIndic, "hindi");
//pramukhIME.enable("careGiverName");
            $("#view_edit_child").hide();
            $(".tabs").tabs();
            dialogueChild();
            $("#accordion").accordion({
                  active : 2
            });
            for ( var i = 1; i < 4; i++) {
                  $("#next-" + i).click(function() {
                        var tabId = $(this).attr("id");
                        var tab = tabId.split("-");
                        
                        if($("#formCareGiver").validationEngine("validate"))
                              $(".tabs").tabs("option", "active", tab[1]);
                        

                  });
            }
            for ( var i = 0; i < 3; i++) {
                  $("#previous-" + i).click(function() {
                        var tabId = $(this).attr("id");
                        var tab = tabId.split("-");
                        $(".tabs").tabs("option", "active", tab[1]);

                  });
            }
            var link = '/motech-platform-server/module/medicmobile/api/deleteCareGiver/';
            dialogueBoxDeleteData(link, {
                  "careGiverID" : '${careGiverView.careGiver.careGiverID}'
            }, "/motech-platform-server/module/medicmobile/api/viewCareGivers");
            var bool;
            var localVolunteerID = "${careGiverView.careGiver.localVolunteerID}";
            $("#idCardType").val("${careGiverView.careGiver.idCardType}");
            $("#localVolunteerID").val("${careGiverView.localVolunteerName}");
            $("#childData").hide();
            $("#delete").hide();
            if ("${careGiverView.careGiver.localVolunteerID}" != null
                        && ("${careGiverView.careGiver.localVolunteerID}").length > 0) {
                  enableLocalVolunteerLabel("${careGiverView.careGiver.localVolunteerID}");
            }

            $("#localVolunteerID").val(localVolunteerID);
            $("#addChild").click(function() {
                  pramukhIME.addLanguage(PramukhIndic, "hindi");
                  pramukhIME.enable("nameInHindi");
                  $("#dialog-form").dialog("open");

            });
            $('#delete').bind('click', function() {
                  $('#dialog-delete').dialog('open');
            });

            $("#hiddenConfirmAnchor").hide();
            $(".ui-datepicker-trigger").addClass("inputField");
            $("#childTable").hide();
            $(".inputField").hide();
            $(".label").hide();
            $("#address").html("${careGiverView.careGiver.address}");
            if ("${careGiverView.careGiver.name}" == '') {
                  pramukhIME.enable("careGiverNameInHindi");
                  $("#idInputBox").attr("disabled", 'disabled');
                  $(".inputField").show();
                  bool = false;

                  $("#editButton").hide();
            } else {
                  $(".label").show();
                  $("#editButton").show();
                  $("#saveForm").hide();
                  bool = false;
                  $("#addChild").hide();
                  $("#delete").show();
            }
            <c:forEach var="child" items="${childList}">
            addChildVariablesExisting(
                        "${child.name}",
                        "${child.nameInHindi}",
                        "${child.gender}",
                        "<fmt:formatDate pattern='dd MMM yyyy' value='${child.dateOfBirth}'/>",
                        "${child.bloodGroup}", "${child.childCalendarID}");
            </c:forEach>
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
            $("#editButton")
                        .click(
                                    function() {
                                          pramukhIME
                                                      .enable("careGiverNameInHindi");
                                          $(".label").hide();
                                          $(".inputField").show();
                                          $("#saveForm").show();
                                          $("#editButton").hide();
                                          $("#delete").hide();
                                          bool = true;
                                          $("#addChild").show();
                                          $("#idCardType").find("${careGiverView.careGiver.idCardType}").attr("selected", true);
                                          if($("#idCardType :selected").val() == 'not_selected'){
                                        	  $("#idInputBox").attr('disabled','true');
                                          }
                                          $("#anganwadiID")
                                                      .val(
                                                                  "${careGiverView.careGiver.anganwadiID}");
                                          var localVolunteerID = "${careGiverView.careGiver.localVolunteerID}";
                                          if (localVolunteerID != "")
                                                setLocalVolunteer(localVolunteerID);
                                          fetchLocalVolunteersWithOutAll();
                                          //$("#localVolunteer").text("${careGiverView.localVolunteerName}");

                                    });
            $("#formCareGiver").validationEngine("attach");

            $(".inputField").change(function() {
                  bool = true;
            });
            $("#dialogMissingDetails").dialog({
                  autoOpen : false
            });
            $("#dialogMissingName").dialog({
                  autoOpen : false
            });
            $("#formCareGiver")
                        .bind(
                                    "submit",
                                    function(event) {
                                          if (($("#anganwadiID").val() == "no_AWC_selected"
                                                      || $("#anganwadiID")
                                                                  .val() == null
                                                      || $("#localVolunteerID")
                                                                  .val() == "" || $(
                                                      "#localVolunteerID")
                                                      .val() == null)
                                                      && (!($("#careGiverName")
                                                                  .val() == "" || !($(
                                                                  "#careGiverName")
                                                                  .val().length > 0)))) {
                                                $("#dialogMissingDetails")
                                                            .dialog("open");
                                                event.preventDefault();
                                                $("#tabs").tabs("option",
                                                            "active", "0");
                                          }
                                          if ($("#careGiverName").val() == ""
                                                      || !($("#careGiverName")
                                                                  .val().length > 0)) {
                                                $("#dialogMissingName").dialog(
                                                            "open");
                                                event.preventDefault();
                                                $("#tabs").tabs("option",
                                                            "active", "0");
                                          }
                                    });
            $(".careGiverName")
                        .keyup(
                                    function() {
                                          console.log($(this).val());
                                          console.log($(this).attr('id'));
                                          if ($(this).attr('id') == "careGiverName") {
                                                var array = $(this).val()
                                                            .split(" ");
                                                var string = "";
                                                for ( var index in array) {
                                                      string = string
                                                                  + pramukhIME
                                                                              .convert(
                                                                                          array[index],
                                                                                          getFromLanguage(),
                                                                                          getToLanguage());
                                                      string = string + " ";
                                                }
                                                $("#careGiverNameInHindi").val(
                                                            string);
                                          } else if ($(this).attr('id') == "careGiverNameInHindi") {
                                                var array = $(this).val()
                                                            .split(" ");
                                                var string = "";
                                                for ( var index in array) {
                                                      string = string
                                                                  + pramukhIME
                                                                              .convert(
                                                                                          array[index],
                                                                                          getToLanguage(),
                                                                                          getFromLanguage());
                                                      string = string + " ";
                                                }
                                                console.log("hindi: " + string);
                                                $("#careGiverName").val(string);
                                          }
                                    });
            $(".careGiverName")
            .change(
                        function() {
                              console.log($(this).val());
                              console.log($(this).attr('id'));
                              if ($(this).attr('id') == "careGiverName") {
                                    var array = $(this).val()
                                                .split(" ");
                                    var string = "";
                                    for ( var index in array) {
                                          string = string
                                                      + pramukhIME
                                                                  .convert(
                                                                              array[index],
                                                                              getFromLanguage(),
                                                                              getToLanguage());
                                          string = string + " ";
                                    }
                                    $("#careGiverNameInHindi").val(
                                                string);
                              } else if ($(this).attr('id') == "careGiverNameInHindi") {
                                    var array = $(this).val()
                                                .split(" ");
                                    var string = "";
                                    for ( var index in array) {
                                          string = string
                                                      + pramukhIME
                                                                  .convert(
                                                                              array[index],
                                                                              getToLanguage(),
                                                                              getFromLanguage());
                                          string = string + " ";
                                    }
                                    console.log("hindi: " + string);
                                    $("#careGiverName").val(string);
                              }
                        });
            $(".childName")
                        .keyup(
                                    function() {
                                          if ($(this).attr('id') == "name") {
                                                var array = $(this).val()
                                                            .split(" ");
                                                var string = "";
                                                for ( var index in array) {
                                                      string = string
                                                                  + pramukhIME
                                                                              .convert(
                                                                                          array[index],
                                                                                          getFromLanguage(),
                                                                                          getToLanguage());
                                                      string = string + " ";
                                                }
                                                $("#nameInHindi").val(string);
                                          } else if ($(this).attr('id') == "nameInHindi") {
                                                var array = $(this).val()
                                                            .split(" ");
                                                var string = "";
                                                for ( var index in array) {
                                                      string = string
                                                                  + pramukhIME
                                                                              .convert(
                                                                                          array[index],
                                                                                          getToLanguage(),
                                                                                          getFromLanguage());
                                                      string = string + " ";
                                                }
                                                $("#name").val(string);
                                          }
                                    });
            $(".childName")
            .change(
                        function() {
                              if ($(this).attr('id') == "name") {
                                    var array = $(this).val()
                                                .split(" ");
                                    var string = "";
                                    for ( var index in array) {
                                          string = string
                                                      + pramukhIME
                                                                  .convert(
                                                                              array[index],
                                                                              getFromLanguage(),
                                                                              getToLanguage());
                                          string = string + " ";
                                    }
                                    $("#nameInHindi").val(string);
                              } else if ($(this).attr('id') == "nameInHindi") {
                                    var array = $(this).val()
                                                .split(" ");
                                    var string = "";
                                    for ( var index in array) {
                                          string = string
                                                      + pramukhIME
                                                                  .convert(
                                                                              array[index],
                                                                              getToLanguage(),
                                                                              getFromLanguage());
                                          string = string + " ";
                                    }
                                    $("#name").val(string);
                              }
                        });
      });
      
      function enableIdInputBox(){
            var idCardString = $("#idCardType").val();
            if(!(idCardString == "not_selected")) {
                  $("#idInputBox").removeAttr('disabled');
            }
            else
                  $("#idInputBox").attr('disabled','true');
      }
</script>
</head>
<body>
	<div id="dialogMissingDetails" title="Unfilled Fields">

		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>Select Anganwadi
			Center and LocalVolunteer
		</p>
	</div>
	<div id="dialogMissingName" title="Unfilled Fields">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>Please enter
			Caregiver name
		</p>
	</div>
	<div id="dialog-confirm" title="Unsaved Date">
		<div id="hiddenConfirmAnchor"></div>
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>Form Contains
			unsaved Data. Sure you want to continue ?
		</p>
	</div>
	<div id="dialog-delete" title="Delete">

		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>Sure you want to
			delete the data.
		</p>
	</div>
	<div id="view_edit_child" title="">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>Can't view/edit
			child until saved to database!
		</p>
	</div>
	<div id="content">
		<div id="Care-giver">
			<div id="dialog-form" title="Add New Child">
				<p class="validateTips"></p>
				<form id="childForm" action="/medicmobile/addChild" method="POST">
					<fieldset>
						<div class="leftDialogue">
							<label for="name">Name</label>
						</div>
						<input type="text" name="name" id="name"
							class="text ui-widget-content ui-corner-all childName" />
						<div class="clear"></div>
						<div class="leftDialogue">
							<label for="nameInHindi">Name in hindi</label>
						</div>
						<input type="text" name="nameInHindi" id="nameInHindi"
							class="text ui-widget-content ui-corner-all childName" />
						<div class="clear"></div>
						<div class="leftDialogue">
							<label for="dateOfBirth" class="date leftDialogue ">DateOfBirth<em
								style="color: red"> *</em></label>
						</div>
						<input type="text" name="dateOfBirth" id="dateOfBirth" value=""
							class="date text ui-widget-content ui-corner-all validate[required,custom[dob]]" />
						<div class="clear"></div>
						<div class="leftDialogue">
							<label for="gender" class="leftDialogue">Gender</label>
						</div>
						<input type="radio" name="gender" id="childGender" value="MALE"
							class="radio" checked="checked">Male <input type="radio"
							name="gender" class="radio" value="FEMALE">Female
						<div class="clear"></div>
						<div class="leftDialogue">
							<label for="bloodGroup" class="leftDialogue">Blood Group</label>
						</div>
						<select id="bloodGroup"><option value="">--select--</option>
							<c:set var="bloodgroup" value="${allbloodgroups}" />
							<c:forEach var="bloodGroup" items="${bloodgroup}">
								<option value="${bloodGroup}">${bloodGroup.value}</option>
							</c:forEach>
						</select>
					</fieldset>
				</form>



			</div>
			<div class="title">
				<div style="float: left" class="inputField">
					<h1>Enroll Mother</h1>
				</div>
				<div style="float: left" class="label">
					<h1>View Mother</h1>
				</div>
				<button class="rightButton button" type="button" id="editButton">Edit</button>
				<button type="button" class="rightButton button" id="delete">Delete</button>
			</div>

			<div class="tabs">
				<ul>
					<li><a href="#tabs-1">General Details</a></li>
					<li><a href="#tabs-2">Children</a></li>
				</ul>
				<form id="formCareGiver"
					action="/motech-platform-server/module/medicmobile/api/CareGiver"
					method="POST">
					<input type="hidden" name="careGiverID"
						value="${careGiverView.careGiver.careGiverID}" />
					<div id="tabs-1">
						<div class="left">
							Anganwadi Center<em style="color: red"> *</em>
						</div>
						<div style="float: left">
							<select id="anganwadiID" name="anganwadiID" class="inputField"
								onchange="fetchLocalVolunteersWithOutAll()">
								<option value="no_AWC_selected">Select AWC</option>
								<c:forEach var="anganwadiCenterView"
									items="${anganwadiCenterList}">
									<option
										value="${anganwadiCenterView.anganwadiCenter.anganwadiID}">${anganwadiCenterView.anganwadiCenter.anganwadiCenterName}</option>
								</c:forEach>
							</select> <label class="label" id="anganwadiLabel"> <c:forEach
									var="anganwadiID"
									items="${careGiverView.careGiver.anganwadiID}">
									<c:forEach var="anganwadiCenterView"
										items="${anganwadiCenterList}">
										<c:if
											test="${anganwadiID == anganwadiCenterView.anganwadiCenter.anganwadiID}">
											<a
												href="/motech-platform-server/module/medicmobile/api/AnganwadiCenter/${careGiverView.careGiver.anganwadiID}">${anganwadiCenterView.anganwadiCenter.anganwadiCenterName}&nbsp;</a>
										</c:if>
									</c:forEach>
								</c:forEach>
							</label>
						</div>
						<div class="clear"></div>
						<div class="left">
							Local Volunteer<em style="color: red"> *</em>
						</div>
						<div style="float: left">

							<select name="localVolunteerID" class="inputField"
								style="margin-right: 20px" id="localVolunteerID">
								<option>Select an AWC above</option>
							</select> <label class="label" id="localVolunteerLabel"></label>
						</div>
						<div class="clear"></div>
						<div class="left">Panchayat/Village</div>
						<input class="inputField validate[custom[onlyLetterSp]]" size="20"
							name="village" value="${careGiverView.careGiver.village}">
						<label class="label">${careGiverView.careGiver.village}</label>
						<div class="clear"></div>

						<div class="left">Para</div>
						<input class="inputField validate[custom[onlyLetterSp]]" size="20"
							name="para" value="${careGiverView.careGiver.para}"> <label
							class="label">${careGiverView.careGiver.para}</label>
						<div class="clear"></div>
						<div class="left">
							<label for="center">Name<em style="color: red"> *</em></label>
						</div>
						<input size="20" name="name"
							class="inputField validate[required, custom[onlyLetterSp]] careGiverName"
							value="${careGiverView.careGiver.name}" id="careGiverName">
						<label class="label">${careGiverView.careGiver.name}</label>
						<div class="clear"></div>
						<div class="clear"></div>
						<div class="left">
							<label for="center">Name in hindi<em style="color: red">
									*</em></label>
						</div>
						<input size="20" name="careGiverNameInHindi"
							class="inputField careGiverName"
							value="${careGiverView.careGiver.nameInHindi}"
							id="careGiverNameInHindi"> <label class="label">${careGiverView.careGiver.nameInHindi}</label>
						<div class="clear"></div>
						<div class="left">Father/spouse name</div>
						<input size="20" name="husbandName"
							class="inputField validate[custom[onlyLetterSp]]"
							value="${careGiverView.careGiver.husbandName}"> <label
							class="label">${careGiverView.careGiver.husbandName}</label>
						<div class="clear"></div>

						<div class="left">Date Of Birth</div>
						<input size="20" name="dateOfBirth"
							class="date inputField validate[custom[dob]]"
							value="<fmt:formatDate value='${careGiverView.careGiver.dateOfBirth}' pattern='dd MMM yyyy'/>" />
						<label class="label"><fmt:formatDate
								value='${careGiverView.careGiver.dateOfBirth}'
								pattern='dd MMM yyyy' /></label>
						<div class="clear"></div>

						<div class="left">ID type</div>
						<select name="idCardType" onchange="enableIdInputBox()"
							id="idCardType" class="inputField"><option
								value="not_selected">--Select --</option>
							<c:set var="idCardType" value="${allIdCardTypes}" />
							<c:forEach var="idCard" items="${idCardType}">
								<option value="${idCard}">${idCard.value}</option>
							</c:forEach>
						</select> <label class="label">${careGiverView.careGiver.idCardType.value}</label>
						<div class="clear"></div>
						<div class="left">ID Number</div>
						<input size="20" name="idCardNumber" id="idInputBox"
							class="inputField validate[custom[onlyLetterNumber]]"
							value="${careGiverView.careGiver.idCardNumber}"> <label
							class="label">${careGiverView.careGiver.idCardNumber}</label>
						<div class="clear"></div>

						<div class="left">Phone Number</div>
						<input size="20" name="phoneNumber"
							class="inputField validate[custom[phone]]"
							value="${careGiverView.careGiver.phoneNumber}"> <label
							class="label">${careGiverView.careGiver.phoneNumber}</label>
						<div class="clear"></div>
						<div class="left">Phone Number of whom</div>
						<input size="20" name="phoneNumberOfWhom"
							class="inputField validate[custom[onlyLetterSp]]"
							value="${careGiverView.careGiver.phoneNumberOfWhom}"> <label
							class="label">${careGiverView.careGiver.phoneNumberOfWhom}</label>
						<div class="clear"></div>

						<div id="childData">

							<%
                                              int i = 0;
                                          %>
							<c:forEach var="child" items="${childList}">
								<input type="text" name="childID<%=i%>" id="childID<%=i%>"
									value="${child.childID}" />
								<input type="text" name="childSystemId<%=i%>"
									id="childSystemId<%=i%>" value="${child.childSystemId}" />
								<%
                                                    i = i + 1;
                                                %>

							</c:forEach>


						</div>
						<div style="float: right">
							<button type="button" class="button" id="next-1">next</button>
						</div>
					</div>
					<div id="tabs-2">
						<table id="childTable">
							<thead>
								<tr>
									<th>Id</th>
									<th>Name</th>
									<th>Date Of Birth</th>
									<th>View/Edit</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						<div class="clear"></div>
						<div class="clear"></div>
						<button type="button" class="button" id="addChild">Add
							Child</button>
						<div style="float: right">
							<button type="button" class="button" id="previous-0">previous</button>
							<button type="submit" class="button" id="saveForm">save</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<footer>
		<jsp:include page="footer.jsp" />
	</footer>
</body>
</html>