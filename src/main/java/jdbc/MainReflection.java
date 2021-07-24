package jdbc;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainReflection {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
//        Person person1=new Person();
//    person1.setId(3);
//    person1.setAge(999);
//    person1.setLastname("Octavian");
//    person1.setFirstname("Goga");
//    person1.update();
//    Customer customer=new Customer();
//    customer.setId(2);
//    customer.delete();
//        Order order=new Order();
//        order.setId(9);
//        order.setOrder_date("2020-07-05");
//        order.setShipped_date("2020-07-05");
//        order.setComments("nothing");
//        order.setStatus("egqwg");
//        order.setCustomer_id(3);
//
//        order.insert();
        Order order=new Order();
        ArrayList<Order> orders;
        orders=order.selOrders();
        System.out.println(orders);
//        Customer customer=new Customer();
//        ArrayList<Customer> customers;
//        customers=customer.selCustomers();
//        System.out.println(customers);
//
    }

}
