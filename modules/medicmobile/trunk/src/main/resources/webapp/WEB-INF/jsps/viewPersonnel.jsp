<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%@ page import="org.medicmobile.model.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html lang="en">

<head>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery-1.9.1.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery-ui.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery.dataTables.nightly.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/viewPersonnel.js"></script>

<script
	src="${pageContext.request.contextPath}/medicmobile/js/header.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/sidemenu.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
	      makeLoginNameAppear("${userName}");
		$("#accordion").accordion({
			active : 1
		});
		anganwadiCenterRelation();
		initialize();
		$("#listPersonnelTable").dataTable({"bPaginate":true, "bLengthChange": false, "bFilter":false});
	});
	makeRolesMap();
	function makeRolesMap() {
		var rolesArray = new Array();
		<c:forEach var="role" items="${rolesMap}">
		rolesArray["${role.key}"] = "${role.value}";
		</c:forEach>
		return rolesArray;
	}
</script>
<script>
	
</script>
<script>
	function anganwadiCenterRelation() {
		anganwadiCenterRelation = new Object();
		<c:forEach var="anganwadiCenterView" items="${anganwadiCenterList}">
		anganwadiCenterRelation["${anganwadiCenterView.anganwadiCenter.anganwadiID}"] = "${anganwadiCenterView.anganwadiCenter.anganwadiCenterName}";
		</c:forEach>;
	}
</script>
<link
	href="${pageContext.request.contextPath}/medicmobile/css/jquery.dataTables.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/medicmobile/css/layout.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/medicmobile/css/jquery-ui-1.10.0.custom.css"
	rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="content">

		<div class="title">
			<h1>View Personnel</h1>
		</div>
		<div class="searchForm">

			<div>
				<form>
					<fieldset>
						<legend>Filter Results</legend>
						<div class="leftHalf">
							<div class="forLabel">Role</div>
							<select id="staffRole" name="role" style="margin-right: 20px">
								<option value="">All</option>
								<c:set var="role" value="${allroles}" />
								<c:forEach var="role" items="${role}">
									<option value="${role}">${role.value}</option>
								</c:forEach>
							</select>
						</div>
						<div class="rightHalf">
							<div class="forLabel">Anganwadi Center</div>
							<select id="staffCenterId" name="anganwadiCenterID"
								style="margin-right: 20px">
								<option value="">All</option>

								<c:forEach var="anganwadiCenterView"
									items="${anganwadiCenterList}">
									<option
										value="${anganwadiCenterView.anganwadiCenter.anganwadiID}">${anganwadiCenterView.anganwadiCenter.anganwadiCenterName}</option>
								</c:forEach>

							</select>
						</div>
						<div class="clear"></div>
						<div class="leftHalf">
							<div class="forLabel">Name</div>
							<input id="staffName" size="20" name="name">
						</div>
						<div class="rightHalf">
							<div class="forLabel">Employee Id</div>
							<input id="staffID" size="20" name="employeeID">
						</div>
					</fieldset>
				</form>


				<div id="searchPersonnel">
					<button style="float: right; margin-bottom: 10px;" class="button">Search</button>
				</div>

			</div>

			<div id="emptyFullPersonnel">
				<table id="listPersonnelTable" style="float: left">
					<thead>
						<tr>
							<!-- <th>Staff Id</th> -->
							<th>Role</th>
							<th>Employee Id</th>
							<th>Name</th>
							<th>Anganwadi Center</th>
							<th>View Caregivers</th>
						</tr>
					</thead>
					<tbody id="personnelList">
						<c:forEach var="staff" items="${staffList}">
							<tr>

								<%-- <td>
                                                      <div>
                                                            <a
                                                                  href="/motech-platform-server/module/medicmobile/api/staff/${staff.staffID}">${staff.staffID}</a>
                                                      </div>
                                                </td> --%>
								<td>
									<div>${staff.role.value}</div>
								</td>
								<td>
									<div>
										<a
											href="/motech-platform-server/module/medicmobile/api/staff/${staff.staffID}">${staff.employeeID}</a>
									</div>
								</td>
								<td>
									<div>
										<a
											href="/motech-platform-server/module/medicmobile/api/staff/${staff.staffID}">${staff.name}</a>
									</div>
								</td>
								<%-- <td>
							<a href='/medicmobile/addStaff?staffID=${staff.staffID}'>view/edit</a>
						</td> --%>
								<td><div>
										<c:forEach var="anganwadiID" items="${staff.anganwadiID}"
											varStatus="status">
											<a
												href="/motech-platform-server/module/medicmobile/api/AnganwadiCenter/${anganwadiID}">
												<c:forEach var="anganwadiCenterView"
													items="${anganwadiCenterList}">
													<c:if
														test="${anganwadiID == anganwadiCenterView.anganwadiCenter.anganwadiID}">
														<c:out
															value="${anganwadiCenterView.anganwadiCenter.anganwadiCenterName}" />
														<span>${status.last ? "" : ","}</span>

													</c:if>
												</c:forEach>
											</a>
										</c:forEach>
									</div></td>
								<td><div>
										<c:if test="${staff.role.value == 'Local Volunteer'}">
											<a
												href="/motech-platform-server/module/medicmobile/api/viewCareGivers?anganwadiID=${staff.anganwadiID[0]}&localVolunteerID=${staff.staffID}">View</a>
										</c:if>
										<c:if test="${staff.role.value !='Local Volunteer'}">
								N/A
							</c:if>
									</div></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

		</div>
	</div>
</body>
</html>