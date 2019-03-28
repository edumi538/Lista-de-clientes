package Relatorio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.management.RuntimeErrorException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class Connection_Factory {
	
	public Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:postgresql://localhost:5432/Clientes","postgres","12692444");
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
