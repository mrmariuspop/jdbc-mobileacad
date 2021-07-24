package jdbc;

// create , update , read , delete
// insert into , values

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class ActiveRecord {

    // INSERT INTO persons(id, firstname,lastname,age) VALUES (?,  ? , ? , ?);
    public boolean insert() throws SQLException, ClassNotFoundException, IllegalAccessException {
        Class<ActiveRecord> c = (Class<ActiveRecord>) this.getClass();
        ActiveRecordEntity arAnnotation = (ActiveRecordEntity) c.getAnnotation(ActiveRecordEntity.class);
        Field[] fields = this.getClass().getFields();
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO " + arAnnotation.tablename() + " (");
        byte fieldsCount = 0;
        for (Field field : fields) {
            fieldsCount++;
            query.append(field.getName() + ",");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(") VALUES (");
        for (int i = 0; i < fieldsCount; i++) {
            query.append("?,");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(");");
        DataSourceFactory factory=new DataSourceFactory();
        Connection connection = factory.getConnection();
        PreparedStatement ptstmt = connection.prepareStatement(query.toString());
        byte count = 1;
        for (Field field : fields) {
            if(field.getType() == int.class){
                ptstmt.setInt(count++, field.getInt(this));
            } else if(field.getType() == String.class) {
                ptstmt.setString(count++, (String) field.get(this));
            }
        }
        ptstmt.execute();
        connection.commit();
        return true;
    }
    public boolean update() throws SQLException, ClassNotFoundException, IllegalAccessException {
        Class<ActiveRecord> c = (Class<ActiveRecord>) this.getClass();
        ActiveRecordEntity arAnnotation = (ActiveRecordEntity) c.getAnnotation(ActiveRecordEntity.class);
        Field[] fields = this.getClass().getFields();
        StringBuilder query = new StringBuilder();
        query.append("UPDATE " + arAnnotation.tablename() + " SET ");
        byte fieldsCount = 0;
        for (Field field : fields) {
            fieldsCount++;
            query.append(field.getName() + " =? ,");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(" WHERE "+arAnnotation.keyColumnName()+" ="+fields[0].getInt(this));
        DataSourceFactory factory=new DataSourceFactory();
        Connection connection = factory.getConnection();
        PreparedStatement ptstmt = connection.prepareStatement(query.toString());
        byte count = 1;
        for (Field field : fields) {
            if(field.getType() == int.class){
                ptstmt.setInt(count++, field.getInt(this));
            } else if(field.getType() == String.class) {
                ptstmt.setString(count++, (String) field.get(this));
            }
        }
        ptstmt.execute();
        connection.commit();
        return true;
    }


    public boolean delete() throws SQLException, ClassNotFoundException, IllegalAccessException {
        Class<ActiveRecord> c = (Class<ActiveRecord>) this.getClass();
        ActiveRecordEntity arAnnotation = (ActiveRecordEntity) c.getAnnotation(ActiveRecordEntity.class);
        Field[] fields = this.getClass().getFields();
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM " + arAnnotation.tablename()+" WHERE ");
        query.append(fields[0].getName() + "= ");

        query.append("?");

        DataSourceFactory factory=new DataSourceFactory();
        Connection connection = factory.getConnection();
        PreparedStatement ptstmt = connection.prepareStatement(query.toString());
        byte count = 1;
            if(fields[0].getType() == int.class){
                ptstmt.setInt(count++, fields[0].getInt(this));
            }
        query.append(";");
        ptstmt.execute();
        connection.commit();
        return true;
    }


    public boolean getById(int id) throws SQLException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
        Class<ActiveRecord> c = (Class<ActiveRecord>) this.getClass();
        ActiveRecordEntity arAnnotation = (ActiveRecordEntity) c.getAnnotation(ActiveRecordEntity.class);
        DataSourceFactory factory = new DataSourceFactory();
        Connection conn = DataSourceFactory.getConnection();
        PreparedStatement st = conn.prepareStatement("select * from " + arAnnotation.tablename() + " where id = ?");
        st.setInt(1, id);
        ResultSet res = st.executeQuery();
        if(!res.isBeforeFirst()){
            return false;
        } else {
            res.next();
            ResultSetMetaData metadata = res.getMetaData();
            int colCount = metadata.getColumnCount();
            for(int i=1;i<colCount+1;i++){
                Field f = c.getField(metadata.getColumnName(i).toLowerCase());
                if(f.getType()==int.class){
                    f.setInt(this, res.getInt(i));
                } else
                if(f.getType()==byte.class){
                    f.setByte(this, res.getByte(i));
                } else
                if(f.getType()==String.class){
                    f.set(this, res.getString(i));
                }  else
                if(f.getType()==Date.class){
                    f.set(this, res.getDate(i));
                }
            }
        }
        return true;
    }
    public ArrayList<Customer> selCustomers() throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<ActiveRecord> c = (Class<ActiveRecord>) this.getClass();
        ActiveRecordEntity arAnnotation = (ActiveRecordEntity) c.getAnnotation(ActiveRecordEntity.class);
        ArrayList<Customer> customers=new ArrayList<Customer>();
        DataSourceFactory factory = new DataSourceFactory();
        Connection conn = DataSourceFactory.getConnection();
        PreparedStatement st = conn.prepareStatement("select * from " + arAnnotation.tablename());
        ResultSet res = st.executeQuery();
        int k=0;
        if(!res.isBeforeFirst()){
            return null;
        } else {

            while (res.next()) {
                ResultSetMetaData metadata = res.getMetaData();
                int colCount = metadata.getColumnCount();
                Customer cust = new Customer();
                for (int i = 1; i < colCount + 1; i++) {
                    Field f = c.getField(metadata.getColumnName(i).toLowerCase());
                    if (f.getType() == int.class) {
                        f.setInt(cust, res.getInt(i));
                    } else if (f.getType() == byte.class) {
                        f.setByte(cust, res.getByte(i));
                    } else if (f.getType() == String.class) {
                        f.set(cust, res.getString(i));
                    } else if (f.getType() == Date.class) {
                        f.set(cust, res.getDate(i));
                    }
                }
                customers.add(cust);
                k++;
            }
        }
        return customers;
    }
    public ArrayList<Order> selOrders() throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<ActiveRecord> c = (Class<ActiveRecord>) this.getClass();
        ActiveRecordEntity arAnnotation = (ActiveRecordEntity) c.getAnnotation(ActiveRecordEntity.class);
        ArrayList<Order> orders=new ArrayList<Order>();
        DataSourceFactory factory = new DataSourceFactory();
        Connection conn = DataSourceFactory.getConnection();
        PreparedStatement st = conn.prepareStatement("select * from " + arAnnotation.tablename());
        ResultSet res = st.executeQuery();
        int k=0;
        if(!res.isBeforeFirst()){
            return null;
        } else {

            while (res.next()) {
                ResultSetMetaData metadata = res.getMetaData();
                int colCount = metadata.getColumnCount();
                Order ord = new Order();
                for (int i = 1; i < colCount + 1; i++) {
                    Field f = c.getField(metadata.getColumnName(i).toLowerCase());
                    if (f.getType() == int.class) {
                        f.setInt(ord, res.getInt(i));
                    } else if (f.getType() == byte.class) {
                        f.setByte(ord, res.getByte(i));
                    } else if (f.getType() == String.class) {
                        f.set(ord, res.getString(i));
                    } else if (f.getType() == Date.class) {
                        f.set(ord, res.getDate(i));
                    }
                }
                orders.add(ord);
                k++;
            }
        }
        return orders;
    }
    public ArrayList<Order> selOrders(int id) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<ActiveRecord> c = (Class<ActiveRecord>) this.getClass();
        ActiveRecordEntity arAnnotation = (ActiveRecordEntity) c.getAnnotation(ActiveRecordEntity.class);
        ArrayList<Order> orders=new ArrayList<Order>();
        DataSourceFactory factory = new DataSourceFactory();
        Connection conn = DataSourceFactory.getConnection();
        PreparedStatement st = conn.prepareStatement("select * from " + arAnnotation.tablename()+" where customer_id = "+id);
        ResultSet res = st.executeQuery();
        int k=0;
        if(!res.isBeforeFirst()){
            return null;
        } else {

            while (res.next()) {
                ResultSetMetaData metadata = res.getMetaData();
                int colCount = metadata.getColumnCount();
                Order ord = new Order();
                for (int i = 1; i < colCount + 1; i++) {
                    Field f = c.getField(metadata.getColumnName(i).toLowerCase());
                    if (f.getType() == int.class) {
                        f.setInt(ord, res.getInt(i));
                    } else if (f.getType() == byte.class) {
                        f.setByte(ord, res.getByte(i));
                    } else if (f.getType() == String.class) {
                        f.set(ord, res.getString(i));
                    } else if (f.getType() == Date.class) {
                        f.set(ord, res.getDate(i));
                    }
                }
                orders.add(ord);
                k++;
            }
        }
        return orders;
    }



}
