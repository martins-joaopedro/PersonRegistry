package br.ufjf.repositories;

import br.ufjf.models.Person;

import java.util.List;
import java.util.ArrayList;

public class PersonRepository {
    
    List<Person> list;

    public PersonRepository() {
        this.list = new ArrayList<>();
    }

    public void add(Person p) {
        this.list.add(p);
    }

    public void remove(Person p) {
        this.list.remove(p);
    }

    public void updateByRow(int row, Person p) {
        this.list.get(row).setName(p.getName());
        this.list.get(row).setCPF(p.getCPF());
        this.list.get(row).setBirthDate(p.getBirthDate());
    }

    public List<Person> getAll() {
        return list;
    }

    public Person getByRow(int row) {
        return this.list.get(row);
    }
}
