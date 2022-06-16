package encryptdecrypt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MakeDecryption implements IAction {

    public final String message;
    public int key;
    public final String algorithm;

    public MakeDecryption(String message, int key, String algorithm) {
        this.message = message;
        this.key = key;
        this.algorithm = algorithm;
    }

    @Override
    public String makeTransform() {
        if (algorithm.equals("shift")) {
            return makeShiftDecryption();
        } else if (algorithm.equals("unicode")) {
            return makeUnicodeDecryption();
        } else {
            return "Invalid type of algorithm";
        }
    }

    public String makeShiftDecryption() {
        String decodedMessage = "";
        List<Character> listOfDecryptedCharacters = new ArrayList<>();

        char[] alphabetCase;
        char[] charArray = message.toCharArray();
        int beginInt;
        int finishInt;
        for (char c : charArray) {
            if (c >= 97 && c <= 122) {
                beginInt = 97;
                finishInt = 122;
                alphabetCase = "abcdefghijklmnopqrstuvwxyz".toCharArray();
            } else {
                beginInt = 65;
                finishInt = 90;
                alphabetCase = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
            }
            if (c < 65 || c > 122) {
                listOfDecryptedCharacters.add(c);
            }
            final int keyFinal = key;
            if (c >= beginInt && c <= finishInt) {
                for (int i = 0; i < alphabetCase.length; i++) {
                    if (c == alphabetCase[i]) {
                        if (key > alphabetCase.length) {
                            while (key > alphabetCase.length) {
                                key += alphabetCase.length;
                            }
                            listOfDecryptedCharacters.add(alphabetCase[Math.abs(key)]);
                        } else {
                            if ((i - key) < 0) {
                                key = alphabetCase.length - (key -i);
                            } else {
                                key = i - key;
                            }
                            listOfDecryptedCharacters.add(alphabetCase[key]);
                            key = keyFinal;
                        }
                    }
                }
            }
        }
        for (Character c : listOfDecryptedCharacters) {
            decodedMessage += c;
        }
        return decodedMessage;
    }

    public String makeUnicodeDecryption() {
        String decodedMessage = "";
        List<Character> listOfDecryptedCharacters = new ArrayList<>();
        char[] charArray = message.toCharArray();
        for (char ch : charArray) {
            listOfDecryptedCharacters.add((char) (ch - key));
        }
        for (Character listOfDecryptedCharacter : listOfDecryptedCharacters) {
            decodedMessage += listOfDecryptedCharacter.toString();
        }
        return decodedMessage;
    }
}
