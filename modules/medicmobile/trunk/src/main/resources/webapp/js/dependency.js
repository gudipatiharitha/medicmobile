var month=new Array();
month[0]="Jan";
month[1]="Feb";
month[2]="Mar";
month[3]="Apr";
month[4]="May";
month[5]="Jun";
month[6]="Jul";
month[7]="Aug";
month[8]="Sep";
month[9]="Oct";
month[10]="Nov";
month[11]="Dec";

function formatDate(date) {
      var dateTemp = date.getDate();
      var monthTemp = month[date.getMonth()];
      var yearTemp = date.getFullYear();
      return dateTemp+" "+monthTemp+" "+yearTemp;
}

masterVaccineArrayLocal = null;

function createChildVaccineTable(masterVaccineArray) {
      masterVaccineArrayLocal = masterVaccineArray;
      var tbody = $("#childVaccineTableBody").html("");
      for(var vaccineName in masterVaccineArray) {
            var masterVaccineTemp = masterVaccineArray[vaccineName];
            var tRow = "<tr>";
            var vaccineNameDom = "<td class='vaccineName'>"+vaccineName+"</td>";
            var vaccineAppointmentDueDom = "<td class='vaccineAppointmentDate'>"+formatDate(masterVaccineTemp.originalDate)+"</td>";
            var vaccineAdministeredDom = getAdministeredDom(masterVaccineTemp);
            var checkBoxDom = getCheckBoxDom(masterVaccineTemp);
            tRow =  tRow +""+ vaccineNameDom +""+ vaccineAppointmentDueDom +""+ vaccineAdministeredDom +""+ checkBoxDom + "</tr>";
            tbody.append(tRow);
      }
      $(".vaccineScheduleTable").dataTable(
                  {
                      "bsort":false,
                      "bAutoWidth":false,
                      "bPaginate": false,
                      "aaSorting": [],
                  });
      $(".dateVaccine").each(function(){
            checkDependencyInternal($(this).attr("id"));
      });
}

function getAdministeredDom(masterVaccine) {
      var dom = "<td>";
      if(masterVaccine.visitDate == null) {
            if(masterVaccine.isMissed) {
                  return dom + "<label>missed</label>";
            } else {
                 dom = dom + "<div class=' administeredDate "+masterVaccine.vaccineName+"' hidden='true'><input id='"+masterVaccine.vaccineName+"'  class='dateVaccine'  onchange='checkAdd(\""+masterVaccine.vaccineName+"\",\""+masterVaccine.childCalendarID+"\")'></div>"; 
            }
      } else {
            dom = dom + "<div class=' administeredDate"+masterVaccine.vaccineName+"'><input id='"+masterVaccine.vaccineName+"'  class='dateVaccine' onchange='checkAdd(\""+masterVaccine.vaccineName+"\",\""+masterVaccine.childCalendarID+"\")' value= '"+formatDate(masterVaccine.visitDate)+"'</div>";
      }
      dom = dom + "</td>";
      return dom;
}

function getCheckBoxDom(masterVaccine) {
      var dom = "<td>";
      if(masterVaccine.visitDate == null) {
            if(masterVaccine.isMissed) {
                  dom = dom + "<input id='"+masterVaccine.vaccineName+".a' type='checkbox' onchange='checkAdd(\""+masterVaccine.vaccineName+"\",\""+masterVaccine.childCalendarID+"\")'   disabled='disabled'>";
            } else {
                  dom = dom + "<input id='"+masterVaccine.vaccineName+".a' type='checkbox' onchange='checkAdd(\""+masterVaccine.vaccineName+"\",\""+masterVaccine.childCalendarID+"\")'>";
            }
      } else {
            dom = dom + "<input id='"+masterVaccine.vaccineName+".a' type='checkbox' checked = 'checked' onchange='checkAdd(\""+masterVaccine.vaccineName+"\",\""+masterVaccine.childCalendarID+"\")'>";
      }
      dom = dom + "</td>";
      return dom;
}

