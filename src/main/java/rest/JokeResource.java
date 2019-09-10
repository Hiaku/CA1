package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.JokeDTO;
import entities.Joke;
import facades.JokeFacade;
import java.util.ArrayList;
import java.util.List;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Brandstrup
 */
@Path("joke")
public class JokeResource
{

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/startcode",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final JokeFacade FACADE = JokeFacade.getJokeFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(
            {
                MediaType.APPLICATION_JSON
            })
    public String demo()
    {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("/count")
    @GET
    @Produces(
            {
                MediaType.APPLICATION_JSON
            })
    public String getJokeCountToJson()
    {
        long count = FACADE.getJokeCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllJokesToJson()
    {
        List<JokeDTO> DTOList = new ArrayList<>();
        List<Joke> EntityList = FACADE.getAllJokes();

        for (Joke joke : EntityList)
        {
            DTOList.add(new JokeDTO(joke));
        }
        return GSON.toJson(DTOList);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJokeByIdToJson(@PathParam("id") long jokeId)
    {
        Joke joke = FACADE.getJokeById(jokeId);
        JokeDTO jokeDTO = new JokeDTO(joke);
        return GSON.toJson(jokeDTO);
    }
    
    @GET
    @Path("/random")
    @Produces(MediaType.APPLICATION_JSON)
    public String getRandomJokeToJson()
    {
        Joke joke = FACADE.getRandomJoke();
        JokeDTO jokeDTO = new JokeDTO(joke);
        return GSON.toJson(jokeDTO);
    }

}
