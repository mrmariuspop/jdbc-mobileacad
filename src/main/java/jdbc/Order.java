package jdbc;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@ActiveRecordEntity(tablename="orders",keyColumnName="id")
public class Order extends ActiveRecord{
    public int id;
    public String order_date;
    public String shipped_date;
    public String status;
    public String comments;
    public int customer_id;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", order_date='" + order_date + '\'' +
                ", shipped_date='" + shipped_date + '\'' +
                ", status='" + status + '\'' +
                ", comments='" + comments + '\'' +
                ", customer_id=" + customer_id +
                '}';
    }
}
