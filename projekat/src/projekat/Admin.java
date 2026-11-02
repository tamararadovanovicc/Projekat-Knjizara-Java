
package projekat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
import static projekat.Korisnik.obrisiKorisnika;

public class Admin extends Korisnik implements Meni{
    
    public Admin(String korime, String lozinka, String ime, String prezime, Integer id, boolean status) {
        super(korime, lozinka, ime, prezime, id, status);
    }

    @Override
    public void meni() {
        boolean dalje=true;
        System.out.println("\n--------------------------------------------------------------------------------------");
        System.out.println("Id:"+super.getId() +"      korisnicko ime: "+ super.getKorime()+"          "+"status=admin");
        System.out.println("Ime i prezime: " + super.getIme()+' '+super.getPrezime());
        System.out.println("--------------------------------------------------------------------------------------\n");
        while(dalje){
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("Unesite opciju:");
            System.out.println("1 - lista korisnika");
            System.out.println("2 - lista knjiga");
            System.out.println("3 - lista kupona");
            System.out.println("4 - dodavanje novog korisnika");
            System.out.println("5 - dodavanje nove knjige");
            System.out.println("6 - dodavanje novog kupona");
            System.out.println("7 - promena podataka korisnika");
            System.out.println("8 - promena podataka knjige");
            System.out.println("9 - brisanje korisnika");
            System.out.println("10 - brisanje knjige");
            System.out.println("11 - brisanje kupona");
            System.out.println("0 - odjava");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.print("Opcija: ");
            Scanner scan = new Scanner(System.in);
            String unos = scan.nextLine();

            try {
                Integer opcija = Integer.parseInt(unos);
                String odgovor;
                switch(opcija){
                case 1:
                    ArrayList<Korisnik> listaKorisnika=new ArrayList<>();
                    try {
                       listaKorisnika = Korisnik.ucitajKorisnikeJSON("korisnici.json");
                    } catch (Exception ex) {
                       System.out.println("Greska u programu!");
                    }
                    do{
                        System.out.print("\nDa li zelite sortiranje po nekom od atributa (da/ne) :  ");
                        odgovor=scan.nextLine();
                        if(odgovor.equals("da"))
                            Korisnik.sortirajKorisnike(listaKorisnika);
                        if(odgovor.equals("ne"))
                            break;
                        if(!odgovor.equals("da") & !odgovor.equals("ne"))
                            System.out.println("\nUnesite da ili ne kao opcije!\n");
                    }while(!odgovor.equals("da") & !odgovor.equals("ne"));
                    System.out.println("\n\n-----------------------------Lista korisnika:-----------------------------");
                    for(Korisnik k : listaKorisnika)
                    {
                       System.out.println(k.toString());
                    }
                    System.out.println("\n\n");
                    break;

                case 2:                   
                    ArrayList<Knjiga> listaKnjiga=new ArrayList<>();
                    try {
                       listaKnjiga = Knjiga.ucitajKnjigeJSON("knjige.json");
                    } catch (Exception ex) {
                       System.out.println("Greska u programu!");
                    }
                    do{
                        System.out.print("\nDa li zelite sortiranje po nekom od atributa (da/ne) :  ");
                        odgovor=scan.nextLine();
                        if(odgovor.equals("da"))
                            Knjiga.sortirajKnjige(listaKnjiga);
                        if(odgovor.equals("ne"))
                            break;
                        if(!odgovor.equals("da") & !odgovor.equals("ne"))
                            System.out.println("\nUnesite da ili ne kao opcije!\n");
                    }while(!odgovor.equals("da") & !odgovor.equals("ne"));
                    System.out.println("\n\n-----------------------------Lista knjiga:-----------------------------");
                    for(Knjiga k : listaKnjiga)
                    {
                       System.out.println(k.toString()+"     ID:"+k.getId()+" Kolicina:"+k.getKolicina());
                    }
                    System.out.println("\n\n");
                    break;
                    
                case 3:
                    ArrayList<Kupon> listaKupona=new ArrayList<>();
                    try {
                       listaKupona = Kupon.ucitajKuponJSON("kuponi.json");
                    } catch (Exception ex) {
                       System.out.println("Greska u programu!");
                    }
                    System.out.println("\n\n-----------------------------Lista kupona:-----------------------------");
                    for(Kupon k : listaKupona)
                    {
                       System.out.println(k.toString());
                    }
                    System.out.println("\n\n");
                    break;
                    
                case 4:
                    System.out.println("\n--------------------------Registracija novog korisnika:--------------------------");
                    try {
                       Korisnik.registrujse();
                       System.out.println("\n\n");
                    } catch (Exception ex) {
                       System.out.println("Greska u programu!");
                    }
                    break;

                case 5:
                    System.out.println("\n--------------------------Dodaj novu knjigu:--------------------------");
                    try {
                       Knjiga.dodajKnjigu();
                       System.out.println("\n\n");
                    } catch (Exception ex) {
                       System.out.println("Greska u programu!");
                    }
                    break;
                    
                case 6:
                    System.out.println("\n--------------------------Dodaj novi kupon:--------------------------");
                    try {
                       Kupon.dodajNoviKupon();
                       System.out.println("\n\n");
                    } catch (Exception ex) {
                       System.out.println("Greska u programu!");
                    }
                    break;
                    
                case 7:
                    while(true){
                        System.out.println("\n--------------------------Promena podataka korisnika:--------------------------");
                        System.out.print("Unesite ID korisnika cije podatke zelite da promenite ili 0 za prekid: ");
                        unos = scan.nextLine();
                        try {
                            int korisnikID = Integer.parseInt(unos);
                            if (korisnikID == 0) {
                                break;
                            } else {
                            System.out.println("\n");
                            Korisnik.promeniPodatkeKorisnika("korisnici.json", korisnikID);
                            System.out.println("\n\n");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Unos nije validan broj! Molimo probajte ponovo.");
                        } catch (IOException | ParseException e) {
                            System.out.println("Greska u programu!");
                        }
                    }
                    break;
                    
                case 8:
                    while(true){
                        System.out.println("\n--------------------------Promena podataka knjige:--------------------------");
                        System.out.print("Unesite ID knjige cije podatke zelite da promenite ili 0 za prekid: ");
                        unos = scan.nextLine();
                        try {
                            int knjigaID = Integer.parseInt(unos);
                            if (knjigaID == 0) {
                                break;
                            } else {
                            System.out.println("\n");
                            Knjiga.promeniPodatkeKnjige("knjige.json", knjigaID);
                            System.out.println("\n\n");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Unos nije validan broj! Molimo probajte ponovo.");
                        } catch (IOException | ParseException e) {
                            System.out.println("Greska u programu!");
                        }
                    }
                    break;
                   
                case 9:
                    dalje=true;
                    while(dalje){
                        System.out.println("\n----------------------------Brisanje korisnika:----------------------------");
                        System.out.print("\n\nUnesite id korisnika za brisanje ili 0 za prekid: ");
                        unos = scan.nextLine();
                        try{
                            Integer korisnikId = Integer.parseInt(unos);
                            if(korisnikId==0)
                                dalje=false;
                            else
                            {
                                try {
                                    if(obrisiKorisnika("korisnici.json",korisnikId)){
                                        System.out.println("Odjavili ste se!\n\n");
                                       dalje=false; 
                                    };
                                } catch (IOException | ParseException ex) {
                                    System.out.println("\nGreska u programu!\n");
                                }
                            }
                        }catch(NumberFormatException e){
                            System.out.println("Pogresna opcija! Molimo probajte ponovo!");
                        }
                    }
                    break;

                case 10:
                    while(true){
                        System.out.println("\n----------------------------Brisanje knjige:----------------------------");
                        System.out.print("\n\nUnesite id knjige za brisanje ili 0 za prekid: ");
                        unos = scan.nextLine();
                        try{
                            Integer knjigaId = Integer.parseInt(unos);
                            if(knjigaId==0)
                                break;
                            else
                            {
                                try {
                                    Knjiga.obrisiKnjigu("knjige.json",knjigaId);
                                } catch (IOException | ParseException ex) {
                                    System.out.println("\nGreska u programu!\n");
                                }
                            }
                        }catch(NumberFormatException e){
                            System.out.println("Pogresna opcija! Molimo probajte ponovo!");
                        }
                    }
                   break;
                   
                case 11:
                    while(true){
                        System.out.println("\n----------------------------Brisanje kupona:----------------------------");
                        System.out.print("\n\nUnesite naziv kupona za brisanje ili \"kraj\" za prekid: ");
                        unos = scan.nextLine();
                        if(unos.trim().equalsIgnoreCase("kraj"))
                            break;
                        else
                        {
                            try {
                                Kupon.obrisiKupon("kuponi.json",unos);
                            } catch (IOException | ParseException ex) {
                                System.out.println("\nGreska u programu!\n");
                            }
                        }
                   }
                    break;
                    
                case 0:
                    dalje=false;
                    System.out.println("Odjavili ste se!\n\n");
                   break;
                   
                default:
                   System.out.println("Pogresno uneta opcija! Probajte ponovo!");
                   dalje=true;
                   break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Pogresna opcija! Molimo probajte ponovo!");
            }
        }
    }
}
