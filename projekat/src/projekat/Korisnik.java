
package projekat;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class Korisnik {
    protected String korime, lozinka, ime, prezime;
    protected Integer id;
    protected boolean status;
    
    public Korisnik(String korime, String lozinka, String ime, String prezime, Integer id, boolean status) {
        this.korime = korime;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.id = id;
        this.status = status;
    }

    public String getKorime() {
        return korime;
    }
    public String getLozinka() {
        return lozinka;
    }
    public String getIme() {
        return ime;
    }
    public String getPrezime() {
        return prezime;
    }
    public Integer getId() {
        return id;
    }
    public boolean getStatus() {
        return status;
    }
    public void setKorime(String korime) {
        this.korime = korime;
    }
    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
    public void setIme(String ime) {
        this.ime = ime;
    }
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        String stat = status ? "admin" : "kupac";
    return "Korisnik{" + id + ' ' + ime + ' ' + prezime + " korisnicko ime=" + korime + ", lozinka=" + lozinka + ", status=" + stat + "}";
    }
    
    public static ArrayList<Korisnik> ucitajKorisnikeJSON(String naziv) throws Exception{
        ArrayList<Korisnik> lista = new ArrayList<Korisnik>();
        JSONArray jsonKorisnici = (JSONArray) new JSONParser().parse(new FileReader(naziv));
        for(Object o :jsonKorisnici){
            JSONObject jsonObj = (JSONObject) o;
            Integer id = Integer.parseInt(jsonObj.get("id").toString());
            String ime = jsonObj.get("ime").toString();
            String prezime = jsonObj.get("prezime").toString();
            String korime= jsonObj.get("korisnickoIme").toString();
            String sifra= jsonObj.get("lozinka").toString();
            boolean status = Boolean.parseBoolean(jsonObj.get("status").toString());
            
            Korisnik a;
            if(status == true)
                 a = new Admin(korime, sifra, ime, prezime, id, status);
            else
                a = new Kupac(korime, sifra, ime, prezime, id, status);
            lista.add(a);
        }
        return lista;
    }
    
    public static void dodajKorisnikaJSON(Korisnik noviKorisnik, String nazivFajla) throws IOException, ParseException
    {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray;

        try (FileReader reader = new FileReader(nazivFajla)) {
            Object obj = parser.parse(reader);
            jsonArray = (JSONArray) obj;
        } catch (FileNotFoundException e) {
            jsonArray = new JSONArray();
        }

        JSONObject noviKorisnikJSON = new JSONObject();
        noviKorisnikJSON.put("korisnickoIme", noviKorisnik.getKorime());
        noviKorisnikJSON.put("lozinka", noviKorisnik.getLozinka());
        noviKorisnikJSON.put("ime", noviKorisnik.getIme());
        noviKorisnikJSON.put("prezime", noviKorisnik.getPrezime());
        noviKorisnikJSON.put("id", noviKorisnik.getId());
        noviKorisnikJSON.put("status", noviKorisnik.getStatus());

        jsonArray.add(noviKorisnikJSON);

        try (FileWriter file = new FileWriter(nazivFajla)) {
            file.write(jsonArray.toJSONString());
            file.flush();
        }
    }
    
    public static boolean obrisiKorisnika(String nazivFajla, int korisnikID) throws IOException, ParseException
    {
        JSONArray jsonKorisnici = (JSONArray) new JSONParser().parse(new FileReader(nazivFajla));
        Scanner scan = new Scanner(System.in);
        
        int indexZaBrisanje = -1;
        JSONObject korisnikZaBrisanje = null;
        for (int i = 0; i < jsonKorisnici.size(); i++) {
            JSONObject jsonObj = (JSONObject) jsonKorisnici.get(i);
            int id = Integer.parseInt(jsonObj.get("id").toString());
            if (id == korisnikID) {
                indexZaBrisanje = i;
                korisnikZaBrisanje = jsonObj;
                break;
            }
        }
        if (indexZaBrisanje != -1) {
            System.out.println("Pronadjen korisnik: ");
            System.out.println("ID: " + korisnikZaBrisanje.get("id"));
            System.out.println("Ime: " + korisnikZaBrisanje.get("ime"));
            System.out.println("Prezime: " + korisnikZaBrisanje.get("prezime"));
            System.out.println("Korisnicko ime: " + korisnikZaBrisanje.get("korisnickoIme"));
            System.out.println("Status: " + (Boolean.parseBoolean(korisnikZaBrisanje.get("status").toString()) ? "admin" : "kupac")+'\n');
            while(true){
                System.out.println("Da li ste sigurni da zelite da obrisete ovog korisnika? (da/ne)");
                System.out.print("Odgovor: ");
                String odgovor= scan.nextLine();
            
                if(odgovor.equalsIgnoreCase("da")){
                    jsonKorisnici.remove(indexZaBrisanje);
                    try (FileWriter file = new FileWriter(nazivFajla)) {
                        file.write(jsonKorisnici.toJSONString());
                        file.flush();
                    }
                    System.out.println("Korisnik sa ID " + korisnikID + " je uspesno obrisan.\n\n");
                    return true;
                }
                else if(odgovor.equalsIgnoreCase("ne")){
                    System.out.println("Brisanje korisnika je otkazano.\n\n");
                    return false;
                }
                else{
                    System.out.println("\nMolimo unesite da ili ne kao odgovor!\n");
                }
            }
        } else {
            System.out.println("Korisnik sa ID " + korisnikID + " nije pronadjen.\n\n");
            return false;
        }
    }
    
    public static void ulogujse() throws Exception
    {
        ArrayList<Korisnik> listaKorisnika=new ArrayList<>();
        listaKorisnika = Korisnik.ucitajKorisnikeJSON("korisnici.json");
        Scanner scan = new Scanner(System.in);
        boolean pronadjen=false;
        Korisnik kor = null;
        String korisnickoime, lozinka;
        System.out.println("Molimo Vas da se prijavite:");
        System.out.print("Korisnicko ime: ");
        korisnickoime=scan.nextLine();
        System.out.print("Loznika: ");
        lozinka = scan.nextLine();
        
        for(Korisnik k : listaKorisnika){
            if(k.getKorime().equals(korisnickoime) && k.getLozinka().equals(lozinka))
            {
                pronadjen=true;
                kor =k;
                break;
            }
        }
        if(pronadjen == true){
            System.out.println("\nUspesna prijava!");
            if(kor instanceof Admin admin)
            {
                admin.meni();
            }
            if(kor instanceof Kupac kupac)
            {
                kupac.meni();
            }
        }
        else
            System.out.println("\nKorisnik nije pronadjen! Pokusajte ponovo!");
        
    }
    
    public static void registrujse() throws Exception
    {
        ArrayList<Korisnik> listaKorisnika=new ArrayList<>();
        listaKorisnika = Korisnik.ucitajKorisnikeJSON("korisnici.json");
        Scanner scan = new Scanner(System.in);
        boolean zastava;
        Korisnik kor = null;
        String ime, prezime, korisnickoime, lozinka;
        Integer id=0;
        
        do{
            zastava=false;
            System.out.print("Ime: ");
            ime=scan.nextLine().trim();
            if(ime.isBlank() || ime.isEmpty())
            {
                System.out.println("-----------");
                System.out.println("Ime ne moze biti prazno! Molimo probajte ponovo!");
                System.out.println("-----------");
                zastava=true;
            }
            else if(!Character.isUpperCase(ime.charAt(0)) || !ime.matches("[A-ZČĆŽŠĐa-zčćžšđ]+") || ime.length()<3 || ime.length()>10)
            {
                System.out.println("-----------");
                System.out.println("Ime mora pocinjati velikim slovom i mora sadrzati samo slova!\nIme ne sme imati manje od 3 ili vise od 10 slova!\nMolimo probajte ponovo!");
                System.out.println("-----------");
                zastava=true;
            }
            
        }while(zastava);
        
        do{
            zastava=false;
            System.out.print("Prezime: ");
            prezime=scan.nextLine().trim();
            if(prezime.isBlank() || prezime.isEmpty())
            {
                System.out.println("-----------");
                System.out.println("Prezime ne moze biti prazno! Molimo probajte ponovo!");
                System.out.println("-----------");
                zastava=true;
            }
            else {
                String[] delovi = prezime.split("\\s+");
                for(String deo :delovi){
                    if(!Character.isUpperCase(prezime.charAt(0)) || !prezime.matches("[A-ZČĆŽŠĐa-zčćžšđ]+"))
                    {
                        System.out.println("-----------");
                        System.out.println("Prezime mora pocinjati velikim slovom i mora sadrzati samo slova!\nMolimo probajte ponovo!");
                        System.out.println("-----------");
                        zastava=true;
                        break;
                    }
                }
                if(prezime.length()<4 || prezime.length()>30){
                    System.out.println("-----------");
                    System.out.println("Prezime mora biti duze od 4 i krace od 30 karaktera!\nMolimo probajte ponovo!");
                    System.out.println("-----------");
                    zastava=true;
                }
            }
        }while(zastava);
        
        do{
            zastava=false;
            System.out.print("Korisnicko ime: ");
            korisnickoime=scan.nextLine().trim();
            if(korisnickoime.isBlank() || korisnickoime.isEmpty())
            {
                System.out.println("-----------");
                System.out.println("Korisnicko ime ne moze biti prazno! Molimo probajte ponovo!");
                System.out.println("-----------");
                zastava=true;
            }
            for(Korisnik k :listaKorisnika)
            {
                if(k.getKorime().equals(korisnickoime) || korisnickoime.matches(".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*"))
                {
                    zastava=true;
                    System.out.println("-----------");
                    System.out.println("Korisnik sa tim korisnickim imenom vec postoji! Molimo probajte ponovo!\nKorisnicko ime ne sme imati specijalne karaktere!\nKorisnicko ime ne moze biti krace od 3 ili duze od 10 karaktera!\nMolimo probajte ponovo!");
                    System.out.println("-----------");
                    break;
                }
            }
        }while(zastava);
        
        for(Korisnik k : listaKorisnika){
            if(k.getId()>id)
                id=k.getId();
        }
        id++;
        
        do{
            zastava=false;
            System.out.print("Lozinka: ");
            lozinka=scan.nextLine().trim();
            boolean imaBroj = lozinka.matches(".*\\d.*");
            boolean imaVelikoSlovo = lozinka.matches(".*[A-Z].*");
            boolean imaSpecijalniKarakter = lozinka.matches(".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*");
            if(lozinka.isBlank() || lozinka.isEmpty())
            {
                System.out.println("-----------");
                System.out.println("Lozinka ne moze biti prazna! Molimo probajte ponovo!");
                System.out.println("-----------");
                zastava=true;
            }
            else if(!imaBroj || !imaVelikoSlovo || !imaSpecijalniKarakter || lozinka.length()<5 || lozinka.length()>15)
            {
                System.out.println("\n-----------");
                System.out.println("Lozinka mora sadrzati barem 1 veliko slovo, barem 1 broj i barem 1 specijalni karakter!\nLozinka ne moze biti kraca od 5 ili duza od 15 karaktera!\nMolimo probajte ponovo!");
                System.out.println("-----------");
                zastava=true;
            }
            
        }while(zastava);
        kor = new Kupac(korisnickoime,lozinka,ime,prezime,id,false);
        Kupac.dodajKorisnikaJSON(kor, "korisnici.json");
        System.out.println("Uspesno generisan novi korisnik!");
    }

    public static void promeniPodatkeKorisnika(String nazivFajla, int korisnikID) throws IOException, ParseException
    {
        JSONArray jsonKorisnici = (JSONArray) new JSONParser().parse(new FileReader(nazivFajla));

        JSONObject korisnikZaIzmenu = null;
        for (Object o : jsonKorisnici) {
            JSONObject jsonObj = (JSONObject) o;
            int id = Integer.parseInt(jsonObj.get("id").toString());
            if (id == korisnikID) {
                korisnikZaIzmenu = jsonObj;
                break;
            }
        }

        if (korisnikZaIzmenu != null) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Trenutni podaci korisnika:");
            System.out.println("ID: " + korisnikZaIzmenu.get("id"));
            System.out.println("Ime: " + korisnikZaIzmenu.get("ime"));
            System.out.println("Prezime: " + korisnikZaIzmenu.get("prezime"));
            System.out.println("Korisnicko ime: " + korisnikZaIzmenu.get("korisnickoIme"));
            System.out.println("Lozinka: " + korisnikZaIzmenu.get("lozinka"));
            System.out.println("Status: " + (Boolean.parseBoolean(korisnikZaIzmenu.get("status").toString()) ? "admin" : "kupac")+'\n');

            boolean provera;
            do{
                provera=false;
                System.out.print("Unesite novo ime (ili pritisnite Enter za preskakanje polja): ");
                String novoIme = scanner.nextLine();
                if (!novoIme.trim().isEmpty()) {
                    if(Character.isUpperCase(novoIme.charAt(0)) && novoIme.matches("[A-ZČĆŽŠĐa-zčćžšđ]+") && novoIme.length()>3 && novoIme.length()<10)
                        korisnikZaIzmenu.put("ime", novoIme);
                    else
                    {
                        System.out.println("\n-----------");
                        System.out.println("Ime mora pocinjati velikim slovom i mora sadrzati samo slova!\nIme ne sme imati manje od 3 ili vise od 10 slova!\nMolimo probajte ponovo!");
                        System.out.println("-----------");
                        provera=true;
                    }
                }
            }while(provera);
            
            do{
                provera=false;
                System.out.print("\nUnesite novo prezime (ili pritisnite Enter za preskakanje polja): ");
                String novoPrezime = scanner.nextLine();
                if (!novoPrezime.trim().isEmpty()) {
                    if(Character.isUpperCase(novoPrezime.charAt(0)) && novoPrezime.matches("[A-ZČĆŽŠĐa-zčćžšđ]+") && novoPrezime.length()>4 && novoPrezime.length()<15)
                        korisnikZaIzmenu.put("prezime", novoPrezime);
                    else
                    {
                        System.out.println("\n-----------");
                        System.out.println("Prezime mora pocinjati velikim slovom i mora sadrzati samo slova!\nPrezime ne sme imati manje od 4 ili vise od 15 slova!\nMolimo probajte ponovo!");
                        System.out.println("-----------");
                        provera=true;
                    }
                }
            }while(provera);

            do{
                provera=false;
                System.out.print("\nUnesite novo korisnicko ime (ili pritisnite Enter za preskakanje polja): ");
                String novoKorIme = scanner.nextLine();
                if (!novoKorIme.trim().isEmpty()) {
                    boolean zastava=false;
                    for(Object o:jsonKorisnici){
                        JSONObject jsonObj = (JSONObject) o;
                        String korisnickoIme=jsonObj.get("korisnickoIme").toString();
                        if(korisnickoIme.equals(novoKorIme)){
                            zastava=true;
                            break;
                        }
                    }
                    if(zastava || novoKorIme.matches(".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*"))
                    {
                        System.out.println("\n-----------");
                        System.out.println("Korisnik sa tim imenom vec postoji!\nKorisnicko ime ne moze sadrzati specijalne karaktere!\nMolimo probajte ponovo!");
                        System.out.println("-----------");
                        provera=true;
                    }
                    else{
                        if (!novoKorIme.trim().isEmpty())
                            korisnikZaIzmenu.put("korisnickoIme", novoKorIme);
                    }
                        
                }
            }while(provera);

            do{
                provera=false;
                System.out.print("\nUnesite novu lozinku (ili pritisnite Enter za preskakanje polja): ");
                String novaLozinka = scanner.nextLine();
                boolean imaBroj = novaLozinka.matches(".*\\d.*");
                boolean imaVelikoSlovo = novaLozinka.matches(".*[A-Z].*");
                boolean imaSpecijalniKarakter = novaLozinka.matches(".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*");
                if(novaLozinka.trim().isEmpty()){
                    break;
                }else{
                    if(!imaBroj || !imaVelikoSlovo || !imaSpecijalniKarakter || novaLozinka.length()<5 || novaLozinka.length()>15)
                    {
                        System.out.println("\n-----------");
                        System.out.println("Lozinka mora sadrzati barem 1 veliko slovo, barem 1 broj i barem 1 specijalni karakter!\nLozinka ne moze biti kraca od 5 ili duza od 15 karaktera!\nMolimo probajte ponovo!");
                        System.out.println("-----------");
                        provera=true;
                    }
                    else{
                        if (!novaLozinka.trim().isEmpty())
                            korisnikZaIzmenu.put("lozinka", novaLozinka);
                    } 
                }
            }while(provera);
            
            do{
                provera=false;
                System.out.print("\nUnesite novi status (admin/kupac, ili pritisnite Enter za preskakanje polja): ");
                String noviStatus = scanner.nextLine();
                if (!noviStatus.trim().isEmpty()) {
                    if(noviStatus.equals("admin")||noviStatus.equals("kupac"))
                    {
                        boolean status = noviStatus.equalsIgnoreCase("admin");
                        korisnikZaIzmenu.put("status", status);
                    }
                    else{
                        System.out.println("\n-----------");
                        System.out.println("Molimo da unesete odgovarajuci status ili preskocite polje!");
                        System.out.println("-----------");
                        provera=true;
                    }
                }
            }while(provera);
            
            try (FileWriter file = new FileWriter(nazivFajla)) {
                file.write(jsonKorisnici.toJSONString());
                file.flush();
            }

            System.out.println("Podaci korisnika su uspesno azurirani.");
        } else {
            System.out.println("Korisnik sa ID " + korisnikID + " nije pronadjen.");
        }
    }
    
    public static ArrayList<Korisnik> sortirajKorisnike(ArrayList<Korisnik> lista)
    {
        boolean dalje=true;
        do{
            System.out.println("Odaberite jedno od sortiranja:");
            System.out.println("1 - po ID");
            System.out.println("2 - po imenu");
            System.out.println("3 - po prezimenu");
            System.out.println("4 - po statusu");
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
                    Collections.sort(lista, new Comparator<Korisnik>(){
                        @Override
                        public int compare(Korisnik k1, Korisnik k2){
                            return Integer.compare(k1.getId(), k2.getId());
                        }
                    });
                break;
                case 2:
                    dalje=false;
                    Collections.sort(lista,new Comparator<Korisnik>(){
                        @Override
                        public int compare(Korisnik k1, Korisnik k2){
                            return k1.getIme().compareTo(k2.getIme());
                        }
                    });
                break;
                case 3:
                    dalje=false;
                    Collections.sort(lista, new Comparator<Korisnik>(){
                        @Override
                        public int compare (Korisnik k1, Korisnik k2){
                            return k1.getPrezime().compareTo(k2.getPrezime());
                        }
                    });
                break;
                case 4:
                    dalje=false;
                    Collections.sort(lista,new Comparator<Korisnik>(){
                        @Override
                        public int compare(Korisnik k1, Korisnik k2){
                            return Boolean.compare(k2.getStatus(), k1.getStatus());
                        }
                    });
                break;
                case 0:
                    dalje=false;
                    System.out.println("\n---Prekid sortiranja!---\n");
                break;
                default:
                    System.out.println("\nNeispravna opcija, probajte ponovo!\n");
                break;
                }
            } catch (NumberFormatException e) {
            System.out.println("Pogresna opcija! Molimo probajte ponovo!\n");
            }
        }while(dalje);
        return lista;
    }
}
