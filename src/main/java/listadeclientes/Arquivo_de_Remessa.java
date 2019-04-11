/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listadeclientes;

import java.io.FileWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool.Record;
import org.apache.lucene.store.FSDirectory;
import org.aspectj.weaver.tools.cache.FlatFileCacheBacking;
import org.h2.store.fs.FileUtils;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.utilix.ClassLoaders;
import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Texgit;

/**
 *
 * @author 
 */
@WebServlet("/Arquivo_Remessa")
public class Arquivo_de_Remessa extends HttpServlet{
    
    static final String USUARIO = "Grande Empresa";
    static final Integer FILIALMATRIZ = 5000;
    private static final long serialVersionUID = 1L;
    
    static int numeroSequencial;

public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, FileNotFoundException, IOException{
 
    
    response.setHeader("Content-Disposition","\"attachment; filename=arquivo_remessa.txt\"");
    File layout = new File(ClassLoaders.getResource("LayoutContabilidadeToy.txg.xml").getFile());
    FlatFile<org.jrimum.texgit.Record> ff = Texgit.createFlatFile(layout);
    ff.addRecord(createHeader(ff));
    
    File arquivo = new File("ContabilidadeToy.txt");
    FileWriter saida = new FileWriter(arquivo);
    
    java.util.List<String> linhas = ff.write();
    
    for(String linha:linhas){
      
        saida.write(linha);
        
    }
    saida.close();
    
    OutputStream out = response.getOutputStream();
    FileInputStream in = new FileInputStream(arquivo);
    byte[] buffer = new byte[4096];
    int length;
    while((length = in.read(buffer)) > 0){
        out.write(buffer, 0 ,length);
}
    in.close();
    out.flush();
    }

    	static org.jrimum.texgit.Record createHeader(FlatFile<org.jrimum.texgit.Record> ff){
            org.jrimum.texgit.Record header = ff.createRecord("Header");
            
            header.setValue("CodigoDaEmpresa", FILIALMATRIZ);
            header.setValue("CPRF", "03827635000155");
            header.setValue("DataInicial", new Date());
            header.setValue("DataFinal", new Date());
            header.setValue("TipoDeNota", 5);
            header.setValue("Sistema", 1);
            
            return header;
            
        }
     static org.jrimum.texgit.Record lancamentoUmDebitoParaUmCredito(FlatFile<org.jrimum.texgit.Record> ff){
         
         org.jrimum.texgit.Record headerLancamento = ff.createRecord("Detalhe-Header-Lancamento");
         headerLancamento.setValue("TipoDeLancamento", 'X');
         headerLancamento.setValue("DataDoLancamento", new Date());
         headerLancamento.setValue("Usuario", USUARIO);
         headerLancamento.setValue("Sequencia", ++numeroSequencial);
         
         org.jrimum.texgit.Record bodyLancamento = ff.createRecord("Detalhe-Body-Lancamento");
         bodyLancamento.setValue("ContaDebito", 5);
         bodyLancamento.setValue("ContaCredito", 313);
         bodyLancamento.setValue("Valor", new BigDecimal(1.50));
         bodyLancamento.setValue("CodigoDoHistorico", 1);
         bodyLancamento.setValue("ConteudoDoHistorico" , "Lancamento de teste: UM DEBITO PARA UM CREDITO.");
         bodyLancamento.setValue("CodigoDaLoja", FILIALMATRIZ);
         bodyLancamento.setValue("Sequencia", ++numeroSequencial);
         
         headerLancamento.addInnerRecord(bodyLancamento);
         
         return headerLancamento;
     } 
         static org.jrimum.texgit.Record lancamentoVariosDebitosParaVariosCreditos(FlatFile<org.jrimum.texgit.Record> ff){
             
             org.jrimum.texgit.Record headerLancamento = ff.createRecord("Detalhe-Header-Lancamento");
             headerLancamento.setValue("TipoDeLancamento", 'V');
             headerLancamento.setValue("DataDoLancamento", new Date());
             headerLancamento.setValue("Usuario", USUARIO);
             headerLancamento.setValue("Sequencia", ++numeroSequencial);
             
             org.jrimum.texgit.Record debito1 = ff.createRecord("Detalhe-Body-Lancamento");
             debito1.setValue("ContaDebito", 5);
             debito1.setValue("ContaCredito", 0);
             debito1.setValue("Valor", new BigDecimal(1));
             debito1.setValue("CodigoDoHistorico", 1);
             debito1.setValue("ConteudoDoHistorico", "Lancamento de teste VARIOS DEBITOS PARA VARIOS CREDITOS:  debito 1");
             debito1.setValue("CodigoDaLoja", FILIALMATRIZ);
             debito1.setValue("Sequencia", ++numeroSequencial);
             
             org.jrimum.texgit.Record debito2 = ff.createRecord("Detalhe-Body-Lancamento");
                        debito2.setValue("ContaDebito", 5);
			debito2.setValue("ContaCredito", 0);
			debito2.setValue("Valor", new BigDecimal(2));
			debito2.setValue("CodigoDoHistorico", 1);
			debito2.setValue("ConteudoDoHistorico", "Lancamento de teste VARIOS DEBITOS PARA VARIOS CREDITOS: debito 2");
			debito2.setValue("CodigoDaLoja", FILIALMATRIZ);
			debito2.setValue("Sequencia", ++numeroSequencial);
                        
            org.jrimum.texgit.Record debito3 = ff.createRecord("Detalhe-Body-Lancamento");
			debito3.setValue("ContaDebito", 0);
			debito3.setValue("ContaCredito", 313);
			debito3.setValue("Valor", new BigDecimal(1));
			debito3.setValue("CodigoDoHistorico", 1);
			debito3.setValue("ConteudoDoHistorico", "Lancamento de teste VARIOS DEBITOS PARA VARIOS CREDITOS: credito 1");
			debito3.setValue("CodigoDaLoja", FILIALMATRIZ);
			debito3.setValue("Sequencia", ++numeroSequencial);
                        
                        
            org.jrimum.texgit.Record credito1 = ff.createRecord("Detalhe-Body-Lancamento");
			credito1.setValue("ContaDebito", 0);
			credito1.setValue("ContaCredito", 313);
			credito1.setValue("Valor", new BigDecimal(1));
			credito1.setValue("CodigoDoHistorico", 1);
			credito1.setValue("ConteudoDoHistorico", "Lancamento de teste VARIOS DEBITOS PARA VARIOS CREDITOS: credito 2");
			credito1.setValue("CodigoDaLoja", FILIALMATRIZ);
			credito1.setValue("Sequencia", ++numeroSequencial);
                        
            	org.jrimum.texgit.Record credito2 = ff.createRecord("Detalhe-Body-Lancamento");
			credito2.setValue("ContaDebito", 0);
			credito2.setValue("ContaCredito", 313);
			credito2.setValue("Valor", new BigDecimal(1));
			credito2.setValue("CodigoDoHistorico", 1);
			credito2.setValue("ConteudoDoHistorico", "Lancamento de teste VARIOS DEBITOS PARA VARIOS CREDITOS: credito 3");
			credito2.setValue("CodigoDaLoja", FILIALMATRIZ);
			credito2.setValue("Sequencia", ++numeroSequencial);

			headerLancamento.addInnerRecord(debito1);
			headerLancamento.addInnerRecord(debito2);
			headerLancamento.addInnerRecord(debito3);
			headerLancamento.addInnerRecord(credito1);
			headerLancamento.addInnerRecord(credito2);
			
			return headerLancamento;
     }
         
