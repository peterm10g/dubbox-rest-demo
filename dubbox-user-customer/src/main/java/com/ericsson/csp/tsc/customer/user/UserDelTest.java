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

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.csp.tsc.api.pojo.TscUserProfile;

public class UserDelTest {
    private static String save_url;
    private static String del_url;
    
    private static Logger LOGGER = LoggerFactory.getLogger(UserRegisterTest.class);
    private static String testUserId;
    
    @BeforeClass
    public static void init() {
        save_url = "http://localhost:10880/user";
        testUserId = "tuxxxx";
        del_url = "http://localhost:10880/user/"+ testUserId;
        LOGGER.info("Registering user save_url: {};del_url:{};testUserId: {}", save_url, del_url, testUserId);
    }
    
    @BeforeClass
    public static void before() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(save_url);
        Date date = new Date();
        TscUserProfile user = new TscUserProfile(testUserId, "123456", "NDk33","17677777777", "name", "address", "salt", date, date);
        Response response = target.request().post(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));
        try {
            LOGGER.info("save user response status: {}, body: {}", response.getStatus(), response.readEntity(String.class));
        } finally {
            response.close();
            client.close();
        }
    }
    /**
     * test correct operate
     */
    @Test
    public void del() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(del_url);
        Response response = target.request().delete();
        try {
            LOGGER.info("save user response status: {}, body:{}", response.getStatus(), response.readEntity(String.class));
            assertEquals(Status.OK.getStatusCode(), response.getStatus());
        } finally {
            response.close();
            client.close();
        }
    }
    
    /**
     * test not exist delete object
     */
    @Test
    public void del1() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(del_url+"1");
        Response response = target.request().delete();
        try {
            LOGGER.info("save user response status: {}, body:{}", response.getStatus(), response.readEntity(String.class));
            assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
        } finally {
            response.close();
            client.close();
        }
    }
}
