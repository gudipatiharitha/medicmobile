<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<script
	src="${pageContext.request.contextPath}/medicmobile/js/validator-en.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/validator.js"></script>
<link
	href="${pageContext.request.contextPath}/medicmobile/css/jquery-ui-1.10.0.custom.css"
	rel="stylesheet" type="text/css" />
<script
	src="${pageContext.request.contextPath}/medicmobile/js/header.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/sidemenu.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/dialogueBox.js"></script>

<script
	src="${pageContext.request.contextPath}/medicmobile/js/pramukhime.js"></script>

<script
	src="${pageContext.request.contextPath}/medicmobile/js/pramukhindic.js"></script>

<script
	src="${pageContext.request.contextPath}/medicmobile/js/dependency.js"></script>

<script
	src="${pageContext.request.contextPath}/medicmobile/js/MasterVaccine.js"></script>

<link
	href="${pageContext.request.contextPath}/medicmobile/css/validator.css"
	rel="stylesheet">
<script>

var bool = false;
$(function(){
      pramukhIME.addLanguage(PramukhIndic, "hindi");
      pramukhIME.enable("nameInHindi");
      var masterVaccineArray = {};
      <c:forEach var = "vaccine" items = "${childView.visitResponses}">
      masterVaccineArray["${vaccine.name}"] = new MasterVaccine("${childView.child.childCalendarID}",
                              "${vaccine.name}",
                              "<fmt:formatDate value='${vaccine.originalAppointmentDueDate}' pattern='dd MMM yyyy' />",
                              "<fmt:formatDate value='${vaccine.visitDate}' pattern='dd MMM yyyy' />",
                              "${vaccine.missed}",
                              "${vaccine.isBeforeCompulsory}", "${vaccine.gapInWeeks}");
      </c:forEach>
      
		$(function(){
		      $("#previous-dosage").dialog({
	                  autoOpen : false
	            });
        	createChildVaccineTable(masterVaccineArray);
        	/*  $(".dateVaccine").each(function(){
                   $(this).keypress(function(event) {event.preventDefault();});;
             }); */
        });
    $(".childName").keyup(function(){
          if($(this).attr('id') == "name") {
                var array = $(this).val().split(" ");
                var string = "";
                for(var index in array){
                      string = string + pramukhIME.convert(array[index], getFromLanguage(), getToLanguage());
                      string = string + " ";
                }
                $("#nameInHindi").val(string);                        
          } else if($(this).attr('id') == "nameInHindi"){
                var array = $(this).val().split(" ");
                var string = "";
                for(var index in array){
                      string = string + pramukhIME.convert(array[index], getToLanguage(), getFromLanguage());
                      string = string + " ";
                }
                $("#name").val(string); 
          }
    });
    $(".childName").change(function(){
          if($(this).attr('id') == "name") {
                var array = $(this).val().split(" ");
                var string = "";
                for(var index in array){
                      string = string + pramukhIME.convert(array[index], getFromLanguage(), getToLanguage());
                      string = string + " ";
                }
                $("#nameInHindi").val(string);                        
          } else if($(this).attr('id') == "nameInHindi"){
                var array = $(this).val().split(" ");
                var string = "";
                for(var index in array){
                      string = string + pramukhIME.convert(array[index], getToLanguage(), getFromLanguage());
                      string = string + " ";
                }
                $("#name").val(string); 
          }
    });

                makeLoginNameAppear("${userName}");
                $(".tabs").tabs();
                $("#accordion").accordion({
                    active : 2
                });
                dialogueVaccine();
               
                $(".hidden").hide();
                $(".vaccineDetails").click(function() {
                    appendVaccineInfo(this.id,$("#"+this.id+'  .vaccineAppointmentDate').html(),$("#"+this.id+'  .vaccineAdministeredDate'));
                    $("#dialog-form").dialog("open");
                
                });
                 $("#hiddenConfirmAnchor").hide();
                
                $("#formChildData .ui-datepicker-trigger").addClass("inputField");
                $(".inputField").hide();
                $(".button").button();
                $("#button1").hide();
                $(".childData").hide();
                $("#editButton")
                .click(
                function() {
                    pramukhIME.addLanguage(PramukhIndic, "hindi");
                    $(".label").hide();
                    bool = true;
                    $(".inputField").show();
                    $("#editButton").hide();
                    $("#delete").hide();
                    $("#button1").show();
                });
                $("#"+"${childView.child.gender}").prop('checked', true);
                $("#bloodGroup").val("${childView.child.bloodGroup}");
                dialogueBoxIfNotSaved();
                    $(".changeWindow").bind("click" , function(event){
                        var id = $(this).attr("id");
                       
                        $("#hiddenConfirmAnchor").text($("#"+id+" a").attr("href"));
                        if(bool){
                            event.preventDefault();
                            $("#dialog-confirm").dialog("open");
                           
                        }
                    });
                     $("#formChildData").validationEngine("attach"); 
                     var link ='/motech-platform-server/module/medicmobile/api/deleteChild/';
                     dialogueBoxDeleteData(link, {"childID":'${childView.child.childID}'}, "/motech-platform-server/module/medicmobile/api/viewCareGivers");       
                     $('#delete').bind('click',function(){
                           $('#dialog-delete').dialog('open');
                     });
                    dialogueBoxIfNotSaved();
            $(".changeWindow").bind("click" , function(event){
                var id = $(this).attr("id");
                $("#hiddenConfirmAnchor").text($("#"+id+" a").attr("href"));
                if(bool){
                    event.preventDefault();
                    $("#dialog-confirm").dialog("open");
                    
                }
            });
            $("#" + "${childView.child.gender}").prop('checked', true);
            $("#bloodGroup").val("${childView.child.bloodGroup}");
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
            $("#formChildData").validationEngine("attach");
            var link = '/motech-platform-server/module/medicmobile/api/deleteChild/';
            dialogueBoxDeleteData(link, {
                  "childID" : '${childView.child.childID}'
            }, "/motech-platform-server/module/medicmobile/api/viewCareGivers");
            $('#delete').bind('click', function() {
                  $('#dialog-delete').dialog('open');
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

      });
      function checkAdd(name, extId) {
            if(checkDependency(name)) {
                  bool = true;
                  if (document.getElementById(name + ".a").checked) {
                        $("#" + name).attr('name', extId + "." + name + ".0");
                        $("." + name).removeAttr('hidden');
                  } else {
                        $("#" + name).attr('name', extId + "." + name + ".1");
                        $("." + name).attr('hidden', true);
                  }     
            } else {
                  document.getElementById(name+".a").checked = false;
            }
            /* 
            bool = true;
            if (document.getElementById(name + ".a").checked) {
                  $("#" + name).attr('name', extId + "." + name + ".0");
                  $("." + name).removeAttr('hidden');
            } else {
                  $("#" + name).attr('name', extId + "." + name + ".1");
                  $("." + name).attr('hidden', true);
            } */
      }
</script>
</head>
<body>
	<div id="dialog-confirm" title="Unsaved Date">
		<div id="hiddenConfirmAnchor"></div>
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>Form Contains
			unsaved Data. Sure you want to Navigate ?
		</p>
	</div>
	<div id="dialog-delete" title="Delete">

		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>Sure you want to
			delete the data.
		</p>
	</div>
	<div id="previous-dosage" title="Previous Dosage">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>
		</p>
	</div>
	<div id="dialog-form">
		<p class="validateTips"></p>

		<div class="leftDialogue">
			<label for="name">Vaccine Name</label>
		</div>
		<label id="name"></label>
		<div class="clear"></div>
		<div class="leftDialogue">
			<label for="dueDate">Due Date</label>
		</div>
		<label id="dueDate"></label>
		<div class="clear"></div>
		<div class="leftDialogue">
			<label for="TakenNot">Administered ?</label>
		</div>
		<input type="radio" name="taken" id="taken" class="radio"
			checked="checked">Yes<input type="radio" name="taken"
			class="radio">No
		<div class="clear"></div>
		<div class="leftDialogue">
			<label for="name">Administered Date</label>
		</div>
		<input id="date" class="date" />
		<div class="clear"></div>

	</div>
	<div id="content">
		<div id="child">

			<div class="title">
				<h1>View Child</h1>
				<button class="rightButton button" type="button" id="editButton">Edit</button>
				<button type="button" class="rightButton button" id="delete">Delete</button>
			</div>
			<div class="tabs">
				<ul>
					<li><a href="#tabs-1">General Details</a></li>
					<li><a href="#tabs-2">Vaccination Details</a></li>
				</ul>
				<div id="tabs-1">
					<div>
						<form
							action="/motech-platform-server/module/medicmobile/api/saveChild"
							method="POST" id="formChildData">
							<div class="left">
								<label for="center">Name</label>
							</div>
							<input class="inputField childName" name="name" id="name"
								value="${childView.child.name}" /> <label class="label">
								${childView.child.name}</label>

							<div class="clear"></div>
							<div class="left">
								<label for="center">Name in hindi</label>
							</div>
							<input class="inputField childName" name="nameInHindi"
								id="nameInHindi" value="${childView.child.nameInHindi}" /> <label
								class="label"> ${childView.child.nameInHindi}</label>

							<div class="clear"></div>
							<div class="left">Date Of Birth</div>
							<input size="20" name="dateOfBirth"
								class="date inputField validate[required]"
								value="<fmt:formatDate value='${childView.child.dateOfBirth}' pattern='dd MMM yyyy'/>">
							<label class="label"><fmt:formatDate
									value='${childView.child.dateOfBirth}' pattern='dd MMM yyyy' /></label>
							<div class="clear"></div>
							<div class="left">Gender</div>
							<input type="radio" name="gender" id="MALE" value="MALE"
								class="radio inputField" checked="checked"><label
								class="inputField"> Male </label><input type="radio"
								name="gender" id="FEMALE" class="radio inputField"
								value="FEMALE"><label class="inputField"> Female</label>
							<label class="label">${childView.child.gender.value}</label>
							<div class="clear"></div>
							<div class="left">
								<label>Blood Group</label>
							</div>
							<select id="bloodGroup" name="bloodGroup" class="inputField"><option
									value="">--select--</option>
								<c:set var="bloodgroup" value="${allbloodgroups}" />
								<c:forEach var="bloodGroup" items="${bloodgroup}">
									<option value="${bloodGroup}">${bloodGroup.value}</option>
								</c:forEach>
							</select> <label class="label">${childView.child.bloodGroup.value}</label>
							<div class="clear"></div>

							<div class="left">CareGiver</div>
							<label><a
								href="/motech-platform-server/module/medicmobile/api/CareGiver/${childView.careGiverView.careGiver.careGiverID}">${childView.careGiverView.careGiver.name}</a></label>
							<div class="clear"></div>
							<div class="hidden">
								<input name="careGiverID" id="careGiverID"
									value="${childView.careGiverView.careGiver.careGiverID}" /> <input
									name="childCalendarID" id="childCalendarID"
									value="${childView.child.childCalendarID}" /> <input
									name="childID" id="childID" value="${childView.child.childID}" />
								<input name="childSystemId" id="childSystemId"
									value="${childView.child.childSystemId}" />
							</div>
							<div id="clear"></div>
							<div style="float: right">
								<button type="submit" id="button1">Save</button>
							</div>
						</form>
					</div>
				</div>
				<div id="tabs-2">
					<h3>Vaccination Details</h3>
					<form
						action="/motech-platform-server/module/medicmobile/api/updateVaccination/${childView.child.childID}"
						method="POST" id="vaccineDetails">
						<div>
							<table class="vaccineScheduleTable">
								<thead>
									<tr>
										<th>Vaccine Name</th>
										<th>Scheduled Date</th>
										<th>Administered Date</th>
										<th>Administered</th>
									</tr>
								</thead>
								<tbody id="childVaccineTableBody">

								</tbody>
							</table>
							<button id="saveVaccines" class="button" type="submit"
								style="float: right">Save</button>
						</div>


					</form>

				</div>

			</div>
		</div>
	</div>
</body>
</html>