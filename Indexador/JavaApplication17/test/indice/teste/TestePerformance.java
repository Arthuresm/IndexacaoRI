package indice.teste;

import indice.estrutura.ArquivoUtil;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import indice.estrutura.*;
import java.io.File;
import java.io.IOException;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

public class TestePerformance {

	private Indice indiceTeste;
    public long usedMem() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }
	@Test
	public void testIndexPerformance() {
		long usedMemNow,usedMemBefore;
//		indiceTeste = new IndiceSimples();
		indiceTeste = new IndiceLight(15000);
		
		
		//cria um vocabulario de 25*25*25 (15625) palavras
		String[] vocabulario = new String[25*25*25];
		int count = 0;
		for(int i =65; i<90 ; i++){
			for(int j=65; j<90 ; j++){
				for(int k=65; k<90 ; k++){
					
					vocabulario[count] = String.valueOf((char)i)+String.valueOf((char)j)+String.valueOf((char)k);
					count++;
				}
			}
		}
		//verifica se o vocabulario foi criado corretamente: 15625 palavras, sem termos repetidos
		Set<String> termos = new HashSet<String>();
		for(int i =0; i<15625 ; i++){
			assertNotNull(vocabulario[i]);
			termos.add(vocabulario[i]);
		}
		assertEquals(15625, termos.size());
		
		
		
		
		usedMemBefore = usedMem();
		System.out.println("Mem before:"+usedMemBefore/(1024.0*1024.0));
		
		//cria NUM_DOCS documentos cada um com NUM_TERM_PER_DOC percorrendo os 15625 termos
		final int NUM_DOCS = 20000; //Diminuir se demorar muito para rodar
		final int NUM_TERM_PER_DOC = 50; 
		long tempo = System.currentTimeMillis();
		int countTotal =0;
		count = 0;

			for(int d=0; d<NUM_DOCS ; d++){
//                            System.out.println("NUM DOC = " + d);
                            for(int term = 0; term<NUM_TERM_PER_DOC; term++){
                                if(1000>(usedMemBefore/(1024.0*1024.0))){
                                    indiceTeste.index(vocabulario[(count+1)%15625], d, (count%10)+1);
                                    //System.out.println("Index: "+count);
                                    count = (count+1)%15625;
                                    
                                    if(countTotal % 100000==0){
//                                            System.out.println("Indexando ocorrencia #"+countTotal);
                                    }
                                    countTotal++;
                                    usedMemBefore = usedMem();
                                    //System.out.println("Mem used:"+usedMemBefore/(1024.0*1024.0));
                                }else{
                                    System.out.println("Max heap size reached!");
                                    break;
                                }    
                            }
                                if(1000<=(usedMemBefore/(1024.0*1024.0)))
                                    break;
                                //System.out.println("Proximo doc!");
			}
                System.out.println("Concluindo indexacao!");
		indiceTeste.concluiIndexacao();
		System.out.println("Indexacao finalizada");
                
		System.out.println("Count: "+count);
		System.out.println("Tempo de indexacao: "+(System.currentTimeMillis()-tempo)/(1000.0)+" segundos");
		usedMemNow = usedMem();
		System.out.println("mem. now: "+usedMemNow/(1024.0*1024));
		System.out.println("Uso de memória para indexar: "+(usedMemNow-usedMemBefore)/(1024.0*1024.0)+" mb");
                
                
                ArquivoUtil arq = new ArquivoUtil();
                
                try {
                    arq.gravaTexto(indiceTeste.toString(), new File("teste.txt"), false);
                } catch (IOException ex) {
                    Logger.getLogger(TestePerformance.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
	}

}
