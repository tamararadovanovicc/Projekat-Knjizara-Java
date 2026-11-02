/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projekat;

/**
 *
 * @author korisnik
 */
public abstract class Korisnik {
    private String korisnicko;
    private String sifra;
    private boolean status;
    
    public String enkripcija(String sifra){
        String sifra2="";
        int slovo;
        for (int i = 0; i < sifra.length(); i++) {
           slovo =sifra.charAt(i)+1;
           sifra2+=(char)slovo;
            
        }
        return sifra2;
    }
    
    public String dekripcija(String sifra){
      String sifra2="";
        int slovo;
        for (int i = 0; i < sifra.length(); i++) {
           slovo =sifra.charAt(i)-1;
           sifra2+=(char)slovo;
        }
        return sifra2; 
    }

    public Korisnik(String korisnicko, String sifra, boolean status) {
        this.korisnicko = korisnicko;
        this.sifra = sifra;
        this.status = status;
    }

    public String getKorisnicko() {
		return korisnicko;
	}
	public void setKorisnicko(String korisnicko) {
		this.korisnicko = korisnicko;
	}
	public String getSifra() {
		return sifra;
	}
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Korisnik() {
    }

    @Override
    public String toString() {
        return "Korisnik{" + "korisnicko=" + korisnicko + ", sifra=" + sifra + '}';
    }
    
}
