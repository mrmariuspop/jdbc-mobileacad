package jdbc;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@NoArgsConstructor
@ActiveRecordEntity(tablename="persons",keyColumnName="id")
public class Person extends ActiveRecord{
    public int id;
    public String firstname;
    public String lastname;
    public int age;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstname + '\'' +
                ", lastName='" + lastname + '\'' +
                ", age=" + age +
                '}';
    }


}
