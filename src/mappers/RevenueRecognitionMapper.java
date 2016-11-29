package mappers;

import models.Contract;
import models.DomainObject;
import models.RevenueRecognition;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by pyoung on 2016-11-27.
 */
public class RevenueRecognitionMapper extends AbstractMapper {
    private final static String findStatementString = "select * from revenueRecognitions where contract_id=?";
    private final static String insertStatementString = "INSERT INTO revenueRecognitions VALUES (?,?,?,?";
    private final static String lastIDStatement = "SELECT MAX(id) FROM revenueRecognitions";
    private String connectionString = "jdbc:mysql://localhost:3306/revenue?user=pyoung&password=password";
    private final static String findAllStatement = "SELECT * from revenueRecognitions";

    private static ContractMapper contractMapper = new ContractMapper();

    public RevenueRecognition find(long id){
        return find(new Long(id));
    }

    public RevenueRecognition find(Long id){

        return (RevenueRecognition) abstractFind(id);
    }

    public List<RevenueRecognition> findAllWithId(long id) {
        return findAllWithId(new Long(id));
    }

    public List<RevenueRecognition> findAllWithId(Long id) {
        List<RevenueRecognition> revenueRecognitions = (List<RevenueRecognition>) (Object) abstractFindAllWithId(id);
        return revenueRecognitions;
    }

    public Long insertRevenueRecognition(RevenueRecognition r) {
        return insert(r);
    }

    public List<RevenueRecognition> findAll() {
        List<RevenueRecognition> revenueRecognition = (List<RevenueRecognition>) (Object) abstractFindAll();
        return revenueRecognition;
    }

    @Override
    protected DomainObject doLoad(Long id, ResultSet rs) throws SQLException {
        double amount = rs.getDouble(2);
        LocalDate recognizedOn = rs.getDate(3).toLocalDate();
        Long contract_id = rs.getLong(4);
        RevenueRecognition result = new RevenueRecognition(amount, recognizedOn, contract_id);
        return result;
    }

    @Override
    protected void doInsert(DomainObject r, PreparedStatement stmt) throws SQLException{
        stmt.setDouble(2, ((RevenueRecognition) r).getAmount());
        stmt.setDate(3, (Date.valueOf(((RevenueRecognition) r).getDate())));
        stmt.setLong(4, ((RevenueRecognition) r).getContractId());
    }

    @Override
    protected String findStatement() {
        return findStatementString;
    }

    @Override
    protected String insertStatement() {
        return insertStatementString;
    }

    @Override
    protected String findAllStatement() {
        return findAllStatement;
    }

    @Override
    protected String lastIDStatement() {
        return lastIDStatement;
    }

    @Override
    protected String connectionString() { return connectionString; }
}
