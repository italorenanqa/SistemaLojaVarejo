package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL    = "jdbc:mysql://localhost:3306/loja_db?useSSL=false&serverTimezone=America/Sao_Paulo";
    private static final String USER   = "root";
    private static final String PASS   = "1234";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }
}
