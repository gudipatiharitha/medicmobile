package org.medicmobile.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.medicmobile.model.AnganwadiCenter;
import org.medicmobile.model.AnganwadiCenterView;
import org.medicmobile.model.CareGiver;
import org.medicmobile.model.Role;
import org.medicmobile.model.Staff;
import org.medicmobile.repository.AnganwadiCenterRepository;
import org.medicmobile.repository.CareGiverRepository;
import org.medicmobile.repository.StaffRepository;
import org.medicmobile.service.AnganwadiCenterService;
import org.medicmobile.service.CareGiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class AnganwadiCenterServiceImpl.
 */
@Service(value = "anganwadiCenterService")
public class AnganwadiCenterServiceImpl implements AnganwadiCenterService {

      /** The anganwadi center repo. */
      @Resource(name = "anganwadiCenterRepo")
      private AnganwadiCenterRepository anganwadiCenterRepo;

      /** The staff repo. */
      @Resource(name = "staffRepo")
      private StaffRepository staffRepo;

      @Autowired
      private CareGiverService careGiverService;

      @Autowired
      private CareGiverRepository careGiverRepo;

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.AnganwadiCenterService#addAnganwadiCenter(org
       * .medicmobile.model.AnganwadiCenter)
       */
      public AnganwadiCenter addAnganwadiCenter(AnganwadiCenter anganwadiCenter) {
            anganwadiCenterRepo.addAnganwadiCenter(anganwadiCenter);
            return anganwadiCenter;
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.AnganwadiCenterService#viewAnganwadiCenter(org
       * .medicmobile.model.AnganwadiCenter)
       */
      public AnganwadiCenter viewAnganwadiCenter(AnganwadiCenter anganwadiCenter) {
            return anganwadiCenterRepo.viewAnganwadiCenter(anganwadiCenter);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.AnganwadiCenterService#findAllAnganwadiCenter()
       */
      public List<AnganwadiCenterView> findAllAnganwadiCenter() {
            List<AnganwadiCenter> anganwadiCenters = anganwadiCenterRepo
                        .getAll();
            List<AnganwadiCenterView> anganwadiCenterViews = new ArrayList<AnganwadiCenterView>();
            for (AnganwadiCenter anganwadiCenter : anganwadiCenters) {
                  String anganwadiCenterID = anganwadiCenter.getAnganwadiID();
                  List<Staff> aNMList = staffRepo.findByRoleAndAnganwadiID(
                              Role.AUXILIARY_NURSING_MOTHER, anganwadiCenterID);
                  if (aNMList.isEmpty()) {
                        aNMList = new ArrayList<Staff>();
                        Staff staff = new Staff();
                        staff.setName("Not Available");
                        staff.setStaffID("");
                        aNMList.add(staff);
                  }
                  List<Staff> aWWList = staffRepo.findByRoleAndAnganwadiID(
                              Role.ANGANWADI_WORKER, anganwadiCenterID);
                  if (aWWList.isEmpty()) {
                        aWWList = new ArrayList<Staff>();
                        Staff staff = new Staff();
                        staff.setName("Not Available");
                        staff.setStaffID("");
                        aWWList.add(staff);
                  }
                  List<Staff> localVolunteerList = staffRepo
                              .findByRoleAndAnganwadiID(Role.LOCAL_VOLUNTEER,
                                          anganwadiCenterID);
                  if (localVolunteerList.isEmpty()) {
                        localVolunteerList = new ArrayList<Staff>();
                        Staff staff = new Staff();
                        staff.setName("Not Available");
                        staff.setStaffID("");
                        localVolunteerList.add(staff);
                  }
                  anganwadiCenterViews.add(new AnganwadiCenterView(
                              anganwadiCenter, aNMList, aWWList,
                              localVolunteerList));
            }
            return anganwadiCenterViews;
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.AnganwadiCenterService#findAnganwadiCenterByID
       * (java.lang.String)
       */
      public AnganwadiCenter findAnganwadiCenterByID(String anganwadiID) {
            return anganwadiCenterRepo.findByAnganwadiID(anganwadiID).get(0);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.AnganwadiCenterService#deleteAnganwadiCenter(
       * java.lang.String)
       */
      public void deleteAnganwadiCenter(String anganwadiID) {
            anganwadiCenterRepo.deleteAnganwadiCenter(anganwadiID);
            for (CareGiver current : careGiverRepo
                        .findByAnganwadiCenterID(anganwadiID)) {
                  careGiverService.deleteCareGiver(current.getCareGiverID());
            }
            for (Staff current : staffRepo.findByAnganwadiID(anganwadiID)) {
                  if (current.getAnganwadiID().size() < 2) {
                        staffRepo.deleteStaff(current.getStaffID());
                  } else {
                        current.getAnganwadiID().remove(anganwadiID);
                        staffRepo.addStaff(current);
                  }

            }
      }

}
