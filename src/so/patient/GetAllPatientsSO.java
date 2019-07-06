package so.patient;

import domain.GeneralEntity;
import domain.Patient;
import java.sql.SQLException;
import java.util.List;
import so.AbstractGenericOperation;

public class GetAllPatientsSO extends AbstractGenericOperation{

    private List<GeneralEntity> patients;
    
    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Patient)) {
            throw new Exception("Invalid parameter type!");
        }
    }

    @Override
    protected void execute(Object entity)throws Exception{
        try {
            patients = databaseBroker.getAllRecord(new Patient());
        } catch (SQLException ex) {
            throw new Exception("Sistem ne može da učita pacijente");
        }
    }
    
    public List<GeneralEntity> getList(){
        return patients;
    }
    
}
