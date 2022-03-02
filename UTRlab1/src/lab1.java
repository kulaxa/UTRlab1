import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class State{
    private String name;
    private Map<String, List<State>> transitions;

    public static List<State> allStates = new ArrayList<>();

    public State(String name) {
        this.name = name;
        transitions = new HashMap<String, List<State>>();
    }
    public Map<String, List<State>> getTransitions(){
        return transitions;
    }
    public String getName(){
        return name;
    }

    public List<State> getNextStates(String key){
        return transitions.get(key);
    }
    public void addNextSate(String key, String state){
        if(!transitions.containsKey(key)){
            List<State> temp = new ArrayList<>();
            temp.add(getStateByName(state));
            transitions.put(key, temp);
        }
        else{
            transitions.get(key).add(getStateByName(state));
        }

    }

    public static State getStateByName(String name){
      // return allStates.stream().filter((s) -> { return name == s.getName();}).findAny().get();

       for(State s : State.allStates){
            if(s.getName().equals(name)){
                return s;
            }
        }
       return null;
    }

    public void CheckForEmptyKey(List<State> result){
         CheckForEmptyKeyRec(this, result);
    }

    private void CheckForEmptyKeyRec(State state, List<State> result) {
        if(state.getNextStates("$") ==null )return;
        result.addAll(state.getNextStates("$"));
        for(State s: state.getNextStates("$")){
            CheckForEmptyKeyRec(s, result);
        }
    }


    public void printAllNextStates(){
            transitions.entrySet().stream().forEach((entry)->{
                System.out.print( entry.getKey() + ": ");
                for(State s :entry.getValue()){
                    System.out.print(s.name + ", ");
                }
                System.out.println();
            });

    }
}


public class lab1 {


    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<State> goodStates = new ArrayList<>();
        State beginState = null;
        State.allStates.add(new State("#"));
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
                    if(State.getStateByName(el) != null){
                        goodStates.add(State.getStateByName(el));
                    }

                }

                s = reader.readLine(); //begin state
                beginState = State.getStateByName(s);


                //load everything else
               while(true){
                   String line = reader.readLine();
                   if(line ==null) break;

                   String[] splits = line.split("->");
                    String[] starting = splits[0].split(",");
                    String[] nextStates = splits[1].split(",");
                    for(String st: nextStates){
                        State.getStateByName(starting[0]).addNextSate(starting[1],st);
                        //starting[0] -> state to change
                        //starting[1] -> key to change state
                        //nextStates -> list of states that change with that key
                    }

               }
               for(State st: State.allStates){
                   System.out.println(st.getName());
                   st.printAllNextStates();
               }


                //checking empty states for begin state
               List<State> tempRez = new ArrayList<>();
            beginState.CheckForEmptyKey( tempRez);
            System.out.println("Checking empty states");
            for(State st: tempRez){
               System.out.print(st.getName() + ", ");
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
