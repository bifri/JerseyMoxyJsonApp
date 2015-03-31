package com.example;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("checkout")
public class CheckOutResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EmployeeLoggerBean postCheck(EmployeeBean employee) throws SQLException {
        long currTime = System.currentTimeMillis();
        
        Main.getDB().insertCheck(employee.name, "out", currTime);
        
        String time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .format(new Date(currTime));
        
        return new EmployeeLoggerBean(employee.name, time);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EmployeeLoggerBean> getCheck() throws SQLException {
        return Main.getDB().queryAll("out");
    }
    
    @Path("/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TimeBean> getTimes(@PathParam("name") String employeeName) throws SQLException {
        return Main.getDB().queryByName("out", employeeName);
    }
}
