import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.*;

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
        for(String p : person) {
            //iterate through every word of element of person
            for(String r : p.split("\s++")) {
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
                    case 1 -> firstOption(person, mappedFile);
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

    public static void firstOption(ArrayList<String> person, Map<String, LinkedHashSet<Integer>> mappedFile) {
        /* it's a part of unused code from the bottom of this methods block

        int index = 0;
        int ac = 0;*/
        System.out.println("Enter a name or email to search all suitable people.");
        scanner.nextLine();
        String word = scanner.nextLine();


        if(mappedFile.containsKey(word)) {
            for (Integer i : mappedFile.get(word)) {
                System.out.println(person.get(i));
            }
        }


        /* previous code used for searching the data

        person.forEach((s -> {
            if (s.toLowerCase().contains(word.toLowerCase())) {
                System.out.println(s);
            }
        }));*/

        /* this code has not been used, it was written for educational purposes only

        person.stream().filter(s -> s.toLowerCase()
                .contains(word.toLowerCase()))
                .forEach(System.out::println);*/

        /* this code has not been used, it was written for educational purposes only

        for (String s : person) {
            if (s.toLowerCase().contains(word.toLowerCase())) {
                if (ac == 0) {
                    System.out.println("Found people:");
                    ac++;
                }
                System.out.println(s);
            }
        }*/
    }


    public static void secondOption(ArrayList<String> person) {
        System.out.println("=== List of people ===");
        person.forEach(System.out::println);
    }
}
