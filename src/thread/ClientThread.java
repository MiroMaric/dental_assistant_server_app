package thread;

import controler.Controller;
import domain.Appointment;
import domain.GeneralEntity;
import domain.Patient;
import domain.User;
import domain.intervention.Intervention;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.Request;
import transfer.Response;
import transfer.dto.CreateCardboardDto;
import transfer.util.Operation;
import transfer.util.ResponseStatus;

public class ClientThread extends Thread {

    private final Socket socket;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            handleRequest();
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleRequest() throws IOException, ClassNotFoundException {
        while (!isInterrupted()) {
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            Request request = (Request) inSocket.readObject();
            Response response = new Response();
            try {
                int operation = request.getOperation();
                System.out.println("Operation: " + operation);
                switch (operation) {
                    case Operation.OPERATION_LOGIN:
                        User dataUser = (User) request.getData();
                        User user = (User) Controller.getInstance().logIn(dataUser);
                        response.setStatus(ResponseStatus.OK);
                        response.setData(user);
                        break;
                    case Operation.OPERATION_GET_ALL_APPOINTMENTS:
                        List<GeneralEntity> appointments = Controller.getInstance().getAllAppointments();
                        response.setStatus(ResponseStatus.OK);
                        response.setData(appointments);
                        break;
                    case Operation.OPERATION_GET_ALL_PATIENTS:
                        List<GeneralEntity> patients = Controller.getInstance().getAllPatients();
                        response.setStatus(ResponseStatus.OK);
                        response.setData(patients);
                        break;
                    case Operation.OPERATION_DELETE_APPOINTMENT:
                        Appointment appointment = (Appointment) request.getData();
                        Controller.getInstance().deleteAppointment(appointment);
                        response.setStatus(ResponseStatus.OK);
                        response.setData(null);
                        break;
                    case Operation.OPERATION_SAVE_APPOINTMENT:
                        Appointment sAppointment = (Appointment) request.getData();
                        Controller.getInstance().saveAppointment(sAppointment);
                        response.setStatus(ResponseStatus.OK);
                        response.setData(null);
                        break;
                    case Operation.OPERATION_UPDATE_APPOINTMENT:
                        Appointment uAppointment = (Appointment) request.getData();
                        Controller.getInstance().updateAppointment(uAppointment);
                        response.setStatus(ResponseStatus.OK);
                        response.setData(null);
                        break;
                    case Operation.OPERATION_UPDATE_PATIENT:
                        Patient patient = (Patient) request.getData();
                        Controller.getInstance().updatePatient(patient);
                        response.setStatus(ResponseStatus.OK);
                        response.setData(null);
                        break;
                    case Operation.OPERATION_GET_ALL_TOOTH_SIDE_STATES:
                        List<GeneralEntity> toothSideStates = Controller.getInstance().getAllToothSideStates();
                        response.setStatus(ResponseStatus.OK);
                        response.setData(toothSideStates);
                        break;
                    case Operation.OPERATION_GET_ALL_TOOTH_ROOT_STATES:
                        List<GeneralEntity> toothRootStates = Controller.getInstance().getAllToothRootStates();
                        response.setStatus(ResponseStatus.OK);
                        response.setData(toothRootStates);
                        break;
                    case Operation.OPERATION_GET_ALL_TOOTH_STATES:
                        List<GeneralEntity> toothStates = Controller.getInstance().getAllToothStates();
                        response.setStatus(ResponseStatus.OK);
                        response.setData(toothStates);
                        break;
                    case Operation.OPERATION_REGISTER_NEW_USER:
                        User rUser = (User) request.getData();
                        Controller.getInstance().registerNewUser(rUser);
                        response.setStatus(ResponseStatus.OK);
                        response.setData(null);
                        break;
                    case Operation.OPERATION_CREATE_CARDBOARD:
                        CreateCardboardDto createCardboardDto = (CreateCardboardDto) request.getData();
                        Controller.getInstance().createCardboard(createCardboardDto);
                        response.setStatus(ResponseStatus.OK);
                        response.setData(null);
                        break;
                    case Operation.OPERATION_FIND_CARDBOARD:
                        Patient fcpatient = (Patient) request.getData();
                        Patient cardboard = (Patient) Controller.getInstance().findCardboard(fcpatient);
                        response.setStatus(ResponseStatus.OK);
                        response.setData(cardboard);
                        break;
                    case Operation.OPERATION_SAVE_INTERVENTION:
                        Intervention intervention = (Intervention) request.getData();
                        Controller.getInstance().saveIntervention(intervention);
                        response.setStatus(ResponseStatus.OK);
                        response.setData(null);
                        break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setStatus(ResponseStatus.ERROR);
                response.setError(ex);
            }
            sendResponse(response);
        }
    }

    private void sendResponse(Response response) throws IOException {
        ObjectOutputStream outSocket = new ObjectOutputStream(socket.getOutputStream());
        outSocket.writeObject(response);
    }

}
