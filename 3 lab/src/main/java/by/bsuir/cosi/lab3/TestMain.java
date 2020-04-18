package by.bsuir.cosi.lab3;

import by.bsuir.cosi.lab3.auxiliary.FunctionGenerator;

import java.util.*;

public class TestMain {


    public static void main(String[] args) {

        int N=32;

        List<Double> list=new ArrayList<>();

        for (double i=0; i<N; ++i){
            list.add(i);
        }

        System.out.println(list);

        List<Double> result= FunctionGenerator.doFWC(list, N);

        System.out.println(result);

        //System.out.println(FunctionGenerator.getMirrorNumber(1, 8));

        Map<Integer, Double> map=new HashMap<>();
        for(int i=0; i<N; ++i){
            map.put(i, result.get(FunctionGenerator.getMirrorNumber(i, N)));
        }

        System.out.println(map.values());

        List<Double> listOfMapValues=new ArrayList<>();
        listOfMapValues.addAll(map.values());

        System.out.println(listOfMapValues);

        List<Double> returnAl=FunctionGenerator.doFWCReturn(listOfMapValues, N);

        System.out.println(returnAl);

        for (int i=0; i<N; ++i){
            returnAl.set(i, returnAl.get(i)/N);
        }

        Collections.reverse(returnAl);

        System.out.println(returnAl);

    }




}
