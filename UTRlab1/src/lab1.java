import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class State{
    private String name;
    private Map<String, List<State>> transitions;

    public static List<State> allStates = new ArrayList<>();

    public State(String ime){
        this.name = name;
        transitions = new HashMap<String, List<State>>();
    }
    public String getName(){
        return name;
    }

    public List<State> getNextStates(String key){
        return transitions.get(key);
    }

    public static State getStateByName(String name){
      // return allStates.stream().filter((s) -> { return name == s.getName();}).findAny().get();

       for(State s : allStates){
            if(s.getName() == name){
                return s;
            }
        }
       return null;
    }
}


public class lab1 {


    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<State> goodStates = new ArrayList<>();
        State beginState = null;
        //String string = new String(Files.readAllBytes(Path.of("rjesenja.txt")));

        // String line = reader.readLine();
        try (FileWriter writer = new FileWriter("rjesenja.txt")) {
            String s = reader.readLine(); //Inputi1
            String[] str = s.split("\\|"); //Ako ima vise inputa preko |

            List<List<String>> inputs = new ArrayList<List<String>>(); //VARIJABLA ZA PRVI RED - INPUTI

            //int i = 0;
            for (int i = 0; i < str.length; i++) {
                inputs.add(new ArrayList<String>());
                String[] temp = str[i].split(",");

                for (String el : temp) {
                    el.strip();
                    inputs.get(i).add(el);
                }
            }
                s = reader.readLine(); //Stanja

                str = s.split(",");
                for (String el : str) {

                    State.allStates.add(new State(el));
                }

                s = reader.readLine(); //Abeceda
                List<String> abeceda = new ArrayList<>();
                str = s.split(",");

                for (String el : str) {
                    abeceda.add(el);
                }

                s = reader.readLine(); //poželjna stanja
                for(String el: s.split(",")){
                    goodStates.add(State.getStateByName(el));
                }

                s = reader.readLine();
                beginState = State.getStateByName(s);

                //provjera
            System.out.println("Provjera: ");
                for(State sat: State.allStates){
                    System.out.println(sat.getName());
                }

            System.out.println("Provjera abeceda: ");
                for(String st: abeceda){
                    System.out.print(st + " ");
                }

                for(State st: goodStates){
                    System.out.print(st.getName() + " ");
                }
            System.out.println(beginState.getName());
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
