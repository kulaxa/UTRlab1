import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class State{
    private String name;
    private Map<String, List<State>> transitions;

    public static List<State> allStates = new ArrayList<>();

    public State(String name){
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
        for(State s : allStates){
            if(s.getName() == name){
                return s;
            }
        }
        return null;
    }

}

public class lab {
    public lab() {
            }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        FileWriter writer = new FileWriter("output.txt");

        //prvi red - inputa
        String s = reader.readLine(); //Inputi1
        String[] str = s.split("\\|"); //Ako ima vise inputa preko |

        List<List<String>> inputs = new ArrayList<List<String>>(); //VARIJABLA ZA PRVI RED - INPUTI

        int i = 0;
        for(String element : str){
            inputs.add(new ArrayList<String>());
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
        List<String> stanja = new ArrayList<>();
        str = s.split(",");
        for(String el: str){
            stanja.add(el);
        }

        s = reader.readLine(); //Abeceda
        List<String> abeceda = new ArrayList<>();
        str = s.split(",");
        for(String el: str){
            abeceda.add(el);
        }

        s = reader.readLine(); //Prihvatljiva stanja
        List<String> prihvatljiva_stanja = new ArrayList<>();
        str = s.split(",");
        for(String el: str){
            prihvatljiva_stanja.add(el);
        }

        String pocetak = reader.readLine(); //Pocetak


        s = reader.readLine(); //Pocetak stanja

        while(s != null){

            Pattern pattern3 = Pattern.compile("^(.*?),");
            Matcher matcher3 = pattern3.matcher(s);
            if (matcher3.find())
            {
                System.out.println(matcher3.group(1));
            }

            Pattern pattern2 = Pattern.compile(",(.*?)->");
            Matcher matcher2 = pattern2.matcher(s);
            if (matcher2.find())
            {
                System.out.println(matcher2.group(1));
            }

            Pattern pattern = Pattern.compile("->(.*?)$");
            Matcher matcher = pattern.matcher(s);
            if (matcher.find())
            {
                System.out.println(matcher.group(1));
            }

            s = reader.readLine();
        }



        writer.close();
    }
}
