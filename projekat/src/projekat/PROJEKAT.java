
package projekat;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PROJEKAT {
    public static void main(String[] args) throws Exception {
        Integer opcija;
        Scanner scan = new Scanner(System.in);
        
        while (true) {
        System.out.println("\n--------------------DOBRODOSLI--------------------");
        meni();
    
        try {
            opcija = scan.nextInt(); 
        
            switch (opcija) {
                case 1:
                    scan.nextLine();
                    ukras();
                    Korisnik.ulogujse();
                    ukras();
                    break;
                
                case 2:
                    scan.nextLine();
                    ukras();
                    Korisnik.registrujse();
                    ukras();
                    break;
                
                case 0:
                    ukras();
                    System.out.println("Izasli ste iz programa! Prijatan dan! :)");
                    ukras();
                    System.exit(0);
                    break;
                
                default:
                    System.out.println("Uneta nepostojeca opcija! Probajte ponovo!");
                    break;
            }
        
        } catch (Exception e) {
            System.out.println("Pogresna opcija! Molimo probajte ponovo!");
            scan.nextLine(); 
        }
    }
        
    }
    public static void meni()
    {
        System.out.println("Odaberite jednu od opcija:");
        System.out.println("1 - ulogujte se");
        System.out.println("2 - registrujte se");
        System.out.println("0 - izlaz");
        System.out.print("Unesite opciju: ");
    }
    
    public static void ukras(){
        System.out.println("\n----------------------------------------------------------");
                System.out.println("**********************************************************");
                System.out.println("----------------------------------------------------------\n");
    }
        
}
