package br.ufjf.models;

public class Person {
    
    String CPF;
    String birthDate;
    String name;

    public Person(String name, String birthDate, String CPF) {
        this.CPF = CPF;
        this.birthDate = birthDate;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCPF() {
        return CPF;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public void setName(String name) {
        this.name = name;
    }
}
