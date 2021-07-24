package web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdbc.Customer;
import jdbc.DataSourceFactory;
import jdbc.Order;
import jdbc.Person;

import java.sql.Connection;
import java.util.ArrayList;

import static spark.Spark.*;

public class SparkMain {
    public static void main(String[] args) {
        port(8081);
        get("/hello", (req, res) -> {
            String param=req.queryParams("name");
            res.status(201);
            return "Hello World "+param;});
        get("/hello/:name/word",(req,res)->{
            String param=req.params(":name");
            ObjectMapper objMap = new ObjectMapper();

            Response response = new Response("De la mine!"+param,null,1);
            res.status(201);
            String toJson = objMap.writeValueAsString(response);
            return toJson;
        });
        get("/person/:id",(req,res)->{
            String param=req.params(":id");
            int id = Integer.parseInt(param);
            ObjectMapper objMap = new ObjectMapper();
            DataSourceFactory dataSourceFactory = new DataSourceFactory();
            Connection connection = dataSourceFactory.getConnection();
            Person person1=new Person();
            person1.getById(id);
            res.status(201);
            String toJson = objMap.writeValueAsString(person1);
            return toJson;
        });
        get("/customer/:id",(req,res)->{ //getById
            String param=req.params(":id");
            int id = Integer.parseInt(param);
            ObjectMapper objMap = new ObjectMapper();
            DataSourceFactory dataSourceFactory = new DataSourceFactory();
            Connection connection = dataSourceFactory.getConnection();
            Customer customer=new Customer();
            customer.getById(id);
            res.status(201);
            String toJson = objMap.writeValueAsString(customer);
            return toJson;
        });
        put("/customer/:id/delete",(req,res)->{ //DELETE BY ID
            String param=req.params(":id");
            int id = Integer.parseInt(param);
            ObjectMapper objMap = new ObjectMapper();
            DataSourceFactory dataSourceFactory = new DataSourceFactory();
            Connection connection = dataSourceFactory.getConnection();
            Customer customer=new Customer();
            customer.getById(id);
            customer.delete();
//            String toJson = objMap.writeValueAsString(customer);
            return 1;
        });
        put("/customer/:id/insert",(req,res)->{ //insert BY ID
            String param=req.params(":id");
            String body=req.body();
            int id = Integer.parseInt(param);
            ObjectMapper objMap = new ObjectMapper();
            DataSourceFactory dataSourceFactory = new DataSourceFactory();
            Connection connection = dataSourceFactory.getConnection();
//            Customer customer=new Customer();
//            customer.setId(id);
            Customer customer=objMap.readValue(req.body(),Customer.class);
            customer.setId(id);
            customer.insert();
//            String toJson = objMap.writeValueAsString(customer);
            return 1;
        });
        put("/customer/:id/update",(req,res)->{ //update BY ID
            String param=req.params(":id");
            String body=req.body();
            int id = Integer.parseInt(param);
            ObjectMapper objMap = new ObjectMapper();
            DataSourceFactory dataSourceFactory = new DataSourceFactory();
            Connection connection = dataSourceFactory.getConnection();
//            Customer customer=new Customer();
//            customer.setId(id);
            Customer customer=new Customer();
            customer.getById(id);
            customer=objMap.readValue(req.body(),Customer.class);
            customer.update();
//            String toJson = objMap.writeValueAsString(customer);
            return 1;
        });
        get("/order/:id",(req,res)->{ //getById
            String param=req.params(":id");
            int id = Integer.parseInt(param);
            ObjectMapper objMap = new ObjectMapper();
            DataSourceFactory dataSourceFactory = new DataSourceFactory();
            Connection connection = dataSourceFactory.getConnection();
            Order order=new Order();
            order.getById(id);
            res.status(201);
            String toJson = objMap.writeValueAsString(order);
            return toJson;
        });
        get("/order",(req,res)->{ //getAll
            ObjectMapper objMap = new ObjectMapper();
            String param=req.queryParams("customer_id");
            if(param!=null)
            {int id = Integer.parseInt(param);
            DataSourceFactory dataSourceFactory = new DataSourceFactory();
            Connection connection = dataSourceFactory.getConnection();
            ArrayList<Order> orders=new ArrayList<>();
            orders=new Order().selOrders(id);
            res.status(201);
            String toJson = objMap.writeValueAsString(orders);
            return toJson;}
            else{
                DataSourceFactory dataSourceFactory = new DataSourceFactory();
                Connection connection = dataSourceFactory.getConnection();
                ArrayList<Order> orders=new ArrayList<>();
                orders=new Order().selOrders();
                res.status(201);
                String toJson = objMap.writeValueAsString(orders);
                return toJson;
            }
        });
        get("/customer",(req,res)->{ //getAll
            ObjectMapper objMap = new ObjectMapper();
            DataSourceFactory dataSourceFactory = new DataSourceFactory();
            Connection connection = dataSourceFactory.getConnection();
            ArrayList<Customer> customers=new ArrayList<>();
            customers=new Customer().selCustomers();
            res.status(201);
            String toJson = objMap.writeValueAsString(customers);
            return toJson;
        });

        put("/order/:id/delete",(req,res)->{ //DELETE BY ID
            String param=req.params(":id");
            int id = Integer.parseInt(param);
            ObjectMapper objMap = new ObjectMapper();
            DataSourceFactory dataSourceFactory = new DataSourceFactory();
            Connection connection = dataSourceFactory.getConnection();
            Order order=new Order();
            order.getById(id);
            order.delete();
//            String toJson = objMap.writeValueAsString(customer);
            return 1;
        });
        put("/order/:id/insert",(req,res)->{ //insert BY ID
            String param=req.params(":id");
            String body=req.body();
            int id = Integer.parseInt(param);
            ObjectMapper objMap = new ObjectMapper();
            DataSourceFactory dataSourceFactory = new DataSourceFactory();
            Connection connection = dataSourceFactory.getConnection();
//            Customer customer=new Customer();
//            customer.setId(id);
            Order order=objMap.readValue(req.body(),Order.class);
            order.setId(id);
            order.insert();
//            String toJson = objMap.writeValueAsString(customer);
            return 1;
        });
        put("/order/:id/update",(req,res)->{ //update BY ID
            String param=req.params(":id");
            String body=req.body();
            int id = Integer.parseInt(param);
            ObjectMapper objMap = new ObjectMapper();
            DataSourceFactory dataSourceFactory = new DataSourceFactory();
            Connection connection = dataSourceFactory.getConnection();
//            Customer customer=new Customer();
//            customer.setId(id);
            Order order=new Order();
            order.getById(id);
            Order order2=objMap.readValue(req.body(),Order.class);
            order.setStatus(order2.getStatus());
            order.update();
//            String toJson = objMap.writeValueAsString(customer);
            return 1;
        });



        



    }
}

