package jdbc;

// create , update , read , delete
// insert into , values

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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


}
