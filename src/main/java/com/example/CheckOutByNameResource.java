package com.example;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("checkout/{name}")
public class CheckOutByNameResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TimeBean> getTimes(@PathParam("name") String employeeName) throws SQLException {
        return Main.getDB().queryByName("out", employeeName);
    }
}