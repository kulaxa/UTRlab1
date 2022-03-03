import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class UniqueQueue<T> implements Queue<T> {

    private final Queue<T> queue = new LinkedList<T>();
    private final Set<T> set = new HashSet<T>();

    @Override public boolean add(T t) {
        // Only add element to queue if the set does not contain the specified element.
        if (set.add(t))
            queue.add(t);
        return true; // Must always return true as per API def.
    }

    @Override public boolean addAll(Collection<? extends T> arg0) {
        boolean ret = false;
        for (T t: arg0)
            if (set.add(t)) {
                queue.add(t);
                ret = true;
            }
        return ret;
    }

    @Override public T remove() throws NoSuchElementException {
        T ret = queue.remove();
        set.remove(ret);
        return ret;
    }

    @Override public boolean remove(Object arg0) {
        boolean ret = queue.remove(arg0);
        set.remove(arg0);
        return ret;
    }

    @Override public boolean removeAll(Collection<?> arg0) {
        boolean ret = queue.removeAll(arg0);
        set.removeAll(arg0);
        return ret;
    }

    @Override public void clear() {
        set.clear();
        queue.clear();
    }

    @Override public boolean contains(Object arg0) {
        return set.contains(arg0);
    }

    @Override public boolean containsAll(Collection<?> arg0) {
        return set.containsAll(arg0);
    }

    @Override public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override public Iterator<T> iterator() {
        return queue.iterator();
    }

    @Override public boolean retainAll(Collection<?> arg0) {
        throw new UnsupportedOperationException();
    }

    @Override public int size() {
        return queue.size();
    }

    @Override public Object[] toArray() {
        return queue.toArray();
    }

    @Override public <T> T[] toArray(T[] arg0) {
        return queue.toArray(arg0);
    }

    @Override public T element() {
        return queue.element();
    }

    @Override public boolean offer(T e) {
        return queue.offer(e);
    }

    @Override public T peek() {
        return queue.peek();
    }

    @Override public T poll() {
        return queue.poll();
    }

    public  UniqueQueue<T> copy(Collection<? extends T> arg0){
        UniqueQueue<T> nova = new UniqueQueue<>();
        for(var el: arg0){
            nova.add(el);
        }

        return  nova;
    }
}

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
        if(states == null){
            return  output;
        }

        for(State el: states){
            output.push(el.getName());
        }
        return  output;
    }

}

public class lab {
    public lab() {}


    public UniqueQueue<String> Rekurzija(String current, UniqueQueue<String> sadasnji){
        UniqueQueue<String> temp = new UniqueQueue<>();
        Rec(current, temp);

        UniqueQueue<String> output = new UniqueQueue<>();

        while(!temp.isEmpty()){
            String el = temp.remove();
            if(sadasnji.contains(el) == false){
                output.add(el);
            }
        }

        return  output;
    }

    public void Rec(String curr, UniqueQueue<String> output){
        State sadasnji = State.getStateByName(curr);
        Map<String, LinkedList<State>> sadasnji_map = sadasnji.getTransitions();

        if(sadasnji_map.containsKey("$") == false){
            return;
        }

        if(output.contains(curr)){
            return;
        }

        output.add(curr);

        UniqueQueue<String> sadasnji_list = new UniqueQueue<>();
        sadasnji_list.addAll( State.StringElements(sadasnji_map.get("$")));

        Iterator<String> Iterator = sadasnji_list.iterator();

        while(Iterator.hasNext()){
            String el = Iterator.next();
            output.add(el);
            Rec(el, output);
        }

        return;
    }

