package web;

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
            res.status(201);
            return "Hello World "+param;
        });
    }
}

