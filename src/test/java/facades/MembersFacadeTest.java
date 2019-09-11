package facades;

import dto.MembersDTO;
import utils.EMF_Creator;
import entities.Members;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Settings;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class MembersFacadeTest {

    private static EntityManagerFactory emf;
    private static MembersFacade facade;
    public static Members member;
    public static List<Members> members = new ArrayList<>();

    public MembersFacadeTest() {
    }

    //@BeforeAll
    public static void setUpClass() {
//        emf = EMF_Creator.createEntityManagerFactory(
//                "pu",
//                "jdbc:mysql://localhost:3307/startcode_test",
//                "dev",
//                "ax2",
//                EMF_Creator.Strategy.CREATE);
//        facade = MembersFacade.getMembersFacade(emf);
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST,Strategy.DROP_AND_CREATE);
    }

    @BeforeAll
    public static void setUpClassV2() {
       emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST,Strategy.DROP_AND_CREATE);
       facade = MembersFacade.getMembersFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        facade = MembersFacade.getMembersFacade(emf);
        
        member = new Members("N", "T", "Yellow", "nt@cphbusiness.dk");
        members.add(member);
        
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("DELETE FROM MEMBERS").executeUpdate();
            em.persist(member);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
    }

    // TODO: Delete or change this method 
    @Test
    public void testGetMembersCount() {
        Long expected = 1l;
        Long result = facade.getAllMembersCount();
        assertEquals(expected, result);
    }
    
    @Test
    public void testGetAllMembers(){
        List<MembersDTO> expected = new ArrayList<>();
        expected.add(new MembersDTO(member));
        
        List<MembersDTO> result = facade.getAllMembers();
        
        assertEquals(expected, result);
    }
    
    // Not sure if it works
    @Test
    public void testGetMembersByID(){
        Long expected = new MembersDTO(member).getId();
        
        Long result = member.getId();
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testGetMembersByColor(){
        List<MembersDTO> expected = new ArrayList<>();
        expected.add(new MembersDTO(member));
        
        List<MembersDTO> result = facade.getMembersByColor("Yellow");
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testGetMembersByEmail(){
        String expected = new MembersDTO(member).getEmail();
        
        String result = member.getEmail();
        
        assertEquals(expected, result);
    }
    
    @Test 
    public void testGetAddMember(){
        
        Members newM = new Members("M", "B", "Yellow", "");
        
        Members result = facade.addMember(newM);
        
        assertEquals(newM, result);
        
        EntityManager em = emf.createEntityManager();
        try{
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.remove(em.find(Members.class, new Long(members.size()+1)));
            em.getTransaction().commit();
        } finally{
            em.close();
        }
    }
}
