package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 * 
 * @author Brandstrup
 */
@Entity
@NamedQuery(name = "Joke.deleteAllRows", query = "DELETE from Joke")
public class Joke implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String body;
    private String reference;
    private JokeType type;

    public enum JokeType
    {
        PUNS,
        DARK,
        MOM,
        RIDDLES
    }

    public Joke()
    {
    }

    public Joke(String title, String body, String reference, JokeType type)
    {
        this.title = title;
        this.body = body;
        this.reference = reference;
        this.type = type;
    }
    
    

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public String getReference()
    {
        return reference;
    }

    public void setReference(String reference)
    {
        this.reference = reference;
    }

    public JokeType getType()
    {
        return type;
    }

    public void setType(JokeType type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "Joke{" + "id=" + id + ", title=" + title + ", reference=" + reference + ", type=" + type + '}';
    }

}
