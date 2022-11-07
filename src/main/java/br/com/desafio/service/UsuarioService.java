package br.com.desafio.service;

import br.com.desafio.model.entity.Usuario;
import br.com.desafio.model.enums.Sexo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class UsuarioService {

    private List<Usuario> usuarios;

    //Cria uma nova lista de cadastros.
    public Usuario save(Usuario usuario) {
        if (usuarios == null) {
            usuarios = new ArrayList<>();
        }
        setarId(usuario);
        usuarios.add(usuario);
        return usuario;
    }

    public List<Usuario> findAll() {
        return this.usuarios;
    }

    //Gera um novo id para cada cadastro.
    private void setarId(Usuario usuario) {
        int id = 0;
        if (usuarios.isEmpty()) {
            id++;
            usuario.setId(id);
        } else {
            if (usuarios.get(usuarios.size() - 1) != null) {
                id = usuarios.get(usuarios.size() - 1).getId(id);
                id++;
                usuario.setId(id);
            }
        }
    }

    //Deleta um cadastro.
    public boolean delete(int id) {
        boolean find = false;
        for (int i = 0; i < usuarios.size(); i++) {
            if (id == usuarios.get(i).getId(id)) {
                find = true;
                usuarios.remove(i);
                break;
            }
        }
        return find;
    }

    //Retorna um id de cadastro da lista.
    public Usuario retornaUsuario(int id) {
        for (Usuario usuario : usuarios) {
            if (id == usuario.getId(id)) {
                return usuario;
            }
        }
        return null;
    }

    //Retorna a altera��o solicitada no cadastro.
    public Usuario insert(Usuario cadastro) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Informe o nome do Usu�rio:");
        String nome = scanner.next();
        System.out.println("Informe o CPF do Usu�rio:");
        String cpf = scanner.next();
        System.out.println("Informe o email do Usu�rio:");
        String email = scanner.next();
        LocalDate dataNascimento = null;
        do {
            try {
                System.out.println("Informe a data de nascimento do Usu�rio:");
                dataNascimento = LocalDate.parse(scanner.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (Exception e) {
                System.err.println("O Formato da data inserida est� errada. \nInsira a data com o formato correto 00/00/0000: ");
            }
        } while (dataNascimento == null);
        System.out.println("Informe o sexo do Usu�rio: ");
        Sexo sexo = scanner.next().equalsIgnoreCase("F") ? Sexo.FEMININO : Sexo.MASCULINO;
        Usuario usuario = new Usuario(nome, cpf, email, dataNascimento, sexo);
        save(usuario);
        return cadastro;
    }

    public Usuario update(Usuario atualizaUsuario) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o ID que deseja atualizar");
        int id = scanner.nextInt();
        Usuario usuarioEncontrado = retornaUsuario(id);
        int position = id - 1;
        System.out.println(usuarioEncontrado.toString());
        String user = usuarioEncontrado.toString();
        String[] userArray = user.split(",");
//        System.out.println(Arrays.toString(userArray));
//        System.out.println(userArray[4]);
        String nome = userArray[1].replace("nome=", "");
        String cpf = userArray[2].replace("cpf=", "");
        String dataNascimento = userArray[4].replace("dataNascimento=", "");
//        System.out.println(d);
        // String data = Arrays.toString(user.split("id=1, nome='1', cpf='a', email='a', dataNascimento, sexo=FEMININO"));
        //System.out.println(data);
        System.out.println("Informe o nome do Usu�rio:");
        String novoNome = scanner.nextLine();
        if (novoNome == ""){
            novoNome = nome;
        }
        scanner.nextLine();
        System.out.println("Informe o CPF do Usu�rio:");
        String novoCpf = scanner.nextLine();
        if (novoCpf == ""){
            novoCpf = cpf;
        }
        System.out.println("Informe o email do Usu�rio:");
        String novoEmail = scanner.nextLine();
        LocalDate novaDataNascimento = null;
        try {
            System.out.println("Informe a data de nascimento do Usu�rio:");
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            novaDataNascimento = LocalDate.parse(scanner.nextLine(), formatador);
            if (novaDataNascimento == null || novaDataNascimento.equals(""));
            {
                novaDataNascimento = LocalDate.from(formatador.parse(dataNascimento));
                System.out.println("Data de Nascimento n�o alterada.");
            }
        } catch (Exception e) {
            System.err.println("O Formato da data inserida est� errada. \nInsira a data com o formato correto 00/00/0000: ");
        }
        System.out.println("Informe o sexo do Usu�rio: ");
        Sexo novoSexo = scanner.nextLine().equalsIgnoreCase("F") ? Sexo.FEMININO : Sexo.MASCULINO;
        Usuario novoUsuario = new Usuario(id, novoNome, novoCpf, novoEmail, novaDataNascimento, novoSexo);
        System.out.println(novoUsuario);
        System.out.print("Voc� deseja atualizar as informa��es ? [S/N]");
        String aceite = scanner.next();
        if (aceite.equalsIgnoreCase("S")) {
            usuarios.set(position, novoUsuario);
        }
        return atualizaUsuario;
    }

}
