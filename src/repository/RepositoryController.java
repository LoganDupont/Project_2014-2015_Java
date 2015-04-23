
package repository;

import domain.Clause;
import domain.ClauseComponent;
import domain.ClimateChart;
import domain.Continent;
import domain.Country;
import domain.Grade;
import domain.MonthOfTheYear;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class RepositoryController {
    
    private ContinentRepository continentRepo;
    private CountryRepository countryRepo;
    private ClimateChartRepository chartRepo;
    private DeterminateTableRepository determinateRepo;
    private GradeRepository gradeRepo;
    
    public RepositoryController(){
        this.continentRepo = new ContinentRepository();
        this.countryRepo = new CountryRepository();
        this.chartRepo = new ClimateChartRepository();
        this.determinateRepo = new DeterminateTableRepository();
        this.gradeRepo = new GradeRepository();
    }
    
    public List<Continent> getAllContinents(){
        return continentRepo.getAllContinents();
    }
    
    public void insertContinent(Continent c){
        continentRepo.insertContinent(c);
    }
    
    public Continent findContinentById(int id){
        return continentRepo.findContinentById(id);
    }
    
    public List<Country> getAllCountries(){
        return countryRepo.getAllCountries();
    }
    
    public List<Country> getCountriesOfContinent(int continentId){
        return countryRepo.getCountriesOfContinent(continentId);
    }
    
    public List<ClimateChart> getClimateChartsOfCountry(int countryId){
        return chartRepo.getClimateChartsOfCountry(countryId);
    }
    public void updateClimateChart(int id,String LCord,String BCord,int bP,int eP,double longi,double lat)
    {
        chartRepo.updateClimateChart();
    }
    public ClimateChart getClimateChartByClimateChartID(int chartId){
        return chartRepo.getClimateChartByClimateChartID(chartId);
    }
    
    public void insertCountry(Country c){
        countryRepo.insertCountry(c);
    }
    
    public Country findCountryById(int id){
        return countryRepo.findCountryById(id);
    }
    public void updateTemp(int id,double temp){
        chartRepo.updateTemp(id, temp);
    }
    public void updateSed(int id,int sed)
    {
        chartRepo.updateSed(id,sed);
    }
    public void InsertClimatechart(ClimateChart c)
    {
        chartRepo.insertClimateChart(c);
    }
    
    public List<MonthOfTheYear> getMonthsOfTheYear(){
        return Arrays.asList(MonthOfTheYear.values());
    }
    
    public Grade findGradeById(int id){
        return gradeRepo.findById(id);
    }
    
    public List<ClauseComponent> findClauseComponentsByDeterminateTableId(int id){
        return determinateRepo.getAllClauseComponentsOfDeterminateTable(id);
    }
    
    public List<ClauseComponent> findClausesByDeterminateTableId(int id){
        return determinateRepo.getAllClausesOfDeterminateTable(id);
    }
    
    public ClauseComponent findClauseById(int clauseId){
        return determinateRepo.findClauseById(clauseId);
    }
}
