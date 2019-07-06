package so.patient;

import domain.intervention.Intervention;
import domain.intervention.InterventionItem;
import domain.intervention.RootIntervention;
import domain.intervention.SideIntervention;
import domain.intervention.ToothIntervention;
import domain.tooth.Tooth;
import domain.tooth.ToothLabel;
import domain.tooth.ToothRoot;
import domain.tooth.ToothRootLabel;
import domain.tooth.ToothRootState;
import domain.tooth.ToothSide;
import domain.tooth.ToothSideLabel;
import domain.tooth.ToothSideState;
import domain.tooth.ToothState;
import exception.InsertEntityException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import so.AbstractGenericOperation;
import transfer.dto.CreateCardboardDto;

public class CreateCardboardSO extends AbstractGenericOperation {

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof CreateCardboardDto)) {
            throw new Exception("Invalid parameter type!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        try {
            CreateCardboardDto dto = (CreateCardboardDto) entity;
            Intervention intervention = new Intervention(new Date(), "Novi elektronski karton", dto.getUser());
            databaseBroker.insertRecord(intervention);
            databaseBroker.insertRecord(dto.getPatient());
            List<ToothLabel> toothLabels = databaseBroker.getAllRecord(new ToothLabel()).stream().map(o -> (ToothLabel) o).collect(Collectors.toList());
            //i za ovo bi bilo logicno napraviti novu stavku intervencije kao "pocetno stanje"
            ToothState toothState = (ToothState) databaseBroker.findRecord(new ToothState("prirodan"));

            List<ToothSideLabel> toothSideLabels = databaseBroker.getAllRecord(new ToothSideLabel()).stream().map(o -> (ToothSideLabel) o).collect(Collectors.toList());
            ToothSideState toothSideState = (ToothSideState) databaseBroker.findRecord(new ToothSideState(-1, "zdrava", null, -1));

            List<ToothRootLabel> toothRootLabels = databaseBroker.getAllRecord(new ToothRootLabel()).stream().map(o -> (ToothRootLabel) o).collect(Collectors.toList());
            ToothRootState toothRootState = (ToothRootState) databaseBroker.findRecord(new ToothRootState(-1, "zdrav", null, -1));
            for (ToothLabel tl : toothLabels) {
                Tooth tooth = new Tooth(dto.getPatient(), tl);
                ToothIntervention toothIntervention = new ToothIntervention(intervention, "Pocetno stanje", tooth, toothState);
                databaseBroker.insertRecord(tooth);
                databaseBroker.insertRecord(new InterventionItem(toothIntervention.getItemID(), toothIntervention.getIntervention(), toothIntervention.getNote()) {
                    @Override
                    public String getToothLabel() {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
                databaseBroker.insertRecord(toothIntervention);
                for (ToothSideLabel tsl : toothSideLabels) {
                    ToothSide toothSide = new ToothSide(tooth, tsl);
                    SideIntervention sideIntevention = new SideIntervention(intervention, "Pocetno stanje", toothSide, toothSideState);
                    databaseBroker.insertRecord(toothSide);
                    databaseBroker.insertRecord(new InterventionItem(sideIntevention.getItemID(), sideIntevention.getIntervention(), sideIntevention.getNote()) {
                        @Override
                        public String getToothLabel() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    });
                    databaseBroker.insertRecord(sideIntevention);
                }
                for (ToothRootLabel trl : toothRootLabels.subList(0, tooth.getLabel().getNumOfRoots())) {
                    ToothRoot toothRoot = new ToothRoot(tooth, trl);
                    RootIntervention rootIntervention = new RootIntervention(intervention, "Pocetno stanje", toothRoot, toothRootState);
                    databaseBroker.insertRecord(toothRoot);
                    databaseBroker.insertRecord(new InterventionItem(rootIntervention.getItemID(), rootIntervention.getIntervention(), rootIntervention.getNote()) {
                        @Override
                        public String getToothLabel() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    });
                    databaseBroker.insertRecord(rootIntervention);
                }
            }
        } catch (SQLException | InsertEntityException ex) {
            Logger.getLogger(CreateCardboardSO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Sistem ne može da otvori novi karton");
        }
    }

//    public boolean savePatient(Patient patient) {
//        try {
//            DatabaseConnection.getInstance().getConnection().setAutoCommit(false);
//            Intervention intervention = new Intervention(new Date(), "Novi elektronski karton", Session.getInstance().getUser());
//            brokerDatabase.insertRecord(intervention);
//            brokerDatabase.insertRecord(patient);
//            List<ToothLabel> toothLabels = brokerDatabase.getAllRecord(new ToothLabel()).stream().map(o -> (ToothLabel) o).collect(Collectors.toList());
//            //i za ovo bi bilo logicno napraviti novu stavku intervencije kao "pocetno stanje"
//            ToothState toothState = (ToothState) brokerDatabase.findRecord(new ToothState(null, "prirodan", null, null));
//
//            List<ToothSideLabel> toothSideLabels = brokerDatabase.getAllRecord(new ToothSideLabel()).stream().map(o -> (ToothSideLabel) o).collect(Collectors.toList());
//            ToothSideState toothSideState = (ToothSideState) brokerDatabase.findRecord(new ToothSideState(-1, "zdrava", null, -1));
//
//            List<ToothRootLabel> toothRootLabels = brokerDatabase.getAllRecord(new ToothRootLabel()).stream().map(o -> (ToothRootLabel) o).collect(Collectors.toList());
//            ToothRootState toothRootState = (ToothRootState) brokerDatabase.findRecord(new ToothRootState(-1, "zdrav", null, -1));
//            for (ToothLabel tl : toothLabels) {
//                Tooth tooth = new Tooth(patient, tl, toothState);
//                brokerDatabase.insertRecord(tooth);
//                for (ToothSideLabel tsl : toothSideLabels) {
//                    ToothSide toothSide = new ToothSide(tooth, tsl);
//                    SideIntervention sideIntevention = new SideIntervention(intervention, "Pocetno stanje", toothSide, toothSideState);
//                    brokerDatabase.insertRecord(toothSide);
//                    brokerDatabase.insertRecord(new InterventionItem(sideIntevention.getItemID(), sideIntevention.getIntervention(), sideIntevention.getNote()) {
//                        @Override
//                        public String getToothLabel() {
//                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                        }
//                    });
//                    brokerDatabase.insertRecord(sideIntevention);
//                }
//                for (ToothRootLabel trl : toothRootLabels.subList(0, tooth.getLabel().getNumOfRoots())) {
//                    ToothRoot toothRoot = new ToothRoot(tooth, trl);
//                    RootIntervention rootIntervention = new RootIntervention(intervention, "Pocetno stanje", toothRoot, toothRootState);
//                    brokerDatabase.insertRecord(toothRoot);
//                    brokerDatabase.insertRecord(new InterventionItem(rootIntervention.getItemID(), rootIntervention.getIntervention(), rootIntervention.getNote()) {
//                        @Override
//                        public String getToothLabel() {
//                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                        }
//                    });
//                    brokerDatabase.insertRecord(rootIntervention);
//                }
//            }
//            DatabaseConnection.getInstance().getConnection().commit();
//            return true;
//        } catch (SQLException ex) {
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }
}
