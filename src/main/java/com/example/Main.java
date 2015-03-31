package com.example;


import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.ext.ContextResolver;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.server.ResourceConfig;

public class Main {

    public static final URI BASE_URI = URI.create("http://localhost:8080/myapp/");
    private static final String DB_NAME = "ai_db";
    
    private static EmployeeLoggerDAO sDB;
    
    public static void main(String[] args) {
        try {
            System.out.println("JSON with MOXy Jersey Example App");
            sDB = initDb(DB_NAME);
            final HttpServer server = startServer();
            System.out.println(String.format("Application started.%nHit enter to stop it..."));
            System.in.read();
            server.shutdownNow();
            sDB.shutdown();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, createApp());
    }
        
    public static ResourceConfig createApp() {
        return new ResourceConfig()
                .packages("com.example")
                .register(createMoxyJsonResolver());
    }

    public static ContextResolver<MoxyJsonConfig> createMoxyJsonResolver() {
        final MoxyJsonConfig moxyJsonConfig = new MoxyJsonConfig();
        Map<String, String> namespacePrefixMapper = new HashMap<String, String>(1);
        namespacePrefixMapper.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
        moxyJsonConfig.setNamespacePrefixMapper(namespacePrefixMapper).setNamespaceSeparator(':');
        return moxyJsonConfig.resolver();
    }
    
    public static EmployeeLoggerDAO initDb(String name) throws Exception {
        return new EmployeeLoggerDAO(name);
    }
    
    public static EmployeeLoggerDAO getDB() {
        return sDB;
    }
}