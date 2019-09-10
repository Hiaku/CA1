/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Joke;

/**
 *
 * @author Brandstrup
 */
public class JokeDTO
{
 
    private Long id;
    private String title;
    private String body;
    private String reference;
    private String type;

    public JokeDTO(Joke j)
    {
        this.id = j.getId();
        this.title = j.getTitle();
        this.body = j.getBody();
        this.reference = j.getReference();
        this.type = j.getType().toString();
    }
    
    
}