         static org.jrimum.texgit.Record lancamentoUmDebitoParaVariosCreditos(FlatFile<org.jrimum.texgit.Record> ff){
		 	
		 	org.jrimum.texgit.Record headerLancamento = ff.createRecord("Detalhe-Header-Lancamento");
			headerLancamento.setValue("TipoDeLancamento", 'D');
			headerLancamento.setValue("DataDoLancamento", new Date());
			headerLancamento.setValue("Usuario", USUARIO);
			headerLancamento.setValue("Sequencia", ++numeroSequencial);
			
			org.jrimum.texgit.Record debito = ff.createRecord("Detalhe-Body-Lancamento");
			debito.setValue("ContaDebito", 5);
			debito.setValue("ContaCredito", 0);
			debito.setValue("Valor", new BigDecimal(9));
			debito.setValue("CodigoDoHistorico", 1);
			debito.setValue("ConteudoDoHistorico", "Lancamento de teste UM DEBITO PARA VARIOS CREDITOS: debito");
			debito.setValue("CodigoDaLoja", FILIALMATRIZ);
			debito.setValue("Sequencia", ++numeroSequencial);
			
			org.jrimum.texgit.Record credito1 = ff.createRecord("Detalhe-Body-Lancamento");
			credito1.setValue("ContaDebito", 0);
			credito1.setValue("ContaCredito", 313);
			credito1.setValue("Valor", new BigDecimal(3));
			credito1.setValue("CodigoDoHistorico", 1);
			credito1.setValue("ConteudoDoHistorico", "Lancamento de teste UM DEBITO PARA VARIOS CREDITOS: credito 1");
			credito1.setValue("CodigoDaLoja", FILIALMATRIZ);
			credito1.setValue("Sequencia", ++numeroSequencial);
			
			org.jrimum.texgit.Record credito2 = ff.createRecord("Detalhe-Body-Lancamento");
			credito2.setValue("ContaDebito", 0);
			credito2.setValue("ContaCredito", 313);
			credito2.setValue("Valor", new BigDecimal(3));
			credito2.setValue("CodigoDoHistorico", 1);
			credito2.setValue("ConteudoDoHistorico", "Lancamento de teste UM DEBITO PARA VARIOS CREDITOS: credito 2");
			credito2.setValue("CodigoDaLoja", FILIALMATRIZ);
			credito2.setValue("Sequencia", ++numeroSequencial);
			
			org.jrimum.texgit.Record credito3 = ff.createRecord("Detalhe-Body-Lancamento");
			credito3.setValue("ContaDebito", 0);
			credito3.setValue("ContaCredito", 313);
			credito3.setValue("Valor", new BigDecimal(3));
			credito3.setValue("CodigoDoHistorico", 1);
			credito3.setValue("ConteudoDoHistorico", "Lancamento de teste UM DEBITO PARA VARIOS CREDITOS: credito 3");
			credito3.setValue("CodigoDaLoja", FILIALMATRIZ);
			credito3.setValue("Sequencia", ++numeroSequencial);

			headerLancamento.addInnerRecord(debito);
			headerLancamento.addInnerRecord(credito1);
			headerLancamento.addInnerRecord(credito2);
			headerLancamento.addInnerRecord(credito3);			
			
			return headerLancamento;
	}
         
