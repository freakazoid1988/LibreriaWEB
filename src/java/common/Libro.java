package common;

import java.io.Serializable;
import java.util.*;

public class Libro implements Serializable 
{

    private String isbn;
    private String titolo;
    private String casaEditrice;
    private float prezzo;
    private Vector autori;

    public Libro(String isbn, String titolo, String casaEditrice, float prezzo, Vector autori) 
    {
        this.isbn = isbn;
        this.titolo = titolo;
        this.casaEditrice = casaEditrice;
        this.prezzo = prezzo;
        this.autori = autori;
    }

    public String getIsbn() 
    {
        return isbn;
    }

    public String getTitolo() 
    {
        return titolo;
    }

    public String getCasaEditrice() 
    {
        return casaEditrice;
    }

    public float getPrezzo() 
    {
        return prezzo;
    }

    public Vector getAutori() 
    {
        return autori;
    }

    /*public String toString()
    {
    String libro = "";
    libro +="ISBN : "+isbn+"<BR>";
    libro +="Titolo : "+titolo+"<BR>";
    libro +="Autori : ";
    Iterator it = autori.iterator();
    while (it.hasNext())
    {
    libro +=it.next()+" ";
    }
    libro +="<BR>Casa Editrice : "+casaEditrice;
    libro +="<BR>Prezzo : "+prezzo;
    return libro;
    }*/
}
