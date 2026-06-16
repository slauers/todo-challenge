# To-Do App — Gerenciador de Tarefas

API REST de gerenciamento de tarefas com backend em Java 21 + Spring Boot, frontend moderno em React 19 + TypeScript, e arquitetura em camadas. Pronto para produção com Docker, CI/CD e testes automatizados.

## Stack

- **Backend**: Java 21 + Spring Boot 3.5 (Web, Data JPA, Validation, Actuator)
- **Banco de dados**: PostgreSQL 16 + Flyway (migrations)
- **Frontend**: React 19 + TypeScript + Vite
- **Orquestração**: Docker Compose
- **CI/CD**: GitHub Actions

## Como executar

### Com Docker (recomendado)

```bash
docker compose up --build
```

- Frontend: http://localhost:5173
- API: http://localhost:8080/api/tasks
- Health: http://localhost:8080/actuator/health

### Localmente (sem Docker)

1. **PostgreSQL** (via Docker apenas):
   ```bash
   docker compose up postgres
   ```

2. **Backend**:
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

3. **Frontend**:
   ```bash
   cd frontend
   npm install
   npm run dev
   ```

## Endpoints REST

| Método | Path | Descrição |
|--------|------|-----------|
| POST | `/api/tasks` | Criar tarefa |
| GET | `/api/tasks` | Listar tarefas |
| GET | `/api/tasks/{id}` | Obter tarefa |
| PUT | `/api/tasks/{id}` | Atualizar tarefa |
| DELETE | `/api/tasks/{id}` | Remover tarefa |
| GET | `/actuator/health` | Health check |

**Status**: `PENDING` (Pendente), `IN_PROGRESS` (Em andamento), `DONE` (Concluída).

## Arquitetura

O backend segue uma **arquitetura em camadas**:

```
com.inter.todo
├── domain/           # Entidades e regras de negócio
│   ├── model/        # Task, TaskStatus
│   ├── repository/   # TaskRepository (interface)
│   ├── event/        # TaskCreatedEvent, TaskUpdatedEvent
│   └── exception/    # TaskNotFoundException, InvalidTaskException
├── application/      # Casos de uso (CreateTask, ListTasks, etc.)
├── infrastructure/   # Implementações (JPA, Eventos, Config)
└── api/              # Controllers, DTOs, Exception Handler
```

Cada camada tem responsabilidade clara:
- **Domain**: regras de negócio puras
- **Application**: orquestração dos casos de uso
- **Infrastructure**: persistência e eventos
- **API**: exposição HTTP e validação

## Frontend

Interface com layout em duas colunas:
- **Coluna esquerda**: formulário de criação de tarefas (sticky)
- **Coluna direita**: lista com filtros por status (Todas / Pendente / Em andamento / Concluída)
- **Design**: identidade visual inspirada no Inter (laranja `#FF7A00`, fonte Inter)

## Testes

```bash
cd backend
./mvnw test
```

Testes unitários do domain (Task) e testes com mocks do serviço (TaskService). Executados automaticamente no CI/CD.

## Decisões técnicas

1. **Arquitetura em camadas (DDD)**: separação clara entre domain (regras de negócio), application (orquestração), infrastructure (persistência) e api (HTTP). Facilita testes, evolução e manutenção.

2. **Event-driven com @EventListener + @Async**: eventos publicados no service e processados em thread separada. Desacoplamento natural sem complexidade de Kafka — suficiente para o escopo.

3. **UUID no domínio**: `Task.create()` gera ID, não depende do banco. Permite testes sem persistência.

4. **TaskService consolidado**: uma classe com toda lógica CRUD. Pragmático para escopo simples; em crescimento, separaria em use cases.

5. **Flyway para migrations**: schema versionado como source of truth; Hibernate em modo `validate`.

## Evolução em Produção

A solução atual cumpre todos os requisitos do desafio. Para uma **aplicação em produção**, as seguintes extensões seriam naturais:

| Aspecto | Próximo passo |
|---------|---------------|
| **CORS** | Mover `@CrossOrigin` para `CorsConfig` dedicada com políticas por ambiente |
| **Paginação** | Adicionar `Page<TaskResponse>` com `Pageable` em `GET /api/tasks` |
| **Testes** | Expandir para E2E, integração e mutation testing |
| **Autenticação** | OAuth2 / JWT + Spring Security |
| **Caching** | Redis para listagens frequentes |
| **Observabilidade** | Distributed tracing (OpenTelemetry) + correlation IDs |
| **Persistência em escala** | CQRS ou event sourcing para read-heavy workloads |
| **Rate limiting** | Redis + Spring Cloud Gateway |

**Princípio aplicado**: YAGNI (You Aren't Gonna Need It) — implementar apenas o necessário, mantendo arquitetura extensível.

## Como escalar

- **Eventos → Kafka**: publicador adapter desacoplado
- **API horizontal**: stateless, pronta para múltiplas instâncias
- **Paginação**: parâmetros `page`/`size` no `GET /api/tasks`
- **Cache**: Redis ou `@Cacheable` para leitura intensiva
- **Observabilidade**: Prometheus + OpenTelemetry

## Uso de IA

IA (Claude Code — Anthropic) utilizada para acelerar escrita de controllers, services, componentes React, configurações Docker/GitHub Actions e boilerplate.

## Notas

- **Docker Compose**: toda a stack (backend, frontend, PostgreSQL) sobe com um comando
- **CI/CD**: GitHub Actions executa build, testes em cada push
- **CORS**: configurado via `@CrossOrigin` no controller para localhost:5173
- **Logging**: JSON estruturado via Logstash Logback Encoder
