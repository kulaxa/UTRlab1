
import java.io.*;
import java.util.*;

class State implements Comparable<State> {
    private String name;
    private Map<String, Set<State>> transitions;
    public static Set<State> allStates = new TreeSet<>();

    public State(String name) {
        this.name = name;
        transitions = new HashMap<String, Set<State>>();
    }

    @Override
    public boolean equals(Object obj) {
        return ((State) obj).getName().equals(this.getName());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public String getName() {
        return name;
    }

    public Set<State> getNextStates(String key) {
        return transitions.get(key);
    }

    public void addNextSate(String key, String state) {
        Set<State> tempSet = transitions.computeIfAbsent(key, k -> new TreeSet<>());
        tempSet.add(getStateByName(state));
    }

    public static State getStateByName(String name) {
        for (State s : State.allStates) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }

    public void CheckForEmptyKey(Collection<State> result) {
        CheckForEmptyKeyRec(this, result, new ArrayList<>());
    }

    private void CheckForEmptyKeyRec(State state, Collection<State> result, List<String> controlList) {
        if (state.getNextStates("$") == null) return;
        controlList.add(state.getName());
        result.addAll(state.getNextStates("$"));
        for (State s : state.getNextStates("$")) {
            boolean visited = false;
            for (String str : controlList) {
                if (str.equals(s.getName())) {
                    visited = true;
                }
            }
            if (!visited)
                CheckForEmptyKeyRec(s, result, controlList);
        }
    }

    @Override
    public int compareTo(State state) {
        return this.getName().compareTo(state.getName());
    }
}

public class SimEnka {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<State> goodStates = new ArrayList<>();
        State.allStates.add(new State("#"));
        String novaLinija = reader.readLine();
        String[] splitsLinija = novaLinija.split("\\|");
        List<List<String>> prvalinija = new ArrayList<List<String>>();
        for (int i = 0; i < splitsLinija.length; i++) {
            prvalinija.add(new ArrayList<String>());
            String[] temp = splitsLinija[i].split(",");
            for (String el : temp) {
                el.strip();
                prvalinija.get(i).add(el);
            }
        }
        novaLinija = reader.readLine(); //Stanja
        splitsLinija = novaLinija.split(",");
        for (String el : splitsLinija) {
            State.allStates.add(new State(el));
        }
        novaLinija = reader.readLine(); //Abeceda
        List<String> svaSlova = new ArrayList<>();
        splitsLinija = novaLinija.split(",");

        for (String el : splitsLinija) {
            svaSlova.add(el);
        }
        novaLinija = reader.readLine(); //pozeljna stanja
        for (String el : novaLinija.split(",")) {
            if (State.getStateByName(el) != null) {
                goodStates.add(State.getStateByName(el));
            }
        }
        novaLinija = reader.readLine(); //begin state
        State beginState = State.getStateByName(novaLinija);
        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            String[] splits = line.split("->");
            String[] starting = splits[0].split(",");
            String[] nextStates = splits[1].split(",");
            for (String st : nextStates) {
                State.getStateByName(starting[0]).addNextSate(starting[1], st);
            }
        }
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < prvalinija.size(); index++) {
            Set<State> nextStepStates = new TreeSet<>();
            beginState.CheckForEmptyKey(nextStepStates);
            nextStepStates.add(beginState);
            Set<State> tempSet = new TreeSet<>();
            for (String inp : prvalinija.get(index)) {
                for (State st : nextStepStates) {
                    if (st.getNextStates(inp) != null) {
                        tempSet.addAll(st.getNextStates(inp));
                    } else {
                    }
                }
                for (Object st : tempSet.toArray()) {
                    ((State) st).CheckForEmptyKey(tempSet);
                }
                writeToBuilder(nextStepStates, false, builder);
                nextStepStates.clear();
                nextStepStates.addAll(tempSet);
                tempSet.clear();
            }
            writeToBuilder(nextStepStates, true, builder);
            System.out.print(builder.toString());
            builder = new StringBuilder();
        }
    }

    public static void writeToBuilder(Set<State> nextStatesSet, boolean lastCall, StringBuilder builder) {
        boolean addedSomething = false;
        for (int i = 0; i < nextStatesSet.size(); i++) {
            if (!((State) nextStatesSet.toArray()[i]).getName().equals("#")) {
                builder.append(((State) nextStatesSet.toArray()[i]).getName());
                if (i != nextStatesSet.size() - 1) {
                    builder.append(",");
                }
                addedSomething = true;
            }
        }
        if (!addedSomething) {
            builder.append("#");
        }
        if (!lastCall) {
            builder.append("|");
        } else {
            builder.append("\n");
        }
    }
}
//trebam promjeniti nazive dok ucitvam podatke.


