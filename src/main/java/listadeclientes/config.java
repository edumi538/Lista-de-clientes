package listadeclientes;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SpringBootApplication
public class config {
    
    public static void main(String [] args){
        SpringApplication.run(config.class, args);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://" + "localhost:" + "5432/" + "Clientes" + "?autoReconnect=true");
        dataSource.setUsername("postgres");
        dataSource.setPassword("123456");
        return dataSource;
    }
}