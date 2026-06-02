package view;

import controller.CaixaController;
import enums.NivelAcesso;
import model.Caixa;
import model.Funcionario;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalFrame extends JFrame {
    
    private Funcionario usuarioLogado;
    private Caixa caixaAtual;
    private CaixaController caixaController;
    
    private JLabel lblUsuario;
    private JLabel lblCaixa;
    
    public MenuPrincipalFrame(Funcionario usuario) {
        this.usuarioLogado = usuario;
        this.caixaController = new CaixaController();
        this.caixaAtual = caixaController.buscarCaixaAberto();
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Sistema de Loja - Menu Principal");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 240, 240));
        
        // Título
        JLabel lblTitulo = new JLabel("MENU PRINCIPAL");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBounds(180, 20, 250, 30);
        panel.add(lblTitulo);
        
        // Info usuário
        lblUsuario = new JLabel("Usuário: " + usuarioLogado.getNome() + " [" + usuarioLogado.getNivelAcesso() + "]");
        lblUsuario.setBounds(20, 60, 400, 25);
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(lblUsuario);
        
        // Info caixa
        String statusCaixa = caixaAtual != null ? "ABERTO (ID " + caixaAtual.getId() + ")" : "FECHADO";
        lblCaixa = new JLabel("Caixa: " + statusCaixa);
        lblCaixa.setBounds(20, 85, 400, 25);
        lblCaixa.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(lblCaixa);
        
        // Botões
        int yPos = 130;
        int buttonWidth = 250;
        int buttonHeight = 40;
        int xPos = (600 - buttonWidth) / 2;
        
        JButton btnProdutos = criarBotao("Produtos", xPos, yPos);
        btnProdutos.addActionListener(e -> abrirProdutos());
        panel.add(btnProdutos);
        yPos += 50;
        
        JButton btnClientes = criarBotao("Clientes", xPos, yPos);
        btnClientes.addActionListener(e -> abrirClientes());
        panel.add(btnClientes);
        yPos += 50;
        
        JButton btnVendas = criarBotao("Vendas", xPos, yPos);
        btnVendas.addActionListener(e -> abrirVendas());
        panel.add(btnVendas);
        yPos += 50;
        
        JButton btnCaixa = criarBotao("Caixa", xPos, yPos);
        btnCaixa.addActionListener(e -> abrirCaixa());
        panel.add(btnCaixa);
        yPos += 50;
        
        // Botões apenas para ADMIN e GERENTE
        if (usuarioLogado.getNivelAcesso() == NivelAcesso.ADMIN || 
            usuarioLogado.getNivelAcesso() == NivelAcesso.GERENTE) {
            
            JButton btnFuncionarios = criarBotao("Funcionários", xPos, yPos);
            btnFuncionarios.addActionListener(e -> abrirFuncionarios());
            panel.add(btnFuncionarios);
            yPos += 50;
            
            JButton btnRelatorios = criarBotao("Relatórios", xPos, yPos);
            btnRelatorios.addActionListener(e -> abrirRelatorios());
            panel.add(btnRelatorios);
            yPos += 50;
        }
        
        // Botão Sair
        JButton btnSair = criarBotao("Sair", xPos, yPos + 20);
        btnSair.setBackground(new Color(220, 53, 69));
        btnSair.addActionListener(e -> sair());
        panel.add(btnSair);
        
        add(panel);
    }
    
    private JButton criarBotao(String texto, int x, int y) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, 250, 40);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(new Color(0, 123, 255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }
    
    private void abrirProdutos() {
        new ProdutoFrame().setVisible(true);
    }
    
    private void abrirClientes() {
        new ClienteFrame().setVisible(true);
    }
    
    private void abrirVendas() {
        if (caixaAtual == null) {
            JOptionPane.showMessageDialog(this,
                "Abra o caixa antes de realizar vendas!",
                "Atenção",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        new VendaFrame(usuarioLogado, caixaAtual).setVisible(true);
    }
    
    private void abrirCaixa() {
        new CaixaFrame(this).setVisible(true);
    }
    
    private void abrirFuncionarios() {
        if (usuarioLogado.getNivelAcesso() != NivelAcesso.ADMIN) {
            JOptionPane.showMessageDialog(this, "Acesso negado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new FuncionarioFrame().setVisible(true);
    }
    
    private void abrirRelatorios() {
        new RelatorioFrame().setVisible(true);
    }
    
    public void atualizarCaixa() {
        caixaAtual = caixaController.buscarCaixaAberto();
        String statusCaixa = caixaAtual != null ? "ABERTO (ID " + caixaAtual.getId() + ")" : "FECHADO";
        lblCaixa.setText("Caixa: " + statusCaixa);
    }
    
    public Caixa getCaixaAtual() {
        return caixaAtual;
    }
    
    private void sair() {
        int resposta = JOptionPane.showConfirmDialog(this,
            "Deseja realmente sair?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            new LoginFrame().setVisible(true);
            dispose();
        }
    }
}
