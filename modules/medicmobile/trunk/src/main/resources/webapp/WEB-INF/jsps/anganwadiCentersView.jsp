<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List of all Anganwadi Centers</title>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery-1.9.1.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery-ui.js"></script>
<script
	src="${pageContext.request.contextPath}/medicmobile/js/jquery.dataTables.nightly.js"></script>
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
<script>
	$(function() {
		$("#accordion").accordion({
			active : 0
		});
		$("#anganwadiTable .staff").each(function(index1){
		      var number = $(this).children().length;
		      $(this).children().each(function(index2){
		            if(index2 < number - 1){
		            $(this).append(',');}
		      });
		});
		makeLoginNameAppear("${userName}");
	});
</script>
</head>
<body>
	<div id="content">
		<div class="title">
			<h1 style="margin-top: 0">View Anganwadi Centers</h1>
		</div>
		<div class="listView">
			<table id="anganwadiTable">
				<thead>
					<tr>
						<th>Anganwadi Center</th>
						<th>AWW</th>
						<th>ANM</th>
						<th>Local Volunteers</th>
						<th>View Care-givers</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="anganwadiView" items="${anganwadiCenterList}">
						<tr>
							<td><div>
									<a
										href='/motech-platform-server/module/medicmobile/api/AnganwadiCenter/${anganwadiView.anganwadiCenter.anganwadiID}'>${anganwadiView.anganwadiCenter.anganwadiCenterName}</a>
								</div></td>
							<c:if
								test='${anganwadiView.anganwadiWorkerNameIdList[0].name == "Not Available"}'>
								<td><div>N/A</div></td>
							</c:if>
							<c:if
								test='${anganwadiView.anganwadiWorkerNameIdList[0].name !="Not Available"}'>
								<td class="staff"><c:forEach var="anganwadiWorkerView"
										items="${anganwadiView.anganwadiWorkerNameIdList}">
										<span> <a
											href="/motech-platform-server/module/medicmobile/api/staff/${anganwadiWorkerView.id}">
												<c:if test="${anganwadiWorkerView.name == ''}">Unknown</c:if>
												<c:if test="${anganwadiWorkerView.name != ''}">${anganwadiWorkerView.name}</c:if>
										</a>
										</span>
									</c:forEach> <%-- <div>
                                                            <a
                                                                  href="/motech-platform-server/module/medicmobile/api/staff/${anganwadiView.anganwadiWorkerID}">${anganwadiView.anganwadiWorkerName}</a>
                                                      </div> --%></td>
							</c:if>
							<c:if
								test='${anganwadiView.auxilasryNurseNameIdList[0].name == "Not Available"}'>
								<td><div>N/A</div></td>
							</c:if>
							<c:if
								test='${anganwadiView.auxilasryNurseNameIdList[0].name !="Not Available"}'>
								<td class="staff"><c:forEach var="auxilaryNurseView"
										items="${anganwadiView.auxilasryNurseNameIdList}">
										<span> <a
											href="/motech-platform-server/module/medicmobile/api/staff/${auxilaryNurseView.id}">
												<c:if test="${auxilaryNurseView.name == ''}">Unknown</c:if>
												<c:if test="${auxilaryNurseView.name != ''}">${auxilaryNurseView.name}</c:if>
										</a>
										</span>
									</c:forEach> <%-- <div>
                                                            <a
                                                                  href="/motech-platform-server/module/medicmobile/api/staff/${anganwadiView.anganwadiWorkerID}">${anganwadiView.anganwadiWorkerName}</a>
                                                      </div> --%></td>
							</c:if>
							<c:if
								test='${anganwadiView.localVolunteerNameIdList[0].name == "Not Available"}'>
								<td><div>N/A</div></td>
							</c:if>
							<c:if
								test='${anganwadiView.localVolunteerNameIdList[0].name !="Not Available"}'>
								<td class="staff"><c:forEach var="localVolunteerView"
										items="${anganwadiView.localVolunteerNameIdList}">
										<span> <a
											href="/motech-platform-server/module/medicmobile/api/staff/${localVolunteerView.id}">
												<c:if test="${localVolunteerView.name == ''}">Unknown</c:if>
												<c:if test="${localVolunteerView.name != ''}">${localVolunteerView.name}</c:if>
										</a>
										</span>
									</c:forEach> <%-- <div>
                                                            <a
                                                                  href="/motech-platform-server/module/medicmobile/api/staff/${anganwadiView.anganwadiWorkerID}">${anganwadiView.anganwadiWorkerName}</a>
                                                      </div> --%></td>
							</c:if>
							<td><div>
									<a
										href="/motech-platform-server/module/medicmobile/api/viewCareGivers?anganwadiID=${anganwadiView.anganwadiCenter.anganwadiID}">View</a>
								</div></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<script>
				$(function() {
					$("#anganwadiTable").dataTable();
				});
			</script>
		</div>
	</div>
</body>
</html>