         static org.jrimum.texgit.Record lancamentoUmCreditoParaVariosDebitos(FlatFile<org.jrimum.texgit.Record> ff){
		 	
		 org.jrimum.texgit.Record headerLancamento = ff.createRecord("Detalhe-Header-Lancamento");
			headerLancamento.setValue("TipoDeLancamento", 'C');
			headerLancamento.setValue("DataDoLancamento", new Date());
			headerLancamento.setValue("Usuario", USUARIO);
			headerLancamento.setValue("Sequencia", ++numeroSequencial);
			
			org.jrimum.texgit.Record credito = ff.createRecord("Detalhe-Body-Lancamento");
			credito.setValue("ContaDebito", 0);
			credito.setValue("ContaCredito", 313);
			credito.setValue("Valor", new BigDecimal(10));
			credito.setValue("CodigoDoHistorico", 1);
			credito.setValue("ConteudoDoHistorico", "Lancamento de teste UM CREDITO PARA VARIOS DEBITOS: credito");
			credito.setValue("CodigoDaLoja", FILIALMATRIZ);
			credito.setValue("Sequencia", ++numeroSequencial);
			
			org.jrimum.texgit.Record debito1 = ff.createRecord("Detalhe-Body-Lancamento");
			debito1.setValue("ContaDebito", 5);
			debito1.setValue("ContaCredito", 0);
			debito1.setValue("Valor", new BigDecimal(2));
			debito1.setValue("CodigoDoHistorico", 1);
			debito1.setValue("ConteudoDoHistorico", "Lancamento de teste UM CREDITO PARA VARIOS DEBITOS: debito 1");
			debito1.setValue("CodigoDaLoja", FILIALMATRIZ);
			debito1.setValue("Sequencia", ++numeroSequencial);
			
			org.jrimum.texgit.Record debito2 = ff.createRecord("Detalhe-Body-Lancamento");
			debito2.setValue("ContaDebito", 5);
			debito2.setValue("ContaCredito", 0);
			debito2.setValue("Valor", new BigDecimal(2));
			debito2.setValue("CodigoDoHistorico", 1);
			debito2.setValue("ConteudoDoHistorico", "Lancamento de teste UM CREDITO PARA VARIOS DEBITOS: debito 2");
			debito2.setValue("CodigoDaLoja", FILIALMATRIZ);
			debito2.setValue("Sequencia", ++numeroSequencial);
			
			org.jrimum.texgit.Record debito3 = ff.createRecord("Detalhe-Body-Lancamento");
			debito3.setValue("ContaDebito", 5);
			debito3.setValue("ContaCredito", 0);
			debito3.setValue("Valor", new BigDecimal(6));
			debito3.setValue("CodigoDoHistorico", 1);
			debito3.setValue("ConteudoDoHistorico", "Lancamento de teste UM CREDITO PARA VARIOS DEBITOS: debito 3");
			debito3.setValue("CodigoDaLoja", FILIALMATRIZ);
			debito3.setValue("Sequencia", ++numeroSequencial);

			headerLancamento.addInnerRecord(credito);
			headerLancamento.addInnerRecord(debito1);
			headerLancamento.addInnerRecord(debito2);
			headerLancamento.addInnerRecord(debito3);			
			
			return headerLancamento;
	}
    
}
