package web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdbc.DataSourceFactory;
import jdbc.Person;

import java.sql.Connection;

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
    }
}

