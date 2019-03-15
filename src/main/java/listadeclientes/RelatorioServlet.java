/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listadeclientes;

import Relatorio.Connection_Factory;
import Relatorio.gerar_relatorio;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;

@WebServlet("/gastos")
public class RelatorioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

//        String nome = request.getServletContext().getRealPath("/templates/jasper/Blank_Gastos.jasper");
            ClassPathResource resource = new ClassPathResource("templates/jasper/Blank_Gastos.jasper");
            String nome = resource.getFile().getAbsolutePath();
            Connection connection = new Connection_Factory().getConnection();
            Map<String, Object> Parametros = new HashMap<String, Object>();

            gerar_relatorio gerador = new gerar_relatorio(nome, Parametros, connection);

            gerador.GerarPDF(response.getOutputStream());

            connection.close();

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

}
