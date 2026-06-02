package view;

import controller.CaixaController;
import controller.ProdutoController;
import controller.VendaController;
import model.Caixa;
import model.Produto;
import model.Venda;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class RelatorioFrame extends JFrame {
    
    private ProdutoController produtoController;
    private VendaController vendaController;
    private CaixaController caixaController;
    private JTextArea txtAreaRelatorio;
    
    public RelatorioFrame() {
        produtoController = new ProdutoController();
        vendaController = new VendaController();
        caixaController = new CaixaController();
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Relatórios");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior com botões
        JPanel panelTop = new JPanel(new GridLayout(1, 3, 10, 10));
        panelTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnEstoqueBaixo = new JButton("Estoque Baixo");
        btnEstoqueBaixo.addActionListener(e -> relatorioEstoqueBaixo());
        panelTop.add(btnEstoqueBaixo);
        
        JButton btnVendasDia = new JButton("Vendas do Dia");
        btnVendasDia.addActionListener(e -> relatorioVendasDia());
        panelTop.add(btnVendasDia);
        
        JButton btnHistoricoCaixa = new JButton("Histórico Caixa");
        btnHistoricoCaixa.addActionListener(e -> relatorioHistoricoCaixa());
        panelTop.add(btnHistoricoCaixa);
        
        add(panelTop, BorderLayout.NORTH);
        
        // Área de texto para o relatório
        txtAreaRelatorio = new JTextArea();
        txtAreaRelatorio.setEditable(false);
        txtAreaRelatorio.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtAreaRelatorio);
        add(scrollPane, BorderLayout.CENTER);
        
        // Botão fechar
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        JPanel panelBottom = new JPanel();
        panelBottom.add(btnFechar);
        add(panelBottom, BorderLayout.SOUTH);
    }
    
    private void relatorioEstoqueBaixo() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════════════════\n");
        sb.append("               RELATÓRIO - ESTOQUE BAIXO\n");
        sb.append("═══════════════════════════════════════════════════════════\n\n");
        
        List<Produto> produtos = produtoController.listarEstoqueBaixo();
        
        if (produtos.isEmpty()) {
            sb.append("Nenhum produto com estoque baixo.\n");
        } else {
            sb.append(String.format("%-5s %-30s %10s %10s\n", "ID", "Nome", "Estoque", "Mínimo"));
            sb.append("───────────────────────────────────────────────────────────\n");
            
            for (Produto p : produtos) {
                sb.append(String.format("%-5d %-30s %10d %10d\n",
                    p.getId(),
                    p.getNome(),
                    p.getQuantidadeEstoque(),
                    p.getEstoqueMinimo()));
            }
            sb.append("\nTotal de produtos: " + produtos.size());
        }
        
        txtAreaRelatorio.setText(sb.toString());
    }
    
    private void relatorioVendasDia() {
        String data = JOptionPane.showInputDialog(this, "Data (AAAA-MM-DD) ou deixe vazio para hoje:");
        if (data == null) return;
        
        if (data.isEmpty()) {
            data = LocalDate.now().toString();
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════════════════\n");
        sb.append("            RELATÓRIO - VENDAS DO DIA: " + data + "\n");
        sb.append("═══════════════════════════════════════════════════════════\n\n");
        
        List<Venda> vendas = vendaController.listarPorData(data);
        
        if (vendas.isEmpty()) {
            sb.append("Nenhuma venda registrada nesta data.\n");
        } else {
            sb.append(String.format("%-5s %-20s %-20s %15s\n", "ID", "Cliente", "Funcionário", "Total"));
            sb.append("───────────────────────────────────────────────────────────\n");
            
            double totalDia = 0;
            for (Venda v : vendas) {
                String cliente = v.getNomeCliente() != null ? v.getNomeCliente() : "Sem cliente";
                sb.append(String.format("%-5d %-20s %-20s R$ %12.2f\n",
                    v.getId(),
                    cliente.length() > 20 ? cliente.substring(0, 17) + "..." : cliente,
                    v.getNomeFuncionario().length() > 20 ? v.getNomeFuncionario().substring(0, 17) + "..." : v.getNomeFuncionario(),
                    v.getTotal()));
                totalDia += v.getTotal();
            }
            
            sb.append("───────────────────────────────────────────────────────────\n");
            sb.append(String.format("Total de vendas: %d | Total em R$: %.2f\n", vendas.size(), totalDia));
        }
        
        txtAreaRelatorio.setText(sb.toString());
    }
    
    private void relatorioHistoricoCaixa() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════════════════\n");
        sb.append("            RELATÓRIO - HISTÓRICO DE CAIXAS\n");
        sb.append("═══════════════════════════════════════════════════════════\n\n");
        
        List<Caixa> caixas = caixaController.listar();
        
        if (caixas.isEmpty()) {
            sb.append("Nenhum caixa registrado.\n");
        } else {
            sb.append(String.format("%-5s %-20s %15s %15s %-10s\n", "ID", "Funcionário", "Saldo Inicial", "Saldo Final", "Status"));
            sb.append("───────────────────────────────────────────────────────────\n");
            
            for (Caixa c : caixas) {
                sb.append(String.format("%-5d %-20s R$ %12.2f R$ %12.2f %-10s\n",
                    c.getId(),
                    c.getNomeFuncionario().length() > 20 ? c.getNomeFuncionario().substring(0, 17) + "..." : c.getNomeFuncionario(),
                    c.getSaldoInicial(),
                    c.getSaldoFinal(),
                    c.getStatus()));
            }
            
            sb.append("\nTotal de registros: " + caixas.size());
        }
        
        txtAreaRelatorio.setText(sb.toString());
    }
}
