LiterAlura
Descrição do Projeto
LiterAlura é uma aplicação de console construída com Spring Boot para gerenciar uma biblioteca de livros. O projeto permite buscar livros de uma API externa, registrar no banco de dados, e listar os livros e autores salvos.

A aplicação utiliza a API Gutendex para buscar dados de mais de 70 mil livros da biblioteca online e gratuita do Projeto Gutenberg. Os dados obtidos são persistidos em um banco de dados PostgreSQL para futuras consultas.

Funcionalidades
O menu de console oferece as seguintes opções:

Buscar livro por título: Busca e salva um livro da API Gutendex no banco de dados.

Listar livros registrados: Exibe todos os livros que foram salvos no banco de dados.

Listar autores: Exibe todos os autores que foram salvos no banco de dados.

Listar autores vivos em determinado ano: Filtra e exibe autores que estavam vivos em um ano específico.

Listar livros em determinado idioma: Filtra e exibe livros por um idioma específico (ex: en para inglês, pt para português).

Tecnologias Utilizadas
Java 17: Linguagem de programação.

Spring Boot: Framework para desenvolvimento rápido da aplicação.

Spring Data JPA: Para a persistência de dados.

PostgreSQL: Banco de dados relacional.

Maven: Ferramenta para gerenciamento de dependências.

Gutendex API: API externa para obtenção de dados de livros.

Como Rodar o Projeto
Pré-requisitos
Certifique-se de ter as seguintes ferramentas instaladas:

Java Development Kit (JDK) 17 ou superior.

Maven.

PostgreSQL.
