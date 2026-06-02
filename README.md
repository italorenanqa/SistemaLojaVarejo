# Sistema de Loja Varejo

Sistema desktop para gerenciar uma loja de varejo, feito em Java com Swing e MySQL.

## O que faz

Um sistema completo pra controlar vendas, estoque, clientes e funcionários de uma loja. Tem login com níveis de acesso diferentes (admin, gerente e vendedor), controle de caixa e tudo mais que você precisa pra tocar uma loja pequena ou média.

## Principais funcionalidades

- **Login e permissões** - Cada usuário tem seu nível de acesso
- **Produtos** - Cadastra, edita, controla estoque e avisa quando tá acabando
- **Clientes** - Cadastro completo com CPF, telefone e email
- **Caixa** - Abre com saldo inicial, fecha no final do dia
- **Vendas** - Registra tudo certinho, atualiza o estoque automaticamente
- **Relatórios** - Vendas do período, produtos mais vendidos, estoque baixo (só pra gerente e admin)

## Tecnologias usadas

- Java + Swing
- MySQL 8.0
- JDBC (MySQL Connector 8.0.33)
- Arquitetura MVC

## Como rodar

1. Clone o repositório
2. Crie o banco de dados MySQL
3. Ajuste a conexão no `ConnectionFactory.java` com seu usuário e senha
4. Rode o `Main.java` ou `LoginFrame.java`

O conector MySQL já tá incluído na pasta `lib/`.

## Estrutura

```
src/
├── controller/    - Controladores
├── model/         - Classes do domínio
├── view/          - Telas do sistema
├── service/       - Regras de negócio
├── repository/    - Acesso ao banco
└── enums/         - Enums do sistema
```
