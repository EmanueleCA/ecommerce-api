# E-commerce API

API REST de um sistema de e-commerce simplificado, desenvolvida em Java com Spring Boot. Projeto pessoal de portfólio, construído do zero com foco em boas práticas de arquitetura e proximidade com um cenário de produção real.

> 🚧 **Projeto em desenvolvimento.** As seções abaixo refletem o estado atual e serão atualizadas conforme novas funcionalidades forem implementadas.

## Tecnologias utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Data JPA** — persistência e mapeamento objeto-relacional
- **MySQL 8.0** — banco de dados relacional
- **Docker / Docker Compose** — containerização do banco de dados
- **Lombok** — redução de código boilerplate
- **Maven** — gerenciamento de dependências
- **Postman** — testes manuais de endpoints

## Arquitetura

O projeto segue uma arquitetura em camadas, separando responsabilidades:

```
Controller → Service → Repository → Entity
```

- **Controller**: recebe as requisições HTTP e retorna as respostas
- **Service**: concentra a lógica de negócio
- **Repository**: interface de acesso ao banco de dados (Spring Data JPA)
- **Entity**: representa as tabelas do banco de dados

## Modelagem do domínio

Entidades planejadas para o sistema:

| Entidade | Descrição | Status |
|---|---|---|
| `Category` | Categorias de produtos | ✅ Implementado |
| `Product` | Produtos do catálogo | ✅ Implementado |
| `Customer` | Clientes | 🔜 Próxima etapa |
| `Address` | Endereços dos clientes | 🔜 Próxima etapa |
| `Order` | Pedidos | 🔜 Planejado |
| `OrderItem` | Itens de um pedido | 🔜 Planejado |

## Endpoints disponíveis

### Category

| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/categories` | Lista todas as categorias |
| GET | `/api/categories/{id}` | Busca uma categoria por id |
| POST | `/api/categories` | Cria uma nova categoria |
| PUT | `/api/categories/{id}` | Atualiza uma categoria existente |
| DELETE | `/api/categories/{id}` | Remove uma categoria |

**Exemplo de corpo (POST/PUT):**
```json
{
    "name": "Eletrônicos",
    "description": "Produtos eletrônicos em geral"
}
```

### Product

| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/products` | Lista todos os produtos |
| GET | `/api/products/{id}` | Busca um produto por id |
| POST | `/api/products` | Cria um novo produto |
| PUT | `/api/products/{id}` | Atualiza um produto existente |
| DELETE | `/api/products/{id}` | Remove um produto |

**Exemplo de corpo (POST/PUT):**
```json
{
    "name": "Notebook Dell",
    "description": "Notebook 15 polegadas",
    "price": 3500.00,
    "stockQuantity": 10,
    "active": true,
    "category": {
        "id": 1
    }
}
```

> O campo `category.id` deve corresponder a uma categoria já existente no banco de dados.

## Como rodar o projeto localmente

### Pré-requisitos

- Java 17+
- Maven
- Docker e Docker Compose

### Passo a passo

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/ecommerce-api.git
cd ecommerce-api
```

2. Suba o banco de dados MySQL via Docker:
```bash
docker compose up -d
```

3. Confirme que o container está rodando:
```bash
docker ps
```

4. Rode a aplicação Spring Boot:
```bash
mvn spring-boot:run
```

A aplicação sobe por padrão em `http://localhost:8080`.

5. Teste os endpoints com o Postman (ou outra ferramenta de sua preferência) usando as rotas listadas acima.

## Roadmap

- [x] Setup do projeto e configuração do banco de dados via Docker
- [x] CRUD de `Category`
- [x] CRUD de `Product` com relacionamento `@ManyToOne` com `Category`
- [ ] Entidades `Customer` e `Address`
- [ ] Entidades `Order` e `OrderItem`
- [ ] Regras de negócio: controle de estoque, cálculo automático do valor total do pedido
- [ ] Separação de DTOs (não expor entidades JPA diretamente na API)
- [ ] Validação de dados de entrada (Bean Validation)
- [ ] Tratamento global de exceções (`@ControllerAdvice`)
- [ ] Autenticação e autorização com Spring Security + JWT
- [ ] Documentação da API com Swagger/OpenAPI
- [ ] Testes unitários e de integração
- [ ] Dockerfile da aplicação e docker-compose completo (app + banco)

## Autor

Desenvolvido por Emanuele como projeto de estudo e portfólio.
