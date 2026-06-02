package view;

import controller.CaixaController;
import model.Caixa;

import javax.swing.*;
import java.awt.*;

public class CaixaFrame extends JFrame {
    
    private CaixaController controller;
    private MenuPrincipalFrame menuPrincipal;
    
    public CaixaFrame(MenuPrincipalFrame menu) {
        this.menuPrincipal = menu;
        this.controller = new CaixaController();
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Gerenciar Caixa");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        Caixa caixaAtual = controller.buscarCaixaAberto();
        
        if (caixaAtual == null) {
            JLabel lblInfo = new JLabel("Nenhum caixa aberto", SwingConstants.CENTER);
            lblInfo.setFont(new Font("Arial", Font.BOLD, 16));
            panel.add(lblInfo);
            
            JButton btnAbrir = new JButton("Abrir Caixa");
            btnAbrir.setFont(new Font("Arial", Font.BOLD, 14));
            btnAbrir.addActionListener(e -> abrirCaixa());
            panel.add(btnAbrir);
        } else {
            JLabel lblInfo = new JLabel("Caixa ABERTO - ID: " + caixaAtual.getId(), SwingConstants.CENTER);
            lblInfo.setFont(new Font("Arial", Font.BOLD, 16));
            panel.add(lblInfo);
            
            JLabel lblSaldo = new JLabel(String.format("Saldo Inicial: R$ %.2f", caixaAtual.getSaldoInicial()), SwingConstants.CENTER);
            panel.add(lblSaldo);
            
            JButton btnFechar = new JButton("Fechar Caixa");
            btnFechar.setFont(new Font("Arial", Font.BOLD, 14));
            btnFechar.setBackground(new Color(220, 53, 69));
            btnFechar.setForeground(Color.WHITE);
            btnFechar.addActionListener(e -> fecharCaixa(caixaAtual));
            panel.add(btnFechar);
        }
        
        JButton btnCancelar = new JButton("Voltar");
        btnCancelar.addActionListener(e -> dispose());
        panel.add(btnCancelar);
        
        add(panel, BorderLayout.CENTER);
    }
    
    private void abrirCaixa() {
        String input = JOptionPane.showInputDialog(this, "Saldo inicial do caixa:");
        if (input != null && !input.isEmpty()) {
            try {
                double saldo = Double.parseDouble(input.replace(",", "."));
                Caixa caixa = controller.abrir(saldo, 1); // ID fictício, ajustar conforme necessário
                JOptionPane.showMessageDialog(this, "Caixa aberto com sucesso!");
                menuPrincipal.atualizarCaixa();
                dispose();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void fecharCaixa(Caixa caixa) {
        String input = JOptionPane.showInputDialog(this, "Saldo final do caixa:");
        if (input != null && !input.isEmpty()) {
            try {
                double saldo = Double.parseDouble(input.replace(",", "."));
                controller.fechar(saldo);
                JOptionPane.showMessageDialog(this, "Caixa fechado com sucesso!");
                menuPrincipal.atualizarCaixa();
                dispose();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
