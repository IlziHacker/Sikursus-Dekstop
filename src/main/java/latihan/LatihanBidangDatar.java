package latihan;

import java.util.Scanner;

public class LatihanBidangDatar {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.print("Masukkan panjang: ");
        double panjang = input.nextDouble();
        
        System.out.print("Masukkan lebar: ");
        double lebar = input.nextDouble();
        
        System.out.println("--------------------");
        
        if (panjang <= 0 || lebar <= 0) {
            System.out.println("Input tidak valid! Angka harus lebih dari 0.");
        } 
        else if (panjang == lebar) {
            double luas = panjang * lebar;
            double keliling = 4 * panjang;
            
            System.out.println("Bentuk: Persegi");
            System.out.println("Luas: " + luas);
            System.out.println("Keliling: " + keliling);
        } 
        else {
            double luas = panjang * lebar;
            double keliling = 2 * (panjang + lebar);
            
            System.out.println("Bentuk: Persegi Panjang");
            System.out.println("Luas: " + luas);
            System.out.println("Keliling: " + keliling);
        }
        
        input.close();
    }
}
