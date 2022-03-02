import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class lab1 {
    public static void main(String[] args) throws IOException {

       BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //String string = new String(Files.readAllBytes(Path.of("rjesenja.txt")));

        String line = reader.readLine();
        try (FileWriter writer = new FileWriter("rjesenja.txt")) {
           // int broj = Integer.parseInt(line);
           // writer.append(broj * broj + "\n");
                writer.append(line+"\n");
            while (line != null) {
               line = reader.readLine();

                try {
                    //broj = Integer.parseInt(line);
                    //writer.append(broj * broj + "\n");
                    writer.append(line+"\n");

                } catch (NumberFormatException e) {
                    //dodajem komentar
                }
            }


        }
    }
}
//
//import java.io.*;
//        import java.nio.charset.StandardCharsets;
//        import java.nio.file.Files;
//        import java.nio.file.Path;
//
//public class proba {
//    public static void main(String[] args) throws IOException {
//        FileWriter writer = new FileWriter("data.txt", StandardCharsets.UTF_8);
//        String podatci = new String(Files.readAllBytes(Path.of("unos.txt")));
//        System.out.println("Podatci:" + podatci);
//
//        String s = reader.readLine();
//        while (s != null) {
//            System.out.println("NOVO");
//            //int x = Integer.parseInt(s);
//            System.out.println(s);
//            writer.write(s + "\n");
//            s = reader.readLine();
//        }
//
//        writer.close();
//    }
//}
