/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latihan; //package

/**
 *
 * @author USER
 */
public class LatihanBiayaKursus { //class
    public static void main (String[] args){ //main method
        //Variabel
    String halo = "Selamat datang di Toko Ilzi";
    String kode = "JAVA-BSC";
    String nama = "Java Desktop Fundamental";
    double biaya = 3_800_000;
    double diskon = 0.10;
    double regs = 500_000;
    boolean aktif = true;
    double total = biaya;
    if (biaya >= 3_000_000){
       total = (biaya + regs) - ((biaya + regs) *0.15); 
    }
    else if(biaya >= 1_500_000){
       total = (biaya + regs) - ((biaya + regs) *0.10); 
    }
    
    else{
        total = (biaya + regs) - ((biaya + regs) *0.05); 
    }    //output
    System.out.println(halo);
    System.out.println("Kode   : " + kode);
    System.out.println("Kursus : " + nama);
    System.out.println("Aktif  : " + aktif);
    System.out.printf("Total  : Rp%,.0f%n", total );
    }
}
