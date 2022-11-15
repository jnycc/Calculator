package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Experiments {

    public Experiments() {
    }

    void startExperiment() {
        String input = "+5-5+3+1+(2-2)--8*7/3";
//        String regex = "(?<=[-−+*/()])|(?=[-−+*/()])";
//        String regex = "(?=[-+*/()])";
//        String regex = "(?=[-)])|([+*/])|(?<=[(])";
//        String regex = "(?=[-/*()])|([+])|(?<=[*/()])";
//        String tokens[] = input.split(regex);
//        System.out.println("Tokens: " + Arrays.toString(tokens));

        List<Character> character = Arrays.asList('a', 'b');
        int count = 0;
        for (int i = 0; i < character.size(); i++) {
            Character currentCharacter = character.get(i);
            Character previousCharacter = (i==0)? 0 : character.get(i - 1);
            System.out.println("currentChar:" + currentCharacter);
            System.out.println("previousChar:" + previousCharacter);
//            if (currentCharacter.equals('+') && !character.get(i - 1).equals('+')) {
//                continue;
        }
    }
}


