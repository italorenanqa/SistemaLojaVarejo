package view;

import controller.FuncionarioController;
import enums.NivelAcesso;
import model.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FuncionarioFrame extends JFrame {
    
    private FuncionarioController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNome, txtLogin, txtSenha;
    private JComboBox<String> cbNivel;
    
    public FuncionarioFrame() {
        controller = new FuncionarioController();
        inicializarComponentes();
        carregarTabela();
    }
    
    private void inicializarComponentes() {
        setTitle("Gerenciar Funcionários");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior com formulário
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new GridLayout(5, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Dados do Funcionário"));
        
        panelForm.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        panelForm.add(txtNome);
        
        panelForm.add(new JLabel("Login:"));
        txtLogin = new JTextField();
        panelForm.add(txtLogin);
        
        panelForm.add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        panelForm.add(txtSenha);
        
        panelForm.add(new JLabel("Nível de Acesso:"));
        cbNivel = new JComboBox<>(new String[]{"ADMIN", "GERENTE", "CAIXA"});
        panelForm.add(cbNivel);
        
        // Botões
        JPanel panelBotoes = new JPanel();
        
        JButton btnNovo = new JButton("Novo");
        btnNovo.addActionListener(e -> limparCampos());
        panelBotoes.add(btnNovo);
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvar());
        panelBotoes.add(btnSalvar);
        
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluir());
        panelBotoes.add(btnExcluir);
        
        panelForm.add(panelBotoes);
        
        add(panelForm, BorderLayout.NORTH);
        
        // Tabela
        String[] colunas = {"ID", "Nome", "Login", "Nível"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        
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
        List<Funcionario> funcionarios = controller.listar();
        for (Funcionario f : funcionarios) {
            tableModel.addRow(new Object[]{
                f.getId(),
                f.getNome(),
                f.getLogin(),
                f.getNivelAcesso()
            });
        }
    }
    
    private void limparCampos() {
        txtNome.setText("");
        txtLogin.setText("");
        txtSenha.setText("");
        cbNivel.setSelectedIndex(2);
        table.clearSelection();
    }
    
    private void salvar() {
        String nome = txtNome.getText().trim();
        String login = txtLogin.getText().trim();
        String senha = txtSenha.getText().trim();
        
        if (nome.isEmpty() || login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        NivelAcesso nivel = NivelAcesso.valueOf(cbNivel.getSelectedItem().toString());
        
        Funcionario f = new Funcionario(nome, login, senha, nivel);
        controller.cadastrar(f);
        
        JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!");
        limparCampos();
        carregarTabela();
    }
    
    private void excluir() {
        int linha = table.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int resposta = JOptionPane.showConfirmDialog(this,
            "Deseja realmente excluir este funcionário?",
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
