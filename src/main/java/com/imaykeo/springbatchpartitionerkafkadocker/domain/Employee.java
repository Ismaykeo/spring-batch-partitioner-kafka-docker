package com.imaykeo.springbatchpartitionerkafkadocker.domain;


import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import com.imaykeo.springbatchpartitionerkafkadocker.Utils.OnlyDataBase;
import com.imaykeo.springbatchpartitionerkafkadocker.Utils.OnlyResource;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Employee implements Serializable {

    private Long empId;

    private String namePrefix;

    private String firstName;

    private String middleinitial;

    private String lastName;

    private String gender;

    private String email;

    private String fatherSName;

    private String mothersName;

    private String motherMaidenName;

    @OnlyResource
    private String dateOfBithResource;
    @OnlyDataBase
    private Date dateOfBirth;

    @OnlyResource
    private String timeOfBirthResource;
    @OnlyDataBase
    private Time timeOfBirt;

    private Double ageInYears;

    private Double weightInKgs;

    @OnlyResource
    private String dateOfJoiningResource;
    @OnlyDataBase
    private Date dateOfJoining;

    private String quarterOfJoining;


    private String HalfOfJoining;

    private Long yearOfJoining;

    private Long MonthOfJoining;

    private String monthNameOfJoining;

    private String shortMonth;

    private Long dayOfJoining;

    private String dowOfJoining;

    private String shortDow;

    private Double ageInCompany;

    private Double salary;

    private String lastPercentHike;

    private String ssn;

    private String phoneNumber;

    private String placeName;

    private String country;

    private String city;

    private String state;

    private String zip;

    private String region;

    private String userName;

    private String passwordEncoded;


}
