package by.bsuir.cosi.lab1.auxiliary;

import by.bsuir.cosi.lab1.entity.Complex;
import by.bsuir.cosi.lab1.entity.logic.ComplexLogic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionGenerator {

    private FunctionGenerator() {
    }

    public static double getValueOfMainFunction(double x){
        return (Math.cos(3*x)+Math.sin(2*x));
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
        imaginary/=N;

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
            result.put(i, (1/ComplexLogic.getTan(dpfMap.get(i))));
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

        real/=N;
        imaginary/=N;

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

    public static List<Complex> doBPF(List<Double> a, int N, double dir){


    }
}
