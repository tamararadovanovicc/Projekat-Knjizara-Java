
package projekat;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Kupac extends Korisnik implements Meni{
    
    private ArrayList<Knjiga> korpa;
    
    public Kupac(String korime, String lozinka, String ime, String prezime, Integer id, boolean status) {
        super(korime, lozinka, ime, prezime, id, status);
        this.korpa=new ArrayList<Knjiga>();
    }

    @Override
    public void meni() {
        boolean dalje = true;
        System.out.println("\n--------------------------------------------------------------------------------------");
        System.out.println("korisnicko ime: "+ super.getKorime()+"          "+"status=kupac");
        System.out.println("Ime i prezime: " + super.getIme()+' '+super.getPrezime());
        System.out.println("--------------------------------------------------------------------------------------\n");
        while(dalje){
            System.out.println("\n\nUnesite opcije:");
            System.out.println("1 - kupovina knjiga");
            System.out.println("2 - pregled korpe");
            System.out.println("3 - izbacivanje knjige iz korpe");
            System.out.println("4 - zavrsi kupovinu");
            System.out.println("5 - promena podataka");
            System.out.println("6 - obrisi nalog");
            System.out.println("0 - odjava");
            Scanner scan = new Scanner(System.in);
            System.out.print("Opcija: ");
            String unos = scan.nextLine();
            try{
                Integer opcija= Integer.parseInt(unos);
                switch(opcija){
                    case 1:
                        System.out.println("\n-------------------------Kupovina knjiga:-------------------------");
                        dodajUKorpu();
                    break;
                    case 2:
                        System.out.println("\n-------------------------Pregled korpe:-------------------------");
                        pregledKorpe();
                    break;
                    case 3:
                        while(true){
                            System.out.println("\n----------------------Izbacivanje knjiga iz korpe:----------------------");
                            System.out.print("Unesite naziv knjige koju zelite da izbacite ili \"kraj\" za prekid: ");
                            unos = scan.nextLine();
                            if(unos.equalsIgnoreCase("kraj"))
                            {
                                break;
                            }
                            else{
                                izbaciIzKorpe(unos);
                            }
                        }
                    break;
                    case 4:
                        System.out.println("\n-------------------------Kupovina:-------------------------");
                        kupovina();
                    break;
                    case 5:
                        System.out.println("\n-------------------------Promena podataka naloga:-------------------------");
                        promeniPodatke("korisnici.json",this.getId());
                    break;
                    case 6:
                        System.out.println("\n----------------------------Brisanje naloga:----------------------------");
                        if(obrisiNalog("korisnici.json",this.getId())){
                            System.out.println("Odjavili ste se!\n\n");
                            dalje=false;
                        }
                    break;
                    case 0:
                        dalje=false;
                        System.out.println("Odjavili ste se!\n\n");
                    break;
                   
                    default:
                        System.out.println("Pogresno uneta opcija! Probajte ponovo!\n");
                    break;
                }
            }catch(NumberFormatException e){
               System.out.println("Pogresna opcija! Molimo probajte ponovo!\n");
            } catch (IOException | ParseException ex) {
                System.out.println("Greska u programu!\n");
            } catch (Exception ex) {
                System.out.println("Greska u programu!\n");
            }
           
        }
    }
    
    private void kupovina() throws IOException, FileNotFoundException, ParseException, Exception
    {
        if(korpa.size()!=0){
            Scanner scan = new Scanner(System.in);
            Double ukupnaCena =0.0, staraCena;
            String unos,nacinPlacanja;
            System.out.println("Knjige u korpi:");
            for(int i=0;i<this.korpa.size();i++){
                System.out.println((i+1)+". "+korpa.get(i).getNaziv()+" Cena:"+korpa.get(i).getCena());
                ukupnaCena+=korpa.get(i).getCena();
            }
            System.out.println("UKUPNA CENA:"+ ukupnaCena.toString());
            staraCena=ukupnaCena;
            
            while(true){
                System.out.println("Ako imate kupon za popuste i zelite da ga iskoristite unesite sifru kupona u polje!\nU suprotnom pritisnite Enter za nastavak placanja.");
                System.out.print("Kupon:");
                unos = scan.nextLine();
                if(!unos.trim().isEmpty()){
                    if(Kupon.proveriKupon(unos)){
                        ukupnaCena=Kupon.cenaSaKuponom(unos, ukupnaCena);
                        System.out.println("CENA SA KUPONOM: "+ukupnaCena.toString());
                        break;
                    }
                    else{
                        System.out.println("\nNevazeci kupon! Pokusajte ponovo!\n");
                    }
                }
                else{
                    break;
                }
            }
            
            while(true){
                System.out.println("Odaberite nacin placanja:");
                System.out.println("1 - gotovinsko placanje");
                System.out.println("2 - platne kartice");
                System.out.print("Opcija: ");
                unos=scan.nextLine();
                try{
                    Integer opcija = Integer.parseInt(unos);
                    if(opcija ==1){
                        nacinPlacanja="gotovina";
                        break;
                    }
                    if(opcija==2){
                        nacinPlacanja="kartica";
                        break;
                    }
                    if(opcija!=1 && opcija !=2)
                        System.out.println("\nPogresno uneta opcija! Molimo probajte ponovo!\n");
                }catch(NumberFormatException e){
                    System.out.println("\nPogresno uneta opcija! Molimo probajte ponovo!\n");
                }
            }
            
            boolean zastava=true;
            while(zastava){
                System.out.println("Da biste zavrsili kupovinu molimo potvrdite lozinku naloga!");
                System.out.println("Ako otkazujete kupovinu pritisnite Enter!");
                System.out.print("Odgovor: ");
                unos=scan.nextLine();
                System.out.println();
                if(unos.trim().isEmpty())
                    zastava=false;
                else{
                    boolean pronadjen = false;
                    ArrayList<Korisnik> listaKorisnika = Korisnik.ucitajKorisnikeJSON("korisnici.json");
                    for(Korisnik k: listaKorisnika){
                        if(k.getId()==this.getId() && k.getLozinka().equalsIgnoreCase(unos)){
                            pronadjen=true;
                            System.out.println("\n\n*************************************************************");
                            System.out.println("Ime: "+ this.getIme()+"\nPrezime: "+this.getPrezime());
                            System.out.println("----------------------Promet prodaja----------------------");
                            for(int i=0;i<this.korpa.size();i++){
                                System.out.println((i+1)+". "+korpa.get(i).getNaziv()+" Cena:"+korpa.get(i).getCena());
                            }
                            System.out.println("----------------------------------------------------------");
                            System.out.println("Ukupni iznos: "+staraCena.toString());
                            System.out.println("Cena sa popustom: "+ukupnaCena.toString());
                            System.out.println("Nacin placanja: "+nacinPlacanja);
                            LocalDateTime sada = LocalDateTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            String formatiraniDatumIVreme = sada.format(formatter);
                            System.out.println("Datum i vreme: "+formatiraniDatumIVreme);
                            System.out.println("*************************************************************\n\n");
                            
                            
                            korpa.clear();
                            JSONArray jsonKorisnici= (JSONArray) new JSONParser().parse(new FileReader("korisnici.json"));
                            for(Object o:jsonKorisnici){
                                JSONObject jsonObj = (JSONObject) o;
                            if(Integer.parseInt(jsonObj.get("id").toString())==this.getId())
                            {
                                jsonObj.remove("korpa");
                                break;
                            }
                        }
                        try(FileWriter file = new FileWriter("korisnici.json")){
                            file.write(jsonKorisnici.toJSONString());
                            file.flush();
                        }
                        zastava=false;
                        }
                    }
                    if(!pronadjen){
                        System.out.println("\n------------");
                        System.out.println("Neispravna lozinka! Molimo probajte ponovo!");
                        System.out.println("------------\n");
                    }
                }      
            }
        }
        else{
            System.out.println("\nNemate nista u korpi da biste zavrsili kupovinu!\n");
        }
        
    }
    
    private  void izbaciIzKorpe(String naziv) throws IOException, ParseException
    {
        JSONArray jsonKorisnici = (JSONArray) new JSONParser().parse(new FileReader("korisnici.json"));
        JSONArray jsonKnjige = (JSONArray) new JSONParser().parse(new FileReader("knjige.json"));
        
        for(Object o:jsonKorisnici){
            JSONObject jsonObj= (JSONObject) o;
            if(Integer.parseInt(jsonObj.get("id").toString())==this.getId()){
                JSONArray jsonKorpa = (JSONArray) jsonObj.get("korpa");
                if(jsonKorpa !=null){
                    JSONObject knjigaZaUkloniti = null;
                    for(Object o2:jsonKorpa){
                        JSONObject jsonObj2= (JSONObject) o2;
                        if(jsonObj2.get("naziv").toString().equalsIgnoreCase(naziv))
                        {
                            knjigaZaUkloniti=jsonObj2;
                            break;
                        }
                    }
                    if(knjigaZaUkloniti !=null){
                        jsonKorpa.remove(knjigaZaUkloniti);
                        try(FileWriter file = new FileWriter("korisnici.json")){
                            file.write(jsonKorisnici.toJSONString());
                            file.flush();
                        }
                        for(Object o2:jsonKnjige){
                            JSONObject jsonKnjiga = (JSONObject) o2;
                            if(jsonKnjiga.get("naziv").toString().equalsIgnoreCase(naziv)){
                                Integer kolicina = Integer.parseInt(jsonKnjiga.get("kolicina").toString());
                                jsonKnjiga.put("kolicina",kolicina+1);
                                break;
                            }
                        }
                        try(FileWriter file= new FileWriter("knjige.json")){
                            file.write(jsonKnjige.toJSONString());
                            file.flush();
                        }
                        System.out.println("\nKnjiga \""+naziv+"\" je uspesno izbacena iz korpe!\n");
                    }else{
                        System.out.println("\nKnjiga sa nazivom \""+naziv+"\" nije pronadjena u korpi!\n");
                    }
                }else{
                    System.out.println("\nKorpa je prazna!\n");
                }
                break;
            }
        }
    }
    
    private void dodajUKorpu() throws IOException, FileNotFoundException, ParseException
    {
        Scanner scan= new Scanner(System.in);
        ArrayList<Knjiga> listaKnjiga=Knjiga.ucitajKnjigeJSON("knjige.json");
        JSONArray jsonKnjige = (JSONArray) new JSONParser().parse(new FileReader("knjige.json"));
        JSONArray jsonKorisnici = (JSONArray) new JSONParser().parse(new FileReader("korisnici.json"));
        boolean kupovina=true;
        while(kupovina){
            System.out.println("Lista knjiga na stanju:");
            for(Knjiga k :listaKnjiga){
                if(k.getKolicina()>0){
                    System.out.println(k.toString());
                }
            }
            System.out.println("****************************************************");
            System.out.println("Unesite naziv knjige za dodavanje u korpu ili \"kraj\" za prekid kupovine:");
            String unos=scan.nextLine();
            if(unos.equalsIgnoreCase("kraj"))
                kupovina=false;
            else{
                boolean pronadjena=false;
                for(Knjiga k :listaKnjiga){
                    if(k.getNaziv().equalsIgnoreCase(unos)){
                        pronadjena=true;
                        Integer kolicina=k.getKolicina()-1;
                        k.setKolicina(kolicina);
                        Knjiga knjiga = new Knjiga(k.getId(),k.getNaziv(),k.getAutor(),k.getZanr(),k.getCena(),k.getKolicina());
                        korpa.add(knjiga);
                        JSONObject knjigaJSON = new JSONObject();
                        for(Object o:jsonKnjige){
                            knjigaJSON= (JSONObject) o;
                            if(knjigaJSON.get("naziv").toString().equalsIgnoreCase(unos))
                            {
                                knjigaJSON.put("kolicina", kolicina);
                                break;
                            }
                            
                        }
                        for(Object o:jsonKorisnici){
                            JSONObject jsonObj= (JSONObject) o;
                            if(Integer.parseInt(jsonObj.get("id").toString())==this.getId()){
                                JSONArray korpaJSON;
                                if(jsonObj.containsKey("korpa"))
                                {
                                    korpaJSON=(JSONArray) jsonObj.get("korpa");
                                }else{
                                    korpaJSON = new JSONArray();
                                    jsonObj.put("korpa", korpaJSON);
                                }
                                
                                korpaJSON.add(knjigaJSON);
                            }
                        }
                        System.out.println("\n****************************************************************");
                        System.out.println("\nKnjiga \""+k.getNaziv()+"\" je uspesno dodata u korpu!\n\n");
                        System.out.println("****************************************************************\n");
                        break;
                    }
                }
                if(!pronadjena){
                    System.out.println("\n-----------");
                    System.out.println("Knjiga nije pronadjena! Proverite da li ste uneli tacan naziv!");
                    System.out.println("-----------\n");
                }
            }
        }
        
        try (FileWriter file = new FileWriter("knjige.json")) {
            file.write(jsonKnjige.toJSONString());
            file.flush();
        }
        try (FileWriter file = new FileWriter("korisnici.json")) {
            file.write(jsonKorisnici.toJSONString());
            file.flush();
        }
    }
    
    private void pregledKorpe() throws FileNotFoundException, IOException, ParseException
    {
        korpa.clear();
        JSONArray jsonKorisnici = (JSONArray) new JSONParser().parse(new FileReader("korisnici.json"));
        for(Object o:jsonKorisnici){
            JSONObject jsonObj = (JSONObject) o;
            if(Integer.parseInt(jsonObj.get("id").toString())==this.getId()){
                JSONArray jsonKorpa;
                if(jsonObj.containsKey("korpa"))
                {
                    jsonKorpa = (JSONArray) jsonObj.get("korpa");
                    for(Object o2:jsonKorpa)
                    {
                        JSONObject jsonObj2 = (JSONObject) o2;
                        Integer ID = Integer.parseInt(jsonObj2.get("id").toString());
                        String naziv = jsonObj2.get("naziv").toString();
                        String autor = jsonObj2.get("autor").toString();
                        JSONArray jsonZanrovi = (JSONArray) jsonObj2.get("zanr");
                        ArrayList<String> zanr = new ArrayList<String>();
                        for(Object z : jsonZanrovi)
                            zanr.add(z.toString());
                        Double cena = Double.parseDouble(jsonObj2.get("cena").toString());
                        Integer kolicina = Integer.parseInt(jsonObj2.get("kolicina").toString());
                        Knjiga k = new Knjiga(ID,naziv,autor,zanr,cena,kolicina);
                        korpa.add(k);
                    }
                }
            }
        }
        if(korpa.isEmpty())
        {
            System.out.println("\nKorpa je prazna.\n");
        } else{
            Double ukupnaCena=0.0;
            for(int i=0; i<korpa.size();i++){
                System.out.println((i+1)+". "+korpa.get(i).getNaziv()+" Cena:"+korpa.get(i).getCena());
                ukupnaCena+=korpa.get(i).getCena();
            }
            System.out.println("Ukupno: "+ukupnaCena.toString());
            System.out.println();
        }
    }
    
    private static boolean obrisiNalog(String nazivFajla, int korisnikID) throws IOException, ParseException
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
        
        while(true){
            System.out.println("Da li ste sigurni da zelite da obrisete svoj nalog? (da/ne)");
            System.out.print("Odgovor: ");
            String odgovor= scan.nextLine();
            
            if(odgovor.equalsIgnoreCase("da")){
                jsonKorisnici.remove(indexZaBrisanje);
                try (FileWriter file = new FileWriter(nazivFajla)) {
                    file.write(jsonKorisnici.toJSONString());
                    file.flush();
                }
                System.out.println("Nalog je uspesno obrisan.\n\n");
                return true;
            }
            else if(odgovor.equalsIgnoreCase("ne")){
                System.out.println("Brisanje naloga je otkazano.\n\n");
                return false;
            }
            else{
                System.out.println("\nMolimo unesite da ili ne kao odgovor!\n");
            }
        }
    }
    
    private static void promeniPodatke(String nazivFajla, int korisnikID) throws IOException, ParseException
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
        Scanner scanner = new Scanner(System.in);

        System.out.println("Vasi trenutni podaci:");
        System.out.println("Ime: " + korisnikZaIzmenu.get("ime"));
        System.out.println("Prezime: " + korisnikZaIzmenu.get("prezime"));
        System.out.println("Korisnicko ime: " + korisnikZaIzmenu.get("korisnickoIme"));
        System.out.println("Lozinka: " + korisnikZaIzmenu.get("lozinka"));
        
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
            
        try (FileWriter file = new FileWriter(nazivFajla)) {
            file.write(jsonKorisnici.toJSONString());
            file.flush();
        }
        System.out.println("\n***************************");
        System.out.println("Vasi podaci su uspesno azurirani!");
        System.out.println("***************************\n");
    }
}