import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

        for(var el : inputs){
            for(var x: el){
                System.out.print(x + " ");
            }
            System.out.println();
        }

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


        writer.close();
    }
}
