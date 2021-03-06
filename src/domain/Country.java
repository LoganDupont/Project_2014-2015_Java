package domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity(name = "Countries")
@Table(name = "Countries")
@NamedQueries({
    @NamedQuery(name = "Country.findAll",
            query = "select c from Countries c"),
    @NamedQuery(name = "Country.findCountriesByContinent",
            query = "SELECT c FROM Countries c WHERE c.continent.continentId = :continentID"),
    @NamedQuery(name = "Country.findById",
            query = "SELECT c FROM Countries c WHERE c.countryId = :countryID"),
    @NamedQuery(name = "Country.findByName",
        query = "SELECT c FROM Countries c WHERE c.name = :countryName")
})
public class Country implements Serializable, Comparable<Country> {
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CountryID")
    private int countryId;

    @ManyToOne
    @JoinColumn(name = "ContinentID")
    private Continent continent;

    public Country() {
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public Country(String name, int id) {
        setName(name);
        setId(id);
    }

    public Country(String name, Continent c) {
        setName(name);
        setContinent(c);
    }

    public Country(String name, int id, int continentId) {
        setName(name);
        setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    public int getId() {
        return countryId;
    }

    public void setId(int id) {
        this.countryId = id;
    }
    
    @Override
    public int compareTo(Country c) {
        return this.name.compareTo(c.name);
    }
}
