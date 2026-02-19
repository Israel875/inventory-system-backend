# Inventory System API

Este repositório contém a API responsável pelo gerenciamento de produtos, matérias-primas e cálculo de sugestões de produção com base no estoque disponível.  
Todo o código-fonte, nomes de classes, tabelas e colunas foram desenvolvidos **em inglês**, conforme solicitado no teste prático.

---

## 📌 Funcionalidades

### ✔ CRUD de Produtos
- Cadastro, edição, listagem e exclusão de produtos.
- Cada produto possui:
  - `id`
  - `name`
  - `price`

### ✔ CRUD de Matérias-Primas
- Cadastro, edição, listagem e exclusão de matérias-primas.
- Cada matéria-prima possui:
  - `id`
  - `name`
  - `quantityInStock`

### ✔ Associação Produto ↔ Matéria-Prima
- Define quais matérias-primas compõem cada produto.
- Registra a quantidade necessária de cada matéria-prima para produzir uma unidade do produto.

### ✔ Sugestão de Produção
- Calcula quantas unidades de cada produto podem ser produzidas com o estoque atual.
- Prioriza produtos de **maior valor**, conforme exigido.
- Retorna:
  - nome do produto
  - quantidade possível de produzir
  - valor total gerado

---

## 🛠 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL**
- **Maven**

---

## 📂 Estrutura do Projeto
src/ └── main/ ├── java/com/inventory │    ├── controller │    ├── service │    ├── repository │    ├── model │    └── dto └── resources/ ├── application.properties └── schema.sql (se utilizado)
## ⚙️ Como Executar o Projeto

1. Clone o repositório
```bash
git clone https://github.com/Israel875/inventory-system-backend.git

Configure o banco de dados
Crie o banco no PostgreSQL:
CREATE DATABASE inventory;

Edite o arquivo application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/inventory
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

Execute a aplicação
Via Maven:
mvn spring-boot:run Ou diretamente pelo IntelliJ.

Testes (Opcional)
O projeto pode ser expandido com testes:
- Unitários (JUnit, Mockito)
- Integração


Observação
Este projeto foi desenvolvido como parte de um teste prático técnico, seguindo todos os requisitos funcionais e não funcionais solicitados.











