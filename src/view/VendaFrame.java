package view;

import controller.ClienteController;
import controller.ProdutoController;
import controller.VendaController;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VendaFrame extends JFrame {
    
    private VendaController vendaController;
    private ProdutoController produtoController;
    private ClienteController clienteController;
    
    private Funcionario funcionario;
    private Caixa caixa;
    private Venda vendaAtual;
    
    private JComboBox<String> cbCliente;
    private JComboBox<String> cbProduto;
    private JTextField txtQuantidade;
    private JLabel lblTotal;
    private JTable table;
    private DefaultTableModel tableModel;
    
    private List<Cliente> clientes;
    private List<Produto> produtos;
    
    public VendaFrame(Funcionario funcionario, Caixa caixa) {
        this.funcionario = funcionario;
        this.caixa = caixa;
        this.vendaController = new VendaController();
        this.produtoController = new ProdutoController();
        this.clienteController = new ClienteController();
        
        this.vendaAtual = vendaController.iniciarVenda(funcionario.getId(), caixa.getId());
        
        inicializarComponentes();
        carregarClientes();
        carregarProdutos();
    }
    
    private void inicializarComponentes() {
        setTitle("Nova Venda");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior
        JPanel panelTop = new JPanel(new GridLayout(4, 2, 5, 5));
        panelTop.setBorder(BorderFactory.createTitledBorder("Nova Venda"));
        
        panelTop.add(new JLabel("Cliente:"));
        cbCliente = new JComboBox<>();
        panelTop.add(cbCliente);
        
        panelTop.add(new JLabel("Produto:"));
        cbProduto = new JComboBox<>();
        panelTop.add(cbProduto);
        
        panelTop.add(new JLabel("Quantidade:"));
        txtQuantidade = new JTextField();
        panelTop.add(txtQuantidade);
        
        JButton btnAdicionar = new JButton("Adicionar Item");
        btnAdicionar.addActionListener(e -> adicionarItem());
        panelTop.add(new JLabel());
        panelTop.add(btnAdicionar);
        
        add(panelTop, BorderLayout.NORTH);
        
        // Tabela de itens
        String[] colunas = {"Produto", "Quantidade", "Preço Unit.", "Subtotal"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        // Panel inferior
        JPanel panelBottom = new JPanel(new BorderLayout());
        
        lblTotal = new JLabel("TOTAL: R$ 0,00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
        panelBottom.add(lblTotal, BorderLayout.NORTH);
        
        JPanel panelBotoes = new JPanel();
        JButton btnFinalizar = new JButton("Finalizar Venda");
        btnFinalizar.setBackground(new Color(40, 167, 69));
        btnFinalizar.setForeground(Color.WHITE);
        btnFinalizar.addActionListener(e -> finalizarVenda());
        panelBotoes.add(btnFinalizar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(220, 53, 69));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.addActionListener(e -> cancelarVenda());
        panelBotoes.add(btnCancelar);
        
        panelBottom.add(panelBotoes, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
    }
    
    private void carregarClientes() {
        clientes = clienteController.listar();
        cbCliente.addItem("-- Sem cliente --");
        for (Cliente c : clientes) {
            cbCliente.addItem(c.getId() + " - " + c.getNome());
        }
    }
    
    private void carregarProdutos() {
        produtos = produtoController.listar();
        cbProduto.removeAllItems();
        for (Produto p : produtos) {
            cbProduto.addItem(p.getId() + " - " + p.getNome() + " (R$ " + String.format("%.2f", p.getPrecoVenda()) + ")");
        }
    }
    
    private void adicionarItem() {
        if (cbProduto.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int qtd = Integer.parseInt(txtQuantidade.getText());
            if (qtd <= 0) {
                JOptionPane.showMessageDialog(this, "Quantidade deve ser maior que zero!", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Produto p = produtos.get(cbProduto.getSelectedIndex());
            
            if (p.getQuantidadeEstoque() < qtd) {
                JOptionPane.showMessageDialog(this, "Estoque insuficiente! Disponível: " + p.getQuantidadeEstoque(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            vendaController.adicionarItem(vendaAtual, p.getId(), p.getNome(), p.getPrecoVenda(), qtd);
            
            tableModel.addRow(new Object[]{
                p.getNome(),
                qtd,
                String.format("R$ %.2f", p.getPrecoVenda()),
                String.format("R$ %.2f", p.getPrecoVenda() * qtd)
            });
            
            atualizarTotal();
            txtQuantidade.setText("");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarTotal() {
        lblTotal.setText(String.format("TOTAL: R$ %.2f", vendaAtual.getTotal()));
    }
    
    private void finalizarVenda() {
        if (vendaAtual.getItens().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Adicione ao menos um item!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Vincular cliente se selecionado
        if (cbCliente.getSelectedIndex() > 0) {
            Cliente c = clientes.get(cbCliente.getSelectedIndex() - 1);
            vendaAtual.setIdCliente(c.getId());
            vendaAtual.setNomeCliente(c.getNome());
        }
        
        int resposta = JOptionPane.showConfirmDialog(this,
            String.format("Finalizar venda no valor de R$ %.2f?", vendaAtual.getTotal()),
            "Confirmar",
            JOptionPane.YES_NO_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            vendaController.finalizarVenda(vendaAtual);
            JOptionPane.showMessageDialog(this, "Venda finalizada com sucesso!");
            dispose();
        }
    }
    
    private void cancelarVenda() {
        int resposta = JOptionPane.showConfirmDialog(this,
            "Deseja realmente cancelar esta venda?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            vendaController.cancelarVenda(vendaAtual);
            dispose();
        }
    }
}
