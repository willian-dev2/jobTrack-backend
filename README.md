# JobTrack

API REST para organizar candidaturas a vagas de emprego. Cada usuário registra as vagas em que se candidatou e acompanha o status de cada uma: enviada, entrevista, oferta ou rejeitada.

## Tecnologias

- **Java 17** e **Spring Boot 4**
- **Spring Security** + **JWT** (jjwt)
- **Spring Data JPA** + **PostgreSQL**
- **Lombok**
- **Gradle**

## Funcionalidades

- Cadastro e login de usuários com senha criptografada (BCrypt) e token JWT
- Registro de candidaturas vinculadas ao usuário autenticado (o e-mail é extraído do token)
- Data da candidatura gravada automaticamente
- Bloqueio de candidatura duplicada para a mesma vaga na mesma empresa
- Atualização de status com validação: só o dono da candidatura pode alterá-la, e o status precisa ser um valor válido
- Cada usuário vê apenas as próprias candidaturas
- Tratamento global de exceções

### Status de uma candidatura

| Status | Significado |
|---|---|
| `APPLIED` | Candidatura enviada (status inicial) |
| `INTERVIEW` | Entrevista marcada |
| `OFFER` | Oferta recebida |
| `REJECTED` | Candidatura rejeitada |

## Arquitetura

API em camadas: `controller` → `business/service` → `repository`, com DTOs e conversores separados das entidades.

```
src/main/java/com/vinicius/jobTrack
├── controller/          → endpoints REST
├── business/
│   ├── dtos/            → objetos de entrada e saída
│   ├── converter/       → conversão entre entidades e DTOs
│   └── service/         → regras de negócio
├── model/               → entidades JPA (Userr, Job) e enum de status
├── repository/          → Spring Data JPA
├── config/              → Spring Security, filtro e utilitário JWT
└── exception/           → exceções e handler global
```

Um usuário tem várias candidaturas (relacionamento um-para-muitos entre `Userr` e `Job`).

## Como rodar

### Pré-requisitos

- Java 17+
- PostgreSQL rodando localmente

### 1. Criar o banco

```sql
CREATE DATABASE "JobTrack";
```

### 2. Configurar a conexão

Ajuste usuário e senha do seu PostgreSQL em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/JobTrack
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

As tabelas são criadas automaticamente na primeira execução.

### 3. Rodar

```bash
./gradlew bootRun
```

A API sobe em `http://localhost:8085`.

## Endpoints

| Método | Rota | Autenticação | Descrição |
|---|---|---|---|
| POST | `/usuario` | Não | Cadastra um usuário |
| POST | `/usuario/auth/login` | Não | Faz login e retorna o token JWT |
| GET | `/usuario` | Sim | Lista os usuários |
| DELETE | `/usuario/deleteUser/{email}` | Sim | Remove um usuário |
| POST | `/usuario/jobs` | Sim | Registra uma candidatura |
| GET | `/usuario/candidaturas` | Sim | Lista as candidaturas do usuário autenticado |
| PATCH | `/usuario/jobs/{id}/status` | Sim | Atualiza o status de uma candidatura |
| DELETE | `/usuario/deleteJob/{companyName}/{jobTitle}` | Sim | Remove uma candidatura |

Nas rotas autenticadas, envie o token no header:

```
Authorization: Bearer <token>
```

### Exemplo: cadastro e login

```http
POST /usuario
Content-Type: application/json

{ "name": "Seu nome", "email": "voce@exemplo.com", "password": "sua_senha" }
```

```http
POST /usuario/auth/login
Content-Type: application/json

{ "email": "voce@exemplo.com", "password": "sua_senha" }
```

### Exemplo: registrar uma candidatura

```http
POST /usuario/jobs
Authorization: Bearer <token>
Content-Type: application/json

{
  "companyName": "Empresa X",
  "jobTitle": "Desenvolvedor Back-end Júnior"
}
```

### Exemplo: atualizar o status

```http
PATCH /usuario/jobs/1/status
Authorization: Bearer <token>
Content-Type: application/json

{ "status": "INTERVIEW" }
```

## Autor

**Willian Vinicius Sousa do Nascimento**

- GitHub: [willian-dev2](https://github.com/willian-dev2)
- LinkedIn: [willian-vinicius](https://linkedin.com/in/willian-vinicius-3128b7363)
