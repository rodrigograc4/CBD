package redis.ex4;

import redis.clients.jedis.Jedis;
import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

public class Autocomplete {
    public static String NAMES_KEY = "names";
    public static String FILE_PATH = "./ex4/names.txt";

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        jedis.flushDB();
        Scanner inputScanner = new Scanner(System.in);
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(new File(FILE_PATH));
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException. Exiting.");
            System.exit(1);
        }

        Set<String> nameSet = new HashSet<>();

        while (fileScanner.hasNextLine()) {
            String s = fileScanner.nextLine();
            jedis.sadd(NAMES_KEY, s);
            nameSet.add(s.toLowerCase());
        }

        fileScanner.close();

        while (true) {
            System.out.println();
            System.out.print("Search for ('Enter' for quit): ");
            String input = inputScanner.nextLine().toLowerCase();
            if (input.equals("")) break;

            Set<String> names = jedis.smembers(NAMES_KEY);
            List<String> matches = new ArrayList<>();
            
            for (String name : names) {
                if (name.toLowerCase().startsWith(input) && !matches.contains(name)) {
                    matches.add(name);
                }
            }

            Collections.sort(matches);
            for (String name : matches) {
                System.out.println(name);
            }

            System.out.println();
        }

        inputScanner.close();
        jedis.close();
    }
}
