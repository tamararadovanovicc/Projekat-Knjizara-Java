/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projekat;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author korisnik
 */
public class Knjiga {
 
    private int redniBroj;
    private String autor;
    private String drzava;
    private int cena;
    private String jezik;
    private String zanr;
    private int brojStrana;
    private String naslov;
    private int godina;
    private ArrayList<Knjiga> knjiga;

    public Knjiga() {
    }

    public Knjiga(int redniBroj,String autor, String drzava, int cena, String jezik, String zanr, int brojStrana, String naslov, int godina) {
        this.redniBroj =redniBroj;
        this.autor = autor;
        this.drzava = drzava;
        this.cena = cena;
        this.jezik = jezik;
        this.zanr = zanr;
        this.brojStrana = brojStrana;
        this.naslov = naslov;
        this.godina = godina;
    }

    @Override
    public String toString() {
        return redniBroj + ". Knjiga {" + " autor=" + autor + ", drzava=" + drzava + ", cena=" + cena + ", jezik=" + jezik + ", zanr=" + zanr + ", brojStrana=" + brojStrana + ", naslov=" + naslov + ", godina=" + godina + '}';
    }
    
    public ArrayList<Knjiga> listaKnjiga(){
       knjiga=new ArrayList<Knjiga>();
    try{
    JSONArray jsonKnjige=(JSONArray) new JSONParser().parse(new FileReader("C:\\Users\\Se6t\\Desktop\\projekat\\build\\classes\\projekat\\knjige.json"));
    for(Object o : jsonKnjige){
    JSONObject obj=(JSONObject) o;
    int redni=Integer.parseInt(obj.get("number").toString());
    String autor=obj.get("author").toString();
     String drzava = obj.get("country").toString();
    int cena = Integer.parseInt(obj.get("price").toString());
     String jezik= obj.get("language").toString();
     String zanr= obj.get("genre").toString();
     int brojStrana = Integer.parseInt(obj.get("pages").toString());
   String naslov= obj.get("title").toString();
     int godina= Integer.parseInt(obj.get("year").toString());
     knjiga.add(new Knjiga(redni,autor,drzava,cena,jezik,zanr,brojStrana,naslov,godina));
    }
     } catch (Exception ex) {
            System.err.println(ex.getMessage());
     }
    
    return knjiga;
    }
    
    public Knjiga uzmiKnjigu(int redniBr){
    listaKnjiga();
        
          
      for (int i = 0; i < knjiga.size(); i++) {
            if(knjiga.get(i).redniBroj==redniBr)return knjiga.get(i);
        }
    
    return knjiga.get(2);
    }
    
    
    
    public String sveKnjige() {
   
    String sve="";
    
        for (int i = 0; i < knjiga.size(); i++) {
            sve+=knjiga.get(i).toString()+"\n";
        }
    
    
    return sve;
    }

    public int getRedniBroj() {
        return redniBroj;
    }

    public String getAutor() {
        return autor;
    }

    public String getDrzava() {
        return drzava;
    }

    public int getCena() {
        return cena;
    }

    public String getJezik() {
        return jezik;
    }

    public String getZanr() {
        return zanr;
    }

    public int getBrojStrana() {
        return brojStrana;
    }

    public String getNaslov() {
        return naslov;
    }

    public int getGodina() {
        return godina;
    }

    public ArrayList<Knjiga> getKnjiga() {
        return knjiga;
    }
    
    
   public String pretraziPoZanru(int redniBr){
       listaKnjiga();
       ArrayList<Knjiga> filtrirane = new ArrayList<Knjiga>();
     String[] zanrovi= {"Novel", "Mystery", "Fantasy", "Crime", "Horor", "Sci-Fi"};
    
        
          
      for (int i = 0; i < knjiga.size(); i++) {
            if(knjiga.get(i).getZanr().equals(zanrovi[redniBr-1])){
                filtrirane.add(knjiga.get(i));
            }
        }
      String sve="";
    
        for (int i = 0; i < filtrirane.size(); i++) {
            sve+=filtrirane.get(i).toString()+"\n";
        }
    
    return sve;
   
   
   } 
   public Knjiga pretraziPoNazivu(String naziv){
   Knjiga knjigaNaziv=new Knjiga();
   listaKnjiga();
   for (int i = 0; i < knjiga.size(); i++) {
            if(knjiga.get(i).getNaslov().equals(naziv)){
                knjigaNaziv=knjiga.get(i);
            }
        }
     
   return knjigaNaziv;
   
   }
   
    
}
