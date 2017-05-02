package cinema.dto;

import java.util.Objects;

/**
 * Created by Tory on 23.04.2017.
 */
public class Film {
    private int id;
    private String name;
    private short year;
    private String genre;

    public Film(){
        super();
    }

    public Film(int id, String genre, String name, short year) {
        this.id = id;
        this.genre = genre;
        this.name = name;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public String getName() {
        return name;
    }

    public short getYear() {
        return year;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(short year) {
        this.year = year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, genre);
    }

}
