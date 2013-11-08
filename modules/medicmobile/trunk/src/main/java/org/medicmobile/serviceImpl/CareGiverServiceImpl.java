package org.medicmobile.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.medicmobile.dto.CareGiverSearch;
import org.medicmobile.model.CareGiver;
import org.medicmobile.model.CareGiverView;
import org.medicmobile.model.Child;
import org.medicmobile.model.Staff;
import org.medicmobile.repository.CareGiverRepository;
import org.medicmobile.repository.ChildRepository;
import org.medicmobile.repository.StaffRepository;
import org.medicmobile.service.CareGiverService;
import org.medicmobile.service.ChildService;
import org.medicmobile.service.VaccineService;
import org.medicmobile.util.MedicMobileConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class CareGiverServiceImpl.
 */
@Service(value = "careGiverService")
public class CareGiverServiceImpl implements CareGiverService {

      /** The logger. */
      private static Logger logger = Logger.getLogger(CareGiverService.class);

      /** The care giver repo. */
      @Resource(name = "careGiverRepo")
      private CareGiverRepository careGiverRepo;

      /** The staff repo. */
      @Resource(name = "staffRepo")
      private StaffRepository staffRepo;

      /** The child repo. */
      @Resource(name = "childRepo")
      private ChildRepository childRepo;

      @Autowired
      private VaccineService vaccineService;

      @Autowired
      private ChildService childService;

      /** The care giver temp. */
      private CareGiver careGiverTemp;

