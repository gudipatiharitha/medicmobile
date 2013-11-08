var performanceReportString;
var totalVaccine = 0;
var totalMissed = 0;
function callAjax()
{
	var anganwadiID = $("#anganwadiID").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	if(new Date(startDate) > new Date(endDate)){
	      $("#dialogMissingDetails").dialog("open");
	}
	else
	{  $.ajax({
		type : 'POST',
		url : 'performanceReport' ,
		data : {
			"anganwadiID":anganwadiID,
			"startDate":startDate,
			"endDate":endDate
		},
		success : function(dataReceived) {
		    totalVaccine = 0;
		    totalMissed = 0;
			showPerformanceReport(dataReceived);
		},
		error : function(r, ts, e) {
			
		}
	});
	}
}
function showPerformanceReport(performanceReport){
	$("#performanceReport").empty();
	/*for(var i=0;i<performanceReport.reportAccordions.length;i++)
    {
        nosUpdates=nosUpdates+performanceReport.reportAccordions[i].nosUpdates;
        nosUpdatesSuccessful=nosUpdatesSuccessful+performanceReport.reportAccordions[i].nosUpdatesSuccessful;
        nosUpdatesUnsuccessful=nosUpdatesUnsuccessful+performanceReport.reportAccordions[i].nosUpdatesUnsuccessful;
        nosSMSReceived=nosSMSReceived+performanceReport.reportAccordions[i].nosSMSReceived;
    }*/
	$("#performanceReport").append("<h2>Anganwadi Center &nbsp;&nbsp;"+performanceReport.anganwadiCenter.anganwadiCenterName+"</h2>");
	$("#performanceReport").append("<div class='performancedate'><b><div class='leftdate'>From:</div><div class=rightdate>"+$("#startDate").val()+"</div><div class='clear'/></b></div>");
	$("#performanceReport").append("<div class='performancedate'><b><div class='leftdate'>To:</div><div class=rightdate>"+$("#endDate").val()+"</div><div class='clear'/></b></p>");
	performanceReportString = "<div class='accordion'>";
	performanceReportString += "<h3>Aggregate</h3>";
	performanceReportString += "<div>";
	performanceReportString += "<fieldset>";
	performanceReportString += "<legend>";
	performanceReportString += "Vaccination Details";
	performanceReportString += "</legend>";
	performanceReportString +="<div><table class='table'>";
	
	performanceReportString +="<thead><th>Vaccine</th><th>Nos Due</th><th>Nos Completed</th><th>Nos Missed</th><th>% Covered</th></thead><tbody>";
	for(var i=0;i<performanceReport.anganwadiCenterReportAccodion.vaccines.length;i++)
    {
	        if (typeof performanceReport.anganwadiCenterReportAccodion.vaccines[i].nosTotal !== 'undefined')
	              totalVaccine += performanceReport.anganwadiCenterReportAccodion.vaccines[i].nosTotal;
            else 
                  totalVaccine = 0;
            if (typeof  performanceReport.anganwadiCenterReportAccodion.vaccines[i].nosMissed !== 'undefined')
                  totalMissed  += performanceReport.anganwadiCenterReportAccodion.vaccines[i].nosMissed;
            else 
                  totalMissed = 0;
    }
	if(totalVaccine != 0)
          performanceReportString +="<tr><td>Aggregate</td><td>"+totalVaccine+"</td><td>"+(totalVaccine-totalMissed)+"</td><td>"+totalMissed+"</td><td>"+(((totalVaccine-totalMissed)*100/totalVaccine)).toFixed(2)+"</td></tr>";
    else 
          performanceReportString +="<tr><td>Aggregate</td><td>"+totalVaccine+"</td><td>"+(totalVaccine-totalMissed)+"</td><td>"+totalMissed+"</td><td>"+(0).toFixed(2)+"</td></tr>";
    
	for(var i=0;i<performanceReport.anganwadiCenterReportAccodion.vaccines.length;i++)
	{
		   
	      addVaccineToTable(performanceReport.anganwadiCenterReportAccodion.vaccines[i]);
	      
		
	}
	
	performanceReportString += "</tbody></table></div>";
	performanceReportString += "</fieldset>";
	var nosUpdates=0;
    var nosUpdatesSuccessful=0;
    var nosUpdatesUnsuccessful=0;
    var nosSMSReceived=0;
    //console.log(performanceReport.reportAccordions);
	for(var i=0;i<performanceReport.reportAccordions.length;i++)
    {
	     nosUpdates +=performanceReport.reportAccordions[i].nosUpdates;
	     //console.log(" index "+i+" => "+performanceReport.reportAccordions[i].nosUpdatesSucessful)
	     nosUpdatesSuccessful += performanceReport.reportAccordions[i].nosUpdatesSuccessful;
	     nosUpdatesUnsuccessful += performanceReport.reportAccordions[i].nosUpdatesUnsuccessful;
	     nosSMSReceived += performanceReport.reportAccordions[i].nosSMSReceived;
       
    }
	
	performanceReportString += "<fieldset>";
	performanceReportString += "<legend>";
	performanceReportString += "SMS Details";
	performanceReportString += "</legend>";
	performanceReportString += "<div class='left'><h4>No. of updates done:</h4></div><h4>";
	performanceReportString += nosUpdates;
	performanceReportString += "</h4><div class='clear'></div>";
	performanceReportString += "<div class='left'><h4>No. of successful Updates:</h4></div><h4>";
	performanceReportString += nosUpdatesSuccessful;
	performanceReportString += "</h4><div class='clear'></div>";
	performanceReportString += "<div class='left'><h4>No. of unsuccessful Updates:</h4></div><h4>";
	performanceReportString += nosUpdatesUnsuccessful;
	performanceReportString += "</h4><div class='clear'></div>";
	performanceReportString += "<div class='left'><h4>No. of received messages:</h4></div><h4>";
	performanceReportString += nosSMSReceived;
    performanceReportString += "</h4>";
	performanceReportString += "</fieldset>";
	performanceReportString += "</div>";
	for(var i=0;i<performanceReport.reportAccordions.length;i++)
	{
		addPerformanceReportAccordions(performanceReport.reportAccordions[i]);
	}
	performanceReportString += "</div>";
	$("#performanceReport").append(performanceReportString);
	$(".accordion").accordion({heightStyle:"content"});
	$(".table").dataTable({"sEmptyTable": "no child due for vaccination"});
	//$(".table").dataTable().fnAddData(["Aggregate", totalVaccine, totalVaccine - totalMissed, totalMissed, (((totalVaccine - totalMissed)*100)/totalVaccine).toFixed(2)]);
}
function addPerformanceReportAccordions(reportAccrodion)
{
	performanceReportString += "<h3>"+reportAccrodion.name+"</h3>";
	performanceReportString += "<div>";
	addDataToPerformance(reportAccrodion);
	performanceReportString += "</div>";
}
function addDataToPerformance(reportAccrodion)
{
	addVaccinationDetails(reportAccrodion);
	addSMSDetails(reportAccrodion);
	
}
function addVaccinationDetails(reportAccrodion)
{
	performanceReportString += "<fieldset>";
	performanceReportString += "<legend>";
	performanceReportString += "Vaccination Details";
	performanceReportString += "</legend>";
	performanceReportString +="<div><table class='table'>";
	performanceReportString +="<thead><th>Vaccine</th><th>Nos Due</th><th>Nos Completed</th><th>Nos Missed</th><th>% Covered</th></thead><tbody>";
	totalMissed = 0;
	totalVaccine = 0;
	for(var i=0;i<reportAccrodion.vaccines.length;i++)
    {
	      if (typeof reportAccrodion.vaccines[i].nosTotal !== 'undefined')
	            totalVaccine += reportAccrodion.vaccines[i].nosTotal;
	      else 
	            totalVaccine = 0;
	      if (typeof reportAccrodion.vaccines[i].nosMissed !== 'undefined')
	            totalMissed += reportAccrodion.vaccines[i].nosMissed;
          else 
                totalMissed = 0;
    }
	if(totalVaccine != 0)
	      performanceReportString +="<tr><td>Aggregate</td><td>"+totalVaccine+"</td><td>"+(totalVaccine-totalMissed)+"</td><td>"+totalMissed+"</td><td>"+(((totalVaccine-totalMissed)*100/totalVaccine)).toFixed(2)+"</td></tr>";
	else 
	      performanceReportString +="<tr><td>Aggregate</td><td>"+totalVaccine+"</td><td>"+(totalVaccine-totalMissed)+"</td><td>"+totalMissed+"</td><td>"+(0).toFixed(2)+"</td></tr>";
    
	for(var i=0;i<reportAccrodion.vaccines.length;i++)
	{
		addVaccineToTable(reportAccrodion.vaccines[i]);
	}
	performanceReportString += "</tbody></table></div>";
	performanceReportString += "</fieldset>";
}
function addSMSDetails(reportAccrodion)
{
	performanceReportString += "<fieldset>";
	performanceReportString += "<legend>";
	performanceReportString += "SMS Details";
	performanceReportString += "</legend>";
	performanceReportString += "<div class='left'><h4>No. of updates done:</h4></div><h4>";
	performanceReportString += reportAccrodion.nosUpdates;
	performanceReportString += "</h4><div class='clear'></div>";
	performanceReportString += "<div class='left'><h4>No. of successful Updates:</h4></div><h4>";
	performanceReportString += reportAccrodion.nosUpdatesSuccessful;
	performanceReportString += "</h4><div class='clear'></div>";
	performanceReportString += "<div class='left'><h4>No. of unsuccessful Updates:</h4></div><h4>";
	performanceReportString += reportAccrodion.nosUpdatesUnsuccessful;
	performanceReportString += "</h4><div class='clear'></div>";
	performanceReportString += "<div class='left'><h4>No. of received messages:</h4></div><h4>";
	performanceReportString += reportAccrodion.nosSMSReceived;
	performanceReportString += "</h4>";
	performanceReportString += "</fieldset>";
}
function addVaccineToTable(reportVaccine)
{   
    
	performanceReportString += '<tr>';
	performanceReportString += '<td>';
	performanceReportString += reportVaccine.vaccineName;
	performanceReportString += '</td>';
	performanceReportString += '<td>';
	performanceReportString += reportVaccine.nosTotal;
//	totalVaccine += reportVaccine.nosTotal;
	performanceReportString += '</td>';
	performanceReportString += '<td>';
	performanceReportString += reportVaccine.nosReceived;
	performanceReportString += '</td>';
	performanceReportString += '<td>';
	performanceReportString += reportVaccine.nosMissed;
//	totalMissed += reportVaccine.nosMissed;
	performanceReportString += '</td>';
	performanceReportString += '<td>';
	performanceReportString += ((reportVaccine.nosReceived/reportVaccine.nosTotal)*100).toFixed(2);
	performanceReportString += '</td>';
	performanceReportString += '</tr>';	
}