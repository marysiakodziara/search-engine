//package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.*;

class Finder {

    final FindingStrategy strategy;

    private String passedQuery;

    public Finder(FindingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setPassedQuery(String passedQuery) {
        this.passedQuery = passedQuery;
    }

    public void find(ArrayList<String> person, Map<String, LinkedHashSet<Integer>> mappedFile) {
        // write your code here
        this.strategy.getResult(person, mappedFile, passedQuery);
    }
}

interface FindingStrategy {

    // Returns search result
    void getResult(ArrayList<String> person, Map<String, LinkedHashSet<Integer>> mappedFile, String passedQuery);
}

class AllFindingStrategy implements FindingStrategy {

    @Override
    public void getResult(ArrayList<String> person, Map<String, LinkedHashSet<Integer>> mappedFile, String passedQuery) {
        //algorithm for searching and printing lines containing
        //all the words from the query

        if (mappedFile.containsKey(passedQuery)) {
            for (Integer i : mappedFile.get(passedQuery)) {
                System.out.println(person.get(i));
            }
        }
    }
}

class AnyFindingStrategy implements FindingStrategy {

    @Override
    public void getResult(ArrayList<String> person, Map<String, LinkedHashSet<Integer>> mappedFile, String passedQuery) {
        //algorithm for searching and printing lines containing
        //at least one word from the query

        String[] tokensHolder = passedQuery.split(" ");
        Set<String> queryRecords = new HashSet<>();

        for (String x : tokensHolder) {
            if (mappedFile.containsKey(x.toLowerCase())) {
                for (Integer i : mappedFile.get(x)) {
                    queryRecords.add(person.get(i));
                }
            }
        }

        queryRecords.forEach(System.out::println);
    }
}

class NoneFindingStrategy implements FindingStrategy {

    @Override
    public void getResult(ArrayList<String> person, Map<String, LinkedHashSet<Integer>> mappedFile, String passedQuery) {

        //algorithm for searching and printing lines not containing
        //words from the query

        String[] tokensHolder = passedQuery.split(" ");
        Set<String> queryRecords = new HashSet<>();

        for (String x : tokensHolder) {
            if (mappedFile.containsKey(x.toLowerCase())) {
                for (Integer i : mappedFile.get(x)) {
                    queryRecords.add(person.get(i));
                }
            }
        }


        Set<String> tmpDifference = new HashSet<>(person);
        tmpDifference.removeAll(queryRecords);
        tmpDifference.forEach(System.out::println);


    }
}

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(Paths.get(args[1]).toUri());
        Scanner in = new Scanner(new FileReader(file));
        ArrayList<String> person = new ArrayList<>();
        while (in.hasNextLine()) {
            person.add(in.nextLine());
        }

        //file mapping

        Map<String, LinkedHashSet<Integer>> mappedFile = new HashMap<>();

        //iterate through arraylist person
        for (String p : person) {
            //iterate through every word of element of person
            for (String r : p.split("\s++")) {
                r = r.toLowerCase();
                if (mappedFile.containsKey(r)) {
                    mappedFile.get(r).add(person.indexOf(p));
                } else {
                    LinkedHashSet<Integer> value = new LinkedHashSet<>();
                    value.add(person.indexOf(p));
                    mappedFile.put(r, value);
                }
            }
        }

        file.deleteOnExit();
        menu(person, mappedFile);
        in.close();
    }

    public static void menu(ArrayList<String> person, Map<String, LinkedHashSet<Integer>> mappedFile) {
        boolean stop = true;
        while (stop) {
            System.out.println("=== Menu ===");
            System.out.println("1. Find a person");
            System.out.println("2. Print all people");
            System.out.println("0. Exit");
            try {
                int option = scanner.nextInt();
                switch (option) {
                    case 1 -> {
                        System.out.println("Select a matching strategy: ALL, ANY, NONE");
                        Finder finder = StrategySetting();
                        finder.find(person, mappedFile);
                    }
                    case 2 -> secondOption(person);
                    case 0 -> stop = false;
                    default -> System.out.println("Incorrect option! Try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input, please select a number.");
                scanner.nextLine();
            }
        }
        System.out.println("Bye!");
        scanner.close();
    }

    public static Finder StrategySetting() {

        scanner.nextLine();
        final String type = scanner.nextLine();

        Finder finder = switch (type) {
            case "ALL" -> new Finder(new AllFindingStrategy());
            case "ANY" -> new Finder(new AnyFindingStrategy());
            default -> new Finder(new NoneFindingStrategy());
        };

        System.out.println("Enter a name or email to search all suitable people.");
        finder.setPassedQuery(scanner.nextLine());

        return finder;
    }

    public static void secondOption(ArrayList<String> person) {
        System.out.println("=== List of people ===");
        person.forEach(System.out::println);
    }
}
