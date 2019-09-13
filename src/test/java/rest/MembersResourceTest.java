package rest;

import entities.Members;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class MembersResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    //Read this line from a settings-file  since used several places
    private static final String TEST_DB = "jdbc:mysql://localhost:3307/startcode_test";
    
    public static Members m;
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);

        //NOT Required if you use the version of EMF_Creator.createEntityManagerFactory used above        
        //System.setProperty("IS_TEST", TEST_DB);
        //We are using the database on the virtual Vagrant image, so username password are the same for all dev-databases
        
        httpServer = startServer();
        
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
   
        RestAssured.defaultParser = Parser.JSON;
    }
    
    @AfterAll
    public static void closeTestServer(){
        //System.in.read();
         httpServer.shutdownNow();
    }
    
    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        m = new Members("M", "B", "Yellow", "mb@cphbusiness.dk");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Members.deleteAllRows").executeUpdate();
            em.persist(m);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().
            contentType("application/json").
            get("/groupmembers/test").
        then().log().body().assertThat().
            statusCode(HttpStatus.OK_200.getStatusCode()).
            body("hello", equalTo("world"));
    }
   
    @Test
    public void testCount() throws Exception {
        given()
        .contentType("application/json")
        .get("/groupmembers/count").then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("count", equalTo(1));   
    }
    
    @Test
    public void testGetAll() throws Exception{
        List<String> email = new ArrayList<>();
        email.add(m.getEmail());
        given().
            contentType("application/json").
            get("/groupmembers/all").
        then().log().body().assertThat().
            statusCode(HttpStatus.OK_200.getStatusCode()).
            body("email", equalTo(email));
    }
    
    @Test 
    public void testGetMemberByID() throws Exception{
        given().
                contentType("application/json").
                get("/groupmembers/{id}", m.getId()).
        then().log().body().assertThat().
                statusCode(HttpStatus.OK_200.getStatusCode()).
                body("email", equalTo(m.getEmail()));
    }
    
    @Test
    public void testGetMembersByColor() throws Exception{
        List<Integer> memberID = new ArrayList<>();
        memberID.add(m.getId().intValue());
        given().
                contentType("application/json").
                get("/groupmembers/member/{color}", m.getColor()).
        then().log().body().assertThat().
                statusCode(HttpStatus.OK_200.getStatusCode()).
                body("id", equalTo(memberID));
    }
    
    @Test
    public void testGetMemberByEmail() throws Exception{
//        List<Integer> memberID = new ArrayList<>();
//        memberID.add(m.getId().intValue());
        given().
                contentType("application/json").
                get("/groupmembers/member/email/{mail}", m.getEmail()).
        then().log().body().assertThat().
                statusCode(HttpStatus.OK_200.getStatusCode()).
                body("lastname", equalTo(m.getLastname()));
    }
    
//    @Test
//    public void testChangeMemberColor() throws Exception{
//        given().
//                contentType("application/json").
//                get("/groupmembers/member/{id}/change/{color}", m.getId(), "Red").
//        then().log().body().assertThat().
//                statusCode(HttpStatus.OK_200.getStatusCode()).
//                body("mail", equalTo(m.getEmail()));
//    }
}
