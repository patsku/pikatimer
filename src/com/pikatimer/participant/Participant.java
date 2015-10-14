/*
 *  Copyright 2014 John Garner. All rights reserved. 

 */

package com.pikatimer.participant;

import com.pikatimer.race.Wave;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author jcgarner
 */

@Entity
@DynamicUpdate
@Table(name="PARTICIPANT")
public class Participant {
    

   
   private final StringProperty firstNameProperty;
   private final StringProperty middleNameProperty;
   private final StringProperty lastNameProperty;
   private final StringProperty emailProperty; 
   private final IntegerProperty IDProperty;
   private final StringProperty bibProperty;
   private final IntegerProperty ageProperty;
   private final StringProperty sexProperty; 
   private final StringProperty cityProperty;
   private final StringProperty stateProperty;
   private final StringProperty countryProperty;
   private LocalDate birthdayProperty; 
   private final ObservableList<Wave> waves;         
    public Participant() {
        this("","");
    }
 
    public Participant(String firstName, String lastName) {
        this.firstNameProperty = new SimpleStringProperty();
        this.middleNameProperty = new SimpleStringProperty();
        this.lastNameProperty = new SimpleStringProperty();
        this.emailProperty = new SimpleStringProperty();
        this.IDProperty = new SimpleIntegerProperty();
        this.bibProperty = new SimpleStringProperty();
        this.ageProperty = new SimpleIntegerProperty();
        this.sexProperty = new SimpleStringProperty();
        this.cityProperty = new SimpleStringProperty();
        this.stateProperty = new SimpleStringProperty();
        this.countryProperty = new SimpleStringProperty();
        this.waves=FXCollections.observableArrayList();
        setFirstName(firstName);
        setLastName(lastName);
    }
    public static ObservableMap getAvailableAttributes() {
        ObservableMap<String,String> attribMap = FXCollections.observableHashMap();
        attribMap.put("bib", "Bib");
        attribMap.put("firstName", "First Name");
        attribMap.put("middleName", "Middle Name");
        attribMap.put("lastName", "Last Name");
        attribMap.put("age", "Age");
        attribMap.put("sex", "Sex");
        attribMap.put("city", "City");
        attribMap.put("state", "State");
        attribMap.put("country", "Country");
        attribMap.put("email", "EMail");
        // routine to add custom attributes based on db lookup
        return attribMap; 
    }
    
    public Participant(Map<String, String> attribMap) {
        // bulk set routine. Everything is a string so convert as needed
        this("","");
        attribMap.entrySet().stream().forEach((Map.Entry<String, String> entry) -> {
            if (entry.getKey() != null) {
                //System.out.println("processing " + entry.getKey() );
             switch(entry.getKey()) {
                 case "bib": this.setBib(entry.getValue()); break; 
                 case "firstName": this.setFirstName(entry.getValue()); break;
                 case "lastName": this.setLastName(entry.getValue()); break;
                 //case "middleName": this.setLastName(entry.getValue()); break;
                     
                 // TODO: catch bad integers 
                 case "age": this.setAge(Integer.parseUnsignedInt(entry.getValue())); break; 
                     
                 // TODO: map to selected sex translator
                 case "sex": this.setSex(entry.getValue()); break; 
                     
                 case "city": this.setCity(entry.getValue()); break; 
                 case "state": this.setState(entry.getValue()); break; 
                 case "email": this.setEmail(entry.getValue()); break; 
                     
                 // TODO: Team value
             }
            }
        });
    }
    
    @Id
    @GenericGenerator(name="participant_id" , strategy="increment")
    @GeneratedValue(generator="participant_id")
    @Column(name="PARTICIPANT_ID")
    public Integer getID() {
        return IDProperty.getValue(); 
    }
    public void setID(Integer id) {
        IDProperty.setValue(id);
    }
    public IntegerProperty idProperty() {
        return IDProperty; 
    }
    
    @Column(name="FIRST_NAME")
    public String getFirstName() {
        return firstNameProperty.getValueSafe();
    }
    public void setFirstName(String fName) {
        firstNameProperty.setValue(fName);
    }
    public StringProperty firstNameProperty() {
        return firstNameProperty; 
    }
 
    
    @Column(name="LAST_NAME")
    public String getLastName() {
        return lastNameProperty.getValueSafe();
    }
    public void setLastName(String fName) {
        lastNameProperty.setValue(fName);
    }
    public StringProperty lastNameProperty() {
        return lastNameProperty;
    }
    
    @Column(name="EMAIL")
    public String getEmail() {
        return emailProperty.getValueSafe();
    }
    public void setEmail(String fName) {
        emailProperty.setValue(fName);
    }
    public StringProperty emailProperty() {
        return emailProperty; 
    }
    
