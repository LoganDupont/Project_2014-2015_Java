package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

@Entity(name = "ClimateCharts")
@Table(name = "ClimateCharts")
@NamedQueries({
    @NamedQuery(name = "ClimateChart.findByCountry",
            query = "SELECT c FROM ClimateCharts c WHERE c.country.countryId = :countryID"),
    @NamedQuery(name = "ClimateChart.findById",
            query = "SELECT c FROM ClimateCharts c WHERE c.climateChartId = :chartId"),
    @NamedQuery(name="ClimateChart.findAll",query="SELECT c from ClimateCharts c"),
    @NamedQuery(name = "ClimateChart.findByName",
        query = "SELECT c FROM ClimateCharts c WHERE c.location = :climateChartName")
})
public class ClimateChart implements Serializable, Comparable<ClimateChart> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClimateChartID")
    private int climateChartId;

    private String location;
    @Column(name = "BeginPeriod")
    private int beginperiod;
    @Column(name = "EndPeriod")
    private int endperiod;
    @Column(name = "Latitude")
    private double latitude;
    @Column(name = "Longitude")
    private double longitude;
    @Column(name = "LCord")
    private String LCord;
    @Column(name = "BCord")
    private String BCord;
    @Column(name = "AboveEquator")
    private boolean aboveEquator;
    @OneToMany(mappedBy = "climateChart",cascade = CascadeType.PERSIST)
    @OrderColumn(name="MonthProp")
    private List<Months> months;
    @ManyToOne
    @JoinColumn(name = "CountryID")
    private Country country;

    public ClimateChart() {
        months = new ArrayList<>();
    }

    public ClimateChart(int id, String loc, int begin, int end, double[] temp, int[] sed, double latitude, double longitude,String BCord,String LCord) {
        setLocation(loc);
        setClimateChartId(id);
        setBeginperiod(begin);
        setEndperiod(end);
        setLatitude(latitude);
        setLongitude(longitude);
        setLCord(LCord);
        setBCord(BCord);
        months = new ArrayList<Months>();
        setMonthList(sed, temp);
    }
    public ClimateChart(Country country, String loc, int begin, int end, String BC, String LC, List<Months> ms) {
        months = new ArrayList<>();
        setCountry(country);
        setLocation(loc);
        setBeginperiod(begin);
        setBCord(BC);
        setLCord(LC);
        setEndperiod(end);
        setMonths(ms);
    }

    public ClimateChart(String location, int id) {
        setLocation(location);
        this.climateChartId = id;
    }
    
    public void setCountry(Country c) {
        this.country = c;
    }

    public Country getCountry() {
        return country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String value) {
        location = value;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getBeginperiod() {
        return beginperiod;
    }

    public void setBeginperiod(int beginperiod) {
        this.beginperiod = beginperiod;
    }

    public int getEndperiod() {

        return endperiod;
    }

    public void setEndperiod(int endperiod) {
        if (endperiod < this.beginperiod) {
            throw new IllegalArgumentException("De eind periode mag niet kleiner zijn dan begin periode");
        }
        this.endperiod = endperiod;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLCord() {
        return LCord;
    }

    public void setLCord(String LCord) {
        this.LCord = LCord;
    }

    public String getBCord() {
        return BCord;
    }

    public void setBCord(String BCord) {
        this.BCord = BCord;
    }

    public void setClimateChartId(int climateChartId) {
        this.climateChartId = climateChartId;
    }

    public List<Months> getMonths() {
        return months;
    }

    public void setMonths(List<Months> months) {
        this.months = months;
    }

    public boolean isAboveEquator() {
        return aboveEquator;
    }

    public void setAboveEquator(boolean aboveEquator) {
        this.aboveEquator = aboveEquator;
    }

    public Integer getId() {
        return climateChartId;
    }

    private void setMonthList(int[] sediments, double[] temperature) {
        if (temperature.length != 12 || sediments.length != 12) {
            throw new IllegalArgumentException("Er zijn meer dan 12 maanden in deze lijst");
        }
        int counter = 0;
        for (MonthOfTheYear m : MonthOfTheYear.values()) {
            months.add(new Months(sediments[counter], temperature[counter], m));
            counter++;
        }
    }

    public String giveCords(int degree, int minutes, int seconds) {
        if (degree < 0 || minutes < 0 || seconds < 0) {
            throw new IllegalArgumentException("Coordinaatwaarden moeten positief zijn");
        }
        if (minutes > 60 || seconds > 60) {
            throw new IllegalArgumentException("Minuten en seconden moeten kleiner zijn dan 60");
        }
        if(degree>180)
        {
            throw new IllegalArgumentException("Graden moeten kleiner dan 180 zijn");
        }
        return degree + "° " + minutes + "' " + seconds + "\" ";
    }

    public double calcDecimals(int degree, int min, int sec, String par) {
        double val;
        float f;
        f = min;
        f = f / 60;
        val = degree + f;
        f = sec;
        f = f / 3600;
        val = val + f;
        if (par.equalsIgnoreCase("zb") || par.equalsIgnoreCase("wl")) {
            val *= -1;
        }
        return Math.round(val * 1000000.0) / 1000000.0;
    }
    
    public OptionalDouble calcAverageYearTemp()
    {
        return months.stream().mapToDouble(m -> m.getAverTemp()).average();       
    }
    
    public int calcSedimentYear()
    {
        return months.stream().mapToInt(m -> m.getSediment()).sum();       
    }
    public String getGrade(boolean isBreedte)
    {
        if(isBreedte)
            return getBCord().split("°")[0].trim();
        else
            return getLCord().split("°")[0].trim();
    }
    public String getMinutes(boolean isBreedte)
    {
        if(isBreedte)
            return getBCord().split("°")[1].split("'")[0].trim();
        else
            return getLCord().split("°")[1].split("'")[0].trim();
    }
    public String getSeconds(boolean isBreedte)
    {
        if(isBreedte)
            return getBCord().split("°")[1].split("'")[1].trim().split("\"")[0];
        else
            return getLCord().split("°")[1].split("'")[1].trim().split("\"")[0];
    }
    @Override
    public String toString()
    {
        return this.location;
    }
    
    @Override
    public int compareTo(ClimateChart c) {
        return this.location.compareTo(c.location);
    }
}
