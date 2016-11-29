package mappers;

import models.DomainObject;
import org.omg.CORBA.SystemException;

import java.io.Serializable;
import java.sql.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pyoung on 2016-11-27.
 */

public abstract class AbstractMapper {

    /**
     * loadedMap implements the pattern IdentityMap from [PoEAA, p195]
     * If to find a domain object, first check if this object is in memory, ie.
     * in the loadedMap, before pulling the data from database
     */
    protected Map<Serializable, DomainObject> loadedMap = new HashMap<Serializable, DomainObject>();

    private Connection db;

    private String concurrencyVariables = ",?,?,?)";
    private String checkVersionSQL = "SELECT version, modifiedBy, modified FROM customer WHERE id = ?";

    /**
     * the return string is domain specific
     */
    protected abstract String findStatement();
    protected abstract String connectionString();
    protected abstract String lastIDStatement();
    protected abstract String insertStatement();
    protected abstract String findAllStatement();

    /**
     * need to give this task to domain specific mapper
     * @param p
     * @param stmt
     * @throws SQLException
     */
    protected abstract void doInsert(DomainObject p, PreparedStatement stmt) throws SQLException ;

    /**
     * need to give this task to domain specific mapper
     * @param id
     * @param rs
     * @return
     * @throws SQLException
     */
    protected abstract DomainObject doLoad(Long id, ResultSet rs) throws SQLException;


    protected DomainObject abstractFind(Long id){
        if(db==null) setConnection();
        DomainObject result = (DomainObject) loadedMap.get(id);
        if(result != null) return result;
        PreparedStatement stmt = null;
        try {
            stmt = db.prepareStatement(findStatement());
            stmt.setLong(1, id.longValue());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            result = load(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected List<DomainObject> abstractFindAll(){
        if(db==null) setConnection();
        List<DomainObject> result = null;
        PreparedStatement stmt = null;

        try {
            stmt = db.prepareStatement(findAllStatement());
            ResultSet rs = stmt.executeQuery();
            return loadAll(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected List<DomainObject> abstractFindAllWithId(Long id){
        if(db==null) setConnection();
        List<DomainObject> result = null;
        PreparedStatement stmt = null;

        try {
            stmt = db.prepareStatement(findStatement());
            stmt.setLong(1, id.longValue());
            ResultSet rs = stmt.executeQuery();
            return loadAll(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<DomainObject> loadAll(ResultSet rs) throws SQLException{
        List<DomainObject> result = new ArrayList<DomainObject>();
        while(rs.next()){
            result.add(load(rs));
        }

        return result;
    }

    private DomainObject load(ResultSet rs) throws SQLException {
        Long id = rs.getLong(1);
        ResultSetMetaData rsmd = rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
        int version = rs.getInt(numberOfColumns);
        Timestamp modified = rs.getTimestamp(numberOfColumns-1);
        String modifiedBy = rs.getString(numberOfColumns-2);

        if(loadedMap.containsKey(id)) {
            DomainObject result = (DomainObject) loadedMap.get(id);

            result.setID(id);
            result.setModified(modified);
            result.setModifiedBy(modifiedBy);
            result.setVersion(version);

            return result;
        }
        DomainObject result = doLoad(id, rs);

        result.setID(id);
        result.setModified(modified);
        result.setModifiedBy(modifiedBy);
        result.setVersion(version);

        loadedMap.put(id, result);
        return result;
    }


    protected Long insert(DomainObject p){
        if(db == null) setConnection();
        PreparedStatement stmt = null;
        try{
            stmt = db.prepareStatement(insertStatement() + concurrencyVariables);
            DatabaseMetaData m = db.getMetaData();

            p.setID(findNextDatabaseId());
            
            doInsert(p, stmt);

            int colCount = objectNotChanged(p);
            if (colCount > 0) {
                stmt.setInt(1, p.getID().intValue());
                stmt.setString(colCount-2, m.getUserName());
                stmt.setInt(colCount, p.getVersion()+1);
                stmt.setTimestamp(colCount-1, p.getModified());
                stmt.executeUpdate();
                loadedMap.put(p.getClass(), p);
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        return p.getID();
    }

    private Long findNextDatabaseId() throws SQLException {
        PreparedStatement stmt = db.prepareStatement(lastIDStatement());
        ResultSet rs = stmt.executeQuery();
        rs.next();
        //System.out.println("in Person.findNextDatabaseId"+ rs.getInt(1));
        return (long) (rs.getInt(1)+1);
    }

    private void setConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");

            // Setup the connection with the DB
            db = DriverManager.getConnection(connectionString());
        } catch (ClassNotFoundException | SQLException e ) {
            e.printStackTrace();
        }
    }

    protected int objectNotChanged(DomainObject object) {
        if(db == null) setConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = db.prepareStatement(checkVersionSQL);
            stmt.setInt(1, (int) object.getID().longValue());
            rs = stmt.executeQuery();
            if (rs.next()) {
                int version = rs.getInt(3);
                String modifiedBy = rs.getString(2);
                Timestamp modified = rs.getTimestamp(1);
                if (version > object.getVersion())
                {
                    return -1;
                }
                else
                {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    return rsmd.getColumnCount();
                }
            }
            else {
                return -1;
            }
        }
        catch ( SQLException e ) {
            e.printStackTrace();
        }
        return -1;
    }
}
