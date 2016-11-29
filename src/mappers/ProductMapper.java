package mappers;

import models.DomainObject;
import models.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by pyoung on 2016-11-27.
 */
public class ProductMapper extends AbstractMapper {
    private final static String findStatementString = "select * from products where id=?";
    private final static String insertStatementString = "INSERT INTO products VALUES (?,?,?";
    private final static String lastIDStatement = "SELECT MAX(id) FROM products";
    private String connectionString = "jdbc:mysql://localhost:3306/revenue?user=pyoung&password=password";
    private final static String findAllStatement = "SELECT * from products";

    public Product find(long id){
        return find(new Long(id));

    }

    public Product find(Long id){
        return (Product) abstractFind(id);
    }

    public Long insertProduct(Product p){
        return insert(p);

    }

    private Product getProductFromType(String type, String name) {
        switch (type) {
            case "Spreadsheet":
                return Product.newSpreadsheet(name, type);
            case "Word Processor":
                return Product.newWordProcessor(name, type);
            case "Database":
                return  Product.newDatabase(name, type);
        }
        return null;
    }

    @Override
    protected DomainObject doLoad(Long id, ResultSet rs) throws SQLException {
        String type = rs.getString(2);
        String name = rs.getString(3);

        Product result = getProductFromType(type, name);
        result.setID(id);
        return result;
    }

    @Override
    protected void doInsert(DomainObject p, PreparedStatement stmt) throws SQLException{
        stmt.setString(2, ((Product) p).getName());
        stmt.setString(3, ((Product) p).getType());
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
    protected String lastIDStatement() {
        return lastIDStatement;
    }

    @Override
    protected String connectionString() {
        return connectionString;
    }

    @Override
    protected String findAllStatement() {
        return findAllStatement;
    }
}