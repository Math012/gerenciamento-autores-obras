package com.math012.autoresobras.infra.utils;

import com.math012.autoresobras.infra.exceptions.api.CpfException;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;

@Component
public class CPFValidator {
    public static boolean isCPF(String cpfAuthor) {
        String cpf = cpfAuthor.replace(".", "").replace("-", "").replace(" ", "");
        if (cpf.length() != 11) {
            throw new CpfException("Erro, é preciso informar 11 dígitos no CPF, tente novamente!");
        }
        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char) (r + 48);

            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char) (r + 48);

            if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
                return (true);
            else return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }
}

