package so.patient;

import domain.GeneralEntity;
import domain.Patient;
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
import exception.EntityNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import so.AbstractGenericOperation;

public class FindCardboardSO extends AbstractGenericOperation {

    private Patient cardboard;

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Patient)) {
            throw new Exception("Invalid parameter type!");
        }
    }

    @Override
    protected void execute(Object patient) throws Exception {
        try {
            cardboard = (Patient) databaseBroker.findRecord((GeneralEntity) patient);
            List<Tooth> teeth = databaseBroker.findRecords(new Tooth(), cardboard).stream().map(o -> (Tooth) o).collect(Collectors.toList());
            for (Tooth t : teeth) {
                t.setLabel((ToothLabel) databaseBroker.findRecord(t.getLabel()));
                //t.setState((ToothState) databaseBroker.findRecord(t.getState()));
                t.setPatient(cardboard);
                List<ToothIntervention> toothInterventions = databaseBroker.findRecords(new ToothIntervention(), t).stream().map(o -> (ToothIntervention) o).collect(Collectors.toList());
                for (ToothIntervention ti : toothInterventions) {
                    ti.setTooth(t);
                    ti.setState((ToothState) databaseBroker.findRecord(new ToothState(ti.getState().getToothStateID())));
                    String note = ((InterventionItem) databaseBroker.findRecord(new InterventionItem(ti.getItemID()) {
                        @Override
                        public String getToothLabel() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    })).getNote();
                    ti.setNote(note);
                    ti.setIntervention((Intervention) databaseBroker.findRecord(ti.getIntervention()));
                }
                toothInterventions.sort((o1, o2) -> o1.getIntervention().getDate().compareTo(o2.getIntervention().getDate()));
                t.setToothInterventions(toothInterventions);
                
                List<ToothSide> toothSides = databaseBroker.findRecords(new ToothSide(), t).stream().map(o -> (ToothSide) o).collect(Collectors.toList());
                for (ToothSide ts : toothSides) {
                    ts.setTooth(t);
                    ts.setLabel((ToothSideLabel) databaseBroker.findRecord(ts.getLabel()));
                    List<SideIntervention> sideInterventions = databaseBroker.findRecords(new SideIntervention(), ts).stream().map(o -> (SideIntervention) o).collect(Collectors.toList());
                    for (SideIntervention si : sideInterventions) {
                        si.setToothSide(ts);
                        si.setState((ToothSideState) databaseBroker.findRecord(new ToothSideState(si.getState().getToothSideStateID())));
                        String note = ((InterventionItem) databaseBroker.findRecord(new InterventionItem(si.getItemID()) {
                            @Override
                            public String getToothLabel() {
                                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                        })).getNote();
                        si.setNote(note);
                        si.setIntervention((Intervention) databaseBroker.findRecord(si.getIntervention()));
                    }
                    sideInterventions.sort((o1, o2) -> o1.getIntervention().getDate().compareTo(o2.getIntervention().getDate()));
                    ts.setSideInterventions(sideInterventions);
                }
                toothSides.sort((s1, s2) -> s1.getLabel().getToothSideLabelID().compareTo(s2.getLabel().getToothSideLabelID()));
                t.setSides(toothSides);

                List<ToothRoot> toothRoots = databaseBroker.findRecords(new ToothRoot(), t).stream().map(o -> (ToothRoot) o).collect(Collectors.toList());
                for (ToothRoot tr : toothRoots) {
                    tr.setTooth(t);
                    tr.setLabel((ToothRootLabel) databaseBroker.findRecord(tr.getLabel()));
                    List<RootIntervention> rootInterventions = databaseBroker.findRecords(new RootIntervention(), tr).stream().map(o -> (RootIntervention) o).collect(Collectors.toList());
                    for (RootIntervention ri : rootInterventions) {
                        ri.setToothRoot(tr);
                        ri.setToothRootState((ToothRootState) databaseBroker.findRecord(new ToothRootState(ri.getToothRootState().getToothRootStateID())));
                        String note = ((InterventionItem) databaseBroker.findRecord(new InterventionItem(ri.getItemID()) {
                            @Override
                            public String getToothLabel() {
                                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                        })).getNote();
                        ri.setNote(note);
                        ri.setIntervention((Intervention) databaseBroker.findRecord(ri.getIntervention()));
                    }
                    rootInterventions.sort((o1, o2) -> o1.getIntervention().getDate().compareTo(o2.getIntervention().getDate()));
                    tr.setRootInterventions(rootInterventions);
                }
                toothRoots.sort((s1, s2) -> s1.getLabel().getToothRootLabelID().compareTo(s2.getLabel().getToothRootLabelID()));
                t.setRoots(toothRoots);
            }
            teeth.sort((Tooth o1, Tooth o2) -> o1.getLabel().getToothLabelID().compareTo(o2.getLabel().getToothLabelID()));
            cardboard.setTeeth(teeth);
        } catch (SQLException ex) {
            Logger.getLogger(CreateCardboardSO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Sistem ne može da učita karton");
        }
    }

    public GeneralEntity getValue() {
        return cardboard;
    }
}

//Potrebno veliko poboljsanje!
//    public Patient getPatient(Patient patient) {
//        try {
//            Patient p = (Patient) brokerDatabase.findRecord(patient);
//            List<Tooth> teeth = brokerDatabase.findRecords(new Tooth(), p).stream().map(o -> (Tooth) o).collect(Collectors.toList());
//            for (Tooth t : teeth) {
//                t.setLabel((ToothLabel) brokerDatabase.findRecord(t.getLabel()));
//                t.setState((ToothState) brokerDatabase.findRecord(t.getState()));
//                t.setPatient(p);
//
//                List<ToothSide> toothSides = brokerDatabase.findRecords(new ToothSide(), t).stream().map(o -> (ToothSide) o).collect(Collectors.toList());
//                for (ToothSide ts : toothSides) {
//                    ts.setTooth(t);
//                    ts.setLabel((ToothSideLabel) brokerDatabase.findRecord(ts.getLabel()));
//                    List<SideIntervention> sideInterventions = brokerDatabase.findRecords(new SideIntervention(), ts).stream().map(o -> (SideIntervention) o).collect(Collectors.toList());
//                    System.out.println(sideInterventions.get(0).getItemID());
//                    for (SideIntervention si : sideInterventions) {
//                        si.setToothSide(ts);
//                        si.setState((ToothSideState) brokerDatabase.findRecord(new ToothSideState(si.getState().getToothSideStateID())));
//                        String note = ((InterventionItem) brokerDatabase.findRecord(new InterventionItem(si.getItemID()) {
//                            @Override
//                            public String getToothLabel() {
//                                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                            }
//                        })).getNote();
//                        si.setNote(note);
//                        si.setIntervention((Intervention) brokerDatabase.findRecord(si.getIntervention()));
//                    }
//                    sideInterventions.sort((o1, o2) -> o1.getIntervention().getDate().compareTo(o2.getIntervention().getDate()));
//                    ts.setSideInterventions(sideInterventions);
//                }
//                toothSides.sort((s1, s2) -> s1.getLabel().getToothSideLabelID().compareTo(s2.getLabel().getToothSideLabelID()));
//                t.setSides(toothSides);
//
//                List<ToothRoot> toothRoots = brokerDatabase.findRecords(new ToothRoot(), t).stream().map(o -> (ToothRoot) o).collect(Collectors.toList());
//                for (ToothRoot tr : toothRoots) {
//                    tr.setTooth(t);
//                    tr.setLabel((ToothRootLabel) brokerDatabase.findRecord(tr.getLabel()));
//                    List<RootIntervention> rootInterventions = brokerDatabase.findRecords(new RootIntervention(), tr).stream().map(o -> (RootIntervention) o).collect(Collectors.toList());
//                    System.out.println(rootInterventions.get(0).getItemID());
//                    for (RootIntervention ri : rootInterventions) {
//                        ri.setToothRoot(tr);
//                        ri.setToothRootState((ToothRootState) brokerDatabase.findRecord(new ToothRootState(ri.getToothRootState().getToothRootStateID())));
//                        String note = ((InterventionItem) brokerDatabase.findRecord(new InterventionItem(ri.getItemID()) {
//                            @Override
//                            public String getToothLabel() {
//                                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                            }
//                        })).getNote();
//                        ri.setNote(note);
//                        ri.setIntervention((Intervention) brokerDatabase.findRecord(ri.getIntervention()));
//                    }
//                    rootInterventions.sort((o1, o2) -> o1.getIntervention().getDate().compareTo(o2.getIntervention().getDate()));
//                    tr.setRootInterventions(rootInterventions);
//                }
//                toothRoots.sort((s1, s2) -> s1.getLabel().getToothRootLabelID().compareTo(s2.getLabel().getToothRootLabelID()));
//                t.setRoots(toothRoots);
//            }
//            teeth.sort((Tooth o1, Tooth o2) -> o1.getLabel().getToothLabelID().compareTo(o2.getLabel().getToothLabelID()));
//            p.setTeeth(teeth);
//            return p;
//        } catch (SQLException ex) {
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
