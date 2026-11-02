package projekat;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class Globalna{
	static String trenutniKorisnik;
	static double ukupnaCena=0;
}

public class Projekat {

    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException  {
        
        Scanner scan=new Scanner(System.in);
        Admin a = new Admin();
        System.out.println("Dobrodosli u KNJIZARU");
        System.out.println("----------------------");
        System.out.println("");
        
        int x=0;
        do{
	        System.out.println("Izaberite opciju (unesite 1 ili 2)");
	        System.out.println("1.Nastavi kao kupac");
	        System.out.println("2.Uloguj se kao admin");
	        try{
	        	x=scan.nextInt(); 
	        }catch(Exception ex){
	            System.out.println(ex.getMessage());   
	        }
	        
	        if(x!=1 && x!=2)System.out.println("Unesite pravilno opciju 1 ili 2");
        }while(x!=1 && x!=2);
               
               

        logIn(x);
    }
    		public static void kupovinaKnjiga() throws FileNotFoundException, IOException, ParseException {
                Scanner scan = new Scanner(System.in);
				int opcija2=0;
				System.out.println("1.Kupi knjigu");
				System.out.println("2.Vrati se");
                try{
                	opcija2=scan.nextInt();
                }catch(Exception ex){
                	System.out.println(ex.getMessage());
                } 
				if(opcija2==1) {
					korpa();
				}
				else if(opcija2==2) {
					korisnickiPanel();
				}
				else {
					kupovinaKnjiga();
				}
    		}
    		
    		public static void korpa() throws FileNotFoundException, IOException, ParseException {
    			Scanner scan = new Scanner(System.in);
				int opcija3=0;
                int opcija5=0;
                int kolicina=1;	
				System.out.println("Unesite redni broj knjige koju zelite da kupite");
                try{
                	opcija3=scan.nextInt();
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                    korpa();
                }
				Knjiga k3=new Knjiga();
				System.out.println(k3.uzmiKnjigu(opcija3));
				try{
					while (true) {
                        try {
                        	System.out.println("Kolicinu koju zelite :");
                            kolicina=scan.nextInt();
                            break;
                        } catch (Exception e) {
                            System.out.println("Morate uneti broj!");
                        }   
                    }              
					System.out.println("1.Zavrsi kupovinu");
					System.out.println("2.Nastavi sa kupovinom");
					System.out.println("3.Vrati se na izbor knjiga");
					do {
						try{
							opcija5=scan.nextInt();
						}catch(Exception e){
							System.out.println(e.getMessage());
                        }
					    if(opcija5==1) {
					        System.out.println("Vas racun je:");
					        Globalna.ukupnaCena += k3.uzmiKnjigu(opcija3).getCena()*kolicina;
					        System.out.println(Globalna.ukupnaCena);
					        Racun r=new Racun();
					        r.dodajRacun(Globalna.trenutniKorisnik, Globalna.ukupnaCena);
					        System.exit(0);
					    }
					    else if(opcija5==2) {
					    	Globalna.ukupnaCena += k3.uzmiKnjigu(opcija3).getCena()*kolicina;
					    	System.out.println("Trenutno stanje je: " + Globalna.ukupnaCena);
					    	korpa();
					    }
					    else if(opcija5==3) {
					    	Globalna.ukupnaCena += k3.uzmiKnjigu(opcija3).getCena()*kolicina;
					    	System.out.println("Trenutno stanje je: " + Globalna.ukupnaCena);
					    	kupovinaKnjiga();
					    }
					}while(opcija5!=3||opcija5!=2||opcija5!=1);
				 }catch(Exception ex){
					 System.out.println(ex.getMessage());
				 }
    		}
           
            public static void korisnickiPanel() throws FileNotFoundException, IOException, ParseException {
                int opcija1=0;
            
                Scanner scan = new Scanner(System.in);
                System.out.println("Izaberite neku od opcija :");
                System.out.println("1.Prikazi sve knjige");
                System.out.println("2.Pretrazi knjige po zanru");
                System.out.println("3.Pretrazi knjige po nazivu");
                System.out.println("4.Izadjite iz programa");
                   
                       
                try{
                	opcija1=scan.nextInt();
                }catch(Exception ex){
                	System.out.println(ex.getMessage());
                }
                      //scan.close();
		        if(opcija1==1){
		        	prikaziSveKnjige();
		    	  
			    }
			    else if(opcija1==2){
			    	pretraziPoZanru();
			    }
			    else if(opcija1==3){
			    	pretraziPoNazivu();
			    }
			    else if(opcija1==4) {
			    	System.exit(0);
			    }
			                
			    if(opcija1!=1 && opcija1!=2 && opcija1!=3 && opcija1!=4)korisnickiPanel();
		      
            
                kupovinaKnjiga();

             
            }
            
            public static void pretraziPoZanru(){
                int rednibr=0;
                Scanner scan = new Scanner(System.in);
                do{
                    System.out.println("Izaberite :");
                    System.out.println("1.Roman"); 
                    System.out.println("2.Misterija");
                    System.out.println("3.Fantastika");
                    System.out.println("4.Kriminalistika");
                    System.out.println("5.Horor");
                    System.out.println("6.Sci-Fi");
                    try{
                    	rednibr=scan.nextInt();
                    }catch(Exception ex){
                        System.out.println(ex.getMessage());
                    }
                 }while(rednibr!=1 && rednibr!=2 && rednibr!=3 && rednibr!=4 &&rednibr!=5 &&rednibr!=6);
                
                Knjiga k=new Knjiga();
                System.out.println(k.pretraziPoZanru(rednibr));
               
            
            }
            
