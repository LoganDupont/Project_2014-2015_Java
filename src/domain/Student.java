/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javax.persistence.Transient;

/**
 *
 * @author SAMUEL
 */
@Entity(name = "Students")
@Table(name = "Students")
@NamedQueries({
    @NamedQuery(name="AllStudents",
                query="SELECT s FROM Students s"), 
    @NamedQuery(name = "StudentsOfClassGroup", 
            query = "SELECT s FROM Students s WHERE s.classGroup = :cg ")
}) 
public class Student implements Serializable {

    //Naam-Voornaam-Klas-Leerjaar-Graad

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StudentID")
    private int studentId;

    @Column(name = "Achternaam")
    private String lastName;

    @Column(name = "Voornaam")
    private String firtsName;

    @ManyToOne
    @JoinColumn(name = "groupId")
    private ClassGroup classGroup;

    /*private SchoolYear schoolYear;
    
     private Grade grade;*/
    
    public Student() {
    }

    public Student(String lastName, String firtsName) {
        this.lastName = lastName;
        this.firtsName = firtsName;
    }

    public Student(String lastName, String firtsName, ClassGroup classGroup) {
        this.lastName = lastName;
        this.firtsName = firtsName;
        this.classGroup = classGroup;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirtsName() {
        return firtsName;
    }

    public void setFirtsName(String firtsName) {
        this.firtsName = firtsName;
    }

    public ClassGroup getClassGroup() {
        return classGroup;
    }

    public void setClassGroup(ClassGroup classGroup) {
        this.classGroup = classGroup;
    }
    /*

     public SchoolYear getSchoolYear() {
     return schoolYear;
     }

     public void setSchoolYear(SchoolYear schoolYear) {
     this.schoolYear = schoolYear;
     }

     public Grade getGrade() {
     return grade;
     }

     public void setGrade(Grade grade) {
     this.grade = grade;
     }*/
    
    public String getFullName(){
        return (getLastName()+ " " + getFirtsName());
    }

    @Transient
    private SimpleStringProperty lastNameProp;
    @Transient
    private SimpleStringProperty firstNameProp;
    
    public ObservableValue<String> firstNameProperty() {
        this.lastNameProp = new SimpleStringProperty(lastName);
        return lastNameProp;
    }

    public ObservableValue<String> lastNameProperty() {
        this.firstNameProp = new SimpleStringProperty(firtsName);
        return firstNameProp;
    }

}
