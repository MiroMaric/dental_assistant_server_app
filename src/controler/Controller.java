package controler;

import domain.Appointment;
import domain.GeneralEntity;
import domain.Patient;
import domain.User;
import domain.intervention.Intervention;
import domain.tooth.ToothRootState;
import domain.tooth.ToothSideState;
import domain.tooth.ToothState;
import java.util.List;
import so.AbstractGenericOperation;
import so.appointment.DeleteAppointmentSO;
import so.appointment.GetAllAppointmentsSO;
import so.appointment.SaveAppointmentSO;
import so.appointment.UpdateAppointmentSO;
import so.intervention.SaveInterventionSO;
import so.patient.CreateCardboardSO;
import so.patient.FindCardboardSO;
import so.patient.GetAllPatientsSO;
import so.patient.UpdatePatientSO;
import so.tooth_root_state.GetAllToothRootStatesSO;
import so.tooth_side_state.GetAllToothSideStatesSO;
import so.tooth_state.GetAllToothStatesSO;
import so.user.FindUserByUsernameAndPasswordSO;
import so.user.RegisterNewUserSO;
import transfer.dto.CreateCardboardDto;

public class Controller {

    private static Controller instance;

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    //
    public GeneralEntity logIn(User user) throws Exception {
        AbstractGenericOperation op = new FindUserByUsernameAndPasswordSO();
        op.templateExecute(user);
        return ((FindUserByUsernameAndPasswordSO) op).getValue();
    }

    
    public List<GeneralEntity> getAllAppointments() throws Exception {
        AbstractGenericOperation op = new GetAllAppointmentsSO();
        op.templateExecute(new Appointment());
        return ((GetAllAppointmentsSO) op).getList();
    }

    //
    public List<GeneralEntity> getAllPatients() throws Exception {
        AbstractGenericOperation op = new GetAllPatientsSO();
        op.templateExecute(new Patient());
        return ((GetAllPatientsSO) op).getList();
    }

    //
    public void deleteAppointment(Appointment appointment) throws Exception {
        AbstractGenericOperation op = new DeleteAppointmentSO();
        op.templateExecute(appointment);
    }

    //
    public void saveAppointment(Appointment appointment) throws Exception {
        AbstractGenericOperation op = new SaveAppointmentSO();
        op.templateExecute(appointment);
    }

    //
    public void updateAppointment(Appointment appointment) throws Exception {
        AbstractGenericOperation op = new UpdateAppointmentSO();
        op.templateExecute(appointment);
    }

    //
    public void updatePatient(Patient patient) throws Exception {
        AbstractGenericOperation op = new UpdatePatientSO();
        op.templateExecute(patient);
    }

    //
    public List<GeneralEntity> getAllToothSideStates() throws Exception {
        AbstractGenericOperation op = new GetAllToothSideStatesSO();
        op.templateExecute(new ToothSideState());
        return ((GetAllToothSideStatesSO) op).getList();
    }

    //
    public List<GeneralEntity> getAllToothRootStates() throws Exception {
        AbstractGenericOperation op = new GetAllToothRootStatesSO();
        op.templateExecute(new ToothRootState());
        return ((GetAllToothRootStatesSO) op).getList();
    }

    //
    public List<GeneralEntity> getAllToothStates()throws Exception{
        AbstractGenericOperation op = new GetAllToothStatesSO();
        op.templateExecute(new ToothState());
        return ((GetAllToothStatesSO) op).getList();
    }

    //
    public void registerNewUser(User user) throws Exception {
        AbstractGenericOperation op = new RegisterNewUserSO();
        op.templateExecute(user);
    }

    //
    public void createCardboard(CreateCardboardDto createCardboardDto) throws Exception {
        AbstractGenericOperation op = new CreateCardboardSO();
        op.templateExecute(createCardboardDto);
    }

    //
    public GeneralEntity findCardboard(Patient patient) throws Exception {
        AbstractGenericOperation op = new FindCardboardSO();
        op.templateExecute(patient);
        return ((FindCardboardSO) op).getValue();
    }

    //
    public void saveIntervention(Intervention intervention) throws Exception {
        AbstractGenericOperation op = new SaveInterventionSO();
        op.templateExecute(intervention);
    }

}
