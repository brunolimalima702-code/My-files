# Sistema de Oficina Mecanica

Aplicacao Java com CRUD para **Cliente** e **Ordem de Servico**, integracao com banco de dados **MySQL** via **JDBC** e consumo da API publica **ViaCEP** para buscar endereco pelo CEP.

## Arquitetura

```text
oficina-app/
|-- src/main/java/com/oficina/
|   |-- Main.java
|   |-- api/
|   |   `-- ViaCepService.java
|   |-- dao/
|   |   |-- ClienteDAO.java
|   |   |-- ConexaoDB.java
|   |   `-- OrdemServicoDAO.java
|   `-- model/
|       |-- Cliente.java
|       `-- OrdemServico.java
|-- .env.example
|-- .gitignore
|-- pom.xml
|-- README.md
`-- script.sql
```

## Tecnologias e Dependencias

- Java 17+
- Maven
- MySQL Connector J 8.3.0
- Dotenv-java 3.0.0
- Gson 2.10.1

## Funcionalidades

- Cadastrar cliente com endereco buscado pelo ViaCEP
- Listar clientes
- Deletar clientes
- Cadastrar ordem de servico vinculada a um cliente
- Listar ordens de servico
- Deletar ordens de servico

## Como rodar o projeto

1. Execute o script `script.sql` no MySQL para criar o banco `oficina_db` e as tabelas necessarias.
2. Crie um arquivo `.env` na raiz do projeto usando o `.env.example` como modelo.
3. Preencha suas credenciais do MySQL no `.env`.
4. Compile o projeto com:

```bash
mvn clean package
```

5. Execute a aplicacao com:

```bash
mvn exec:java
```

Ao cadastrar um cliente, informe o CEP e o sistema buscara automaticamente o endereco na API ViaCEP.

## Observacao

O arquivo `.env` fica no `.gitignore` e nao deve ser enviado ao GitHub, pois contem dados de acesso ao banco.
