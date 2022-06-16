package encryptdecrypt;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Scanner;

public class Action {

    private final String[] args;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    private String mode;
    private int key;
    private String out;
    private String data;
    private String algorithm;

    Action(String[] args) {
        this.args = args;
    }

    private void getActionInfo(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < this.args.length; i += 2) {
            map.put(args[i], args[i + 1]);
        }
        setMode(map.getOrDefault("-mode", "enc"));
        setKey(Integer.parseInt(map.getOrDefault("-key", "0")));
        setOut(map.getOrDefault("-out", "standardOutput"));
        if (!map.containsKey("-data") && map.containsKey("-in")) {
            File file = new File(map.get("-in"));
            try (Scanner scanner = new Scanner(file);) {
                setData(scanner.nextLine());
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            setData(map.getOrDefault("-data", " "));
        }
        setAlgorithm(map.getOrDefault("-alg", "shift"));
    }

    public void makeOut() {
        getActionInfo(args);
        if (mode.equals("enc")) {
            MakeEncryption enc = new MakeEncryption(data, key, algorithm);
            if (out.equals("standardOutput")) {
                System.out.println(enc.makeTransform());
            } else {
                try (Writer writer = new FileWriter(out)) {
                    writer.write(enc.makeTransform());
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } else {
            MakeDecryption dec = new MakeDecryption(data, key, algorithm);
            if (out.equals("standardOutput")) {
                System.out.println(dec.makeTransform());
            } else {
                try (Writer writer = new FileWriter(out)) {
                    writer.write(dec.makeTransform());
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

}