      /** The care giver index. */
      private int careGiverIndex;

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.CareGiverService#addCareGiver(org.medicmobile
       * .model.CareGiver)
       */
      public CareGiverView addCareGiver(CareGiver careGiver) {
            List<CareGiver> careGiverBeforeSaveList = careGiverRepo
                        .findByCareGiverIDS(careGiver.getCareGiverID());
            if (!careGiverBeforeSaveList.isEmpty()) {
                  String localVolunteerID = careGiverBeforeSaveList.get(0)
                              .getLocalVolunteerID();
                  String angawadiID = careGiverBeforeSaveList.get(0)
                              .getAnganwadiID();
                  vaccineService.changeVolunteerDetailsForChildSchedule(
                              careGiver, localVolunteerID, angawadiID);
            }

            CareGiver careGiverNew = careGiverRepo.addCareGiver(careGiver);

            List<CareGiver> careGiverList = new ArrayList<CareGiver>();

            careGiverList.add(careGiverNew);

            return this.getListOfCareGiverView(careGiverList).get(0);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.CareGiverService#update(org.medicmobile.model
       * .CareGiver)
       */
      public void update(CareGiver careGiver) {
            careGiverRepo.update(careGiver);
      }

      /**
       * Gets the all care giver views.
       * 
       * @return the all care giver views
       */
      public List<CareGiverView> getAllCareGiverViews() {

            return this.getListOfCareGiverView(careGiverRepo.getAll());
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.CareGiverService#findByCareGiverID(java.lang.
       * String)
       */
      public CareGiverView findByCareGiverID(String careGiverID) {
            List<CareGiverView> careGiverList = this
                        .getListOfCareGiverView(careGiverRepo
                                    .findByCareGiverIDS(careGiverID));
            if (!careGiverList.isEmpty()) {
                  return this.getListOfCareGiverView(
                              careGiverRepo.findByCareGiverIDS(careGiverID))
                              .get(0);
            } else {
                  return new CareGiverView(new CareGiver(), "", "");
            }

      }

      public CareGiver getByCareGiverID(String careGiverID) {
            List<CareGiver> careGivers = careGiverRepo
                        .findByCareGiverIDS(careGiverID);
            if (careGivers.isEmpty()) {
                  return new CareGiver();
            } else {
                  return careGivers.get(0);
            }
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.CareGiverService#findByIDCardNumber(java.lang
       * .String)
       */
      public List<CareGiverView> findByIDCardNumber(String idCardNumber) {
            return this.getListOfCareGiverView(careGiverRepo
                        .findByIDCardNumber(idCardNumber));
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.CareGiverService#findByCareGiverName(java.lang
       * .String)
       */
      public List<CareGiverView> findByCareGiverName(String careGiverName) {
            return this.getListOfCareGiverView(careGiverRepo
                        .findByName(careGiverName));
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.CareGiverService#findByVillage(java.lang.String
       * )
       */
      public List<CareGiverView> findByVillage(String village) {
            return this.getListOfCareGiverView(careGiverRepo
                        .findByVillage(village));
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.CareGiverService#findByChildBirthDate(java.util
       * .Date, java.util.Date)
       */
      public List<CareGiverView> findByChildBirthDate(Date startDate,
                  Date endDate) {
            return this.getListOfCareGiverView(careGiverRepo
                        .findByChildBirthDate(startDate, endDate));
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.CareGiverService#findByChildName(java.lang.
       * String )
       */
      public List<CareGiverView> findByChildName(String childName) {
            return this.getListOfCareGiverView(careGiverRepo
                        .findByChildName(childName));
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.CareGiverService#findByLocalVolunteerID(java.
       * lang.String)
       */
      public List<CareGiverView> findByLocalVolunteerID(String localVolunteerID) {
            return this.getListOfCareGiverView(careGiverRepo
                        .findByLocalVolunteerID(localVolunteerID));
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.CareGiverService#findByAnganwadiID(java.lang.
       * String)
       */
      public List<CareGiverView> findByAnganwadiID(String anganwadiID) {
            return this.getListOfCareGiverView(careGiverRepo
                        .findByAnganwadiCenterID(anganwadiID));
      }

      /*
       * (non-Javadoc)
       * 
       * @see org.medicmobile.service.CareGiverService#findByAllConstraints(org.
       * medicmobile .dto.CareGiverSearch)
       */
      public List<CareGiverView> findByAllConstraints(
                  CareGiverSearch careGiverSearch) {

            Map<String, List<CareGiver>> careGiverMapFromRepo = careGiverRepo
                        .findByAllConstraints(careGiverSearch);

            if (careGiverMapFromRepo
                        .containsKey(MedicMobileConstants.CAREGIVER_ID)) {
                  return this.getListOfCareGiverView(careGiverMapFromRepo
                              .get(MedicMobileConstants.CAREGIVER_ID));
            } else if (careGiverMapFromRepo
                        .containsKey(MedicMobileConstants.ID_CARD_NUMBER)) {
                  return this.getListOfCareGiverView(findByAllConstraintsByIDCardNumber(
                              careGiverMapFromRepo, careGiverSearch));
            } else if (careGiverMapFromRepo
                        .containsKey(MedicMobileConstants.VILLAGE)) {
                  return this.getListOfCareGiverView(careGiverMapFromRepo
                              .get(MedicMobileConstants.VILLAGE));
            } else if (careGiverMapFromRepo
                        .containsKey(MedicMobileConstants.CHILD_BIRTH_DATE)) {
                  return this.getListOfCareGiverView(findByAllConstraintsChildBirthDate(
                              careGiverMapFromRepo, careGiverSearch));
            } else if (careGiverMapFromRepo
                        .containsKey(MedicMobileConstants.CHILD_NAME)) {

                  return this.getListOfCareGiverView(findByAllConstraintsChildName(
                              careGiverMapFromRepo, careGiverSearch));
            } else if (careGiverMapFromRepo
                        .containsKey(MedicMobileConstants.CARE_GIVER_NAME)) {
                  return this.getListOfCareGiverView(findByAllConstraintsCareGiverName(
                              careGiverMapFromRepo, careGiverSearch));
            } else if (careGiverMapFromRepo
                        .containsKey(MedicMobileConstants.LOCAL_VOLUNTEER)) {
                  return this.getListOfCareGiverView(this
                              .findByAllConstraintsLocalVolunteer(
                                          careGiverMapFromRepo, careGiverSearch));
            } else if (careGiverMapFromRepo
                        .containsKey(MedicMobileConstants.ANGANWADI_CENTER)) {
                  return this.getListOfCareGiverView(this
                              .findByAllConstraintsAnganwadiCenter(
                                          careGiverMapFromRepo, careGiverSearch));
            } else if (careGiverMapFromRepo
                        .containsKey(MedicMobileConstants.ALL)) {
                  return this.getAllCareGiverViews();
            }

            return null;
      }

      /**
       * Find by all constraints by id card number.
       * 
       * @param careGiverMapFromRepo
       *              the care giver map from repo
       * @param careGiverSearch
       *              the care giver search
       * @return the list
       */
      private List<CareGiver> findByAllConstraintsByIDCardNumber(
                  Map<String, List<CareGiver>> careGiverMapFromRepo,
                  CareGiverSearch careGiverSearch) {
            List<CareGiver> careGiverListFromRepo = careGiverMapFromRepo
                        .get(MedicMobileConstants.ID_CARD_NUMBER);
            if (careGiverSearch.getCareGiverName() != null
                        && careGiverListFromRepo.size() > 0) {
                  careGiverListFromRepo = this
                              .searchInResultsBasedOnCareGiverName(
                                          careGiverListFromRepo,
                                          careGiverSearch);
            }
            if (careGiverSearch.getChildName() != null
                        && careGiverListFromRepo.size() > 0) {
                  careGiverListFromRepo = this.searchInResultsBasedOnChildName(
                              careGiverListFromRepo, careGiverSearch);
            }
            if (careGiverSearch.getStartDate() != null
                        && careGiverListFromRepo.size() > 0) {
                  careGiverListFromRepo = this
                              .searchInResultsBasedOnChildBirthDate(
                                          careGiverListFromRepo,
                                          careGiverSearch);
            }
            careGiverListFromRepo = searchLVAWVillage(careGiverSearch, careGiverListFromRepo);
            return careGiverListFromRepo;
      }
      private List<CareGiver> searchLVAWVillage(CareGiverSearch careGiverSearch, List<CareGiver> careGiverListFromRepo) {
          if (careGiverSearch.getVillage() != null
                  && careGiverListFromRepo.size() > 0) {
            this.searchInResultsBasedOnVillage(
                        careGiverListFromRepo, careGiverSearch);
          }
          if (careGiverSearch.getLocalVolunteerID() != null
                      && careGiverListFromRepo.size() > 0) {
                this
                            .searchInResultsBasedOnLocalVolunteerID(
                                        careGiverListFromRepo,
                                        careGiverSearch);
          }
          if (careGiverSearch.getAnganwadiID() != null
                      && careGiverListFromRepo.size() > 0) {
                this
                            .searchInResultsBasedOnAnganwadiID(
                                        careGiverListFromRepo,
                                        careGiverSearch);
          }
      return careGiverListFromRepo;
      }
      /**
       * Gets the list of care giver view.
       * 
       * @param careGiverList
       *              the care giver list
       * @return the list of care giver view
       */
      public List<CareGiverView> getListOfCareGiverView(
                  List<CareGiver> careGiverList) {
            List<CareGiverView> careGiverViewList = new ArrayList<CareGiverView>();
            if (careGiverList != null && !careGiverList.isEmpty()) {
                  for (CareGiver careGiver : careGiverList) {
                        List<Child> children = childRepo
                                    .findByCareGiverID(careGiver
                                                .getCareGiverID());
                        if (!children.isEmpty()) {
                              Set<Child> childSet = new HashSet<Child>();
                              childSet.addAll(children);
                              careGiver.setChildren(childSet);

                        }
                        if (staffRepo.findByStaffID(
                                    careGiver.getLocalVolunteerID()).isEmpty()) {
                              careGiverViewList.add(new CareGiverView(
                                          careGiver, "", ""));
                              logger.info("Number of careGivers = "
                                          + careGiverViewList.size());
                        } else {
                              Staff staff = staffRepo.findByStaffID(
                                          careGiver.getLocalVolunteerID()).get(
                                          0);

                              careGiverViewList.add(new CareGiverView(
                                          careGiver, staff.getName(), staff
                                                      .getPhoneNumber()));
                        }
                  }
            }
            return careGiverViewList;
      }

      /**
       * Gets the key.
       * 
       * @param map
       *              the map
       * @param value
       *              the value
       * @return the key
       */
      public String getKey(Map<String, Integer> map, int value) {
            for (Entry<String, Integer> entry : map.entrySet()) {
                  if (entry.getValue().equals(value)) {
                        return entry.getKey();
                  }
            }

            return null;
      }

      /**
       * Find by all constraints child birth date.
       * 
       * @param careGiverMapFromRepo
       *              the care giver map from repo
       * @param careGiverSearch
       *              the care giver search
       * @return the list
       */
      public List<CareGiver> findByAllConstraintsChildBirthDate(
                  Map<String, List<CareGiver>> careGiverMapFromRepo,
                  CareGiverSearch careGiverSearch) {
            List<CareGiver> careGiverListFromRepo = careGiverMapFromRepo
                        .get(MedicMobileConstants.CHILD_BIRTH_DATE);
            careGiverListFromRepo = searchLVAWVillage(careGiverSearch, careGiverListFromRepo);
            return careGiverListFromRepo;
      }

      /**
       * Search in results based on village.
       * 
       * @param careGiverList
       *              the care giver list
       * @param careGiverSearch
       *              the care giver search
       * @return the list
       */
      public List<CareGiver> searchInResultsBasedOnVillage(
                  List<CareGiver> careGiverList, CareGiverSearch careGiverSearch) {
            String village = careGiverSearch.getVillage();
            for (careGiverIndex = careGiverList.size() - 1; careGiverIndex >= 0; careGiverIndex--) {
                  careGiverTemp = careGiverList.get(careGiverIndex);
                  String villageName = careGiverTemp.getVillage().toLowerCase();
                  if (!villageName.contains(village.toLowerCase())) {
                        careGiverList.remove(careGiverIndex);
                  }
            }

            return careGiverList;
      }

      /**
       * Search in results based on child birth date.
       * 
       * @param careGiverList
       *              the care giver list
       * @param careGiverSearch
       *              the care giver search
       * @return the list
       */
      public List<CareGiver> searchInResultsBasedOnChildBirthDate(
                  List<CareGiver> careGiverList, CareGiverSearch careGiverSearch) {
            Date startDate;
            Date endDate;
            for (careGiverIndex = careGiverList.size() - 1; careGiverIndex >= 0; careGiverIndex--) {
                  startDate = careGiverSearch.getStartDate();
                  endDate = careGiverSearch.getEndDate();
                  if (startDate != null && endDate != null) {
                        careGiverTemp = careGiverList.get(careGiverIndex);
                        boolean bool = false;
                        for (Child child : careGiverTemp.getChildren()) {
                              if (child.getDateOfBirth().before(startDate)
                                          && child.getDateOfBirth().after(
                                                      endDate)) {
                                    bool = true;
                              }
                        }

                        if (!bool) {
                              careGiverList.remove(careGiverIndex);
                        }
                  }
            }
            return careGiverList;
      }

      /**
       * Search in results based on child name.
       * 
       * @param careGiverList
       *              the care giver list
       * @param careGiverSearch
       *              the care giver search
       * @return the list
       */
      public List<CareGiver> searchInResultsBasedOnChildName(
                  List<CareGiver> careGiverList, CareGiverSearch careGiverSearch) {
            for (careGiverIndex = careGiverList.size() - 1; careGiverIndex >= 0; careGiverIndex--) {
                  String childName = careGiverSearch.getChildName();
                  careGiverTemp = careGiverList.get(careGiverIndex);
                  boolean bool = false;
                  for (Child child : careGiverTemp.getChildren()) {
                        logger.debug("Child Name is: "+child.getName());
                        String childsName = child.getName().toLowerCase();
                        if (!childsName.contains(childName.toLowerCase())) {
                              bool = true;
                        }
                  }

                  if (!bool) {
                        careGiverList.remove(careGiverIndex);
                  }
            }

            return careGiverList;
      }

      /**
       * Search in results based on care giver name.
       * 
       * @param careGiverList
       *              the care giver list
       * @param careGiverSearch
       *              the care giver search
       * @return the list
       */
      public List<CareGiver> searchInResultsBasedOnCareGiverName(
                  List<CareGiver> careGiverList, CareGiverSearch careGiverSearch) {
            String careGiverName = careGiverSearch.getCareGiverName();
            for (careGiverIndex = careGiverList.size() - 1; careGiverIndex >= 0; careGiverIndex--) {
                  careGiverTemp = careGiverList.get(careGiverIndex);
                  String careGiverTempName = careGiverTemp.getName()
                              .toLowerCase();
                  if (!careGiverTempName.contains(careGiverName.toLowerCase())) {
                        careGiverList.remove(careGiverIndex);
                  }
            }
            return careGiverList;
      }

      /**
       * Search in results based on local volunteer id.
       * 
       * @param careGiverList
       *              the care giver list
       * @param search
       *              the search
       * @return the list
       */
      public List<CareGiver> searchInResultsBasedOnLocalVolunteerID(
                  List<CareGiver> careGiverList, CareGiverSearch careGiverSearch) {
            String localVolunteerID = careGiverSearch.getLocalVolunteerID();
            for (careGiverIndex = careGiverList.size() - 1; careGiverIndex >= 0; careGiverIndex--) {
                  careGiverTemp = careGiverList.get(careGiverIndex);
                  if (!careGiverTemp.getLocalVolunteerID().equals(
                              localVolunteerID)) {
                        careGiverList.remove(careGiverIndex);
                  }
            }
            return careGiverList;
      }

      public List<CareGiver> searchInResultsBasedOnAnganwadiID(
                  List<CareGiver> careGiverList, CareGiverSearch careGiverSearch) {
            String anganwadiID = careGiverSearch.getAnganwadiID();
            for (careGiverIndex = careGiverList.size() - 1; careGiverIndex >= 0; careGiverIndex--) {
                  careGiverTemp = careGiverList.get(careGiverIndex);
                  String careGiverTempAnganwadiID = careGiverTemp
                              .getAnganwadiID().toLowerCase();
                  if (!careGiverTempAnganwadiID.contains(anganwadiID
                              .toLowerCase())) {
                        careGiverList.remove(careGiverIndex);
                  }
            }
            return careGiverList;
      }

      /**
       * Find by all constraints child name.
       * 
       * @param careGiverMapFromRepo
       *              the care giver map from repo
       * @param careGiverSearch
       *              the care giver search
       * @return the list
       */
      public List<CareGiver> findByAllConstraintsChildName(
                  Map<String, List<CareGiver>> careGiverMapFromRepo,
                  CareGiverSearch careGiverSearch) {
            List<CareGiver> careGiverListFromRepo = careGiverMapFromRepo
                        .get(MedicMobileConstants.CHILD_NAME);
            if (careGiverSearch.getChildName() != null
                        && careGiverListFromRepo.size() > 0) {
                  careGiverListFromRepo = this
                              .searchInResultsBasedOnChildBirthDate(
                                          careGiverListFromRepo,
                                          careGiverSearch);
            }
            if (careGiverSearch.getEndDate() != null
                        && careGiverSearch.getStartDate() != null) {
                  careGiverListFromRepo = this
                              .searchInResultsBasedOnChildBirthDate(
                                          careGiverListFromRepo,
                                          careGiverSearch);
            }
            careGiverListFromRepo = searchLVAWVillage(careGiverSearch, careGiverListFromRepo);
            return careGiverListFromRepo;
      }

      /**
       * Find by all constraints care giver name.
       * 
       * @param careGiverMapFromRepo
       *              the care giver map from repo
       * @param careGiverSearch
       *              the care giver search
       * @return the list
       */
      public List<CareGiver> findByAllConstraintsCareGiverName(
                  Map<String, List<CareGiver>> careGiverMapFromRepo,
                  CareGiverSearch careGiverSearch) {
            List<CareGiver> careGiverListFromRepo = careGiverMapFromRepo
                        .get(MedicMobileConstants.CARE_GIVER_NAME);
            if (careGiverSearch.getChildName() != null
                        && careGiverListFromRepo.size() > 0) {
                  careGiverListFromRepo = this.searchInResultsBasedOnChildName(
                              careGiverListFromRepo, careGiverSearch);
            }
            if (careGiverSearch.getStartDate() != null
                        && careGiverListFromRepo.size() > 0) {
                  careGiverListFromRepo = this
                              .searchInResultsBasedOnChildBirthDate(
                                          careGiverListFromRepo,
                                          careGiverSearch);
            }
            careGiverListFromRepo = searchLVAWVillage(careGiverSearch, careGiverListFromRepo);
            return careGiverListFromRepo;
      }

      /**
       * Find by all constraints local volunteer.
       * 
       * @param careGiverMapFromRepo
       *              the care giver map from repo
       * @param careGiverSearch
       *              the care giver search
       * @return the list
       */
      public List<CareGiver> findByAllConstraintsLocalVolunteer(
                  Map<String, List<CareGiver>> careGiverMapFromRepo,
                  CareGiverSearch careGiverSearch) {
            List<CareGiver> careGiverListFromRepo = careGiverMapFromRepo
                        .get(MedicMobileConstants.LOCAL_VOLUNTEER);
            if (careGiverSearch.getCareGiverName() != null
                        && careGiverListFromRepo.size() > 0) {
                  careGiverListFromRepo = this
                              .searchInResultsBasedOnCareGiverName(
                                          careGiverListFromRepo,
                                          careGiverSearch);
            }
            if (careGiverSearch.getChildName() != null
                        && careGiverListFromRepo.size() > 0) {
                  careGiverListFromRepo = this.searchInResultsBasedOnChildName(
                              careGiverListFromRepo, careGiverSearch);
            }
            if (careGiverSearch.getStartDate() != null
                        && careGiverListFromRepo.size() > 0) {
                  careGiverListFromRepo = this
                              .searchInResultsBasedOnChildBirthDate(
                                          careGiverListFromRepo,
                                          careGiverSearch);
            }
            searchVillageAW(careGiverSearch, careGiverListFromRepo);
            return careGiverListFromRepo;
      }
      
      private void searchVillageAW(CareGiverSearch careGiverSearch, List<CareGiver> careGiverListFromRepo) {
          if (careGiverSearch.getVillage() != null
                  && careGiverListFromRepo.size() > 0) {
            this.searchInResultsBasedOnVillage(
                        careGiverListFromRepo, careGiverSearch);
      }
      if (careGiverSearch.getAnganwadiID() != null
                  && careGiverListFromRepo.size() > 0) {
             this
                        .searchInResultsBasedOnAnganwadiID(
                                    careGiverListFromRepo,
                                    careGiverSearch);
      }  
      }
      /**
       * Find by all constraints anganwadi center.
       * 
       * @param careGiverMapFromRepo
       *              the care giver map from repo
       * @param careGiverSearch
       *              the care giver search
       * @return the list
       */
      public List<CareGiver> findByAllConstraintsAnganwadiCenter(
                  Map<String, List<CareGiver>> careGiverMapFromRepo,
                  CareGiverSearch careGiverSearch) {
            return careGiverMapFromRepo
                        .get(MedicMobileConstants.ANGANWADI_CENTER);
      }

      @Override
      public void deleteCareGiver(String careGiverID) {
            careGiverRepo.deleteCareGiver(careGiverID);
            for (Child current : childRepo.findByCareGiverID(careGiverID)) {
                  childService.deleteChild(current);
            }

      }

}