    public static void main(String[] args) throws IOException {
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        FileWriter writer = new FileWriter("output.txt");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        LinkedList<State> goodStates = new LinkedList<>();
        LinkedList<State> allStates = new LinkedList<>();
        State beginState = null;

        //prvi red - inputa
        String s = reader.readLine(); //Inputi1
        String[] str = s.split("\\|"); //Ako ima vise inputa preko |

        List<List<String>> inputs = new LinkedList<List<String>>(); //VARIJABLA ZA PRVI RED - INPUTI

        int i = 0;
        for (String element : str) {
            inputs.add(new LinkedList<String>());
            String[] temp = element.split(",");

            for (String el : temp) {
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
        for (String el : str) {
            abeceda.add(el);
        }

        s = reader.readLine(); //Prihvatljiva stanja
        LinkedList<String> prihvatljiva_stanja = new LinkedList<>();
        str = s.split(",");
        for (String el : str) {
            prihvatljiva_stanja.add(el);
            goodStates.add(State.getStateByName(el));
        }

        String pocetak = reader.readLine(); //Pocetak
        beginState = State.getStateByName(s);


        s = reader.readLine(); //Pocetak stanja
        while (s != null) {
            String pocetno;

            Pattern pattern3 = Pattern.compile("^(.*?),");
            Matcher matcher3 = pattern3.matcher(s);
            matcher3.find();
            pocetno = matcher3.group(1);

            var mapa = State.getStateByName(pocetno).getTransitions();

            String unos;
            Pattern pattern2 = Pattern.compile(",(.*?)->");
            Matcher matcher2 = pattern2.matcher(s);
            matcher2.find();
            unos = matcher2.group(1);

            String zavrsna;
            Pattern pattern = Pattern.compile("->(.*?)$");
            Matcher matcher = pattern.matcher(s);
            matcher.find();
            zavrsna = matcher.group(1);

            String[] zavrsno = zavrsna.split(",");
            for (String x : zavrsno) {
                if (mapa.containsKey(unos) == false) {
                    LinkedList<State> state = new LinkedList<>();
                    mapa.put(unos, state);
                }

                mapa.get(unos).add(State.getStateByName(x));
            }

            s = reader.readLine();
        }


        for (List<String> inputs_list : inputs) {

        UniqueQueue<String> queue = new UniqueQueue<String>();
        UniqueQueue<String> sadasnji = new UniqueQueue<>();
        UniqueQueue<String> buduci = new UniqueQueue<>();
        TreeSet<String> set = new TreeSet<>();

        String ispis = "";
        int counter = 0;
        lab labObj = new lab();
        boolean zastavica = false;

        sadasnji.add(pocetak);
        queue.add(pocetak);

        for (String input : inputs_list) {

            while (!queue.isEmpty()) {
                String el = queue.peek();
                State current = State.getStateByName(el);


                UniqueQueue<String> novo = labObj.Rekurzija(el, sadasnji);

                sadasnji.addAll(novo);
                queue.addAll(novo);

                Map<String, LinkedList<State>> current_map = current.getTransitions();
                buduci.addAll(State.StringElements(current_map.get(input)));
                queue.remove();
            }
            set.addAll(sadasnji);
            counter = set.size();
            if (counter > 0) {        //OBrisi poslije i ispravi da ne dodaje #
                set.remove("#");
                counter = set.size();
            }
            for (String element : set) {
                ispis = ispis + element;

                if (counter - 1 > 0) {
                    ispis = ispis + ",";
                }
                counter--;
            }

            counter = set.size();
            if (counter == 0) {
                ispis = ispis + "#";
            }

            set = new TreeSet<>();
            ispis = ispis + "|";
            sadasnji = buduci;

            sadasnji.remove("#"); //Ako je ostao # unutra
            queue = queue.copy(sadasnji);
            buduci = new UniqueQueue<>();
            counter = 0;

        }

        while (!queue.isEmpty()) {
            String el = queue.remove();
            UniqueQueue<String> novo = labObj.Rekurzija(el, sadasnji);
            sadasnji.addAll(novo);
            queue.addAll(novo);
        }

        set = new TreeSet<>();
        set.addAll(sadasnji);
        counter = set.size();
        zastavica = false;
        if (counter == 0) {
            zastavica = true;
        }

        if (counter > 0) {        //OBRISI I ISPRAVI
            set.remove("#");
            counter = set.size();
        }

        for (String element : set) {
            ispis = ispis + element;
            if (counter - 1 > 0) {
                ispis = ispis + ",";
            }
            counter--;
        }

        if (zastavica == true) {
            ispis = ispis + "#";
        }

        ispis = ispis + "\n";
        writer.write(ispis);

//        System.out.println(ispis);
    }


        writer.close();
    }

}
