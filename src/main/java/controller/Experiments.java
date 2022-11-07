package controller;

import java.util.Arrays;

public class Experiments {

    public Experiments() {
    }

    void startExperiment() {
        String input = "+5-5+3+1+(2-2)--8*7/3";
//        String regex = "(?<=[-−+*/()])|(?=[-−+*/()])";
//        String regex = "(?=[-+*/()])";
//        String regex = "(?=[-)])|([+*/])|(?<=[(])";
        String regex = "(?=[-/*()])|([+])|(?<=[*/()])";
        String tokens[] = input.split(regex);
        System.out.println("Tokens: " + Arrays.toString(tokens));
    }
}
