package util;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;


/**
 * 
 * @author André Gaeta <andremg@dcc.ufrj.br>
 * 
 * Classe para ajudar com funções genéricas e procedurais.
 * 
 */

public class Tools{

    /**
     * 
     * Valida e retorna um array de n inteiros.
     * 
     */
    public static int[] getInputInt(){
        System.out.println("Digite a quantidade de numeros.");
        int quantidade = getInt();
        int[] numeros = new int[quantidade];
        for (int i = 0; i < quantidade; i++) {
            System.out.printf("Digite o %dº numero.", i+1);
            numeros[i] = getInt();
        }
        return numeros;
    }

    /**
     * 
     * Valida e retorna um array de n floats.
     * 
     */

    public static float[] getInputFloat(){
        System.out.println("Digite a quantidade de numeros.");
        int quantidade = getInt();
        float[] numeros = new float[quantidade];
        for (int i = 0; i < quantidade; i++) {
            System.out.printf("Digite o %dº numero.", i+1);
            numeros[i] = getFloat();
        }
        return numeros;
    }

    /** 
     * 
     * Valida e retorna um ArrayList de n strings.
     * 
     */
    public static ArrayList<String> getInputString(){
        System.out.println("Digite a quantidade de palavras.");
        int quantidade = getInt();
        ArrayList<String> palavras = new ArrayList<String>();
        for (int i = 0; i < quantidade; i++) {
            System.out.printf("Digite a %dª palavra.", i+1);
            palavras.add(getString());
        }
        return palavras;
    }

    /**
     *
     * Valida e retorna um float.
     *
     */

    public static float getFloat(){
        boolean sucesso = false;
        float f = 0;
        while (!sucesso) {
            try {
                Scanner scanner = new Scanner(System.in);
                f = scanner.nextFloat();
                sucesso = true;
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Input invalido, tente novamente. Float esperado.");
            }
        }
        return f;
    }

    /** 
     * 
     * Valida e retorna um int.
     * 
     */
    public static int getInt(){
        boolean sucesso = false;
        int i = 0;
        while (!sucesso) {
            try {
                Scanner scanner = new Scanner(System.in);
                i = scanner.nextInt();
                sucesso = true;
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Input invalido, tente novamente. Int esperado.");
            }
        }
        return i;
    }
    /** 
     * 
     * Valida e retorna uma string.
     * 
     */
    public static String getString(){
        boolean sucesso = false;
        String s = null;
        while (!sucesso) {
            try {
                Scanner scanner = new Scanner(System.in);
                s = scanner.nextLine();
                sucesso = true;
            } catch (InputMismatchException e) {
                System.out.println("Input invalido, tente novamente. String esperada.");
            }
        }
        return s;
    }

    
}