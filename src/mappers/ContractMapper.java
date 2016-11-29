package mappers;

import models.Contract;
import models.DomainObject;
import models.Product;
import models.RevenueRecognition;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pyoung on 2016-11-27.
 */
public class ContractMapper extends AbstractMapper{

    private final static String findStatementString = "select * from contracts where id=?";
    private final static String insertStatementString = "INSERT INTO contracts VALUES (?,?,?,?";
    private final static String lastIDStatement = "SELECT MAX(id) FROM contracts";
    private String connectionString = "jdbc:mysql://localhost:3306/revenue?user=pyoung&password=password";
    private final static String findAllStatement = "SELECT * from contracts";

    private static ProductMapper productMapper = new ProductMapper();
    private static RevenueRecognitionMapper revenueRecognitionMapper = new RevenueRecognitionMapper();

    public Contract find(long id){
        return find(new Long(id));

    }

    public Contract find(Long id){
        return (Contract) abstractFind(id);
    }

    public Long insertContract(Contract c){
        return insert(c);

    }

    public List<Contract> findAll() {
        List<Contract> contracts = (List<Contract>) (Object) abstractFindAll();
        return contracts;
    }

    @Override
    protected DomainObject doLoad(Long id, ResultSet rs) throws SQLException {
        double revenue = rs.getDouble(2);
        LocalDate dateSigned = rs.getDate(3).toLocalDate();
        int productId = rs.getInt(4);

        Product product = productMapper.find(productId);
        ArrayList<RevenueRecognition> revenueRecognitions = (ArrayList<RevenueRecognition>) revenueRecognitionMapper.findAllWithId(id);

        Contract result = new Contract(product, revenue, dateSigned);
        result.setRevenueRecognitions(revenueRecognitions);
        return result;
    }

    @Override
    protected void doInsert(DomainObject c, PreparedStatement stmt) throws SQLException{
        stmt.setDouble(2, ((Contract) c).getRevenue());
        stmt.setDate(3, (Date.valueOf(((Contract) c).getWhenSigned())));
        stmt.setLong(4, ((Contract) c).getProductId());
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
    protected String connectionString() {
        return connectionString;
    }
}
