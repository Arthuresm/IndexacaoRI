/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indice.teste;


import indice.estrutura.IndiceLight;

/**
 *
 * @author Bruno
 */
public class Teste {
    
    public static void main(String[] args){
        //TesteEstruturaIndice simples = new TesteEstruturaIndice();
        //simples.iniciaIndice();
        
        //TesteIndiceLight light = new TesteIndiceLight();;
        //light.testOrdenacao();
        
        TestePerformance perf = new TestePerformance();
        perf.testIndexPerformance();
    }
}
