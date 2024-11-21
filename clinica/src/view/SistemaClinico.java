package view;

import model.entidades.Funcionario;
import java.util.ArrayList;

public class SistemaClinico {

    // Lista para armazenar os funcionários cadastrados
    private static ArrayList<Funcionario> funcionarios = new ArrayList<>();

    public static void main(String[] args) {
    	
        Funcionario funcionario1 = new Funcionario("Otavio", "2345678910", "123");
        Funcionario funcionario2 = new Funcionario("Maria", "2345678911", "abc");

        addUser(funcionario1);
        addUser(funcionario2);

        //  operações com um funcionário
        funcionario1.operacoes();

        // remove esse usuário
        removerUser("2345678910");
    }

    // Método para adicionar um funcionário à lista
    public static void addUser(Funcionario funcionario) {
        funcionarios.add(funcionario);
        System.out.println("Funcionário " + funcionario.getName() + " adicionado com sucesso.");
    }

    // Método para remover um funcionário pela CPF
    public static void removerUser(String cpf) {
        Funcionario funcionarioRemover = null;
        
        // Procurar o funcionário na lista pelo CPF
        for (Funcionario f : funcionarios) {
            if (f.getCpf().equals(cpf)) {
                funcionarioRemover = f;
                break;
            }
        }

        // Se o funcionário for encontrado, removê-lo da lista
        if (funcionarioRemover != null) {
            funcionarios.remove(funcionarioRemover);
            System.out.println("Funcionário com CPF " + cpf + " removido com sucesso.");
        } else {
            System.out.println("Funcionário não encontrado com o CPF: " + cpf);
        }
    }
}
