package view;

import controller.ProdutoController;
import model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProdutoFrame extends JFrame {
    
    private ProdutoController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNome, txtDescricao, txtPrecoCusto, txtPrecoVenda, txtEstoque, txtEstoqueMin;
    
    public ProdutoFrame() {
        controller = new ProdutoController();
        inicializarComponentes();
        carregarTabela();
    }
    
    private void inicializarComponentes() {
        setTitle("Gerenciar Produtos");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior com formulário
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new GridLayout(7, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Dados do Produto"));
        
        panelForm.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        panelForm.add(txtNome);
        
        panelForm.add(new JLabel("Descrição:"));
        txtDescricao = new JTextField();
        panelForm.add(txtDescricao);
        
        panelForm.add(new JLabel("Preço Custo:"));
        txtPrecoCusto = new JTextField();
        panelForm.add(txtPrecoCusto);
        
        panelForm.add(new JLabel("Preço Venda:"));
        txtPrecoVenda = new JTextField();
        panelForm.add(txtPrecoVenda);
        
        panelForm.add(new JLabel("Qtd Estoque:"));
        txtEstoque = new JTextField();
        panelForm.add(txtEstoque);
        
        panelForm.add(new JLabel("Estoque Mínimo:"));
        txtEstoqueMin = new JTextField();
        panelForm.add(txtEstoqueMin);
        
        // Botões
        JPanel panelBotoes = new JPanel();
        
        JButton btnNovo = new JButton("Novo");
        btnNovo.addActionListener(e -> limparCampos());
        panelBotoes.add(btnNovo);
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvar());
        panelBotoes.add(btnSalvar);
        
        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> atualizar());
        panelBotoes.add(btnAtualizar);
        
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluir());
        panelBotoes.add(btnExcluir);
        
        panelForm.add(panelBotoes);
        
        add(panelForm, BorderLayout.NORTH);
        
        // Tabela
        String[] colunas = {"ID", "Nome", "Descrição", "P.Custo", "P.Venda", "Estoque", "Est.Min"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                carregarCampos();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        // Botão fechar
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        JPanel panelBottom = new JPanel();
        panelBottom.add(btnFechar);
        add(panelBottom, BorderLayout.SOUTH);
    }
    
    private void carregarTabela() {
        tableModel.setRowCount(0);
        List<Produto> produtos = controller.listar();
        for (Produto p : produtos) {
            tableModel.addRow(new Object[]{
                p.getId(),
                p.getNome(),
                p.getDescricao(),
                String.format("%.2f", p.getPrecoCusto()),
                String.format("%.2f", p.getPrecoVenda()),
                p.getQuantidadeEstoque(),
                p.getEstoqueMinimo()
            });
        }
    }
    
    private void carregarCampos() {
        int linha = table.getSelectedRow();
        if (linha >= 0) {
            txtNome.setText(tableModel.getValueAt(linha, 1).toString());
            txtDescricao.setText(tableModel.getValueAt(linha, 2).toString());
            txtPrecoCusto.setText(tableModel.getValueAt(linha, 3).toString());
            txtPrecoVenda.setText(tableModel.getValueAt(linha, 4).toString());
            txtEstoque.setText(tableModel.getValueAt(linha, 5).toString());
            txtEstoqueMin.setText(tableModel.getValueAt(linha, 6).toString());
        }
    }
    
    private void limparCampos() {
        txtNome.setText("");
        txtDescricao.setText("");
        txtPrecoCusto.setText("");
        txtPrecoVenda.setText("");
        txtEstoque.setText("");
        txtEstoqueMin.setText("");
        table.clearSelection();
    }
    
    private void salvar() {
        try {
            String nome = txtNome.getText().trim();
            String desc = txtDescricao.getText().trim();
            double custo = Double.parseDouble(txtPrecoCusto.getText().replace(",", "."));
            double venda = Double.parseDouble(txtPrecoVenda.getText().replace(",", "."));
            int estoque = Integer.parseInt(txtEstoque.getText());
            int min = Integer.parseInt(txtEstoqueMin.getText());
            
            Produto p = new Produto(nome, desc, custo, venda, estoque, min);
            controller.cadastrar(p);
            
            JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
            limparCampos();
            carregarTabela();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valores inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizar() {
        int linha = table.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = (int) tableModel.getValueAt(linha, 0);
            Produto p = controller.buscarPorId(id);
            
            p.setNome(txtNome.getText().trim());
            p.setDescricao(txtDescricao.getText().trim());
            p.setPrecoCusto(Double.parseDouble(txtPrecoCusto.getText().replace(",", ".")));
            p.setPrecoVenda(Double.parseDouble(txtPrecoVenda.getText().replace(",", ".")));
            p.setQuantidadeEstoque(Integer.parseInt(txtEstoque.getText()));
            p.setEstoqueMinimo(Integer.parseInt(txtEstoqueMin.getText()));
            
            controller.atualizar(p);
            
            JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
            limparCampos();
            carregarTabela();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valores inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluir() {
        int linha = table.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int resposta = JOptionPane.showConfirmDialog(this,
            "Deseja realmente excluir este produto?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            int id = (int) tableModel.getValueAt(linha, 0);
            controller.deletar(id);
            limparCampos();
            carregarTabela();
        }
    }
}
