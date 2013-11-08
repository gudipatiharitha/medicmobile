<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="accordion">
	<h3>Manage Anganwadi Centers</h3>
	<div>
		<div class="accordionMenu" id="registerAnganwadiCenter">
			<a href="${pageContext.request.contextPath}/anganwadiCenterForm">Register
				Anganwadi center</a>
		</div>
		<div class="p accordionMenu" id="viewAnganwadiCenter">
			<a href="${pageContext.request.contextPath}/viewAnganwadiCenters">View
				Anganwadi center</a>
		</div>
	</div>
	<h3>Manage Personnels</h3>
	<div>
		<div class="p accordionMenu" id="registerNewPersonnel">
			<a href="${pageContext.request.contextPath}/staff">Register New
				Personnel</a>
		</div>
		<div class="p accordionMenu" id="viewPersonnel">
			<a href="${pageContext.request.contextPath}/viewPersonnel">View
				Personnel</a>
		</div>
	</div>
	<h3>Manage Beneficiaries</h3>
	<div>
		<div class="p accordionMenu" id="enrollMother">
			<a href="${pageContext.request.contextPath}/CareGiver">Enroll
				Mother</a>
		</div>
		<div class="p accordionMenu" id="viewMother">
			<a href="${pageContext.request.contextPath}/viewCareGivers">View
				Mother</a>
		</div>

	</div>
	<h3>Vaccines</h3>
	<div>
		<div id="viewVaccine" class="accordionMenu">
			<a href="${pageContext.request.contextPath}/viewVaccine">View
				Vaccine</a>
		</div>
	</div>
	<h3>Manage SMS</h3>
	<div>
		<div class="p accordionMenu" id="addNewTemplate">
			<a href="#">Add New Template</a>
		</div>

		<div class="p accordionMenu" id="viewTemplate">
			<a href="#">View Template</a>
		</div>

		<div class="p accordionMenu" id="sendSMS">
			<a href="#">Send SMS</a>
		</div>
	</div>

	<h3>View Reports</h3>
	<div>
		<div class="p accordionMenu" id="weeklyReports">
			<a href="${pageContext.request.contextPath}/getWeeklyReport">Weekly
				Reports</a>
		</div>
		<div class="p accordionMenu" id="performanceReports">
			<a href="${pageContext.request.contextPath}/getPerformanceReport">Performance
				Reports</a>
		</div>

	</div>

	<h3>Brochure</h3>
	<div>
		<div class="p accordionMenu" id="Brochure">
			<a href="#">Vaccination</a>
		</div>
		<div class="p accordionMenu" id="sideEffects">
			<a href="#">List of Side Effects</a>
		</div>

	</div>
</div>