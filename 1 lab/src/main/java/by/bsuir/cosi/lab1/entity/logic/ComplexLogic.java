package by.bsuir.cosi.lab1.entity.logic;

import by.bsuir.cosi.lab1.entity.Complex;

public class ComplexLogic {

    private ComplexLogic(){
    }

    public static double getModule(Complex complex){
        return Math.sqrt(Math.pow(complex.getReal(),2)+Math.pow(complex.getImaginary(), 2));
    }

    public static double getTan(Complex complex){
        return Math.tan(complex.getImaginary()/complex.getReal());
    }
}
