<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>
<head>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery-1.9.1.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery-ui-1.10.0.custom.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/dialogueBox.js"></script>
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
	src="${pageContext.request.contextPath}/medicmobile/js/jquery.dataTables.nightly.js"></script>

<script
	src="${pageContext.request.contextPath}/medicmobile/js/header.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/sidemenu.js"></script>

<script
	src="${pageContext.request.contextPath}/medicmobile/js/careGiverTable.js"></script>

<script
	src="${pageContext.request.contextPath}/medicmobile/js/fetchLocalVolunteer.js"></script>
<script type="text/javascript">
            $(function() {
                makeLoginNameAppear("${userName}");
                $("#accordion").accordion({
                    active : 2
                });
                $( "#dialogMissingDetails" ).dialog({
                    autoOpen: false});
                $("#anganwadiID").val("${anganwadiCenterIDSelected}");
                var localVolunteerID = "${localVolunteerSelectedID}";
                if (localVolunteerID != "")
                    setLocalVolunteer(localVolunteerID);
                fetchLocalVolunteers();
                $("#localVolunteer").val("${localVolunteerSelectedID}");
                commaSeperatedChild();
                initDataTables();
            });
            function commaSeperatedChild(){
            	 $("#careGiverTableBody .children").each(function(index1){
				      var number = $(this).children().length;
				      $(this).children().each(function(index2){
				            if(index2 < number - 1){
				                  if($(this).not($("div"))){
				                        $(this).append(',');}				                        
				                  }
				      });
				});         	
            }
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
		<div class="title">
			<h1>View Mothers</h1>
		</div>
		<form>
			<fieldset>
				<legend>Filter Results</legend>

				<div class="rightHalf">
					<div class="forLabel">
						<label for="careGiverName">Name</label>
					</div>
					<input name="careGiverName" id="careGiverName" />
				</div>
				<div class="clear"></div>

				<div class="rightHalf">
					<div class="forLabel">
						<label for="childName">Child Name</label>
					</div>
					<input name="childName" id="childName" />
				</div>
				<div class="leftHalf">
					<div class="forLabel">
						<label for="idCardNumber">ID No</label>
					</div>
					<input name="idCardNumber" id="idCardNumber" />
				</div>
				<div class="clear"></div>
				<div class="leftHalf">
					<div class="forLabel">Anganwadi Center</div>

					<select id="anganwadiID" onchange="fetchLocalVolunteers()"
						name="anganwadiID">
						<option value="all">All AWC</option>
						<c:forEach var="anganwadiCenterView"
							items="${anganwadiCenterList}">
							<option
								value="${anganwadiCenterView.anganwadiCenter.anganwadiID}">${anganwadiCenterView.anganwadiCenter.anganwadiCenterName}</option>
						</c:forEach>
					</select>
				</div>

				<div class="rightHalf">
					<div class="forLabel exceptionLabel">Local Volunteer</div>

					<select name="localVolunteerID" style="margin-right: 20px"
						id="localVolunteerID">
						<option>Select an AWC above</option>
					</select>
				</div>
				<div class="clear"></div>
				<label class="center" for="startDate"><b>Child's Birth
						Date Range:</b></label>
				<div class="clear"></div>
				<div class="leftHalf">
					<div class="forLabel" id="startChildBirthDate">
						<label for="startDate">From</label>
					</div>
					<input name="startDate" id="startDate" class="date" />
				</div>
				<!--  div class="clear"></div>-->
				<div class="rightHalf">
					<div class="forLabel" id="endChildBirthDate">
						<label for="endDate" class=" ">To</label>
					</div>
					<input name="endDate" id="endDate" class="date" />
				</div>
				<div class="clear"></div>

			</fieldset>
		</form>
		<div class="find">

			<input class="button" type="submit" id="find" value="Find"
				onclick="sendAjax()" />
		</div>
		<div class="clear"></div>
		<h2>Results from search</h2>
		<div>
			<table id="listOfCareGiversTable">
				<thead>
					<tr>
						<th>SNo</th>
						<th>ID Number</th>
						<th>Name</th>
						<th>Anganwadi Center</th>
						<th>Children</th>
						<th>Local Volunteer</th>
					</tr>
				</thead>
				<tbody id="careGiverTableBody">
					<c:forEach var="careGiverView" items="${careGiverViewList}">

						<tr>
							<!-- <td class="serialNumber">
                                              </td> -->
							<td class="serialNumber">
								<div></div>
							</td>
							<td class="cardNumber"><div>
									<a
										href="/motech-platform-server/module/medicmobile/api/CareGiver/${careGiverView.careGiver.careGiverID}">${careGiverView.careGiver.idCardNumber}</a>
								</div></td>
							<td class="careGiver"><div>
									<a
										href="/motech-platform-server/module/medicmobile/api/CareGiver/${careGiverView.careGiver.careGiverID}">
										<c:if test="${careGiverView.careGiver.name == ''}">Unknown</c:if>
										<c:if test="${careGiverView.careGiver.name != ''}">${careGiverView.careGiver.name}</c:if>
									</a><input type="hidden"
										value="${careGiverView.careGiver.careGiverID}" />
								</div></td>
							<td class="anganwadiID"><div>
									<c:forEach var="anganwadiID"
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
								</div></td>
							<td class="children"><c:forEach var="child"
									items="${careGiverView.careGiver.children}">
									<c:if test="${not empty child.childID}">
										<span> <a class="childLink"
											href="/motech-platform-server/module/medicmobile/api/viewChild/${child.childID}">
												<c:if test="${child.name == ''}">
			                                        	unknown
			                                        </c:if> <c:if
													test="${child.name != ''}">
			                                        	${child.name}
			                                        </c:if> <input type="hidden"
												value="${child.childID}" />
										</a>
										</span>
									</c:if>
								</c:forEach></td>
							<td><div>
									<a
										href="/motech-platform-server/module/medicmobile/api/staff/${careGiverView.careGiver.localVolunteerID}">
										<c:if test="${careGiverView.localVolunteerName == ''}">Unknown</c:if>
										<c:if test="${careGiverView.localVolunteerName != '' }">${careGiverView.localVolunteerName}</c:if>
									</a>
								</div></td>
						</tr>

					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="clear"></div>
	</div>
</body>

</html>