function createDatePickerWithMinimumDate(selector, minimumDate) {
      $("#"+selector).datepicker("destroy");
      if($("#"+selector).val() == null || $("#"+selector).val() == ""){
            $("#"+selector).datepicker({
                              dateFormat : 'dd M yy',
                              changeMonth : true,
                              changeYear : true,
                              showOn : "button",
                              buttonImage : "/motech-platform-server/module/medicmobile/images/calendar.gif",
                              buttonImageOnly : true,
                              minDate: minimumDate,
                              defaultDate:minimumDate,
                              onChangeMonthYear: function(year, month, inst){
                                    var date = $(this).datepicker("getDate");
                                    if(date != null) {
                                          date.setFullYear(year);
                                          date.setMonth(month - 1);
                                          $(this).datepicker( "setDate", date );
                                    }
                                 },
                        });
            $("#"+selector).datepicker("setDate", minimumDate);
      } else {
            $("#"+selector).datepicker(
                        {
                              dateFormat : 'dd M yy',
                              changeMonth : true,
                              changeYear : true,
                              showOn : "button",
                              buttonImage : "/motech-platform-server/module/medicmobile/images/calendar.gif",
                              buttonImageOnly : true,
                              constrainInput:false,
                              minDate: minimumDate,
                              defaultDate:$("#"+selector).val(),
                              onChangeMonthYear: function(year, month, inst){
                                    var date = $(this).datepicker("getDate");
                                    if(date != null) {
                                          date.setFullYear(year);
                                          date.setMonth(month - 1);
                                          $(this).datepicker( "setDate", date );
                                    }
                                 },
                        });
            //$("#"+selector).datepicker("setDate", $("#"+selector).val());
      } 
}

function addWeeks(date, weekNumber) {
      date.setDate(date.getDate() + weekNumber * 7)
      return date;
}

function checkDependencyInternal(vaccineName) {
      var masterVaccineTemp = masterVaccineArrayLocal[vaccineName];
      var previousMasterVaccine = getPreviousVaccine(vaccineName);
      var previousMasterVaccineTemp = null;
      if($("#"+vaccineName).val() != "" && $("#"+vaccineName).val() != null) {
          if(vaccineName.indexOf("DT1") != -1) {
                 previousMasterVaccineTemp = masterVaccineArrayLocal["DPT1"];
                 if(!previousMasterVaccineTemp.isMissed) {
                 } else if(previousMasterVaccineTemp.isMissed){
                       console.log("first");
                       createDatePickerWithMinimumDate(vaccineName, addWeeks(masterVaccineTemp.originalDate, masterVaccineTemp.gapInWeeks));
                       //masterVaccineTemp.changed = true;
                       return true;
                 }
          } else {
                previousMasterVaccineTemp = masterVaccineArrayLocal[previousMasterVaccine]
          }
          if(previousMasterVaccineTemp != null) {
                if(masterVaccineTemp.isBeforeCompulsory) {
                      if(previousMasterVaccineTemp.visitDate != null) {
                            console.log("second");
                            var tempDate = addWeeks(previousMasterVaccineTemp.visitDate, masterVaccineTemp.gapInWeeks);
                            console.log(tempDate);
                            if(masterVaccineTemp.originalDate < tempDate){
                                  createDatePickerWithMinimumDate(vaccineName, masterVaccineTemp.originalDate);
                            } else {
                                  createDatePickerWithMinimumDate(vaccineName, tempDate);
                            }
                            
                            //masterVaccineTemp.changed = true;
                      } else {
                      }
                } else {
                      if(previousMasterVaccineTemp.visitDate == null || previousMasterVaccineTemp.isMissed) {
                            console.log("third");
                            var tempTime = addWeeks(previousMasterVaccineTemp.originalDate, masterVaccineTemp.gapInWeeks);
                            if(tempTime < masterVaccineTemp.originalDate) {
                                  createDatePickerWithMinimumDate(vaccineName, masterVaccineTemp.originalDate);
                            } else {
                                  createDatePickerWithMinimumDate(vaccineName, tempTime);
                            }
                            //masterVaccineTemp.changed = true;
                      } else {
                            console.log("fourth");
                            var tempDate = addWeeks(previousMasterVaccineTemp.visitDate, masterVaccineTemp.gapInWeeks);
                            if(tempDate < masterVaccineTemp.originalDate){
                                  createDatePickerWithMinimumDate(vaccineName, masterVaccineTemp.originalDate);  
                            } else {
                                  createDatePickerWithMinimumDate(vaccineName, tempDate); 
                            }
                            //createDatePickerWithMinimumDate(masterVaccineTemp.vaccineName, addWeeks(previousMasterVaccineTemp.visitDate, masterVaccineTemp.gapInWeeks));
                           // masterVaccineTemp.changed = true;
                      }
                }
          } else {
                console.log("previous master vaccine null");
                createDatePickerWithMinimumDate(vaccineName, masterVaccineTemp.originalDate);
          }
      }
      return true;
}

