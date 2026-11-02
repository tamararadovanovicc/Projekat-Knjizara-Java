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

public class Admin extends Korisnik {

    public Admin(String korisnicko, String sifra, boolean status) {
        super(korisnicko, sifra, status);
    }

    public Admin() {
    }
    

    
    
 
  public void dodajKnjigu(String autor,String drzava,int cena,String jezik,int zanr,int brojStrana,String naslov,int godina) throws FileNotFoundException, IOException, ParseException{
 String[] zanrovi= {"Novel", "Mystery", "Fantasy", "Crime", "Horor", "Sci-Fi"};
            ArrayList<Integer> redniBR=new ArrayList<>();
    JSONArray jsonKnjige=(JSONArray) new JSONParser().parse(new FileReader("C:\\Users\\PC\\OneDrive\\Desktop\\projekat\\projekat\\knjige.json"));
    for(Object o : jsonKnjige){
    JSONObject obj=(JSONObject) o;
    int redni=Integer.parseInt(obj.get("number").toString());
     redniBR.add(redni);
    }
      
    int r=redniBR.get(redniBR.size()-1);  
    int zaKnjigu=++r;
      
  PrintWriter pw;
  
 
  JSONObject obj=new JSONObject();
  obj.put("number",zaKnjigu);
  obj.put("author",autor);
  obj.put("country",drzava);
  obj.put("price",cena);
  obj.put("language",jezik);
  obj.put("genre",zanrovi[zanr-1]);
  obj.put("pages",brojStrana);
  obj.put("title",naslov);
  obj.put("year",godina);
  jsonKnjige.add(obj);
 
  pw=new PrintWriter(new FileOutputStream("C:\\Users\\PC\\OneDrive\\Desktop\\projekat\\projekat\\knjige.json"),true);
  pw.write(jsonKnjige.toJSONString());
  
  
  pw.close();
  }
  
  public void dodajKor(String kor,String sifra,boolean status) throws FileNotFoundException, IOException, ParseException{
  if(status==true){
  JSONArray jsonArray=(JSONArray) new JSONParser().parse(new FileReader("C:\\Users\\PC\\OneDrive\\Desktop\\projekat\\projekat\\build\\classes\\projekat\\Admin.json"));
  PrintWriter pw;
  
  JSONObject obj=new JSONObject();
  obj.put("korisnicko",kor);
  obj.put("sifra",enkripcija(sifra));
  obj.put("status", "admin");
      jsonArray.add(obj);
      
   pw=new PrintWriter(new FileOutputStream("C:\\Users\\PC\\OneDrive\\Desktop\\projekat\\projekat\\build\\classes\\projekat\\Admin.json"),true);
   pw.write(jsonArray.toJSONString());
   pw.close();
  
  }
   if(status == false){
   JSONArray jsonArray=(JSONArray) new JSONParser().parse(new FileReader("C:\\Users\\Se6t\\Desktop\\projekat\\build\\classes\\projekat\\Kupac.json"));
  PrintWriter pw;
  
  JSONObject obj=new JSONObject();
  obj.put("korisnicko",kor);
  obj.put("sifra",enkripcija(sifra));
  obj.put("status", "kupac");
      jsonArray.add(obj);
      
   pw=new PrintWriter(new FileOutputStream("C:\\Users\\PC\\OneDrive\\Desktop\\projekat\\projekat\\build\\classes\\projekat\\Kupac.json"),true);
   pw.write(jsonArray.toJSONString());
   pw.close();
   }
      
  
  
  }
  
   
   
    
    
}
