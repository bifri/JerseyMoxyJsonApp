/*package com.example;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MyResourceTest {

    private static final String TEST_DB_NAME = "test_ai_db";
    
    private HttpServer mServer;
    private EmployeeLoggerDAO mDB;
    private WebTarget mTarget;

    @Before
    public void setUp() throws Exception {
        // start the server
        mServer = Main.startServer();
        
        // init db
        mDB = Main.initDb(TEST_DB_NAME);

        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        mTarget = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        mServer.shutdownNow();
        mDB.shutdown();
    }

    @Test
    public void testGetIt() {
        String responseMsg;
        
        responseMsg = mTarget.path("checkin").request(MediaType.APPLICATION_JSON).post(String.class);
        
        
        responseMsg = mTarget.path("checkin").request(MediaType.APPLICATION_JSON).get(String.class);
        assertEquals("Got it!", responseMsg);
    }
}
*/