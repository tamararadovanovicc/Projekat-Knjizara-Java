
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

public class Knjiga {
    Integer id, kolicina;
    String naziv, autor;
    ArrayList<String> zanr;
    Double cena;

    public Knjiga(Integer id, String naziv, String autor, ArrayList<String> zanr, Double cena, Integer kolicina) {
        this.id = id;
        this.naziv = naziv;
        this.autor = autor;
        this.zanr = zanr;
        this.cena = cena;
        this.kolicina = kolicina;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public Integer getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getAutor() {
        return autor;
    }

    public ArrayList<String> getZanr() {
        return zanr;
    }

    public void setZanr(ArrayList<String> zanr) {
        this.zanr = zanr;
    }

    public Double getCena() {
        return cena;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }


    public void setCena(Double cena) {
        this.cena = cena;
    }

    @Override
    public String toString() {
        String zanrovi="";
        for(String z : zanr){
            zanrovi+=z+", ";
        }
        return "\"" + naziv + "\", " + autor + ", Zanr:" + zanrovi + " Cena:" + cena + '\n';
    }
    
    public static ArrayList<Knjiga> ucitajKnjigeJSON(String nazivfajla) throws FileNotFoundException, IOException, ParseException
    {
        ArrayList<Knjiga> lista = new ArrayList<Knjiga>();
        JSONArray jsonKnjige = (JSONArray) new JSONParser().parse(new FileReader(nazivfajla));
        for(Object o :jsonKnjige){
            JSONObject jsonObj = (JSONObject) o;
            Integer id = Integer.parseInt(jsonObj.get("id").toString());
            String naziv = jsonObj.get("naziv").toString();
            String autor = jsonObj.get("autor").toString();
            ArrayList<String> zanr= new ArrayList<String>();
            JSONArray jsonZanrovi= (JSONArray) jsonObj.get("zanr");
            for(Object o2 : jsonZanrovi)
                zanr.add(o2.toString());
            Double cena= Double.parseDouble(jsonObj.get("cena").toString());
            Integer kolicina = Integer.parseInt(jsonObj.get("kolicina").toString());
            
            Knjiga a=new Knjiga(id,naziv,autor,zanr,cena,kolicina);
            lista.add(a);
        }
        return lista;
    }
    
    public static ArrayList<String> ucitajZanrove(String nazivfajla)throws FileNotFoundException, IOException, ParseException
    {
        ArrayList<String> lista = new ArrayList<String>();
        JSONArray jsonZanrovi = (JSONArray) new JSONParser().parse(new FileReader(nazivfajla));
        for(Object o :jsonZanrovi){
            JSONObject jsonObj = (JSONObject) o;
            String zanr= jsonObj.get("naziv").toString();
            lista.add(zanr);
        }
        return lista;
    }
    
    public static ArrayList<Knjiga> sortirajKnjige(ArrayList<Knjiga> lista)
    {
        boolean dalje=true;
        do{
            System.out.println("Odaberite jedno od sortiranja:");
            System.out.println("1 - po ID");
            System.out.println("2 - po nazivu");
            System.out.println("3 - po autoru");
            System.out.println("4 - po zanru");
            System.out.println("5 - po ceni");
            System.out.println("6 - po kolicini");
            System.out.println("0 - prekidanje sortiranja");
            System.out.print("Opcija: ");
            Scanner scan = new Scanner(System.in);
            String unos=scan.nextLine();
        
            try{
                Integer opcija=Integer.parseInt(unos);
        
                switch(opcija)
                {
                case 1:
                    dalje=false;
                    Collections.sort(lista, new Comparator<Knjiga>(){
                        @Override
                        public int compare(Knjiga k1, Knjiga k2){
                            return Integer.compare(k1.getId(), k2.getId());
                        }
                    });
                break;
                    
                case 2:
                    dalje=false;
                    Collections.sort(lista, new Comparator<Knjiga>(){
                        @Override
                        public int compare(Knjiga k1, Knjiga k2){
                            return k1.getNaziv().compareTo(k2.getNaziv());
                        }
                    });
                break;
                case 3:
                    dalje=false;
                    Collections.sort(lista, new Comparator<Knjiga>(){
                        @Override
                        public int compare(Knjiga k1, Knjiga k2){
                            return k1.getAutor().compareTo(k2.getAutor());
                        }
                    });
                break;
                case 4:
                    dalje=false;
                    Collections.sort(lista, new Comparator<Knjiga>(){
                        @Override
                        public int compare(Knjiga k1, Knjiga k2){
                            return k1.getZanr().get(0).compareTo(k2.getZanr().get(0));
                        }
                    });
                break;
                case 5:
                    dalje=false;
                    Collections.sort(lista, new Comparator<Knjiga>(){
                        @Override
                        public int compare(Knjiga k1, Knjiga k2){
                            return Double.compare(k1.getCena(),k2.getCena());
                        }
                    });
                break;
                case 6:
                    dalje = false;
                    Collections.sort(lista, new Comparator<Knjiga>(){
                        @Override
                        public int compare(Knjiga k1, Knjiga k2){
                            return Integer.compare(k1.getKolicina(), k2.getKolicina());
                        }
                    });
                break;
                case 0:
                    dalje=false;
                    System.out.println("\n---Prekid sortiranja!---\n");
                break;
                default:
                    System.out.println("Pogresna opcija! Molimo probajte ponovo!\n");
                break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Pogresna opcija! Molimo probajte ponovo!\n");
            }
        }while(dalje);
        return lista;
    }
    
    public static void dodajKnjiguJSON(Knjiga novaKnjiga, String nazivFajla) throws IOException, ParseException
    {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray;
        
        try (FileReader reader = new FileReader(nazivFajla)) {
            Object obj = parser.parse(reader);
            jsonArray = (JSONArray) obj;
        } catch (FileNotFoundException e) {
            jsonArray = new JSONArray();
        }

        JSONObject novaKnjigaJSON = new JSONObject();
        novaKnjigaJSON.put("id", novaKnjiga.getId());
        novaKnjigaJSON.put("naziv", novaKnjiga.getNaziv());
        novaKnjigaJSON.put("autor", novaKnjiga.getAutor());
        novaKnjigaJSON.put("zanr", novaKnjiga.getZanr());
        novaKnjigaJSON.put("cena", novaKnjiga.getCena());
        novaKnjigaJSON.put("kolicina", novaKnjiga.getKolicina());

        jsonArray.add(novaKnjigaJSON);

        try (FileWriter file = new FileWriter(nazivFajla)) {
            file.write(jsonArray.toJSONString());
            file.flush();
        }
    }
    
    public static void dodajKnjigu() throws Exception
    {
        ArrayList<Knjiga> listaKnjiga=new ArrayList<>();
        listaKnjiga = Knjiga.ucitajKnjigeJSON("knjige.json");
        Scanner scan = new Scanner(System.in);
        boolean zastava;
        Knjiga knjigica = null;
        String naziv, autor;
        ArrayList<String> zanr= new ArrayList<String>();
        Integer id=0, kolicina=0;
        Double cena=0.0;
        
        do{
            zastava=false;
            System.out.print("Naziv: ");
            naziv=scan.nextLine().trim();
            if(naziv.isBlank() || naziv.isEmpty() || (Character.isLetter(naziv.charAt(0))&& !Character.isUpperCase(naziv.charAt(0))) || naziv.length()>50)
            {
                System.out.println("-----------");
                System.out.println("Naziv ne moze biti prazan i mora pocinjati velikim slovom ako pocinje slovom!\nNaziv ne moze biti duzi od 50 karaktera!\nMolimo probajte ponovo!");
                System.out.println("-----------");
                zastava=true;
            }
        }while(zastava);
        
        do{
            zastava=false;
            System.out.print("Autor: ");
            autor=scan.nextLine().trim();
            if(autor.isBlank() || autor.isEmpty())
            {
                System.out.println("-----------");
                System.out.println("Autor ne moze biti prazno polje! Molimo probajte ponovo!");
                System.out.println("-----------");
                zastava=true;
            }
            else 
            {
                String[] delovi = autor.split("\\s+");
                for (String deo : delovi) {
                    if (!Character.isUpperCase(deo.charAt(0)) || !deo.matches("[A-ZČĆŽŠĐa-zčćžšđ]+")) {
                        System.out.println("-----------");
                        System.out.println("Ime i prezime autora mora poceti velikim slovom i mora sadrzati samo slova!");
                        System.out.println("Molimo probajte ponovo!");
                        System.out.println("-----------");
                        zastava = true;
                        break;
                    }
                }
                
                if (autor.length() < 4 || autor.length() > 50) {
                    System.out.println("-----------");
                    System.out.println("Ukupna dužina imena i prezimena ne sme biti manja od 4 ili veća od 50 slova!");
                    System.out.println("Molimo probajte ponovo!");
                    System.out.println("-----------");
                    zastava = true;
                }
            }
        }while(zastava);
        
        do{
            zastava=false;
            ArrayList<String> listaZanrova= Knjiga.ucitajZanrove("zanrovi.json");
            int i=1;
            System.out.println("Zanrovi:");
                for(String s:listaZanrova){
                    System.out.println(i+" - "+s);
                    i++;
                }
                System.out.print("Odaberite zanrove (brojevi odvojeni razmakom): ");
                String odgovor=scan.nextLine().trim();
                String[] delovi= odgovor.split("\\s+");
                
                try{
                    for(String deo:delovi){
                        int izbor = Integer.parseInt(deo);
                        if(izbor<1 || izbor>listaZanrova.size()){
                            throw new NumberFormatException();
                        }
                        zanr.add(listaZanrova.get(izbor-1));
                    }
                }catch(NumberFormatException e){
                    System.out.println("-----------");
                    System.out.println("Molimo da odaberete jedne od ponudjenih opcija!");
                    System.out.println("-----------");
                    zanr.clear();
                    zastava=true;
                }
        }while(zastava);
        
        for(Knjiga k : listaKnjiga){
            if(k.getId()>id)
                id=k.getId();
        }
        id++;
        
        do{
            zastava=false;
            System.out.print("Cena: ");
            String unos = scan.nextLine().trim();
            try{
                Double cena1 = Double.parseDouble(unos);
                if(cena1<200 || cena1>20000 || cena1.isNaN()){
                    zastava=true;
                    System.out.println("-----------");
                    System.out.println("Minimalna cena je 200! Maksimalna cena je 20000!");
                    System.out.println("-----------");
                }
                else{
                    cena=cena1;
                }
            }catch(NumberFormatException e){
                System.out.println("-----------");
                System.out.println("Cena nije validna!");
                System.out.println("-----------");
                zastava=true;
            }
        }while(zastava);
        
        do{
            zastava=false;
            System.out.print("Kolicina: ");
            String unos = scan.nextLine().trim();
            try{
                Integer kolicina1 = Integer.parseInt(unos);
                if(kolicina1<1 || kolicina1>25){
                    zastava=true;
                    System.out.println("-----------");
                    System.out.println("Kolicina ne moze biti manja od 1 ili veca od 25!");
                    System.out.println("-----------");
                }
                else{
                    kolicina=kolicina1;
                }
            }catch(NumberFormatException e){
                System.out.println("-----------");
                System.out.println("Kolicina nije validna!");
                System.out.println("-----------");
                zastava=true;
            }
        }while(zastava);
        
        knjigica = new Knjiga(id,naziv,autor,zanr,cena,kolicina);
        Knjiga.dodajKnjiguJSON(knjigica, "knjige.json");
        System.out.println("\nUspesno generisana nova knjiga!\n");
    }
    
    public static void promeniPodatkeKnjige(String nazivFajla, int knjigaID) throws IOException, ParseException
    {
        JSONArray jsonKnjige = (JSONArray) new JSONParser().parse(new FileReader(nazivFajla));

        JSONObject knjigaZaIzmenu = null;
        for (Object o : jsonKnjige) {
            JSONObject jsonObj = (JSONObject) o;
            int id = Integer.parseInt(jsonObj.get("id").toString());
            if (id == knjigaID) {
                knjigaZaIzmenu = jsonObj;
                break;
            }
        }

        if (knjigaZaIzmenu != null) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Trenutni podaci knjige:");
            System.out.println("ID: " + knjigaZaIzmenu.get("id"));
            System.out.println("Naziv: \"" + knjigaZaIzmenu.get("naziv")+"\"");
            System.out.println("Autor: " + knjigaZaIzmenu.get("autor"));
            System.out.println("Zanr: " + knjigaZaIzmenu.get("zanr"));
            System.out.println("Cena: " + knjigaZaIzmenu.get("cena"));
            System.out.println("Kolicina: " + knjigaZaIzmenu.get("kolicina"));

            boolean provera;
            do{
                provera=false;
                System.out.print("\nUnesite novi naziv (ili pritisnite Enter za preskakanje polja): ");
                String noviNaziv = scanner.nextLine();
                if (!noviNaziv.trim().isEmpty() && noviNaziv.length()<50) {
                    if(Character.isLetter(noviNaziv.charAt(0)))
                        if(Character.isUpperCase(noviNaziv.charAt(0)))
                          knjigaZaIzmenu.put("naziv", noviNaziv);
                        else
                        {
                            System.out.println("\n-----------");
                            System.out.println("Naziv mora pocinjati velikim slovom ako pocinje slovom!\nMolimo probajte ponovo!");
                            System.out.println("-----------");
                            provera=true;
                        }
                    else{
                        knjigaZaIzmenu.put("naziv", noviNaziv);
                    }
                }
                if(noviNaziv.length()>50){
                    System.out.println("\n-----------");
                    System.out.println("Naziv naziv ne moze biti duzi od 50 karaktera!\nMolimo probajte ponovo!");
                    System.out.println("-----------");
                    provera=true;
                }
            }while(provera);
            
            do{
                provera=false;
                System.out.print("\nUnesite novog autora(ili pritisnite Enter za preskakanje polja): ");
                String noviAutor = scanner.nextLine();
                if (!noviAutor.trim().isEmpty()) {
                    boolean aut=true;
                    String[] delovi = noviAutor.split("\\s+");
                    for (String deo : delovi) {
                        if (!Character.isUpperCase(deo.charAt(0)) || !deo.matches("[A-ZČĆŽŠĐa-zčćžšđ]+")) {
                            System.out.println("-----------");
                            System.out.println("Ime i prezime autora mora poceti velikim slovom i mora sadrzati samo slova!");
                            System.out.println("Molimo probajte ponovo!");
                            System.out.println("-----------");
                            provera = true;
                            aut=false;
                        }
                    }
                
                    if (noviAutor.length() < 4 || noviAutor.length() > 50) {
                        System.out.println("-----------");
                        System.out.println("Ukupna duzina imena i prezimena ne sme biti manja od 4 ili veca od 50 slova!");
                        System.out.println("Molimo probajte ponovo!");
                        System.out.println("-----------");
                        provera = true;
                        aut=false;
                    }
                    if(aut){
                        knjigaZaIzmenu.put("autor", noviAutor);
                    }
                }
            }while(provera);

            do{
                provera=false;
                System.out.print("\nOdaberite zanrove (ili pritisnite Enter za preskakanje polja): ");
                ArrayList<String> listaZanrova= Knjiga.ucitajZanrove("zanrovi.json");
                ArrayList<String> zanr = new ArrayList<String>();
                int i=1;
                System.out.println("Zanrovi:");
                for(String s:listaZanrova){
                    System.out.println(i+" - "+s);
                    i++;
                }
                System.out.print("Odaberite zanrove (brojevi odvojeni razmakom): ");
                String odgovor=scanner.nextLine().trim();
                String[] delovi= odgovor.split("\\s+");
                if (!odgovor.trim().isEmpty()) {
                    boolean zan=false;
                    try{
                        for(String deo:delovi){
                            int izbor = Integer.parseInt(deo);
                            if(izbor<1 || izbor>listaZanrova.size()){
                                throw new NumberFormatException();
                            }
                            zanr.add(listaZanrova.get(izbor-1));
                            zan=true;
                        }
                    }catch(NumberFormatException e){
                        System.out.println("-----------");
                        System.out.println("Molimo da odaberete jedne od ponudjenih opcija!");
                        System.out.println("-----------");
                        zanr.clear();
                        provera=true;
                    }
                    if(zan)
                        knjigaZaIzmenu.put("zanr", zanr);
                }
            }while(provera);

            do{
                provera=false;
                System.out.print("\nUnesite novu cenu (ili pritisnite Enter za preskakanje polja): ");
                String novaCena = scanner.nextLine();
                if(!novaCena.trim().isEmpty()){
                    try{
                        Double cena = Double.parseDouble(novaCena);
                        if(cena<200 || cena>20000 || cena.isNaN()){
                            provera=true;
                            System.out.println("-----------");
                            System.out.println("Minimalna cena je 200! Maksimalna cena je 20000!");
                            System.out.println("-----------");
                        }
                        else{
                            knjigaZaIzmenu.put("cena",cena);
                        }
                    }catch(NumberFormatException e){
                        System.out.println("-----------");
                        System.out.println("Cena nije validna!");
                        System.out.println("-----------");
                        provera=true;
                    }
                }
            }while(provera);
            
            do{
                provera=false;
                System.out.print("\nUnesite novu kolicinu (ili pritisnite Enter za preskakanje polja): ");
                String novaKolicina = scanner.nextLine();
                if (!novaKolicina.trim().isEmpty()) {
                    try{
                        Integer kolicina1 = Integer.parseInt(novaKolicina);
                        if(kolicina1<1 || kolicina1>25){
                            provera=true;
                            System.out.println("-----------");
                            System.out.println("Kolicina ne moze biti manja od 1 ili veca od 25!");
                            System.out.println("-----------");
                        }
                        else{
                            knjigaZaIzmenu.put("kolicina",kolicina1);
                        }
                    }catch(NumberFormatException e){
                        System.out.println("-----------");
                        System.out.println("Kolicina nije validna!");
                        System.out.println("-----------");
                        provera=true;
                    }
                }
            }while(provera);
            
            try (FileWriter file = new FileWriter(nazivFajla)) {
                file.write(jsonKnjige.toJSONString());
                file.flush();
            }
            
            System.out.println("Podaci knjige su uspesno azurirani.");
        } else {
            System.out.println("Knjiga sa ID " + knjigaID + " nije pronadjena.");
        }
    }
    
    public static void obrisiKnjigu(String nazivFajla, int knjigaID) throws IOException, ParseException
    {
        JSONArray jsonKnjige = (JSONArray) new JSONParser().parse(new FileReader(nazivFajla));
        Scanner scan = new Scanner(System.in);
        int indexZaBrisanje = -1;
        JSONObject knjigaZaBrisanje = null;
        for (int i = 0; i < jsonKnjige.size(); i++) {
            JSONObject jsonObj = (JSONObject) jsonKnjige.get(i);
            int id = Integer.parseInt(jsonObj.get("id").toString());
            if (id == knjigaID) {
                indexZaBrisanje = i;
                knjigaZaBrisanje = jsonObj;
                break;
            }
        }
        
        if (indexZaBrisanje != -1) {
            System.out.println("Pronadjena knjiga: ");
            System.out.println("ID: " + knjigaZaBrisanje.get("id"));
            System.out.println("Naziv: \"" + knjigaZaBrisanje.get("naziv")+'\"');
            System.out.println("Autor: " + knjigaZaBrisanje.get("autor"));
            System.out.println("Cena: " + knjigaZaBrisanje.get("cena"));
            System.out.println("Kolicina: " + knjigaZaBrisanje.get("kolicina")+'\n');
            while(true){
                System.out.println("Da li ste sigurni da zelite da obrisete ovu knjigu? (da/ne)");
                System.out.print("Odgovor: ");
                String odgovor= scan.nextLine();
            
                if(odgovor.equalsIgnoreCase("da")){
                    jsonKnjige.remove(indexZaBrisanje);
                    try (FileWriter file = new FileWriter(nazivFajla)) {
                        file.write(jsonKnjige.toJSONString());
                        file.flush();
                    }
                    System.out.println("Knjiga sa ID " + knjigaID + " je uspesno obrisana.\n\n");
                    break;
                }
                else if(odgovor.equalsIgnoreCase("ne")){
                    System.out.println("Brisanje knjige je otkazano.\n\n");
                    break;
                }
                else{
                    System.out.println("\nMolimo unesite da ili ne kao odgovor!\n");
                }
            }
        } else {
            System.out.println("Knjiga sa ID " + knjigaID + " nije pronadjena.\n\n");
        }
    }
}
