package by.bsuir.cosi.lab1.auxiliary;

import by.bsuir.cosi.lab1.entity.Complex;
import by.bsuir.cosi.lab1.entity.logic.ComplexLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionGenerator {

    private FunctionGenerator() {
    }

    public static double getValueOfMainFunction(double x){
        return (x<0 || x>2*Math.PI) ? 0 : (Math.cos(3*x)+Math.sin(2*x));
        //return (Math.cos(3*x)+Math.sin(2*x));
    }

    public static Complex getDPFComponent(int m, int N){
        if(m<0){
            m=0;
        }
        if(N<0){
            N=0;
        }

        double real=0;
        double imaginary=0;

        for(int i=0; i<N; ++i) {
            real+=getValueOfMainFunction(i)*Math.cos(2*Math.PI*i*m/N);
            imaginary+=getValueOfMainFunction(i)*Math.sin(2*Math.PI*i*m/N);
        }

        real/=N;
        imaginary/=-N;

        return new Complex(real, imaginary);
    }

    public static Map<Integer, Complex> getDPFMap(int N){
        if(N<0){
            N=0;
        }

        Map<Integer, Complex> result=new HashMap<>();

        for (int i=0; i<N; ++i){
            result.put(i, getDPFComponent(i, N));
        }

        return result;
    }

    public static Map<Integer, Double> getAmplitudeMap(Map<Integer, Complex> dpfMap){
        Map<Integer, Double> result=new HashMap<>();

        for(Integer i:dpfMap.keySet()){
            result.put(i, ComplexLogic.getModule(dpfMap.get(i)));
        }

        return result;
    }

    public static Map<Integer, Double> getPhaseMap(Map<Integer, Complex> dpfMap){
        Map<Integer, Double> result=new HashMap<>();

        for(Integer i:dpfMap.keySet()){
            result.put(i, (ComplexLogic.getArctan(dpfMap.get(i))));
        }

        return result;
    }

    public static Complex getReturnDPFComponent(int m, int N, Map<Integer, Complex> dpfMap){
        if(m<0){
            m=0;
        }
        if(N<0){
            N=0;
        }

        double real=0;
        double imaginary=0;

        for(int i=0; i<N; ++i) {
            real+=dpfMap.get(i).getReal()*Math.cos(2*Math.PI*m*i/N)-dpfMap.get(i).getImaginary()*Math.sin(2*Math.PI*m*i/N);
            imaginary+=dpfMap.get(i).getImaginary()*Math.cos(2*Math.PI*m*i/N)+dpfMap.get(i).getReal()*Math.sin(2*Math.PI*m*i/N);
        }

        return new Complex(real, imaginary);
    }

    public static Map<Integer, Complex> getReturnDPFMap(int N, Map<Integer, Complex> dpfMap){
        if(N<0){
            N=0;
        }

        Map<Integer, Complex> result=new HashMap<>();

        for (int i=0; i<N; ++i){
            result.put(i, getReturnDPFComponent(i, N, dpfMap));
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
