
package projekat;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Kupon {
    String naziv;

    public Kupon(String naziv) {
        this.naziv = naziv;
    }

    public String getNaziv() {
        return naziv;
    }

    
    @Override
    public String toString() {
        String[] str=naziv.split("-");
        if(str[0].startsWith("super"))
            str[1]+="%";
        
        return "Kupon{" + "naziv=" + naziv + ", popust="+str[1]+'}';
    }
    
    
    public static boolean proveriKupon(String naziv) throws FileNotFoundException, IOException, ParseException{
        JSONArray jsonKuponi = (JSONArray) new JSONParser().parse(new FileReader("kuponi.json"));
        boolean provera=false;
        for(Object o:jsonKuponi){
            JSONObject jsonObj = (JSONObject) o;
            if(jsonObj.get("naziv").toString().equalsIgnoreCase(naziv)){
                provera=true;
            }
        }
        return provera;
    }
    
    public static Double cenaSaKuponom(String naziv,Double staraCena){
        Double novaCena=0.0;
        String[] delovi=naziv.split("-");
        if(delovi[0].startsWith("top"))
        {
            Double popust = Double.parseDouble(delovi[1].toString());
            novaCena=staraCena-popust;
        }
        if(delovi[0].startsWith("super")){
            Double popust = Double.parseDouble(delovi[1].toString());
            Double smanjenje = staraCena*popust/100;
            novaCena=staraCena-smanjenje;
        }
        return novaCena;
    }
    
    
    public static void dodajNoviKupon() throws IOException, FileNotFoundException, ParseException {
        ArrayList<Kupon> lista = Kupon.ucitajKuponJSON("kuponi.json");
        Scanner scan = new Scanner(System.in);
        boolean zastava = false;
        Kupon kupon = null;
        String unos, nazivKupona = "";
        Integer popust, opcija;

        while (true) {
            System.out.println("Odaberite vrstu kupona:");
            System.out.println("1 - top (popust je novcana vrednost)");
            System.out.println("2 - super (popust je procenat)");
            System.out.println("0 - odustajanje");
            unos = scan.nextLine();

            try {
                opcija = Integer.parseInt(unos);
                if (opcija == 0)
                    break;

                if (opcija == 1) {
                    while (true) {
                        System.out.println("Unesite vrednost popusta:");
                        unos = scan.nextLine();

                        try {
                            popust = Integer.parseInt(unos);
                            if (popust < 200 || popust > 5000) {
                                System.out.println("\nMinimalni popust je 200, maksimalni popust je 5000! Probajte ponovo!\n");
                            } else {
                                nazivKupona = "top-" + popust.toString();
                                boolean postoji = false;
                                for (Kupon k : lista) {
                                    if (k.getNaziv().equals(nazivKupona)) {
                                        System.out.println("Kupon sa ovim popustom vec postoji! Probajte ponovo!");
                                        postoji = true;
                                        break;
                                    }
                                }
                                if (!postoji) {
                                    zastava = true;
                                    break;
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("-----------");
                            System.out.println("Pogresno uneta vrednost! Molimo unesite ceo broj!");
                            System.out.println("-----------");
                        }
                    }
                    break;
                } else if (opcija == 2) {
                    while (true) {
                        System.out.println("Unesite procenat popusta:");
                        unos = scan.nextLine();

                        try {
                            popust = Integer.parseInt(unos);
                            if (popust < 5 || popust > 70) {
                                System.out.println("\nMinimalni popust je 5%, maksimalni popust je 70%! Probajte ponovo!\n");
                            } else {
                                nazivKupona = "super-" + popust.toString();
                                boolean postoji = false;
                                for (Kupon k : lista) {
                                    if (k.getNaziv().equals(nazivKupona)) {
                                        System.out.println("Kupon sa ovim popustom vec postoji! Probajte ponovo!");
                                        postoji = true;
                                        break;
                                    }
                                }
                                if (!postoji) {
                                    zastava = true;
                                    break;
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("-----------");
                            System.out.println("Pogresno uneta vrednost! Molimo unesite ceo broj!");
                            System.out.println("-----------");
                        }
                    }
                    break;
                } else {
                    System.out.println("-----------");
                    System.out.println("Pogresna opcija! Molimo probajte ponovo!");
                    System.out.println("-----------");
                }
            } catch (NumberFormatException e) {
                System.out.println("-----------");
                System.out.println("Pogresna opcija! Molimo probajte ponovo!");
                System.out.println("-----------");
            }
        }

        if (zastava) {
            kupon = new Kupon(nazivKupona);
            JSONParser parser = new JSONParser();
            JSONArray jsonArray;
            try (FileReader reader = new FileReader("kuponi.json")) {
                Object obj = parser.parse(reader);
                jsonArray = (JSONArray) obj;
            } catch (FileNotFoundException e) {
                jsonArray = new JSONArray();
            } catch (IOException | ParseException e) {
                System.out.println("\nGreska u programu!\n");
                jsonArray = new JSONArray();
            }
            JSONObject noviKuponJSON = new JSONObject();
            noviKuponJSON.put("naziv", kupon.getNaziv());
            jsonArray.add(noviKuponJSON);
            try (FileWriter file = new FileWriter("kuponi.json")) {
                file.write(jsonArray.toJSONString());
                file.flush();
            }
            System.out.println("\nUspesno dodat novi kupon!\n");
        }
    }
    
    public static ArrayList<Kupon> ucitajKuponJSON(String naziv) throws FileNotFoundException, IOException, ParseException{
        ArrayList<Kupon> lista = new ArrayList<Kupon>();
        JSONArray jsonKuponi = (JSONArray) new JSONParser().parse(new FileReader(naziv));
        for(Object o :jsonKuponi){
            JSONObject jsonObj = (JSONObject) o;
            String nazivKupona = jsonObj.get("naziv").toString();
            Kupon a = new Kupon(nazivKupona);
            lista.add(a);
        }
        Collections.sort(lista, new Comparator<Kupon>(){
                        @Override
                        public int compare(Kupon k1, Kupon k2){
                            return k1.getNaziv().compareTo(k2.getNaziv());
                        }
                    });
        return lista;
    }
    
    public static void obrisiKupon(String nazivFajla, String nazivKupona) throws IOException, ParseException{
        JSONArray jsonKuponi = (JSONArray) new JSONParser().parse(new FileReader(nazivFajla));
        Scanner scan = new Scanner(System.in);
        int indexZaBrisanje = -1;
        JSONObject kuponZaBrisanje = null;
        for (int i = 0; i < jsonKuponi.size(); i++) {
            JSONObject jsonObj = (JSONObject) jsonKuponi.get(i);
            String imeKupona = jsonObj.get("naziv").toString();
            if (imeKupona.equals(nazivKupona)) {
                indexZaBrisanje = i;
                kuponZaBrisanje = jsonObj;
                break;
            }
        }
        
        if (indexZaBrisanje != -1) {
            System.out.println("Pronadjen kupon: ");
            System.out.println("Naziv: " + kuponZaBrisanje.get("naziv"));
            while(true){
                System.out.println("Da li ste sigurni da zelite da obrisete ovaj kupon? (da/ne)");
                System.out.print("Odgovor: ");
                String odgovor= scan.nextLine();
            
                if(odgovor.equalsIgnoreCase("da")){
                    jsonKuponi.remove(indexZaBrisanje);
                    try (FileWriter file = new FileWriter(nazivFajla)) {
                        file.write(jsonKuponi.toJSONString());
                        file.flush();
                    }
                    System.out.println("Kupon sa nazivom " + nazivKupona + " je uspesno obrisan.\n\n");
                    break;
                }
                else if(odgovor.equalsIgnoreCase("ne")){
                    System.out.println("Brisanje kupona je otkazano.\n\n");
                    break;
                }
                else{
                    System.out.println("\nMolimo unesite da ili ne kao odgovor!\n");
                }
            }
        } else {
            System.out.println("\nKupon sa nazivom " + nazivKupona + " nije pronadjen.\n\n");
        }
    }
}
