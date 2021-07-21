package web;

import static spark.Spark.*;

public class SparkMain {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
    }
}

