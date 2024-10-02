package br.ufjf.services;

import java.util.List;

import br.ufjf.utils.Utils;
import br.ufjf.exceptions.CPFInvalidoException;
import br.ufjf.exceptions.DataInvalidaException;
import br.ufjf.models.Person;
import br.ufjf.repositories.PersonRepository;
import br.ufjf.swing.Window;

public class PersonService {
 
    private PersonRepository repository;

    public PersonService() {
        this.repository = new PersonRepository();
    }

    public void add(String name, String date, String cpf) {
        try {
            if(Utils.validateBirthDate(date))
                this.repository.add(new Person(name, date, Utils.validateCPF(cpf)));
        } catch (CPFInvalidoException e) {
            Window.showError(e.getMessage());
        } catch (DataInvalidaException e) {
            Window.showError(e.getMessage());
        }
    }

    public void remove(Person p) {
        this.repository.remove(p);
    }

    public void updateByRow(int row, Person p) {
        try {
            if(Utils.validateBirthDate(p.getBirthDate())) {
                p.setCPF(Utils.validateCPF(p.getCPF()));
                this.repository.updateByRow(row, p);
            }

        } catch (CPFInvalidoException e) {
            Window.showError(e.getMessage());
        } catch (DataInvalidaException e) {
            Window.showError(e.getMessage());
        }
    }

    public Person getByRow(int row) {
        return this.repository.getByRow(row);
    }

    public List<Person> getAll() {
        return this.repository.getAll();
    }
}
