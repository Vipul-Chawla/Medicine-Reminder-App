package com.example.project7;

public class UserDetails {
    private String email;
    private String name;
    private String phoneNumber;
    private String gender;
    private int age;
    private String bloodType;
    private String majorIllness;
    private String surgery;
    private String illness;
    private String injuries;
    private String allergy;
    private String recentMed;
    private String geneticDisease;

    public UserDetails(String email, String name, String phoneNumber, String gender, String age) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.age = Integer.parseInt(age);
    }

    public UserDetails(String bloodType, String majorIllness, String surgery, String illness, String  injuries, String allergy, String geneticDisease){
        this.bloodType = bloodType;
        this.majorIllness = majorIllness;
        this.surgery = surgery;
        this.illness = illness;
        this.injuries = injuries;
        this.allergy = allergy;
        this.geneticDisease = geneticDisease;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getMajorIllness() {
        return majorIllness;
    }

    public String getSurgery() {
        return surgery;
    }

    public String getIllness() {
        return illness;
    }

    public String getInjuries() {
        return injuries;
    }

    public String getAllergy() {
        return allergy;
    }

    public String getRecentMed() {
        return recentMed;
    }

    public String getGeneticDisease() {
        return geneticDisease;
    }
}
