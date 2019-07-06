package so.intervention;

import domain.intervention.Intervention;
import domain.intervention.InterventionItem;
import exception.InsertEntityException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import so.AbstractGenericOperation;

public class SaveInterventionSO extends AbstractGenericOperation {

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Intervention)) {
            throw new Exception("Invalid parameter type!");
        }
    }

    @Override
    protected void execute(Object entity)throws Exception{
        try {
            Intervention intervention = (Intervention)entity;
            databaseBroker.insertRecord(intervention);
            for (InterventionItem interventionItem : intervention.getItems()) {
                databaseBroker.insertRecord(new InterventionItem(interventionItem.getItemID(), interventionItem.getIntervention(), interventionItem.getNote()) {
                    @Override
                    public String getToothLabel() {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
                databaseBroker.insertRecord(interventionItem);
            }
        } catch (SQLException|InsertEntityException ex) {
            throw new Exception("Sistem ne može da sačuva intervenciju");
        }
    }
}

//    public boolean saveIntervention(Intervention intervention) {
//        try {
//            DatabaseConnection.getInstance().getConnection().setAutoCommit(false);
//            brokerDatabase.insertRecord(intervention);
//            for (InterventionItem interventionItem : intervention.getItems()) {
//                brokerDatabase.insertRecord(new InterventionItem(interventionItem.getItemID(), interventionItem.getIntervention(), interventionItem.getNote()) {
//                    @Override
//                    public String getToothLabel() {
//                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                    }
//                });
//                brokerDatabase.insertRecord(interventionItem);
//            }
//            DatabaseConnection.getInstance().getConnection().commit();
//            return true;
//        } catch (SQLException ex) {
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }
