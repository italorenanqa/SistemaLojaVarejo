package view;

import controller.FuncionarioController;
import model.Funcionario;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private FuncionarioController funcionarioController;
    
    public LoginFrame() {
        funcionarioController = new FuncionarioController();
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Sistema de Loja - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 240, 240));
        
        // Título
        JLabel lblTitulo = new JLabel("SISTEMA DE LOJA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setBounds(100, 20, 200, 30);
        panel.add(lblTitulo);
        
        // Label e campo Login
        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setBounds(50, 70, 80, 25);
        panel.add(lblLogin);
        
        txtLogin = new JTextField();
        txtLogin.setBounds(130, 70, 200, 25);
        panel.add(txtLogin);
        
        // Label e campo Senha
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(50, 110, 80, 25);
        panel.add(lblSenha);
        
        txtSenha = new JPasswordField();
        txtSenha.setBounds(130, 110, 200, 25);
        panel.add(txtSenha);
        
        // Botão Entrar
        btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(130, 150, 200, 30);
        btnEntrar.setBackground(new Color(0, 123, 255));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        btnEntrar.addActionListener(e -> fazerLogin());
        panel.add(btnEntrar);
        
        // Enter no campo senha também faz login
        txtSenha.addActionListener(e -> fazerLogin());
        
        add(panel);
    }
    
    private void fazerLogin() {
        String login = txtLogin.getText().trim();
        String senha = new String(txtSenha.getPassword());
        
        if (login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Preencha login e senha!", 
                "Atenção", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Funcionario funcionario = funcionarioController.autenticar(login, senha);
        
        if (funcionario != null) {
            JOptionPane.showMessageDialog(this,
                "Bem-vindo(a), " + funcionario.getNome() + "!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Abre o menu principal e fecha a tela de login
            new MenuPrincipalFrame(funcionario).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Login ou senha incorretos!",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            txtSenha.setText("");
            txtLogin.requestFocus();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
