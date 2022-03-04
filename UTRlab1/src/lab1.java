
import java.io.*;
import java.util.*;

class State{
    private String name;
    private Map<String, List<State>> transitions;
    private int order;
    private static int currentOrder=0;
    public static List<State> allStates = new ArrayList<>();

    public State(String name) {
        this.name = name;
        order = currentOrder++;
        transitions = new HashMap<String, List<State>>();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return ((State)obj).getName().equals(this.getName());
    }

    public Map<String, List<State>> getTransitions(){
        return transitions;
    }
    public String getName(){
        return name;
    }
    public int getOrder(){
        return order;
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
       for(State s : State.allStates){
            if(s.getName().equals(name)){
                return s;
            }
        }
       return null;
    }
    public void CheckForEmptyKey(Collection<State> result){
         CheckForEmptyKeyRec(this, result, new ArrayList<>());
    }
    private void CheckForEmptyKeyRec(State state, Collection<State> result, List<String> controlList) {
        if(state.getNextStates("$") ==null )return;
        controlList.add(state.getName());
        result.addAll(state.getNextStates("$"));
        for(State s: state.getNextStates("$")){
            if(!state.getName().equals(s.getName())) {
                boolean visited = false;
                for(String str: controlList){
                    if(str.equals(s.getName())){
                        visited = true;
                    }
                }
                if(!visited)
                CheckForEmptyKeyRec(s, result,controlList);
            }
        }
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
                StringBuilder builder = new StringBuilder();
               //main algorithm
                for(int index=0; index< inputs.size(); index++) {
                    List<State> nextStepStates = new ArrayList<>();
                    beginState.CheckForEmptyKey(nextStepStates);
                    nextStepStates.add(beginState);
                    Set<State> temptempSet = new LinkedHashSet<>();
                    nextStepStates.stream().forEach((state) -> {
                        temptempSet.add((state));
                    });
                    nextStepStates.clear();
                    temptempSet.stream().forEach((state) -> nextStepStates.add(state));
                    nextStepStates.sort(new Comparator<State>() {
                        @Override
                        public int compare(State state, State t1) {
                            return state.getOrder() - t1.getOrder();
                        }
                    });
                    List<State> tempList = new ArrayList<>();
                    for (String inp : inputs.get(index)) {
                        for (State st : nextStepStates) {
                            if (st.getNextStates(inp) != null) {
                                tempList.addAll(st.getNextStates(inp));
                            } else {
                            }
                        }
                        Set<State> tempSet = new LinkedHashSet<>();
                        for (State st : tempList) {
                            st.CheckForEmptyKey(tempSet);
                        }
                        tempSet.addAll(tempList);
                        tempList.clear();
                        tempSet.stream().forEach((state) -> tempList.add(state));
                        tempList.sort(new Comparator<State>() {
                            @Override
                            public int compare(State state, State t1) {
                                return state.getOrder() - t1.getOrder();
                            }
                        });
                        boolean addedSomething = false;
                        for (int i = 0; i < nextStepStates.size(); i++) {
                            if (i + 1 != nextStepStates.size()) {
                                if (!nextStepStates.get(i).getName().equals("#")) {
                                    builder.append(nextStepStates.get(i).getName() + ",");
                                    addedSomething = true;
                                }
                            } else {
                                if (!nextStepStates.get(i).getName().equals("#")) {
                                    builder.append(nextStepStates.get(i).getName());
                                    addedSomething = true;
                                }
                            }
                        }
                        if (!addedSomething) {
                            builder.append("#");
                        }
                        builder.append("|");
                        nextStepStates.clear();
                        nextStepStates.addAll(tempList);
                        tempList.clear();
                    }
                    boolean addedSomething = false;
                    for (int i = 0; i < nextStepStates.size(); i++) {
                        if (i + 1 != nextStepStates.size()) {
                            if (!nextStepStates.get(i).getName().equals("#")) {
                                builder.append(nextStepStates.get(i).getName() + ",");
                                addedSomething = true;
                            }
                        } else {
                            if (!nextStepStates.get(i).getName().equals("#")) {
                                builder.append(nextStepStates.get(i).getName());
                                addedSomething = true;
                            }
                        }
                    }
                    if (!addedSomething) {
                        builder.append("#");
                    }
                    builder.append("\n");
                    writer.append(builder.toString());
                    builder = new StringBuilder();
                }

            }
        }
    }

    //mogu staviti da je koristim setove svugdi
//mogu promjeniti funkcije rekurzivne mislim da imaju redudantan if
//trebam promjeniti nazive dok učitvam podatke.
//ipak su stanja leksikografski poredana, a ne po onom njihovom redu, jebiga.

