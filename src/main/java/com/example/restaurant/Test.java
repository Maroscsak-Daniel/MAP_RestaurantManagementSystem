package com.example.restaurant;

enum Zi {
    Luni,
    Marti,
    Miercuri
};
public class Test{
    public static void main(String [] args){
        Zi azi = Zi.Luni;
        System.out.println(azi);
        for(Zi zi : Zi.values()){
            System.out.println(zi);
        }
    }
}
