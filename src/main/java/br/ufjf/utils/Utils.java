package br.ufjf.utils;

import br.ufjf.exceptions.CPFInvalidoException;
import br.ufjf.exceptions.DataInvalidaException;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class Utils {
    
    public static String validateCPF(String cpf) throws CPFInvalidoException {
        
        String cleanedCPF = cpf.replaceAll("[^0-9]", "");

        System.out.println(cpf);

        // valida tamanho
        int expectedLength = 11;
        if (cleanedCPF.length() != expectedLength)
            throw new CPFInvalidoException("CPF possui tamanho incorreto");

        // valida se são números iguais ou possui valores inválidos
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < expectedLength; i++) {

            if (!Character.isDigit(cleanedCPF.charAt(i)))
                throw new CPFInvalidoException("CPF contém caracteres inválidos");

            set.add(cleanedCPF.charAt(i));
        }
        if (set.size() == 1)
            throw new CPFInvalidoException("CPF com caracteres iguais! ");

        // valida dígitos verificadores
        int digit10 = calcSumAndReturnTheDigit(10, cleanedCPF);
        int digit11 = calcSumAndReturnTheDigit(11, cleanedCPF);
      
        if(digit10 == (cleanedCPF.charAt(9) - '0') && digit11 == (cleanedCPF.charAt(10) - '0'))
            return formatCPF(cleanedCPF);
        else throw new CPFInvalidoException("CPF invalidado pelos dígitos qualificadores");
    }

    public static int calcSumAndReturnTheDigit(int value, String cpf) {
        int sum = 0;
        int weight = value; 

        for (int i = 0; i < value - 1; i++) {
            int digit = cpf.charAt(i) - '0';
            sum += (digit * weight);
            weight--;
        }

        int r = 11 - (sum % 11);
        if (r == 10 || r == 11)
            return 0;
        return r;
    }

    public static boolean validateBirthDate(String date) throws DataInvalidaException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(date, formatter);
        LocalDate today = LocalDate.now();

        if(data.getYear() < today.getYear()) {
            return true;
        } else throw new DataInvalidaException("Data inválida: Não tem idade mínima!");
    }

    public static int getYearsFromBirthDate(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(date, formatter);
        LocalDate today = LocalDate.now();
        Period period = Period.between(data, today);
        return period.getYears();
    }

    public static String formatCPF(String cpf) {
        return (cpf.substring(0, 3)) + "." + cpf.substring(3, 6) + "." 
        + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
    }
}
