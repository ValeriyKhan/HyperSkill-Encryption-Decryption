package encryptdecrypt;

import java.util.*;

public class MakeEncryption implements IAction {
    public final String message;
    public int key;
    public final String algorithm;

    public MakeEncryption(String message, int key, String algorithm) {
        this.message = message;
        this.key = key;
        this.algorithm = algorithm;
    }

    @Override
    public String makeTransform() {
        if (algorithm.equals("shift")) {
            return makeShiftEncryption();
        } else if (algorithm.equals("unicode")) {
            return makeUnicodeEncryption();
        } else {
            return "Invalid type of algorithm";
        }
    }

    private String makeShiftEncryption() {
        String encodedMessage = "";
        List<Character> listOfEncryptedCharacters = new ArrayList<>();

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
                listOfEncryptedCharacters.add(c);
            }
            final int keyFinal = key;
            if (c >= beginInt && c <= finishInt) {
                for (int i = 0; i < alphabetCase.length; i++) {
                    if (c == alphabetCase[i]) {
                        if (key > alphabetCase.length) {
                            while (key > alphabetCase.length) {
                                key -= alphabetCase.length;
                            }
                            listOfEncryptedCharacters.add(alphabetCase[Math.abs(key)]);
                        } else {
                            if ((i + key) > alphabetCase.length) {
                                key = (i + key) - alphabetCase.length;
                            } else {
                                key = i + key;
                            }
                            listOfEncryptedCharacters.add(alphabetCase[key]);
                            key= keyFinal;
                        }
                    }
                }
            }
        }
        for (Character c : listOfEncryptedCharacters) {
            encodedMessage += c;
        }
        return encodedMessage;
    }

    private String makeUnicodeEncryption() {
        String decodedMessage = "";
        List<Character> listOfDecryptedCharacters = new ArrayList<>();
        char[] charArray = message.toCharArray();
        for (char ch : charArray) {
            listOfDecryptedCharacters.add((char) (ch + key));
        }
        for (Character listOfDecryptedCharacter : listOfDecryptedCharacters) {
            decodedMessage += listOfDecryptedCharacter.toString();
        }
        return decodedMessage;
    }
}