    @Column(name="BIB_Number")
    public String getBib() {
        return bibProperty.getValueSafe();
    }
    public void setBib(String b) {
        bibProperty.setValue(b);
    }
    public StringProperty bibProperty() {
        return bibProperty;
    }
    
    @Column(name="AGE")
    public Integer getAge () {
        return ageProperty.getValue();
    }
    public void setAge (Integer a) {
        ageProperty.setValue(a);
    }
    public IntegerProperty ageProperty() {
        return ageProperty; 
    }
    
    @Column(name="SEX")
    public String getSex() {
        return sexProperty.getValueSafe();
    }
    public void setSex(String s) {
        sexProperty.setValue(s);
    }
    public StringProperty sexProperty() {
        return sexProperty;
    }
    
    @Column(name="CITY")
    public String getCity() {
        return cityProperty.getValueSafe();
    }
    public void setCity(String c) {
        cityProperty.setValue(c);
    }
    public StringProperty cityProperty() {
        return cityProperty; 
    }
    
   @Column(name="STATE")
    public String getState() {
        return stateProperty.getValueSafe();
    }
    public void setState(String s) {
        stateProperty.setValue(s);
    }
    public StringProperty stateProperty(){
        return stateProperty;
    }
    
    @Column(name="BIRTHDAY",nullable=true)
    public String getBirthday() {
        if (birthdayProperty != null) {
            //return Date.from(birthdayProperty.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            return birthdayProperty.toString();
        } else {
            return null; 
        }
    }
    public void setBirthday(LocalDate d) {
        if (d != null) {
            birthdayProperty = d;
        }
    }    
    public void setBirthday(String d) {
        if (d != null) {
            birthdayProperty = LocalDate.parse(d,DateTimeFormatter.ISO_LOCAL_DATE);
            //Instant instant = Instant.ofEpochMilli(d.getTime());
            //setBirthday(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate());
        }
    }
    public LocalDate birthdayProperty() {
        return birthdayProperty;
    }
    
    
    //create table part2wave (participant_id int, wave_id int); 
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "part2wave", joinColumns = { 
                    @JoinColumn(name = "PARTICIPANT_ID") }, 
                    inverseJoinColumns = { @JoinColumn(name = "WAVE_ID") })
    @Fetch(FetchMode.SELECT)
    public Set<Wave> getWaves() {
            return new HashSet<>(waves);
    }
    public void setWaves(Set<Wave> w) {
            waves.setAll(w);
    }
    public void setWaves(List<Wave> w) {
        waves.setAll(w);
    }
    public void addWave(Wave w) {
        waves.add(w); 
    }
    public ObservableList<Wave> wavesProperty() {
        return waves; 
    }
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.firstNameProperty);
        hash = 89 * hash + Objects.hashCode(this.lastNameProperty);
        hash = 89 * hash + Objects.hashCode(this.emailProperty);
        hash = 89 * hash + Objects.hashCode(this.bibProperty);
        hash = 89 * hash + Objects.hashCode(this.ageProperty);
        hash = 89 * hash + Objects.hashCode(this.sexProperty);
        hash = 89 * hash + Objects.hashCode(this.cityProperty);
        hash = 89 * hash + Objects.hashCode(this.countryProperty);
        hash = 89 * hash + Objects.hashCode(this.birthdayProperty);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Participant other = (Participant) obj;
        if (!Objects.equals(this.firstNameProperty.getValue(), other.firstNameProperty.getValue())) {
            return false;
        }
        if (!Objects.equals(this.lastNameProperty.getValue(), other.lastNameProperty.getValue())) {
            return false;
        }
        if (!Objects.equals(this.emailProperty.getValue(), other.emailProperty.getValue())) {
            return false;
        }
        if (!Objects.equals(this.bibProperty.getValue(), other.bibProperty.getValue())) {
            return false;
        }
        if (!Objects.equals(this.ageProperty.getValue(), other.ageProperty.getValue())) {
            return false;
        }
        if (!Objects.equals(this.sexProperty.getValue(), other.sexProperty)) {
            return false;
        }
        if (!Objects.equals(this.cityProperty.getValue(), other.cityProperty)) {
            return false;
        }
        if (!Objects.equals(this.stateProperty.getValue(), other.stateProperty.getValue())) {
            return false;
        }
        if (!Objects.equals(this.countryProperty.getValue(), other.countryProperty.getValue())) {
            return false;
        }
        if (!Objects.equals(this.birthdayProperty, other.birthdayProperty)) {
            return false;
        }
        return true;
    }
    
}