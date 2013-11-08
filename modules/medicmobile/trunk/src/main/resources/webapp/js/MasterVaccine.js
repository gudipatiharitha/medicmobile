function MasterVaccine( childCalendarID, vaccineName, originalDate, visitDate, isMissed, isBeforeCompulsory, gapInWeeks) {
      this.vaccineName = vaccineName;
      this.dosage = getDosage(vaccineName);
      this.previousDosage = getPreviousDosage(vaccineName);
      this.childCalendarID = childCalendarID;
      this.originalDate = new Date(Date.parse(originalDate));
      if(visitDate == null || visitDate == "") {
            this.visitDate = null;
      } else {
            this.visitDate = new Date(Date.parse(visitDate));
      }
      if(isMissed.toLowerCase() == "true") {
            this.isMissed = true;
      } else {
            this.isMissed = false;
      }
      if(isBeforeCompulsory.toLowerCase() == "true") {
            this.isBeforeCompulsory = true;
      } else {
            this.isBeforeCompulsory = false;
      }
      this.gapInWeeks = parseInt(gapInWeeks);
}

function getVaccineName(vaccineNameDosageString) {
      var lastChar = vaccineNameDosageString.substring(
                  vaccineNameDosageString.length - 1, vaccineNameDosageString.length);
      if (isNaN(parseInt(lastChar)) && lastChar != "B") {
            return vaccineNameDosageString;
      } else {
            return vaccineNameDosageString.substring(0,
                        vaccineNameDosageString.length - 1);
      }
}

function getDosage(vaccineNameDosageString) {
      var lastCharacterOfString = vaccineNameDosageString.substring(
                  vaccineNameDosageString.length - 1, vaccineNameDosageString.length);
      if(lastCharacterOfString == 'B') {
            return 4;
      }
      if (isNaN(parseInt(lastCharacterOfString))) {
            return 0;
      } else {
            return parseInt(lastCharacterOfString);
      }
}

function getPreviousDosage(vaccineNameDosageString) {
      var lastCharacterOfString = vaccineNameDosageString.substring(vaccineNameDosageString.length - 1, vaccineNameDosageString.length);
      if(lastCharacterOfString == 'B') {
            return 3;
      }else if (isNaN(parseInt(lastCharacterOfString))
                  || parseInt(lastCharacterOfString) == 0 || vaccineNameDosageString.indexOf("DPT1") != -1 || vaccineNameDosageString.indexOf("DT1") != -1|| vaccineNameDosageString.indexOf("VITA1") != -1) {
            return -1;
      } else {
            return parseInt(lastCharacterOfString) - 1;
      }
}

function getPreviousVaccine(vaccineNameDosageString) {
      if(getPreviousDosage(vaccineNameDosageString) != -1) {
            return getVaccineName(vaccineNameDosageString)+""+getPreviousDosage(vaccineNameDosageString);
      } else {
            return null;
      }
}