package com.ericsson.csp.tsc.customer.user;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.csp.tsc.api.pojo.TscUserProfile;


public class UserQueryTest {
    private static Logger LOGGER = LoggerFactory.getLogger(UserQueryTest.class);
    private static String url;
    private static String userId;
    private static String vin;
    
    @BeforeClass
    public static void init() {
        url = "http://localhost:10880/user/";
        userId = "fff3";
        vin = "VIN999839";
        
        //register a test User
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Date date = new Date();
        TscUserProfile user = new TscUserProfile(userId, "123456", vin,"17677777777", "name", "address", "salt", date, date);
        Response response = target.request().post(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));
        try {
            LOGGER.info("save user response status: {}, body: {}", response.getStatus(), response.readEntity(String.class));
        } finally {
            response.close();
            client.close();
        }
    }
    
    @AfterClass
    public static void after() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url+userId);
        Response response = target.request().delete();
        try {
            LOGGER.info("delete user response status: {}, body:{}", response.getStatus(), response.readEntity(String.class));
        } finally {
            response.close();
            client.close();
        }
    }
    
    /**
     * test correct operate
     */
    @Test
    public void query1() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url+userId);
        Response response = target.request().get();
        try {
            LOGGER.info("query1 response body: {}", response.readEntity(String.class));
            assertEquals(Status.OK.getStatusCode(), response.getStatus());
        } finally {
            response.close();
            client.close();
        }
    }
    
    /**
     * test correct operate
     */
    @Test
    public void query2() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url+vin);
        Response response = target.request().get();
        try {
            LOGGER.info("query2 response body: {}", response.readEntity(String.class));
            assertEquals(Status.OK.getStatusCode(), response.getStatus());
        } finally {
            response.close();
            client.close();
        }
    }
    
    /**
     * query no result.
     */
    @Test
    public void query3() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url+"1293009");
        Response response = target.request().get();
        try {
            LOGGER.info("query3 response body: {}", response.readEntity(String.class));
            assertEquals(Status.OK.getStatusCode(), response.getStatus());
        } finally {
            response.close();
            client.close();
        }
    }
}
