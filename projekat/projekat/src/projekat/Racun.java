package projekat;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Racun {
    private double cena;
    private int id;
    private String korIme;
    private ArrayList<Racun> racuni;

    public Racun(double cena, int id, String korIme) {
		super();
		this.cena = cena;
		this.id = id;
		this.korIme = korIme;
	}

    public void dodajRacun(String korIme, double cena) throws FileNotFoundException, IOException, ParseException {
    	ArrayList<Integer> redniBR=new ArrayList<>();
        JSONArray jsonKnjige=(JSONArray) new JSONParser().parse(new FileReader("C:\\Users\\Se6t\\Desktop\\projekat\\build\\classes\\projekat\\Racun.json"));
        for(Object o : jsonKnjige){
        JSONObject obj=(JSONObject) o;
        int redni=Integer.parseInt(obj.get("id").toString());
         redniBR.add(redni);
        }
          
        int r=redniBR.get(redniBR.size()-1);  
        int id=++r;
        
        PrintWriter pw;
        
        
        JSONObject obj=new JSONObject();
        obj.put("korisnicko",korIme);
        obj.put("cena",cena);
        obj.put("id", id);
        
        jsonKnjige.add(obj);
       
        pw=new PrintWriter(new FileOutputStream("C:\\Users\\Se6t\\Desktop\\projekat\\build\\classes\\projekat\\Racun.json"),true);
        pw.write(jsonKnjige.toJSONString());
        
        
        pw.close();
    }
    
    public ArrayList<Racun> listaRacuna(){
        racuni=new ArrayList<Racun>();
     try{
     JSONArray jsonRacuni=(JSONArray) new JSONParser().parse(new FileReader("C:\\Users\\Se6t\\Desktop\\projekat\\build\\classes\\projekat\\Racun.json"));
     for(Object o : jsonRacuni){
     JSONObject obj=(JSONObject) o;
     double cena=Double.parseDouble(obj.get("cena").toString());
     int id=Integer.parseInt(obj.get("id").toString());
      String korIme = obj.get("korisnicko").toString();
     
      racuni.add(new Racun(cena, id, korIme));
     }
      } catch (Exception ex) {
             System.err.println(ex.getMessage());
      }
     
     return racuni;
     }
    
    public String sviRacuni() {
    	   
        String sve="";
        
            for (int i = 0; i < racuni.size(); i++) {
                sve+=racuni.get(i).toString()+"\n";
            }
        
        
        return sve;
        }
    
	public String getKorIme() {
		return korIme;
	}

	public void setKorIme(String korIme) {
		this.korIme = korIme;
	}

	public Racun(double cena) {
        this.cena = cena;

    }

    public Racun() {
    }

    
    public void setCena(double cena) {
        this.cena = cena;
    }
    
    public double vratiCenu(){
    return cena;
    }
    
    public double dodajNaCenu(double novac){
    return cena+=novac;
    }
}
