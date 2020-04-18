package by.bsuir.cosi.lab2.auxiliary;

import by.bsuir.cosi.lab2.entity.Complex;
import by.bsuir.cosi.lab2.entity.logic.ComplexLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionGenerator {

    private FunctionGenerator() {
    }

    public static double getValueOfMainCosFunction(double x) {
        //return (x < 0 || x > 2*Math.PI/3) ? 0 : (Math.cos(3 * x));    //period
        //return (x < 0 || x > 7) ? 0 : (Math.cos(3 * x));    //period
        return (Math.cos(3 * x));       //full
    }

    public static double getValueOfMainSinFunction(double x) {
        //return (x < 0 || x > Math.PI) ? 0 : (Math.sin(2 * x));    //period
        //return (x < 0 || x > 7) ? 0 : (Math.sin(2 * x));    //period
        return (Math.sin(2 * x));       //full
    }

    public static Map<Integer, Double> getConvolution(int N){
        Map<Integer, Double> result=new HashMap<Integer, Double>();

        int m=N-1;

        for (int i=0; i<=m; ++i){
            double amount=0;
            for (int j=0; j<N; ++j){
                amount+=getValueOfMainCosFunction(j)*getValueOfMainSinFunction(Math.abs(i-j));
            }
            amount/=N;
            result.put(i, amount);
        }

        return result;
    }

    public static Map<Integer, Double> getCorrelation(int N){
        Map<Integer, Double> result=new HashMap<Integer, Double>();

        int m=N-1;

        for (int i=0; i<=m; ++i){
            double amount=0;
            for (int j=0; j<N; ++j){
                amount+=getValueOfMainCosFunction(j)*getValueOfMainSinFunction(Math.abs((i+j)%N));
            }
            amount/=N;
            result.put(i, amount);
        }

        return result;
    }

    public static List<Complex> doBPF(List<Complex> a, int N, double dir){

        if(a.size()==1){
            return a;
        }

        List<Complex> aEven=new ArrayList<>();
        List<Complex> aOdd=new ArrayList<>();

        for(int i=0; i<a.size(); ++i){
            if(i%2==0){
                aEven.add(a.get(i));
            }else{
                aOdd.add(a.get(i));
            }
        }

        List<Complex> bEven=doBPF(aEven,N/2, dir);
        List<Complex> bOdd=doBPF(aOdd,N/2, dir);

        Complex mainRootN=new Complex(Math.cos(2*Math.PI/N), dir*Math.sin(2*Math.PI/N));
        Complex temp=new Complex(1,0);

        List<Complex> result=new ArrayList<>(N);

        for(int i=0; i<N; ++i){
            result.add(new Complex());
        }

        for (int j=0; j<N/2; ++j){
            result.set(j, ComplexLogic.amountOfTwoComplex(bEven.get(j), ComplexLogic.multiplicationOfTwoComplex(temp, bOdd.get(j))));
            result.set(j+N/2, ComplexLogic.subtractionOfTwoComplex(bEven.get(j), ComplexLogic.multiplicationOfTwoComplex(temp, bOdd.get(j))));
            temp=ComplexLogic.multiplicationOfTwoComplex(temp, mainRootN);
        }

        return result;
    }

}
