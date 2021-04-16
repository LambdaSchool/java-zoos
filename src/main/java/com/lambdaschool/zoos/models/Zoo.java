package com.lambdaschool.zoos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "zoos")
public class Zoo extends Auditable{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long zooid;

  @OneToMany(mappedBy = "zoo",
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  @JsonIgnoreProperties(value = "zoo", allowSetters = true)
  private List<Telephone> telephones = new ArrayList<>();

//  @Column(nullable = false, unique = true)
  String zooname;

  @OneToMany(mappedBy = "zoo",
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  @JsonIgnoreProperties(value = "zoo", allowSetters = true)
  private Set<ZooAnimals> animals = new HashSet<>();

  public Zoo() {
  }

  public Zoo(
      String zooname) {
    this.zooname = zooname;
  }

  public long getZooid() {
    return zooid;
  }

  public void setZooid(long zooid) {
    this.zooid = zooid;
  }

  public String getZooname() {
    return zooname;
  }

  public void setZooname(String zooname) {
    this.zooname = zooname.toLowerCase();
  }

  public List<Telephone> getTelephones() {
    return telephones;
  }

  public void setTelephones(List<Telephone> telephones) {
    this.telephones = telephones;
  }

  public Set<ZooAnimals> getAnimals() {
    return animals;
  }

  public void setAnimals(Set<ZooAnimals> animals) {
    this.animals = animals;
  }

}
