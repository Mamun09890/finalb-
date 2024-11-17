package dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Dictionary {
    public static void main(String[] args) {
        BPlusTree bpt = new BPlusTree(4); // Order can be adjusted

        // Read dictionary file
        try (BufferedReader br = new BufferedReader(new FileReader("Dictionary.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    String word = parts[0].trim();
                    String meaning = parts[1].trim();
                    bpt.insert(word, meaning);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // User input for searching the dictionary
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a word to find its meaning (or type 'exit' to quit): ");
            String word = scanner.nextLine();
            if (word.equalsIgnoreCase("exit")) {
                break;
            }
            String meaning = bpt.search(word);
            if (meaning != null) {
                System.out.println("Meaning: " + meaning);
            } else {
                System.out.println("Word not found in the dictionary.");
            }
        }
        scanner.close();
    }
}

