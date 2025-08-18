package com.alura.LiterAlura.servico;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}