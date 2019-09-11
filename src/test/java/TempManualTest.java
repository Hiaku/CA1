
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.JokeDTO;
import entities.Joke;
import facades.JokeFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Brandstrup
 */
public class TempManualTest
{

    public static void main(String[] args)
    {

        Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        List<JokeDTO> DTOList = new ArrayList<>();
        List<Joke> EntityList = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            String title = "title" + i;
            String body = "body" + i;
            String reference = "reference" + i;
            EntityList.add(new Joke(title, body, reference, Joke.JokeType.PUNS));
        }

        for (Joke joke : EntityList)
        {
            DTOList.add(new JokeDTO(joke));
        }

        for (JokeDTO jokeDTO : DTOList)
        {
            System.out.println(GSON.toJson(jokeDTO));
        }
    }
}
