package storage.database;

import domain.GeneralEntity;
import exception.DeleteEntityException;
import exception.EntityNotFoundException;
import exception.InsertEntityException;
import exception.UpdateEntityException;
import java.sql.SQLException;
import java.util.List;

public interface IDatabaseBroker {
    
    List<GeneralEntity> getAllRecord(GeneralEntity entity) throws SQLException;
    GeneralEntity findRecord(GeneralEntity entity) throws SQLException, EntityNotFoundException;
    List<GeneralEntity> findRecords(GeneralEntity entity, GeneralEntity parent) throws SQLException;
    void insertRecord(GeneralEntity entity) throws SQLException,InsertEntityException;
    void deleteRecord(GeneralEntity entity) throws SQLException, DeleteEntityException;
    void updateRecord(GeneralEntity entity, GeneralEntity entityld) throws Exception;
    void updateRecord(GeneralEntity entity) throws SQLException, UpdateEntityException;
    
}
