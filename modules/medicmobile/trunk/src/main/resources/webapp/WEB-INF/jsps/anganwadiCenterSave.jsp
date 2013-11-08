<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html lang="en">

<head>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>
<script
	src="http://datatables.net/download/build/jquery.dataTables.nightly.js"></script>
<link
	href="${pageContext.request.contextPath}/medicmobile/css/jquery.dataTables.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/medicmobile/css/layout.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/medicmobile/css/jquery-ui-1.10.0.custom.css"
	rel="stylesheet" type="text/css" />
<script
	src="${pageContext.request.contextPath}/medicmobile/js/validator-en.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/validator.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/dialogueBox.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/header.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/sidemenu.js"></script>
<link
	href="${pageContext.request.contextPath}/medicmobile/css/validator.css"
	rel="stylesheet">
<script>$(function(){
     		 makeLoginNameAppear("${userName}");
            $(".inputField").hide();
            $(".label").hide();
            $(" #save").hide();
            $("#state").val("${anganwadiCenter.state}");
            $(".addInput").hide();
            $(".editInput").hide();
            $("#hiddenConfirmAnchor").hide();
            var childPop = "${anganwadiCenter.childPopulation}" == ''?0:"${anganwadiCenter.childPopulation}" ;
            var malePop = "${anganwadiCenter.malePopulation}" == ''?0:"${anganwadiCenter.malePopulation}" ;
            var femalePop = "${anganwadiCenter.femalePopulation}" == ''?0:"${anganwadiCenter.femalePopulation}" ;
            $("#childrenPop").val(childPop);
            $("#malePop").val(malePop);
            $("#femalePop").val(femalePop);
            $("#delete").hide();
            var link = '/motech-platform-server/module/medicmobile/api/deleteAnganwadiCenter/';
            dialogueBoxDeleteData(link, {"anganwadiID":'${anganwadiCenter.anganwadiID}'}, "/motech-platform-server/module/medicmobile/api/viewAnganwadiCenters");       
            var bool;
            if("${anganwadiCenter.anganwadiCenterName}" ==''){ 
                $(".inputField").show();
                $("#editButton").hide();
                bool = false;
                $("#save").show();
                $(".addInput").show();
            }
            else{
                $(".label").show();
                $("#save").hide();
                $("#delete").show();
                bool = false;
                $(".addInput").hide();		
            }
					
            $("#editButton").click(function(){
                $(".label").hide();
                $(".editInput").show();
                $(".inputField").show();
                $("#save").show();
                $("#delete").hide();
                bool = true;             
                $("#editButton").hide();
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
            $('#delete').bind('click',function(){
                  $('#dialog-delete').dialog('open');
            });
            $(".inputField").change(function(){
               
                bool = true;
            });
            $(".basicForm").validationEngine("attach");
        });</script>
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
	<div id="content">
		<div id="anganwadicenter">
			<form class="basicForm"
				action="/motech-platform-server/module/medicmobile/api/AnganwadiCenter"
				method="POST">
				<input type="hidden" name="anganwadiID"
					value="${anganwadiCenter.anganwadiID}" />
				<div class="title">
					<h1 class="addInput">Add Anganwadi Center</h1>
					<h1 class="label">View Anganwadi Center</h1>
					<h1 class="editInput">Edit Anganwadi Center</h1>
					<button type="button" class="rightButton button" id="editButton">Edit</button>
					<button type="button" class="rightButton button" id="delete">Delete</button>
				</div>

				<div class="clear"></div>
				<div>
					<div class="left">
						<label for="center">Anganwadi Center<em style="color: red">
								*</em>
						</label>
					</div>
					<input size="20" name="anganwadiCenterName"
						id="anganwadiCenterNameId"
						class="inputField validate[required,custom[onlyLetterSp]]"
						value="${anganwadiCenter.anganwadiCenterName}"> <label
						class="label" id="anganwadiCenterName"
						title="${anganwadiCenter.anganwadiCenterName}">${anganwadiCenter.anganwadiCenterName}</label>
					<script>$(function(){
                        var name = "${anganwadiCenter.anganwadiCenterName}";
                        if(name.length >= 20){
                            var temp = name.substring(0,17) +"...";
                            $("#anganwadiCenterName").attr("title",name);
                            $("#anganwadiCenterName").text(temp);
                        }
                        else
                            $("#anganwadiCenterName").attr("title","");
                    });
                        </script>
					<div class="clear"></div>
					<div class="left">
						Name of the Village<em style="color: red"> *</em>
					</div>
					<input size="20" name="village"
						class="inputField validate[required,custom[onlyLetterSp]]"
						value="${anganwadiCenter.village[0]}"> <label
						class="label"><c:forEach var="village"
							items="${anganwadiCenter.village}"> ${village}</c:forEach></label>
					<div class="clear"></div>
					<div class="left">Name of the Panchayat</div>
					<input size="20" name="panchayat" class="inputField"
						value="${anganwadiCenter.panchayat}"> <label class="label">${anganwadiCenter.panchayat}</label>
					<div class="clear"></div>
					<div class="left">Sector</div>
					<input size="20" name="sector" class="inputField"
						value="${anganwadiCenter.sector}"> <label class="label">${anganwadiCenter.sector}</label>
					<div class="clear"></div>
					<div class="left">Program</div>
					<input size="20" name="program" class="inputField"
						value="${anganwadiCenter.program}"> <label class="label">${anganwadiCenter.program}</label>
					<div class="clear"></div>
					<div class="left">State</div>
					<select name="state" id="state" class="inputField"><option
							value="Chhattisgarh">Chhattisgarh</option></select> <label class="label">${anganwadiCenter.state}</label>
					<div class="clear"></div>
					<div class="left">District</div>
					<select name="zilla" id="zilla" class="inputField"><option
							value="Bastar">Bastar</option></select> <label class="label">${anganwadiCenter.zilla}</label>
					<div class="clear"></div>
					<div class="left">Para</div>
					<input size="20" name="para" class="inputField"
						value="${anganwadiCenter.para}"> <label class="label">${anganwadiCenter.para}</label>
					<div class="clear"></div>

				</div>
				<fieldset>
					<legend>Population</legend>
					<div class="left legendForm ">Male</div>
					<input size="5" name="malePopulation"
						class="inputField validate[custom[population]]" id="malePop">
					<label class="label">${anganwadiCenter.malePopulation}</label>
					<div class="clear"></div>
					<div class="left legendForm">Female</div>
					<input size="5" name="femalePopulation"
						class="inputField validate[custom[population]]" id="femalePop">
					<label class="label">${anganwadiCenter.femalePopulation}</label>
					<div class="clear"></div>
					<div class="left legendForm">Children</div>
					<input size="5" name="childPopulation"
						class="inputField validate[custom[population]]" id="childrenPop">
					<label class="label">${anganwadiCenter.childPopulation}</label>
					<div class="clear"></div>
				</fieldset>

				<button type="submit" id="save" class="button rightButton">save</button>
			</form>

		</div>

	</div>

</body>
</html>
