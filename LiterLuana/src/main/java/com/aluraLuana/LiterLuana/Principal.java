package com.alura.LiterAlura;

import com.alura.LiterAlura.dto.DadosAutor;
import com.alura.LiterAlura.dto.DadosLivro;
import com.alura.LiterAlura.dto.ResultadoBusca;
import com.alura.LiterAlura.entidade.Autor;
import com.alura.LiterAlura.entidade.Livro;
import com.alura.LiterAlura.repositorio.AutorRepository;
import com.alura.LiterAlura.repositorio.LivroRepository;
import com.alura.LiterAlura.servico.ConsumoApi;
import com.alura.LiterAlura.servico.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal implements CommandLineRunner {

    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AutorRepository autorRepository;

    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";

    @Override
    public void run(String... args) throws Exception {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    ***************************************************
                    1 - Buscar livro por titulo
                    2 - Listar livros registrados
                    3 - Listar autores
                    4 - Listar autores vivos em determinado ano
                    5 - Listar livros em determinado idioma
                    0 - Sair
                    ***************************************************
                    Escolha uma das opções acima:
                    """;

            System.out.println(menu);
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivosPorAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.println("Digite o nome do livro que você quer buscar:");
        var nomeLivro = sc.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));
        ResultadoBusca resultado = conversor.obterDados(json, ResultadoBusca.class);

        if (!resultado.livros().isEmpty()) {
            DadosLivro dadosLivro = resultado.livros().get(0);
            DadosAutor dadosAutor = dadosLivro.autores().get(0);

            Autor autor;
            Optional<Autor> autorExistente = autorRepository.findByNome(dadosAutor.nome());
            if (autorExistente.isPresent()) {
                autor = autorExistente.get();
            } else {
                autor = new Autor(dadosAutor.nome(), dadosAutor.anoNascimento(), dadosAutor.anoFalecimento());
                autorRepository.save(autor);
            }

            Livro livro = new Livro(dadosLivro.titulo(), autor, dadosLivro.idiomas().get(0), dadosLivro.downloads());
            livroRepository.save(livro);

            System.out.println("Livro encontrado e salvo: " + livro);
        } else {
            System.out.println("Nenhum livro com esse título foi encontrado na API.");
        }
    }

    private void listarLivrosRegistrados() {
        System.out.println("Lista de livros registrados:");
        livroRepository.findAll().forEach(System.out::println);
    }

    private void listarAutores() {
        System.out.println("Lista de autores registrados:");
        autorRepository.findAll().forEach(System.out::println);
    }

    private void listarAutoresVivosPorAno() {
        System.out.println("Digite o ano para buscar autores vivos:");
        var ano = sc.nextInt();
        sc.nextLine();

        List<Autor> autoresVivos = autorRepository.findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(ano, ano);
        autoresVivos.forEach(System.out::println);
    }

    private void listarLivrosPorIdioma() {
        System.out.println("Digite o idioma (ex: en, es, pt):");
        var idioma = sc.nextLine();

        List<Livro> livrosPorIdioma = livroRepository.findByIdioma(idioma);
        livrosPorIdioma.forEach(System.out::println);
    }
}