            public static void pretraziPoNazivu(){
            	String pretraga="";
            	Scanner scan = new Scanner(System.in);
                System.out.println("Unesite ime knjige ");
                pretraga=scan.nextLine();
            
                Knjiga k=new Knjiga();
                if(k.pretraziPoNazivu(pretraga).getCena()!=0)
                {
                    System.out.println("Knjiga postoji!");
                    System.out.println(k.pretraziPoNazivu(pretraga));
                }
                else{
                    System.out.println("Knjiga ne postoji!");
                }
            }
            
            public static void prikaziSveKnjige(){
            	System.out.println("PRIKAZ SVIH KNJIGA ");
                Knjiga k=new Knjiga();
                k.listaKnjiga();
                String knj="";
                knj=k.sveKnjige();
                System.out.println(knj);
            
            }
            
            public static void dodajKnjige() throws IOException, FileNotFoundException, ParseException{
            	Scanner scan = new Scanner(System.in);
                String autor,drzava,jezik,naslov, sve;
                int cena,brojstrana,godina,zanr;
                    
                      
				System.out.println("Unesite podatke: Autor, Drzava, Cena, Jezik, Broj Strana, Naslov, Godina izdavanja.\nPodatke odvojte zarezima");
				sve = scan.nextLine();
				  
				String[] sve2 = sve.split(",");
				  
				autor = sve2[0];
				drzava = sve2[1];
				cena = Integer.parseInt(sve2[2]);
				jezik = sve2[3];
				brojstrana = Integer.parseInt(sve2[4]);
				naslov = sve2[5];
				godina = Integer.parseInt(sve2[6]);
				do{
					System.out.println("Odaberite Zanr");System.out.println("1.Roman"); 
					System.out.println("2.Misterija");
					System.out.println("3.Fantastika");
					System.out.println("4.Kriminalistika");
					System.out.println("5.Horor");
					System.out.println("6.Sci-Fi");
				    zanr = Integer.parseInt(scan.nextLine());
				}while(zanr!=1 && zanr!=2 && zanr!=3 && zanr!=4 &&zanr!=5 &&zanr!=6);
				Admin a=new Admin();
				a.dodajKnjigu(autor, drzava, cena, jezik, zanr, brojstrana, naslov, godina);
				System.out.println("Dodali ste knjigu!");
            }
            
            
            public static void dodajKorisnika() throws FileNotFoundException, IOException, ParseException{
            	int op;
            	String kor, pass;
            	boolean status=true;
            	Scanner scan = new Scanner(System.in);
                System.out.println("1.Dodaj admina");
                System.out.println("2.Dodaj kupca");
                System.out.println("3.Vrati se nazad");
                op=Integer.parseInt(scan.nextLine());
                
                if(op==1){
                    status = true;    
                }
                else if(op==2){
                    status = false;
                }
                else if(op==3){
                	adminPanel();
                }
                else{
                	dodajKorisnika();
                }
                System.out.println("Unesite korisnicno ime");
                kor = scan.next();
                System.out.println("Unesite password");
                pass = scan.next();
            
                Admin a=new Admin();
                a.dodajKor(kor, pass, status);
                System.out.println("Dodali ste korisnika!");
            }

            public static void adminPanel() throws FileNotFoundException, IOException, ParseException {
    			Scanner scan = new Scanner(System.in);
    			int opcijenove;
                 
                System.out.println("ADMIN PANEL");
                System.out.println("Opcije :");
                System.out.println("1.Dodaj knjigu");
                System.out.println("2.Prikazi racune");
                System.out.println("3.Dodaj korisnika");
                opcijenove=Integer.parseInt(scan.nextLine());
                if(opcijenove==1){
                    dodajKnjige();
                
                }
                else if(opcijenove==3){
                	dodajKorisnika();
                }
                else if(opcijenove==2) {
                	prikazRacuna();
                	
                }
                else {
                	adminPanel();
                }
                
    			adminPanel();
    		}

            public static void prikazRacuna() {
            	System.out.println("PRIKAZ SVIH RACUNA ");
                Racun r=new Racun();
                System.out.println(r.sviRacuni());
            }
            
            
            public static void logIn(int x) throws FileNotFoundException, IOException, ParseException {
            	Scanner scan = new Scanner(System.in);
				String korIme="";
                String korSifra="";
                System.out.println("Unesite vase korisnicko ime");
                try{
                	korIme=scan.nextLine();
                }catch(Exception ex){
                	System.out.println(ex.getMessage());
                }
				System.out.println("Unesite vasu sifru");
                try{
                	korSifra=scan.nextLine();
                }catch(Exception ex){
                	System.out.println(ex.getMessage());   
                }
//				Admin a = new Admin();
//				String sve = a.IspisiAdmine();
//                String[] odvojeno = sve.split(" ");
//                int br1=0;
//                int br2=0;
//				Kupac k = new Kupac();
//				String sve2 = k.ispisiKupce();
//                String[] odvojeno2 = sve2.split(" ");
//				if(x==2) {
//                    for(int i = 0; i<odvojeno.length; i+=2){
//                        if(odvojeno[i].equals(korIme) && a.dekripcija(odvojeno[i+1]).equals(korSifra)){
//                            br1++;
//                            adminPanel();
//                            
//                        }
//                        
//                    }
//                }
//				else {
//					for(int i = 0; i<odvojeno2.length; i+=2){
//                        if(odvojeno2[i].equals(korIme) && a.dekripcija(odvojeno2[i+1]).equals(korSifra)){
//                            br2++;
//                            Globalna.trenutniKorisnik = korIme;
//                            korisnickiPanel();
//                            
//                            
//                        }
//                        
//                    }
//				}
//				if(br2==0 && br1==0){
//					System.out.println("Uneli ste pogresnu sifru ili korisnicko ime");
//					logIn(x);
//                }
//			}
            }}
            
            
            
            
            
    

