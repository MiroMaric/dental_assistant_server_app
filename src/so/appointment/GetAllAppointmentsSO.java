package so.appointment;

import domain.Appointment;
import domain.GeneralEntity;
import domain.Patient;
import domain.User;
import exception.EntityNotFoundException;
import java.sql.SQLException;
import java.util.List;
import so.AbstractGenericOperation;

public class GetAllAppointmentsSO extends AbstractGenericOperation {

    private List<GeneralEntity> appointments;

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Appointment)) {
            throw new Exception("Invalid parameter type!");
        }
    }

    @Override
    protected void execute(Object entity)throws Exception{
        try {
            appointments = databaseBroker.getAllRecord(new Appointment());
            for (GeneralEntity ge : appointments) {
                Appointment a = ((Appointment) ge);
                a.setPatient((Patient) databaseBroker.findRecord(new Patient(a.getPatient().getPatientID())));
                a.setUser((User) databaseBroker.findRecord(new User(a.getUser().getUsername())));
            }
        } catch (SQLException|EntityNotFoundException ex) {
            throw new Exception("Greška na strani servera");
        }
    }

    public List<GeneralEntity> getList() {
        return appointments;
    }
}
