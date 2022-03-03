import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class State{
    private String name;
    private Map<String, LinkedList<State>> transitions;

    public static LinkedList<State> allStates = new LinkedList<>();

    public State(String name){
        this.name = name;
        transitions = new HashMap<String, LinkedList<State>>();
    }

    public String getName(){
        return name;
    }

    public Map<String, LinkedList<State>> getTransitions(){
        return transitions;
    }

    public LinkedList<State> getNextStates(String key){
        return transitions.get(key);
    }

    public static State getStateByName(String name){
        for(State s : allStates){
            if(s.getName().equals(name)){
                return s;
            }
        }
        return null;
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

    public String StringName(State state){
        return state.name;
    }

    public static LinkedList<String> StringElements(LinkedList<State> states){
        LinkedList<String> output = new LinkedList<>();
        for(State el: states){
            output.push(el.getName());
        }
        return  output;
    }

}

public class lab {
    public lab() {}


    public LinkedList<String> Rekurzija(String current){
        LinkedList<String> output = new LinkedList<>();
        Rec(current, output);

        return  output;
    }

    public void Rec(String curr, LinkedList<String> output){
        State sadasnji = State.getStateByName(curr);
        Map<String, LinkedList<State>> sadasnji_map = sadasnji.getTransitions();

        if(sadasnji_map.containsKey(State.getStateByName("$")) == false){
            return;
        }

        output.push(curr);

        LinkedList<String> sadasnji_list = new LinkedList<>();
        sadasnji_list.addAll( State.StringElements(sadasnji_map.get(State.getStateByName("$"))));

        Iterator<String> Iterator = sadasnji_list.iterator();

        while(Iterator.hasNext()){
            String el = Iterator.next();
            Rec(el, output);
        }

        return;
    }

    public static void main(String[] args) throws IOException {
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //FileWriter writer = new FileWriter("output.txt");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        LinkedList<State> goodStates = new LinkedList<>();
        LinkedList<State> allStates = new LinkedList<>();
        State beginState = null;

        //prvi red - inputa
        String s = reader.readLine(); //Inputi1
        String[] str = s.split("\\|"); //Ako ima vise inputa preko |

        List<List<String>> inputs = new LinkedList<List<String>>(); //VARIJABLA ZA PRVI RED - INPUTI

        int i = 0;
        for(String element : str){
            inputs.add(new LinkedList<String>());
            String[] temp = element.split(",");

            for(String el : temp){
                el.strip();
                inputs.get(i).add(el);
            }
            i++;
        }

//        for(var el : inputs){
//            for(var x: el){
//                System.out.print(x + " ");
//            }
//            System.out.println();
//        }

        s = reader.readLine(); //Stanja
        //List<String> stanja = new ArrayList<>();
        str = s.split(",");
        State.allStates.add((new State("#")));
        for (String el : str) {
            State.allStates.add(new State(el));

        }

//        for(var x: State.allStates){
//            System.out.println(x.getName());
//        }

        s = reader.readLine(); //Abeceda
        LinkedList<String> abeceda = new LinkedList<>();
        str = s.split(",");
        for(String el: str){
            abeceda.add(el);
        }

        s = reader.readLine(); //Prihvatljiva stanja
        LinkedList<String> prihvatljiva_stanja = new LinkedList<>();
        str = s.split(",");
        for(String el: str){
            prihvatljiva_stanja.add(el);
            goodStates.add(State.getStateByName(el));
        }

        String pocetak = reader.readLine(); //Pocetak
        beginState = State.getStateByName(s);


        s = reader.readLine(); //Pocetak stanja
        while(s != null){
            String pocetno;

            Pattern pattern3 = Pattern.compile("^(.*?),");
            Matcher matcher3 = pattern3.matcher(s);
            matcher3.find();
            pocetno = matcher3.group(1);
//            if (matcher3.find())
//            {
//                System.out.println(matcher3.group(1));
//            }
            var mapa = State.getStateByName(pocetno).getTransitions();


            String unos;
            Pattern pattern2 = Pattern.compile(",(.*?)->");
            Matcher matcher2 = pattern2.matcher(s);
            matcher2.find();
            unos = matcher2.group(1);
//            if (matcher2.find())
//            {
//                System.out.println(matcher2.group(1));
//            }

            String zavrsna;
            Pattern pattern = Pattern.compile("->(.*?)$");
            Matcher matcher = pattern.matcher(s);
            matcher.find();
            zavrsna = matcher.group(1);
//            if (matcher.find())
//            {
//                System.out.println(matcher.group(1));
//            }
            String[] zavrsno = zavrsna.split(",");
            for(String x: zavrsno){
                if(mapa.containsKey(unos) == false){
                    LinkedList<State> state = new LinkedList<>();
                    mapa.put(unos, state);
                }

                mapa.get(unos).add(State.getStateByName(x));
            }

            s = reader.readLine();
        }


//        System.out.println("Svi pogledani");
//        writer.close();


        for(State st: State.allStates){
            System.out.println(st.getName());
            st.printAllNextStates();
        }


        LinkedList<String> sadasnji = new LinkedList<>();
        LinkedList<String> buduci = new LinkedList<>();
        LinkedList<String> temp = new LinkedList<>();
        TreeSet<String> set = new TreeSet<>();

        String ispis = "";
        int counter = 0;

        sadasnji.add(pocetak);

        lab labObj = new lab();

        for(String input: inputs.get(0)){
            Iterator<String> Iterator = sadasnji.iterator();
            while(Iterator.hasNext()) {
//              State current = State.getStateByName(sadasnji.getFirst());
                String el = Iterator.next();
                State current = State.getStateByName(el);

//                ispis = ispis + sadasnji.getFirst();
//                sadasnji.removeFirst();
                buduci.addAll(labObj.Rekurzija(el));

//                if(counter-1 > 0){
//                    ispis = ispis + ",";
//                }

                Map<String, LinkedList<State>> current_map = current.getTransitions();
                buduci.addAll( State.StringElements(current_map.get(input)));
//                counter--;
            }
                set.addAll(sadasnji);
                counter = set.size();
                for(String element : set){
                    ispis = ispis + element;

                    if(counter-1 > 0){
                    ispis = ispis + ",";
                    }
                    counter--;
                }

                set = new TreeSet<>();
//              counter = buduci.size();
                ispis = ispis + "|";
                sadasnji = buduci;
                buduci = new LinkedList<>();
        }

        counter = sadasnji.size();
        for(String element: sadasnji){
            ispis = ispis + element;
            if(counter-1 > 0){
                ispis = ispis + ",";
            }
            counter--;
        }



//        System.out.println();
//        String sort[] = ispis.split("\\|");
//        String innersort[];
//        HashSet<String> polje = new HashSet<>();
//        String output = "";
//
//        for(String el: sort){
//            System.out.println(el);
//            innersort = el.split(",");
//            for(String x: innersort){
//                System.out.println(x);
//                polje.add(x);
//            }
//            polje.stream().sorted();
//            counter = polje.size();
//
//            for(String y: polje){
//                output = output + y;
//                if(counter-1 > 0){
//                    output = output + ",";
//                }
//                counter--;
//            }
//                output = output + "|";
//                polje = new HashSet<>();
//
//        }
//        System.out.println(output);

        System.out.println(ispis);
    }

}
