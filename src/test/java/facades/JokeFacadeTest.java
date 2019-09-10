package facades;

import entities.Joke;
import java.util.List;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Settings;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

/**
 *
 * @author Brandstrup
 */
//Uncomment the line below, to temporarily disable this test
//@Disabled
public class JokeFacadeTest
{

    private static EntityManagerFactory emf;
    private static JokeFacade facade;
    //Change this to change the number of dummy entries to be added to the test DB
    private static int numberOfDummies = 10;

    public JokeFacadeTest()
    {
    }

    //@BeforeAll
    public static void setUpClass()
    {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/startcode_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = JokeFacade.getJokeFacade(emf);
    }

    /*   **** HINT **** 
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that. 
        See below for how to use these files. This is our RECOMENDED strategy
     */
    @BeforeAll
    public static void setUpClassV2()
    {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = JokeFacade.getJokeFacade(emf);
    }

    @AfterAll
    public static void tearDownClass()
    {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            em.createNamedQuery("Joke.deleteAllRows").executeUpdate();

            for (int i = 0; i < numberOfDummies; i++)
            {
                String title = "title" + i;
                String body = "body" + i;
                String reference = "reference" + i;
                em.persist(new Joke(title, body, reference, Joke.JokeType.PUNS));
            }
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }

    @AfterEach
    public void tearDown()
    {
//        Remove any data after each test was run
    }

    @Test
    public void getAllJokesTest()
    {
        List<Joke> jokeList = facade.getAllJokes();
        assertFalse(jokeList == null);
        assertFalse(jokeList.isEmpty());
        assertEquals(numberOfDummies, jokeList.size(), "Expects " + numberOfDummies + " rows in the database");
    }

    @Test
    public void getJokeByIdTest()
    {
        List<Joke> jokeList = facade.getAllJokes();
        assertFalse(jokeList == null);
        assertFalse(jokeList.isEmpty());
        assertTrue(jokeList.get(0).getId() > 0);
    }

    //Should have a very small chance of failing, but is not guaranteed
    @Test
    public void getRandomJokeTest()
    {
        EntityManager em = emf.createEntityManager();
        //overrides numberOfDummies
        int n = 1000;
        try
        {
            em.getTransaction().begin();
            em.createNamedQuery("Joke.deleteAllRows").executeUpdate();
            for (int i = 0; i < n; i++)
            {
                String title = "title" + i;
                String body = "body" + i;
                String reference = "reference" + i;
                em.persist(new Joke(title, body, reference, Joke.JokeType.PUNS));
            }
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }

        List<Joke> jokeList = facade.getAllJokes();
        assertFalse(jokeList == null);
        assertFalse(jokeList.isEmpty());
        assertTrue(jokeList.get(0).getId() > 0);
        assertEquals(n, jokeList.size());
        assertFalse(facade.getRandomJoke().getId().equals(facade.getRandomJoke().getId()));

    }
}
