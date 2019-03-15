package Relatorio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.postgresql.core.ConnectionFactory;
import org.postgresql.core.QueryExecutor;
import org.postgresql.core.v3.ConnectionFactoryImpl;
import org.postgresql.util.HostSpec;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class gerar_relatorio {
    
        private String nomeArquivo;
        private Map<String ,Object> parametros;
        private Connection connection;
        
      public gerar_relatorio(String nomeArquivo , Map<String , Object> parametros, Connection connection){
          this.nomeArquivo = nomeArquivo;
          this.parametros = parametros;
          this.connection = connection;
          
      }
	
	public void GerarPDF(OutputStream outputstream){
		
            try {
                
                    JasperPrint jasper_print = JasperFillManager.fillReport(this.nomeArquivo, this.parametros, this.connection);
                    JRExporter exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasper_print);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputstream);
                    exporter.exportReport();
                    
            }catch(JRException e){
                throw new RuntimeException(e);
            }
	}

}
