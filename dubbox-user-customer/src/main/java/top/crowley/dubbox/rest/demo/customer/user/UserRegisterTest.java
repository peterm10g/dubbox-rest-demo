package top.crowley.dubbox.rest.demo.customer.user;

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

import top.crowley.dubbox.rest.demo.api.pojo.TscUserProfile;


public class UserRegisterTest {
    private static String url;
    private static String del_url;
    private static Logger LOGGER = LoggerFactory.getLogger(UserRegisterTest.class);
    private static String userId = "USeRid";
    private static String userId1 = "userIId";
    
    
    @BeforeClass
    public static void init() {
        url = "http://localhost:10880/user";
        del_url = "http://localhost:10880/user/"+userId;
        LOGGER.info("Registering user url: {}", url);
    }
    
    @AfterClass
    public static void afterClass() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(del_url);
        Response response = target.request().delete();
        try {
            LOGGER.info("delete user response status: {}, body:{}", response.getStatus(), response.readEntity(String.class));
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
    public void register0() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Date date = new Date();
        TscUserProfile user = new TscUserProfile(userId, "123456", "VIN1998485","17677777777", "name", "address", "salt", date, date);
        Response response = target.request().post(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));
        try {
            LOGGER.info("register0 response body: {}", response.readEntity(String.class));
            assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
        } finally {
            response.close();
            client.close();
        }
    }
    
    /**
     * test user data exist.
     */
    @Test
    public void register1() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Date date = new Date();
        TscUserProfile user = new TscUserProfile(userId, "123456", "VIN1998485","17677777777", "name", "address", "salt", date, date);
        Response response = target.request().post(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));
        try {
            LOGGER.info("register1 response body: {}", response.readEntity(String.class));
            assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        } finally {
            response.close();
            client.close();
        }
    }
    
    /**
     * test user parameter invalid.
     */
    @Test
    public void register2() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Date date = new Date();
        //vin out of size.should be max 17.
        String vin = "VINDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD";
        //password is not blank.
        String password = null;
        TscUserProfile user = new TscUserProfile(userId1, password, vin,"17677777777", "name", "address", "salt", date, date);
        Response response = target.request().post(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));
        try {
            LOGGER.info("register2 response body: {}", response.readEntity(String.class));
            assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        } finally {
            response.close();
            client.close();
        }
    }
}
