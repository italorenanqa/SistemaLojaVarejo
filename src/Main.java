import controller.*;
import enums.NivelAcesso;
import model.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static FuncionarioController funcionarioController = new FuncionarioController();
    static ClienteController     clienteController     = new ClienteController();
    static ProdutoController     produtoController     = new ProdutoController();
    static CaixaController       caixaController       = new CaixaController();
    static VendaController       vendaController       = new VendaController();

    static Funcionario usuarioLogado = null;
    static Caixa       caixaAtual   = null;

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║     SISTEMA DE LOJA - LP1    ║");
        System.out.println("╚══════════════════════════════╝");

        if (!fazerLogin()) {
            System.out.println("Sistema encerrado."); return;
        }

        caixaAtual = caixaController.buscarCaixaAberto();

        boolean rodando = true;
        while (rodando) {
            exibirMenuPrincipal();
            int opcao = lerInt("Opção: ");
            switch (opcao) {
                case 1  -> menuProdutos();
                case 2  -> menuClientes();
                case 3  -> menuVendas();
                case 4  -> menuCaixa();
                case 5  -> menuFuncionarios();
                case 6  -> menuRelatorios();
                case 0  -> rodando = false;
                default -> System.out.println("Opção inválida.");
            }
        }
        System.out.println("Sistema encerrado. Até logo!");
    }

    static boolean fazerLogin() {
        System.out.print("\nLogin: ");
        String login = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        usuarioLogado = funcionarioController.autenticar(login, senha);
        if (usuarioLogado == null) {
            System.out.println("Login ou senha incorretos!"); return false;
        }
        System.out.println("\nBem-vindo(a), " + usuarioLogado.getNome() + "! [" + usuarioLogado.getNivelAcesso() + "]");
        return true;
    }

    static void exibirMenuPrincipal() {
        String statusCaixa = caixaAtual != null ? "ABERTO (ID " + caixaAtual.getId() + ")" : "FECHADO";
        System.out.println("\n══════════════════════════════");
        System.out.println("  Usuário : " + usuarioLogado.getNome());
        System.out.println("  Caixa   : " + statusCaixa);
        System.out.println("══════════════════════════════");
        System.out.println("  1. Produtos");
        System.out.println("  2. Clientes");
        System.out.println("  3. Vendas");
        System.out.println("  4. Caixa");
        if (usuarioLogado.getNivelAcesso() == NivelAcesso.ADMIN ||
            usuarioLogado.getNivelAcesso() == NivelAcesso.GERENTE) {
            System.out.println("  5. Funcionários");
            System.out.println("  6. Relatórios");
        }
        System.out.println("  0. Sair");
    }

    static void menuProdutos() {
        System.out.println("\n-- PRODUTOS --");
        System.out.println("1. Listar  2. Cadastrar  3. Editar  4. Remover  0. Voltar");
        int op = lerInt("Opção: ");
        switch (op) {
            case 1 -> {
                List<Produto> produtos = produtoController.listar();
                if (produtos.isEmpty()) System.out.println("Nenhum produto cadastrado.");
                else produtos.forEach(System.out::println);
            }
            case 2 -> {
                System.out.print("Nome: "); String nome = sc.nextLine();
                System.out.print("Descrição: "); String desc = sc.nextLine();
                double custo   = lerDouble("Preço de custo: R$ ");
                double venda   = lerDouble("Preço de venda: R$ ");
                int    estoque = lerInt("Quantidade em estoque: ");
                int    minimo  = lerInt("Estoque mínimo: ");
                produtoController.cadastrar(new Produto(nome, desc, custo, venda, estoque, minimo));
            }
            case 3 -> {
                int id = lerInt("ID do produto: ");
                Produto p = produtoController.buscarPorId(id);
                if (p == null) { System.out.println("Produto não encontrado."); break; }
                System.out.println("Atual: " + p);
                System.out.print("Novo nome (ENTER mantém): "); String nome = sc.nextLine();
                if (!nome.isBlank()) p.setNome(nome);
                double venda = lerDouble("Novo preço de venda (0 mantém): ");
                if (venda > 0) p.setPrecoVenda(venda);
                int estoque = lerInt("Nova qtd estoque (-1 mantém): ");
                if (estoque >= 0) p.setQuantidadeEstoque(estoque);
                produtoController.atualizar(p);
            }
            case 4 -> { int id = lerInt("ID do produto: "); produtoController.deletar(id); }
        }
    }

    static void menuClientes() {
        System.out.println("\n-- CLIENTES --");
        System.out.println("1. Listar  2. Cadastrar  3. Editar  4. Remover  0. Voltar");
        int op = lerInt("Opção: ");
        switch (op) {
            case 1 -> {
                List<Cliente> clientes = clienteController.listar();
                if (clientes.isEmpty()) System.out.println("Nenhum cliente cadastrado.");
                else clientes.forEach(System.out::println);
            }
            case 2 -> {
                System.out.print("Nome: ");     String nome  = sc.nextLine();
                System.out.print("CPF: ");      String cpf   = sc.nextLine();
                System.out.print("Telefone: "); String tel   = sc.nextLine();
                System.out.print("Email: ");    String email = sc.nextLine();
                clienteController.cadastrar(new Cliente(nome, cpf, tel, email));
            }
            case 3 -> {
                int id = lerInt("ID do cliente: ");
                Cliente c = clienteController.buscarPorId(id);
                if (c == null) { System.out.println("Cliente não encontrado."); break; }
                System.out.print("Novo nome (ENTER mantém): "); String nome = sc.nextLine();
                if (!nome.isBlank()) c.setNome(nome);
                System.out.print("Novo telefone (ENTER mantém): "); String tel = sc.nextLine();
                if (!tel.isBlank()) c.setTelefone(tel);
                clienteController.atualizar(c);
            }
            case 4 -> { int id = lerInt("ID do cliente: "); clienteController.deletar(id); }
        }
    }

    static void menuVendas() {
        System.out.println("\n-- VENDAS --");
        System.out.println("1. Nova venda  2. Histórico  0. Voltar");
        int op = lerInt("Opção: ");
        switch (op) {
            case 1 -> realizarVenda();
            case 2 -> {
                List<Venda> vendas = vendaController.listar();
                if (vendas.isEmpty()) System.out.println("Nenhuma venda registrada.");
                else vendas.forEach(System.out::println);
            }
        }
    }

    static void realizarVenda() {
        if (caixaAtual == null) {
            System.out.println("Abra o caixa antes de realizar vendas!"); return;
        }
        Venda venda = vendaController.iniciarVenda(usuarioLogado.getId(), caixaAtual.getId());

        System.out.print("Informar cliente? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) {
            int idCliente = lerInt("ID do cliente: ");
            Cliente c = clienteController.buscarPorId(idCliente);
            if (c != null) { venda.setIdCliente(c.getId()); venda.setNomeCliente(c.getNome()); }
            else System.out.println("Cliente não encontrado, venda sem cliente.");
        }

        boolean adicionando = true;
        while (adicionando) {
            produtoController.listar().forEach(System.out::println);
            int idProduto = lerInt("ID do produto (0 para finalizar): ");
            if (idProduto == 0) break;
            Produto p = produtoController.buscarPorId(idProduto);
            if (p == null) { System.out.println("Produto não encontrado."); continue; }
            int qtd = lerInt("Quantidade: ");
            vendaController.adicionarItem(venda, p.getId(), p.getNome(), p.getPrecoVenda(), qtd);
            System.out.printf("Subtotal atual: R$ %.2f%n", venda.getTotal());
        }

        if (venda.getItens().isEmpty()) {
            System.out.println("Nenhum item adicionado, venda cancelada."); return;
        }

        System.out.printf("Total: R$ %.2f%n", venda.getTotal());
        System.out.print("Confirmar venda? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) vendaController.finalizarVenda(venda);
        else vendaController.cancelarVenda(venda);
    }

    static void menuCaixa() {
        System.out.println("\n-- CAIXA --");
        System.out.println("1. Abrir  2. Fechar  3. Ver status  0. Voltar");
        int op = lerInt("Opção: ");
        switch (op) {
            case 1 -> {
                double saldo = lerDouble("Saldo inicial: R$ ");
                caixaAtual = caixaController.abrir(saldo, usuarioLogado.getId());
            }
            case 2 -> {
                double saldo = lerDouble("Saldo final: R$ ");
                caixaController.fechar(saldo);
                caixaAtual = null;
            }
            case 3 -> {
                Caixa c = caixaController.buscarCaixaAberto();
                if (c == null) System.out.println("Nenhum caixa aberto.");
                else System.out.println(c);
            }
        }
    }

    static void menuFuncionarios() {
        if (usuarioLogado.getNivelAcesso() != NivelAcesso.ADMIN) {
            System.out.println("Acesso negado!"); return;
        }
        System.out.println("\n-- FUNCIONÁRIOS --");
        System.out.println("1. Listar  2. Cadastrar  3. Remover  0. Voltar");
        int op = lerInt("Opção: ");
        switch (op) {
            case 1 -> {
                List<Funcionario> lista = funcionarioController.listar();
                if (lista.isEmpty()) System.out.println("Nenhum funcionário.");
                else lista.forEach(System.out::println);
            }
            case 2 -> {
                System.out.print("Nome: ");  String nome  = sc.nextLine();
                System.out.print("Login: "); String login = sc.nextLine();
                System.out.print("Senha: "); String senha = sc.nextLine();
                System.out.println("Nível: 1-ADMIN  2-GERENTE  3-CAIXA");
                int nivel = lerInt("Nível: ");
                NivelAcesso na = switch (nivel) {
                    case 1 -> NivelAcesso.ADMIN;
                    case 2 -> NivelAcesso.GERENTE;
                    default -> NivelAcesso.CAIXA;
                };
                funcionarioController.cadastrar(new Funcionario(nome, login, senha, na));
            }
            case 3 -> { int id = lerInt("ID do funcionário: "); funcionarioController.deletar(id); }
        }
    }

    static void menuRelatorios() {
        if (usuarioLogado.getNivelAcesso() == NivelAcesso.CAIXA) {
            System.out.println("Acesso negado!"); return;
        }
        System.out.println("\n-- RELATÓRIOS --");
        System.out.println("1. Estoque baixo  2. Vendas do dia  3. Histórico de caixas  0. Voltar");
        int op = lerInt("Opção: ");
        switch (op) {
            case 1 -> {
                List<Produto> lista = produtoController.listarEstoqueBaixo();
                if (lista.isEmpty()) System.out.println("Nenhum produto com estoque baixo.");
                else { System.out.println("== PRODUTOS COM ESTOQUE BAIXO =="); lista.forEach(System.out::println); }
            }
            case 2 -> {
                System.out.print("Data (AAAA-MM-DD) ou ENTER para hoje: ");
                String data = sc.nextLine();
                if (data.isBlank()) data = java.time.LocalDate.now().toString();
                List<Venda> vendas = vendaController.listarPorData(data);
                if (vendas.isEmpty()) System.out.println("Nenhuma venda em " + data);
                else {
                    double total = vendas.stream().mapToDouble(Venda::getTotal).sum();
                    vendas.forEach(System.out::println);
                    System.out.printf("Total do dia: R$ %.2f (%d vendas)%n", total, vendas.size());
                }
            }
            case 3 -> {
                List<Caixa> caixas = caixaController.listar();
                if (caixas.isEmpty()) System.out.println("Nenhum caixa registrado.");
                else caixas.forEach(System.out::println);
            }
        }
    }

    static int lerInt(String msg) {
        while (true) {
            System.out.print(msg);
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Digite um número válido."); }
        }
    }

    static double lerDouble(String msg) {
        while (true) {
            System.out.print(msg);
            try { return Double.parseDouble(sc.nextLine().trim().replace(",", ".")); }
            catch (NumberFormatException e) { System.out.println("Digite um valor válido."); }
        }
    }
}
