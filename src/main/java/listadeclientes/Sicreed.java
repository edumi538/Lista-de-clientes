/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listadeclientes;

import com.lowagie.text.DocumentException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;
import javax.transaction.NotSupportedException;
import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.campolivre.NotSupportedBancoException;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.bopepo.exemplo.banco.sicredi.ItemFaturaBoletoSICREDI;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Banco;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.SacadorAvalista;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

@WebServlet("/Sicredi")
public class Sicreed extends HttpServlet{
    
    private static final long serialVersionUID = 1L;
 
 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, FileNotFoundException, IOException{
 
        Boleto boleto = create_Boleto();
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

    public Boleto create_Boleto(){
               Cedente cedente = new Cedente("PROJETO JRimum", "00.000.208/0001-00");

        final Banco banco = BancosSuportados.BANCO_SICREDI.create();

        ContaBancaria contaBancariaCed = new ContaBancaria(banco);
        contaBancariaCed.setAgencia(new Agencia(123, "6"));
        contaBancariaCed.setCarteira(new Carteira(1, TipoDeCobranca.SEM_REGISTRO));
        contaBancariaCed.setNumeroDaConta(new NumeroDaConta(7891, "0"));
        cedente.addContaBancaria(contaBancariaCed);

        Sacado sacado = new Sacado("JavaDeveloper Pronto Para F�rias", "222.222.222-22");
        Endereco enderecoSac = new Endereco();
        enderecoSac.setUF(UnidadeFederativa.GO);
        enderecoSac.setLocalidade("Goi�nia");
        enderecoSac.setCep(new CEP("59064-120"));
        enderecoSac.setBairro("Grande Centro");
        enderecoSac.setLogradouro("Rua poeta dos programas");
        enderecoSac.setNumero("1");
        sacado.addEndereco(enderecoSac);

        SacadorAvalista sacadorAvalista = new SacadorAvalista("Nordeste Fomento Mercantil", "00.000.000/0001-91");
        Endereco enderecoSacAval = new Endereco();
        enderecoSacAval.setUF(UnidadeFederativa.DF);
        enderecoSacAval.setLocalidade("Bras�lia");
        enderecoSacAval.setCep(new CEP("00000-000"));
        enderecoSacAval.setBairro("Grande Centro");
        enderecoSacAval.setLogradouro("Rua Eternamente Principal");
        enderecoSacAval.setNumero("001");
        sacadorAvalista.addEndereco(enderecoSacAval);

        Titulo titulo = new Titulo(contaBancariaCed, sacado, cedente, sacadorAvalista);
        titulo.setNumeroDoDocumento("123456789");
        titulo.setNossoNumero("07200003");
        titulo.setDigitoDoNossoNumero("9");
        titulo.setValor(BigDecimal.valueOf(0.23));
        titulo.setDataDoDocumento(new Date());
        titulo.setDataDoVencimento(new Date());
        titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
        titulo.setAceite(Titulo.Aceite.A);

        titulo.setParametrosBancarios(new ParametrosBancariosMap("PostoDaAgencia", 02));

        Boleto boleto = new Boleto(titulo);
        boleto.setLocalPagamento("Pag�vel preferencialmente na Rede X ou em "
                + "qualquer Banco at� o Vencimento.");
        boleto.setInstrucaoAoSacado("Senhor sacado, sabemos sim que o valor "
                + "cobrado n�o o esperado, aproveite o DESCONTO!");
        boleto.setInstrucao1("PARA PAGAMENTO 1 at� Hoje n�o cobrar nada!");
        boleto.setInstrucao2("PARA PAGAMENTO 2 at� Amanh� N�o cobre!");
        boleto.setInstrucao3("PARA PAGAMENTO 3 at� Depois de amanh�, OK, n�o cobre.");
        boleto.setInstrucao4("PARA PAGAMENTO 4 at� 04/xx/xxxx de 4 dias atr�s "
                + "COBRAR O VALOR DE: R$ 01,00");
        boleto.setInstrucao5("PARA PAGAMENTO 5 at� 05/xx/xxxx COBRAR O VALOR "
                + "DE: R$ 02,00");
        boleto.setInstrucao6("PARA PAGAMENTO 6 at� 06/xx/xxxx COBRAR O VALOR "
                + "DE: R$ 03,00");
        boleto.setInstrucao7("PARA PAGAMENTO 7 at� xx/xx/xxxx COBRAR O VALOR "
                + "QUE VOC� QUISER!");
        boleto.setInstrucao8("AP�S o Vencimento, Pag�vel Somente na Rede X.");
        
        return boleto;
        
    }
}
