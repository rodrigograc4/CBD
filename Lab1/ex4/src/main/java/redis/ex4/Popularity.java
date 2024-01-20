package redis.ex4;

import java.io.*;
import java.util.*;

import redis.clients.jedis.Jedis;

public class Popularity {
    public static String NAMES_KEY = "names_pt";
    public static String FILE_PATH = "./ex4/nomes-pt-2021.csv";

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        jedis.flushDB();
        Scanner inputScanner = new Scanner(System.in);
        Scanner fileScanner = openFileScanner(FILE_PATH);

        HashMap<String, String> map = parseFileToMap(fileScanner);
        jedis.hmset(NAMES_KEY, map);

        fileScanner.close();

        while (true) {
            System.out.print("Search for ('Enter' to quit): ");
            String input = inputScanner.nextLine();
            if (input.equals(""))
                break;

            Map<String, String> names = jedis.hgetAll(NAMES_KEY);
            names = sortByValue(names);

            for (Map.Entry<String, String> entry : names.entrySet()) {
                String name = entry.getKey();
                if (name.toLowerCase().startsWith(input.toLowerCase()))
                    System.out.println(name);
            }

            System.out.println();
        }

        inputScanner.close();
        jedis.close();
    }

    private static Scanner openFileScanner(String filePath) {
        try {
            return new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException. Exiting.");
            System.exit(1);
            return null;
        }
    }

    private static HashMap<String, String> parseFileToMap(Scanner fileScanner) {
        HashMap<String, String> map = new HashMap<>();
        while (fileScanner.hasNextLine()) {
            String s = fileScanner.nextLine();
            String[] parts = s.split(";");
            if (parts.length == 2) {
                String name = parts[0];
                String entries = parts[1];
                map.put(name, entries);
            }
        }
        return map;
    }

    private static Map<String, String> sortByValue(Map<String, String> map) {
        List<Map.Entry<String, String>> list = new LinkedList<>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> arg0, Map.Entry<String, String> arg1) {
                Integer i1 = Integer.parseInt(arg0.getValue());
                Integer i2 = Integer.parseInt(arg1.getValue());

                return i2.compareTo(i1);
            }
        });

        Map<String, String> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : list) {
            sortedMap.put(entry.getKey(), String.valueOf(entry.getValue()));
        }

        return sortedMap;
    }
}
