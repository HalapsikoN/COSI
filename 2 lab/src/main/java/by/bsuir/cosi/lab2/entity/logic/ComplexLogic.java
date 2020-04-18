package by.bsuir.cosi.lab2.entity.logic;

import by.bsuir.cosi.lab2.entity.Complex;

public class ComplexLogic {

    private ComplexLogic(){
    }

    public static double getModule(Complex complex){
        return Math.sqrt(Math.pow(complex.getReal(),2)+Math.pow(complex.getImaginary(), 2));
    }

    public static double getArctan(Complex complex){
        Double result=Math.atan(complex.getImaginary()/complex.getReal());
        if(result.isInfinite()){
            result=999.999;
        }
        return result;
    }

    public static Complex amountOfTwoComplex(Complex a, Complex b){
        return new Complex(a.getReal()+b.getReal(), a.getImaginary()+b.getImaginary());
    }

    public static Complex subtractionOfTwoComplex(Complex a, Complex b){
        return new Complex(a.getReal()-b.getReal(), a.getImaginary()-b.getImaginary());
    }

    public static Complex multiplicationOfTwoComplex(Complex a, Complex b){

        double real=a.getReal()*b.getReal()-a.getImaginary()*b.getImaginary();
        double imaginary=a.getImaginary()*b.getReal()+a.getReal()*b.getImaginary();

        return new Complex(real, imaginary);
    }

    public static Complex getComplexConjugate(Complex complex){
        Complex result=new Complex();
        result.setReal(complex.getReal());
        result.setImaginary(complex.getImaginary()*(-1));
        return result;
    }
}
