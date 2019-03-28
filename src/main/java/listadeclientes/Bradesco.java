/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listadeclientes;

import Relatorio.Connection_Factory;
import Relatorio.gerar_relatorio;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.view.JasperViewer;
import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.SacadorAvalista;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.springframework.web.jsf.FacesContextUtils;

@WebServlet("/Bradesco")
public class Bradesco extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, FileNotFoundException, IOException{
 
        Boleto boleto = createBoleto();
        BoletoViewer boleto_viewer = new BoletoViewer(boleto);
        byte[] PdfAsByte = boleto_viewer.getPdfAsByteArray();

            try {
                
                response.setHeader("Content-Disposition","\"attachment; filename=boleto.pdf\"");
                
                OutputStream outputstream = response.getOutputStream();
                outputstream.write(PdfAsByte);
                response.flushBuffer();
                
            }catch(IOException e){
                e.printStackTrace();
            }
    }
    public org.jrimum.bopepo.Boleto createBoleto() {
            
                //----- |Cedente| -----//

        Cedente cedente = new Cedente("PROJETO JRimum", "00.000.208/0001-00");
        //----- |Sacado| -----//
        Sacado sacado = new Sacado("Eduardo Miranda", "048.955.401-62");

        // Informando o endere�o do Cliente   
        Endereco endereco = new Endereco();
        endereco.setUF(UnidadeFederativa.GO);
        endereco.setLocalidade("Goi�nia");
        endereco.setCep(new CEP("74915-330"));
        endereco.setBairro("Setor dos Afonsos");
        endereco.setLogradouro("Rua Jos� da cunha, Qd140-A, Lt 1/4,Condom�nio Amarelo/Ap 202/Bloco 1B");
        endereco.setNumero("S/N");

        sacado.addEndereco(endereco);

        SacadorAvalista sacadorAvalista = new SacadorAvalista("JRimum Enterprise", "00.000.000/0001-91");

// Informando o endere�o do sacador avalista.
        Endereco enderecoSacAval = new Endereco();
        enderecoSacAval.setUF(UnidadeFederativa.DF);
        enderecoSacAval.setLocalidade("Bras�lia");
        enderecoSacAval.setCep(new CEP("59000-000"));
        enderecoSacAval.setBairro("Grande Centro");
        enderecoSacAval.setLogradouro("Rua Eternamente Principal");
        enderecoSacAval.setNumero("001");
        sacadorAvalista.addEndereco(enderecoSacAval);
// Informando a Conta Bancaria

        ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_BRADESCO.create());
        contaBancaria.setNumeroDaConta(new NumeroDaConta(123456, "0"));
        contaBancaria.setCarteira(new Carteira(30));
        contaBancaria.setAgencia(new Agencia(1234, "1"));

// Informando o Titulo da conta
        Titulo titulo = new Titulo(contaBancaria, sacado, cedente, sacadorAvalista);
        titulo.setNumeroDoDocumento("123465");
        titulo.setNossoNumero("99345678912");
        titulo.setDigitoDoNossoNumero("5");
        titulo.setValor(BigDecimal.valueOf(0.23));
        titulo.setDataDoDocumento(new java.util.Date());
        titulo.setDataDoVencimento(new java.util.Date());
        titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
        titulo.setAceite(Titulo.Aceite.A);
        titulo.setDesconto(new BigDecimal(0.05));
        titulo.setDeducao(BigDecimal.ZERO);
        titulo.setMora(BigDecimal.ZERO);
        titulo.setAcrecimo(BigDecimal.ZERO);
        titulo.setValorCobrado(BigDecimal.ZERO);

// Informando os dados do Boleto
        org.jrimum.bopepo.Boleto boleto = new org.jrimum.bopepo.Boleto(titulo);

        boleto.setLocalPagamento("Pag�vel preferencialmente na Rede X ou em "
                + "qualquer Banco at� o Vencimento.");
        boleto.setInstrucaoAoSacado("Senhor sacado, sabemos sim que o valor "
                + "cobrado n�o � o esperado, aproveite o DESCONT�O!");
        boleto.setInstrucao1("PARA PAGAMENTO 1 at� Hoje n�o cobrar nada!");
        boleto.setInstrucao2("PARA PAGAMENTO 2 at� Amanh� N�o cobre!");
        boleto.setInstrucao3("PARA PAGAMENTO 3 at� Depois de amanh�, OK, n�o cobre.");
        boleto.setInstrucao4("PARA PAGAMENTO 4 at� 04/xx/xxxx de 4 dias atr�s COBRAR O VALOR DE: R$ 01,00");
        boleto.setInstrucao5("PARA PAGAMENTO 5 at� 05/xx/xxxx COBRAR O VALOR DE: R$ 02,00");
        boleto.setInstrucao6("PARA PAGAMENTO 6 at� 06/xx/xxxx COBRAR O VALOR DE: R$ 03,00");
        boleto.setInstrucao7("PARA PAGAMENTO 7 at� xx/xx/xxxx COBRAR O VALOR QUE VOC� QUISER!");
        boleto.setInstrucao8("AP�S o Vencimento, Pag�vel Somente na Rede X.");
        
        return boleto;
    }
}
