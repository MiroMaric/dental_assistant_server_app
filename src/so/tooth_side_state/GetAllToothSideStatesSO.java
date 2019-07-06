package so.tooth_side_state;

import domain.GeneralEntity;
import domain.tooth.ToothSideState;
import java.sql.SQLException;
import java.util.List;
import so.AbstractGenericOperation;

public class GetAllToothSideStatesSO extends AbstractGenericOperation {

    private List<GeneralEntity> toothSideStates;

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof ToothSideState)) {
            throw new Exception("Invalid parameter type!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        try {
            toothSideStates = databaseBroker.getAllRecord(new ToothSideState());
        } catch (SQLException ex) {
            throw new Exception("Sistem ne može da učita stanja strane zuba");
        }
    }

    public List<GeneralEntity> getList() {
        return toothSideStates;
    }
}
