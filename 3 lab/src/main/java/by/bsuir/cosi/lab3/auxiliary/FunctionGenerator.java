package by.bsuir.cosi.lab3.auxiliary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FunctionGenerator {

    private FunctionGenerator() {
    }

    public static double getValueOfMainFunction(double x){
        //return (x<0 || x>2*Math.PI) ? 0 : (Math.cos(3*x)+Math.sin(2*x));
        return (Math.cos(3*x)+Math.sin(2*x));
    }

    public static List<Double> doFWC(List<Double> a, int N){

        if(a.size()==1){
            return a;
        }

        List<Double> aEven=new ArrayList<>();
        List<Double> aOdd=new ArrayList<>();

        for(int i=0; i<a.size(); ++i){
            if(i%2==0){
                aEven.add(a.get(i));
            }else{
                aOdd.add(a.get(i));
            }
        }

        List<Double> bEven= doFWC(aEven,N/2);
        List<Double> bOdd= doFWC(aOdd,N/2);

        List<Double> result=new ArrayList<>(N);

        for(int i=0; i<N; ++i){
            result.add(0.0);
        }

        for (int j=0; j<N/2; ++j){
            result.set(j, bEven.get(j)+bOdd.get(j));
            result.set(j+N/2, bEven.get(j)-bOdd.get(j));
        }

        return result;
    }

    public static List<Double> doFWCReturn(List<Double> a, int N){

        if(a.size()==1){
            return a;
        }

        List<Double> aOdd=new ArrayList<>(N/2);
        List<Double> aEven=new ArrayList<>(N/2);

        for(int i=0; i<N/2; ++i){
            aOdd.add(0.0);
            aEven.add(0.0);
        }

        for (int j=0; j<N/2; ++j){
            aOdd.set(j, a.get(j)+a.get(j+N/2));
            aEven.set(j, a.get(j)-a.get(j+N/2));
        }

        List<Double> bEven=doFWCReturn(aEven, N/2);
        List<Double> bOdd=doFWCReturn(aOdd, N/2);

        List<Double> result=new ArrayList<>();

        result.addAll(bEven);
        result.addAll(bOdd);

        return result;
    }

    public static int getMirrorNumber(int i, int N){

        int numberOfSymbols= (int) (Math.log(N)/Math.log(2));

        String numberInBinary=Integer.toBinaryString(i);

        StringBuilder reversed=new StringBuilder(numberInBinary);

        while (reversed.length()!=numberOfSymbols){
            reversed.insert(0, "0");
        }

        return Integer.parseInt(reversed.reverse().toString(), 2);
    }
}
