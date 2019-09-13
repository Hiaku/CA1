package facades;

import entities.Joke;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Brandstrup
 */
public class JokeFacade
{

    private static JokeFacade instance;
    private static EntityManagerFactory emf;
    private static Random rng = new Random();

    //Private Constructor to ensure Singleton
    private JokeFacade()
    {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static JokeFacade getJokeFacade(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new JokeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public long getJokeCount()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(j) FROM Joke j").getSingleResult();
            return renameMeCount;
        }
        finally
        {
            em.close();
        }

    }

    public List<Joke> getAllJokes()
    {
        EntityManager em = getEntityManager();
        try
        {
            TypedQuery<Joke> query
                    = em.createQuery("SELECT j FROM Joke j", Joke.class);
            return query.getResultList();
        }
        finally
        {
            em.close();
        }
    }

    public Joke getJokeById(long id)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            Joke joke = em.find(Joke.class, id);
            return joke;
        }
        finally
        {
            em.close();
        }
    }

    public Joke getRandomJoke()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            List<Joke> jokeList = getAllJokes();
            int listSize = jokeList.size();

            return jokeList.get(rng.nextInt(listSize));
        }
        finally
        {
            em.close();
        }
    }

    public void populateJokes(int numberOfEntries)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            em.createNamedQuery("Joke.deleteAllRows").executeUpdate();
            for (int i = 0; i < numberOfEntries; i++)
            {
                String title = "title" + i;
                String body = "body" + i;
                String reference = "reference" + i;
                em.persist(new Joke(title, body, reference, Joke.JokeType.Puns));
            }
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }
}