function checkDependency(vaccineName) {
      console.log("INside checkDependency");
      var masterVaccineTemp = masterVaccineArrayLocal[vaccineName];
      var previousMasterVaccine = getPreviousVaccine(vaccineName);
      var previousMasterVaccineTemp = null;
      if(!masterVaccineTemp.changed) {
          if(vaccineName.indexOf("DT1") != -1) {
                 previousMasterVaccineTemp = masterVaccineArrayLocal["DPT1"];
                 if(!previousMasterVaccineTemp.isMissed) {
                       $("#previous-dosage p").text("Please note  "+previousMasterVaccineTemp.vaccineName+"  can still be given");
                       $("#previous-dosage").dialog("open");
                       return false;
                 } else if(previousMasterVaccineTemp.isMissed){
                       console.log("first");
                       createDatePickerWithMinimumDate(vaccineName, addWeeks(masterVaccineTemp.originalDate, masterVaccineTemp.gapInWeeks));
                       masterVaccineTemp.changed = true;
                       return true;
                 }
          } else {
                previousMasterVaccineTemp = masterVaccineArrayLocal[previousMasterVaccine]
          }
          if(previousMasterVaccineTemp != null) {
                if(masterVaccineTemp.isBeforeCompulsory) {
                      if(previousMasterVaccineTemp.visitDate != null) {
                            console.log("second");
                            var tempDate = addWeeks(previousMasterVaccineTemp.visitDate, masterVaccineTemp.gapInWeeks);
                            console.log(tempDate);
                            if(masterVaccineTemp.originalDate < tempDate){
                                  createDatePickerWithMinimumDate(vaccineName, tempDate);
                            } else {
                                  createDatePickerWithMinimumDate(vaccineName, masterVaccineTemp.originalDate);
                            }
                            masterVaccineTemp.changed = true;
                      } else {
                            $("#previous-dosage p").text("Please administer "+previousMasterVaccineTemp.vaccineName+" before this vaccine");
                            $("#previous-dosage").dialog("open");
                            return false;
                      }
                } else {
                      if(previousMasterVaccineTemp.visitDate == null || previousMasterVaccineTemp.isMissed) {
                            console.log("third");
                            var tempTime = addWeeks(previousMasterVaccineTemp.originalDate, masterVaccineTemp.gapInWeeks);
                            if(tempTime < masterVaccineTemp.originalDate) {
                                  createDatePickerWithMinimumDate(vaccineName, masterVaccineTemp.originalDate);
                            } else {
                                  createDatePickerWithMinimumDate(vaccineName, tempTime);
                            }
                            masterVaccineTemp.changed = true;
                      } else {
                            console.log("fourth");
                            var tempDate = addWeeks(previousMasterVaccineTemp.visitDate, masterVaccineTemp.gapInWeeks);
                            if(tempDate < masterVaccineTemp.originalDate){
                                  createDatePickerWithMinimumDate(vaccineName, masterVaccineTemp.originalDate);  
                            } else {
                                  createDatePickerWithMinimumDate(vaccineName, tempDate); 
                            }
                            //createDatePickerWithMinimumDate(masterVaccineTemp.vaccineName, );
                            masterVaccineTemp.changed = true;
                      }
                }
          } else {
                console.log("previous master vaccine null");
                createDatePickerWithMinimumDate(vaccineName, masterVaccineTemp.originalDate);
          }
      }
      return true;
}