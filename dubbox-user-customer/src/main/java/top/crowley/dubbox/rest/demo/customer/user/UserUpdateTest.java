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


public class UserUpdateTest {
    private static Logger LOGGER = LoggerFactory.getLogger(UserUpdateTest.class);
    private static String save_url;
    private static String testUserId;
    private static String update_url;
    private static String del_url;
    
    @BeforeClass
    public static void init() {
        //init parameter
        save_url = "http://localhost:10880/user";
        testUserId = "tuIdxx";
        update_url = "http://localhost:10880/user/"+ testUserId;
        del_url = "http://localhost:10880/user/"+ testUserId;
        LOGGER.info("Registering user save_url: {};update_url:{};del_url:{};testUserId: {}", save_url, update_url, del_url, testUserId);
        //register a test User
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(save_url);
        Date date = new Date();
        TscUserProfile user = new TscUserProfile(testUserId, "123456", "Nkdk223","17677777777", "name", "address", "salt", date, date);
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
        WebTarget target = client.target(del_url);
        Response response = target.request().delete();
        try {
            LOGGER.info("del user response status: {}, body:{}", response.getStatus(), response.readEntity(String.class));
        } finally {
            response.close();
            client.close();
        }
    }
    /**
     * test correct operate
     */
    @Test
    public void update() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(update_url);
        Date date = new Date();
        TscUserProfile user = new TscUserProfile(testUserId, "123456", "NBLD24433","17677777777", "updateName", "address", "salt", date, date);
        Response response = target.request().put(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));
        try {
            LOGGER.info("update user response body: {}", response.readEntity(String.class));
            assertEquals(Status.OK.getStatusCode(), response.getStatus());
        } finally {
            response.close();
            client.close();
        }
    }
    /**
     * test userId is blank
     */
    @Test
    public void update1() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(update_url);
        Date date = new Date();
        TscUserProfile user = new TscUserProfile("", "123456", "京A23432","17677777777", "updateName", "address", "salt", date, date);
        Response response = target.request().put(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));
        try {
            LOGGER.info("update1 response body: {}", response.readEntity(String.class));
            assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        } finally {
            response.close();
            client.close();
        }
    }
    
    /**
     * test uses property not matched.
     */
    @Test
    public void update2() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(update_url);
        Date date = new Date();
        TscUserProfile user = new TscUserProfile(testUserId, "123456", "京skkks","17677777777", "updateName", "address", "salt", date, date);
        Response response = target.request().put(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));
        try {
            LOGGER.info("update2 response body: {}", response.readEntity(String.class));
            assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        } finally {
            response.close();
            client.close();
        }
    }
    
    /**
     * test update user is not exist.
     */
    @Test
    public void update3() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(update_url);
        Date date = new Date();
        TscUserProfile user = new TscUserProfile("DDDDDXX", "123456", "NH9499003","17677777777", "updateName", "address", "salt", date, date);
        Response response = target.request().put(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));
        try {
            LOGGER.info("update3 response body: {}", response.readEntity(String.class));
            assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
        } finally {
            response.close();
            client.close();
        }
    }
}
