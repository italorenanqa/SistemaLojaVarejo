package view;

import controller.ClienteController;
import model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClienteFrame extends JFrame {
    
    private ClienteController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNome, txtCpf, txtTelefone, txtEmail;
    
    public ClienteFrame() {
        controller = new ClienteController();
        inicializarComponentes();
        carregarTabela();
    }
    
    private void inicializarComponentes() {
        setTitle("Gerenciar Clientes");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior com formulário
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new GridLayout(5, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Dados do Cliente"));
        
        panelForm.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        panelForm.add(txtNome);
        
        panelForm.add(new JLabel("CPF:"));
        txtCpf = new JTextField();
        panelForm.add(txtCpf);
        
        panelForm.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField();
        panelForm.add(txtTelefone);
        
        panelForm.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelForm.add(txtEmail);
        
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
        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "Email"};
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
        List<Cliente> clientes = controller.listar();
        for (Cliente c : clientes) {
            tableModel.addRow(new Object[]{
                c.getId(),
                c.getNome(),
                c.getCpf(),
                c.getTelefone(),
                c.getEmail()
            });
        }
    }
    
    private void carregarCampos() {
        int linha = table.getSelectedRow();
        if (linha >= 0) {
            txtNome.setText(tableModel.getValueAt(linha, 1).toString());
            txtCpf.setText(tableModel.getValueAt(linha, 2).toString());
            txtTelefone.setText(tableModel.getValueAt(linha, 3).toString());
            txtEmail.setText(tableModel.getValueAt(linha, 4).toString());
        }
    }
    
    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        table.clearSelection();
    }
    
    private void salvar() {
        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        String tel = txtTelefone.getText().trim();
        String email = txtEmail.getText().trim();
        
        if (nome.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Cliente c = new Cliente(nome, cpf, tel, email);
        controller.cadastrar(c);
        
        JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
        limparCampos();
        carregarTabela();
    }
    
    private void atualizar() {
        int linha = table.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) tableModel.getValueAt(linha, 0);
        Cliente c = controller.buscarPorId(id);
        
        c.setNome(txtNome.getText().trim());
        c.setCpf(txtCpf.getText().trim());
        c.setTelefone(txtTelefone.getText().trim());
        c.setEmail(txtEmail.getText().trim());
        
        controller.atualizar(c);
        
        JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
        limparCampos();
        carregarTabela();
    }
    
    private void excluir() {
        int linha = table.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int resposta = JOptionPane.showConfirmDialog(this,
            "Deseja realmente excluir este cliente?",
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
