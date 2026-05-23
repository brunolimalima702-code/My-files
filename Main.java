package com.oficina;

import com.oficina.api.ViaCepService;
import com.oficina.dao.ClienteDAO;
import com.oficina.dao.OrdemServicoDAO;
import com.oficina.model.Cliente;
import com.oficina.model.OrdemServico;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            ClienteDAO clienteDAO = new ClienteDAO();
            OrdemServicoDAO osDAO = new OrdemServicoDAO();
            int opcao = -1;

            while (opcao != 0) {
                exibirMenu();

                try {
                    opcao = Integer.parseInt(scanner.nextLine().trim());

                    switch (opcao) {
                        case 1:
                            cadastrarCliente(scanner, clienteDAO);
                            break;
                        case 2:
                            clienteDAO.listar().forEach(System.out::println);
                            break;
                        case 3:
                            System.out.print("ID do cliente para deletar: ");
                            int idClienteDel = Integer.parseInt(scanner.nextLine().trim());
                            clienteDAO.deletar(idClienteDel);
                            break;
                        case 4:
                            cadastrarOrdemServico(scanner, osDAO);
                            break;
                        case 5:
                            osDAO.listar().forEach(System.out::println);
                            break;
                        case 6:
                            System.out.print("ID da OS para deletar: ");
                            int idOsDel = Integer.parseInt(scanner.nextLine().trim());
                            osDAO.deletar(idOsDel);
                            break;
                        case 0:
                            System.out.println("Saindo...");
                            break;
                        default:
                            System.out.println("Opcao invalida.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Erro: informe um numero valido.");
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\n=== SISTEMA DE OFICINA ===");
        System.out.println("1. Cadastrar cliente (ViaCEP)");
        System.out.println("2. Listar clientes");
        System.out.println("3. Deletar cliente");
        System.out.println("4. Cadastrar ordem de servico");
        System.out.println("5. Listar ordens de servico");
        System.out.println("6. Deletar ordem de servico");
        System.out.println("0. Sair");
        System.out.print("Escolha: ");
    }

    private static void cadastrarCliente(Scanner scanner, ClienteDAO clienteDAO) throws Exception {
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine().trim();

        if (nome.isBlank()) {
            throw new IllegalArgumentException("o nome do cliente e obrigatorio.");
        }

        System.out.print("Digite o CEP (apenas numeros): ");
        String cep = scanner.nextLine().trim();

        Cliente clienteViaCep = ViaCepService.buscarEnderecoPorCep(cep);
        clienteViaCep.setNome(nome);
        clienteViaCep.setCep(cep.replaceAll("\\D", ""));

        clienteDAO.salvar(clienteViaCep);
    }

    private static void cadastrarOrdemServico(Scanner scanner, OrdemServicoDAO osDAO) {
        System.out.print("Descricao do servico: ");
        String descricao = scanner.nextLine().trim();

        if (descricao.isBlank()) {
            throw new IllegalArgumentException("a descricao do servico e obrigatoria.");
        }

        System.out.print("Valor (Ex: 150.50): ");
        double valor = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));

        if (valor <= 0) {
            throw new IllegalArgumentException("o valor deve ser maior que zero.");
        }

        System.out.print("ID do cliente: ");
        int idCliente = Integer.parseInt(scanner.nextLine().trim());

        osDAO.salvar(new OrdemServico(descricao, valor, idCliente));
    }
}
