# Plano de Implementação — Desafio Técnico Java Sênior (Inter)

> Como usar este documento: cada tarefa tem um ID, descrição objetiva, arquivos a criar/editar,
> dependências (IDs que precisam estar concluídos antes) e uma classificação de complexidade
> (🟢 Baixa / 🟡 Média / 🔴 Alta) para ajudar a escolher o modelo de IA que vai executar.
> Sugestão de mapeamento: 🟢 → modelo rápido/barato (ex.: Haiku); 🟡 → modelo intermediário
> (ex.: Sonnet); 🔴 → modelo mais avançado (ex.: Opus), especialmente para tarefas que definem
> contratos usados por outras trilhas.

## Protocolo de retomada (se a sessão cair/limite de tokens estourar)

1. Rode `git status` e `git log --oneline` para ver o que está commitado.
2. Olhe os checkboxes abaixo: eles são a fonte de verdade do progresso (marcar IMEDIATAMENTE ao concluir cada tarefa, nunca em lote).
3. Re-dispare do zero as trilhas com tarefas não marcadas (os prompts são idempotentes: "execute as tarefas não marcadas da sua trilha conforme PLAN.md e CONTRACTS.md").
4. Siga a ordem de ondas: Fase 0 (sequencial) → Onda 1a (trilhas A, B, C, E, F, H em paralelo) → Onda 1b (D e G, após A) → Fase 2 (sequencial).
5. Modelos sugeridos: Fase 0/2 = modelo top; A, D, G, H = Sonnet; B, C, E, F = Haiku.

## Status / Progress Tracker

> Marque `[x]` quando a tarefa estiver concluída e validada. Use isso para saber rapidamente
> o que falta sem precisar reler as tabelas detalhadas abaixo.

### Fase 0 — Fundação (bloqueante)
- [x] F0.0 — `git init` + commit inicial (init feito; commit ao final da Fase 0)
- [x] F0.1 — Estrutura de diretórios raiz, `.gitignore`, `README.md` esqueleto
- [x] F0.2 — Esqueleto Maven backend (Spring Boot 3.5.15, Java 21, `pom.xml` com logstash-encoder, `TodoApplication`, `application.yml` COMPLETO — cobre B6/C4/D6)
- [x] F0.3 — Contratos de domínio (`Task`, `TaskStatus`, `TaskRepository`, eventos, exceções — escritos COMPLETOS, cobrem A1–A5)
- [x] F0.4 — Contratos de API (DTOs completos com validação — cobrem D1/D4)
- [x] F0.5 — Esqueleto Vite+React+TS (`npm install` ok)
- [x] F0.6 — Esqueletos `docker-compose.yml` (postgres pronto) e `ci.yml` (jobs prontos)
- [x] F0.7 — `CONTRACTS.md`
- [x] Critério de saída: `./mvnw compile` OK e `npm install` OK

### Trilha A — Domain + Application
- [x] A1 — `Task.java` (feito na Fase 0 como contrato completo)
- [x] A2 — `TaskStatus.java` (feito na Fase 0)
- [x] A3 — Exceções de domínio (feito na Fase 0)
- [x] A4 — `TaskRepository` (porta) (feito na Fase 0)
- [x] A5 — Eventos `TaskCreatedEvent` / `TaskUpdatedEvent` (feito na Fase 0)
- [ ] A6 — `CreateTaskUseCase`
- [ ] A7 — `ListTasksUseCase`
- [ ] A8 — `GetTaskUseCase`
- [ ] A9 — `UpdateTaskUseCase`
- [ ] A10 — `DeleteTaskUseCase`

### Trilha B — Persistência
- [ ] B1 — Migration `V1__create_tasks_table.sql`
- [ ] B2 — `TaskJpaEntity`
- [ ] B3 — `TaskJpaRepository`
- [ ] B4 — `TaskMapper`
- [ ] B5 — `TaskRepositoryAdapter`
- [x] B6 — Config datasource/JPA/Flyway em `application.yml` (feito na Fase 0 — NÃO editar o yml)

### Trilha C — Eventos e Logging
- [ ] C1 — `AsyncConfig`
- [ ] C2 — `TaskEventListener`
- [ ] C3 — `logback-spring.xml` (JSON)
- [x] C4 — Config de logging em `application.yml` (feito na Fase 0 — NÃO editar o yml)

### Trilha D — API/Controller
- [x] D1 — DTOs `TaskRequest`/`UpdateTaskRequest`/`TaskResponse` (feito na Fase 0)
- [ ] D2 — `TaskDtoMapper`
- [ ] D3 — `TaskController` (5 endpoints)
- [x] D4 — `ApiError` (feito na Fase 0)
- [ ] D5 — `GlobalExceptionHandler`
- [x] D6 — Config Actuator (feito na Fase 0 — NÃO editar o yml)
- [ ] D7 — `CorsConfig`

### Trilha E — Frontend
- [ ] E1 — `types/task.ts`
- [ ] E2 — `api/taskApi.ts`
- [ ] E3 — `TaskForm.tsx`
- [ ] E4 — `TaskItem.tsx`
- [ ] E5 — `TaskList.tsx`
- [ ] E6 — `App.tsx`
- [ ] E7 — Estilização básica
- [ ] E8 — `.env` + `vite.config.ts`

### Trilha F — DevOps
- [ ] F1 — `backend/Dockerfile`
- [ ] F2 — `frontend/Dockerfile` (+ nginx)
- [ ] F3 — `docker-compose.yml` completo
- [ ] F4 — `.github/workflows/ci.yml` completo
- [ ] F5 — `.env.example`

### Trilha G — Testes
- [ ] G1 — `CreateTaskUseCaseTest`
- [ ] G2 — `UpdateTaskUseCaseTest` / `DeleteTaskUseCaseTest`
- [ ] G3 — `TaskControllerTest`
- [ ] G4 (opcional) — Teste frontend `TaskForm.test.tsx`

### Trilha H — Documentação
- [ ] H1 — README: visão geral/stack/estrutura
- [ ] H2 — README: instruções de execução
- [ ] H3 — README: decisões técnicas
- [ ] H4 — README: seção "Uso de IA"
- [ ] H5 — README: estratégias de escalabilidade (diferencial)

### Fase 2 — Integração final (bloqueante no fim)
- [ ] I1 — Revisão de injeção de dependências
- [ ] I2 — `mvn test` passando
- [ ] I3 — `docker compose up` funcional
- [ ] I4 — Smoke test ponta a ponta
- [ ] I5 — Publicar repo no GitHub + pipeline Actions validado
- [ ] I6 — Revisão final do README
- [ ] I7 — Histórico de commits limpo (commits incrementais por trilha)

---

## Decisões já tomadas

- **Backend**: Java 17 + Spring Boot 3.x + Maven
- **Persistência**: PostgreSQL + Flyway
- **Arquitetura**: camadas `domain`, `application`, `infrastructure`, `api` (hexagonal leve)
- **Event-driven**: evento de domínio in-process via `ApplicationEventPublisher` +
  `@EventListener` assíncrono, com handler que apenas faz **log estruturado**
  (sem Kafka/Redpanda — decisão documentada no README como simplificação consciente)
- **Frontend**: React + TypeScript + Vite
- **Execução**: Docker Compose (Postgres + backend + frontend)
- **Qualidade**: logs JSON estruturados, Actuator `/actuator/health`, testes JUnit5+Mockito
  + MockMvc
- **CI/CD**: GitHub Actions (build + test backend e frontend)
- **Pacote Java raiz**: `com.inter.todo`
- **Status enum**: `PENDING` (Pendente), `IN_PROGRESS` (Em andamento), `DONE` (Concluído)
- **ID da tarefa**: UUID

## Estrutura de diretórios alvo

```
to-do/
├── PLAN.md
├── README.md
├── .gitignore
├── docker-compose.yml
├── .github/workflows/ci.yml
├── backend/
│   ├── pom.xml
│   ├── Dockerfile
│   └── src/
│       ├── main/java/com/inter/todo/
│       │   ├── TodoApplication.java
│       │   ├── domain/
│       │   │   ├── model/Task.java
│       │   │   ├── model/TaskStatus.java
│       │   │   ├── event/TaskCreatedEvent.java
│       │   │   ├── event/TaskUpdatedEvent.java
│       │   │   ├── exception/TaskNotFoundException.java
│       │   │   ├── exception/InvalidTaskException.java
│       │   │   └── repository/TaskRepository.java
│       │   ├── application/
│       │   │   └── usecase/
│       │   │       ├── CreateTaskUseCase.java
│       │   │       ├── ListTasksUseCase.java
│       │   │       ├── GetTaskUseCase.java
│       │   │       ├── UpdateTaskUseCase.java
│       │   │       └── DeleteTaskUseCase.java
│       │   ├── infrastructure/
│       │   │   ├── persistence/TaskJpaEntity.java
│       │   │   ├── persistence/TaskJpaRepository.java
│       │   │   ├── persistence/TaskRepositoryAdapter.java
│       │   │   ├── persistence/TaskMapper.java
│       │   │   ├── event/TaskEventListener.java
│       │   │   └── config/AsyncConfig.java
│       │   └── api/
│       │       ├── controller/TaskController.java
│       │       ├── dto/TaskRequest.java
│       │       ├── dto/UpdateTaskRequest.java
│       │       ├── dto/TaskResponse.java
│       │       ├── dto/ApiError.java
│       │       ├── exception/GlobalExceptionHandler.java
│       │       └── config/CorsConfig.java
│       ├── main/resources/
│       │   ├── application.yml
│       │   ├── logback-spring.xml
│       │   └── db/migration/V1__create_tasks_table.sql
│       └── test/java/com/inter/todo/
│           ├── application/usecase/*Test.java
│           └── api/controller/TaskControllerTest.java
└── frontend/
    ├── package.json / vite.config.ts / tsconfig.json / index.html
    ├── Dockerfile
    └── src/
        ├── main.tsx / App.tsx
        ├── types/task.ts
        ├── api/taskApi.ts
        └── components/{TaskList,TaskForm,TaskItem}.tsx
```

---

## FASE 0 — Fundação e Contratos (sequencial, 1 agente, BLOQUEANTE)

Tudo na Fase 1 depende disso. Recomendado executar com modelo 🔴 (Opus/Sonnet avançado),
pois define contratos que todas as trilhas vão seguir — inconsistência aqui gera retrabalho.

| ID | Tarefa | Arquivos | Depende de | Complexidade |
|----|--------|----------|------------|--------------|
| F0.0 | `git init` no diretório raiz + commit inicial (a entrega exige repositório Git; commits incrementais por trilha ao longo do projeto) | `.git/` | - | 🟢 |
| F0.1 | Criar estrutura de diretórios raiz, `.gitignore`, `README.md` (esqueleto com seções vazias) | `/`, `.gitignore`, `README.md` | F0.0 | 🟢 |
| F0.2 | Esqueleto Maven do backend: `pom.xml` com dependências (web, data-jpa, validation, actuator, postgresql, flyway-core, logstash-logback-encoder, lombok opcional, spring-boot-starter-test), classe `TodoApplication.java`, `application.yml` mínimo | `backend/pom.xml`, `backend/src/main/java/com/inter/todo/TodoApplication.java`, `backend/src/main/resources/application.yml` | F0.1 | 🟢 |
| F0.3 | Contratos de domínio: `Task.java` (campos: id UUID, title, description, status, createdAt + métodos `create()`/`update()`), `TaskStatus.java` enum, `TaskRepository.java` (porta: save, findById, findAll, deleteById, existsById), `TaskCreatedEvent.java`, `TaskUpdatedEvent.java` (records com snapshot da Task), `TaskNotFoundException.java`, `InvalidTaskException.java` | `backend/.../domain/**` | F0.2 | 🔴 |
| F0.4 | Contratos de API: `TaskRequest.java`, `UpdateTaskRequest.java`, `TaskResponse.java`, `ApiError.java` (apenas os campos/assinaturas, sem lógica) | `backend/.../api/dto/**` | F0.3 | 🟡 |
| F0.5 | Esqueleto Vite+React+TS do frontend (`package.json`, `tsconfig.json`, `vite.config.ts`, `index.html`, `src/main.tsx`, `src/App.tsx` placeholder) | `frontend/**` | F0.1 | 🟢 |
| F0.6 | Esqueletos de `docker-compose.yml` (apenas serviço `postgres`, placeholders comentados para `backend`/`frontend`) e `.github/workflows/ci.yml` (jobs `backend` e `frontend` vazios com `# TODO`) | `docker-compose.yml`, `.github/workflows/ci.yml` | F0.1 | 🟢 |
| F0.7 | `CONTRACTS.md`: documenta endpoints (método, path, request/response JSON), mapeamento de status (Pendente/Em andamento/Concluído ↔ PENDING/IN_PROGRESS/DONE), formato de evento e nome dos campos — referência para as trilhas D (API) e E (Frontend) | `CONTRACTS.md` | F0.3, F0.4 | 🟡 |

**Critério de saída da Fase 0**: `mvn -q compile` no backend compila (mesmo com classes vazias/stubs) e `npm install` no frontend funciona.

---

## FASE 1 — Trilhas paralelas (cada trilha = 1 agente, arquivos isolados)

### Trilha A — Domain + Application (depende de F0.3)

| ID | Tarefa | Arquivos | Depende de | Complexidade |
|----|--------|----------|------------|--------------|
| A1 | Implementar `Task.java` (campos + construtor de criação + métodos `updateDetails(title, description)` e `updateStatus(status)` com validações básicas) | `domain/model/Task.java` | F0.3 | 🟡 |
| A2 | Implementar `TaskStatus.java` enum com os 3 valores e (se útil) método de validação de transição | `domain/model/TaskStatus.java` | F0.3 | 🟢 |
| A3 | Implementar exceções `TaskNotFoundException` e `InvalidTaskException` (mensagens claras) | `domain/exception/*.java` | F0.3 | 🟢 |
| A4 | Implementar interface `TaskRepository.java` (porta) com assinaturas finais: `save`, `findById`, `findAll`, `deleteById`, `existsById` | `domain/repository/TaskRepository.java` | F0.3 | 🟢 |
| A5 | Implementar `TaskCreatedEvent` e `TaskUpdatedEvent` (records imutáveis com snapshot da Task + timestamp) | `domain/event/*.java` | A1 | 🟢 |
| A6 | Implementar `CreateTaskUseCase` (valida input, cria `Task`, persiste via `TaskRepository`, publica `TaskCreatedEvent` via `ApplicationEventPublisher`) | `application/usecase/CreateTaskUseCase.java` | A1, A4, A5 | 🟡 |
| A7 | Implementar `ListTasksUseCase` (retorna todas as tasks via `TaskRepository.findAll`) | `application/usecase/ListTasksUseCase.java` | A4 | 🟢 |
| A8 | Implementar `GetTaskUseCase` (busca por id, lança `TaskNotFoundException` se ausente) | `application/usecase/GetTaskUseCase.java` | A3, A4 | 🟢 |
| A9 | Implementar `UpdateTaskUseCase` (busca task, aplica `updateDetails`/`updateStatus`, persiste, publica `TaskUpdatedEvent`) | `application/usecase/UpdateTaskUseCase.java` | A1, A3, A4, A5 | 🟡 |
| A10 | Implementar `DeleteTaskUseCase` (verifica existência via `existsById`, lança `TaskNotFoundException` ou deleta) | `application/usecase/DeleteTaskUseCase.java` | A3, A4 | 🟢 |

### Trilha B — Infraestrutura: Persistência (depende de F0.2, F0.3)

| ID | Tarefa | Arquivos | Depende de | Complexidade |
|----|--------|----------|------------|--------------|
| B1 | Migration Flyway `V1__create_tasks_table.sql` (tabela `tasks`: id UUID PK, title VARCHAR NOT NULL, description TEXT, status VARCHAR NOT NULL, created_at TIMESTAMP NOT NULL) | `backend/src/main/resources/db/migration/V1__create_tasks_table.sql` | F0.2 | 🟢 |
| B2 | `TaskJpaEntity.java` (entidade JPA mapeando a tabela `tasks`) | `infrastructure/persistence/TaskJpaEntity.java` | B1 | 🟢 |
| B3 | `TaskJpaRepository.java` (`interface extends JpaRepository<TaskJpaEntity, UUID>`) | `infrastructure/persistence/TaskJpaRepository.java` | B2 | 🟢 |
| B4 | `TaskMapper.java` (conversão `Task` domínio ↔ `TaskJpaEntity`) | `infrastructure/persistence/TaskMapper.java` | A1, B2 | 🟡 |
| B5 | `TaskRepositoryAdapter.java` (implementa `domain.repository.TaskRepository`, delega para `TaskJpaRepository` via `TaskMapper`) | `infrastructure/persistence/TaskRepositoryAdapter.java` | A4, B3, B4 | 🟡 |
| B6 | Configuração de datasource/JPA/Flyway em `application.yml` (perfil `default` apontando para Postgres local/Docker, `ddl-auto: validate`) | `backend/src/main/resources/application.yml` | F0.2, B1 | 🟢 |

### Trilha C — Infraestrutura: Eventos e Logging (depende de F0.3)

| ID | Tarefa | Arquivos | Depende de | Complexidade |
|----|--------|----------|------------|--------------|
| C1 | `AsyncConfig.java` (`@EnableAsync` + bean `TaskExecutor` dedicado) | `infrastructure/config/AsyncConfig.java` | F0.2 | 🟢 |
| C2 | `TaskEventListener.java` (`@EventListener @Async` para `TaskCreatedEvent` e `TaskUpdatedEvent`, loga em formato estruturado: evento, taskId, status, timestamp) | `infrastructure/event/TaskEventListener.java` | A5, C1 | 🟡 |
| C3 | `logback-spring.xml` (encoder JSON via `logstash-logback-encoder`, appender console) | `backend/src/main/resources/logback-spring.xml` | F0.2 | 🟢 |
| C4 | Ajustes de logging em `application.yml` (níveis de log por pacote) | `backend/src/main/resources/application.yml` | C3 | 🟢 |

### Trilha D — API/Controller (depende de F0.3, F0.4, F0.7)

| ID | Tarefa | Arquivos | Depende de | Complexidade |
|----|--------|----------|------------|--------------|
| D1 | Finalizar DTOs `TaskRequest`, `UpdateTaskRequest` (com `@NotBlank`/`@Size` etc.) e `TaskResponse` | `api/dto/*.java` | F0.4, F0.7 | 🟡 |
| D2 | `TaskDtoMapper.java` (conversão `Task` domínio ↔ DTOs de request/response) | `api/dto/TaskDtoMapper.java` | A1, D1 | 🟡 |
| D3 | `TaskController.java` — endpoints REST: `POST /api/tasks`, `GET /api/tasks`, `GET /api/tasks/{id}`, `PUT /api/tasks/{id}`, `DELETE /api/tasks/{id}`, injetando os use cases da Trilha A | `api/controller/TaskController.java` | A6-A10, D1, D2 | 🟡 |
| D4 | `ApiError.java` (timestamp, status, error, message, path) | `api/dto/ApiError.java` | F0.4 | 🟢 |
| D5 | `GlobalExceptionHandler.java` (`@RestControllerAdvice`: `TaskNotFoundException` → 404, `MethodArgumentNotValidException` → 400, fallback → 500, usando `ApiError`) | `api/exception/GlobalExceptionHandler.java` | A3, D4 | 🟡 |
| D6 | Configuração do Actuator (`management.endpoints.web.exposure.include: health`) em `application.yml` | `backend/src/main/resources/application.yml` | F0.2 | 🟢 |
| D7 | `CorsConfig.java` (`WebMvcConfigurer` liberando origem do frontend, ex. `http://localhost:5173`) | `api/config/CorsConfig.java` | F0.2 | 🟢 |

### Trilha E — Frontend (depende de F0.5, F0.7)

| ID | Tarefa | Arquivos | Depende de | Complexidade |
|----|--------|----------|------------|--------------|
| E1 | `types/task.ts` — interface `Task` e tipo `TaskStatus` espelhando `CONTRACTS.md` | `frontend/src/types/task.ts` | F0.5, F0.7 | 🟢 |
| E2 | `api/taskApi.ts` — funções `getTasks`, `createTask`, `updateTask`, `deleteTask` (fetch, base URL via `import.meta.env.VITE_API_URL`) | `frontend/src/api/taskApi.ts` | E1 | 🟡 |
| E3 | `components/TaskForm.tsx` — formulário de criação (title, description) chamando `createTask` | `frontend/src/components/TaskForm.tsx` | E1, E2 | 🟡 |
| E4 | `components/TaskItem.tsx` — linha/cartão de uma tarefa com badge de status, select para mudar status e botão remover | `frontend/src/components/TaskItem.tsx` | E1, E2 | 🟡 |
| E5 | `components/TaskList.tsx` — lista de tasks usando `TaskItem`, recebendo dados e callbacks via props | `frontend/src/components/TaskList.tsx` | E1, E4 | 🟢 |
| E6 | `App.tsx` — estado (useState/useEffect), busca tasks no mount, compõe `TaskForm` + `TaskList`, callbacks de criar/atualizar/remover | `frontend/src/App.tsx` | E2, E3, E5 | 🟡 |
| E7 | Estilização básica (CSS simples, layout legível) | `frontend/src/index.css` ou `App.css` | E6 | 🟢 |
| E8 | `.env.development` / `.env.production` com `VITE_API_URL` + ajuste em `vite.config.ts` se necessário | `frontend/.env.*`, `frontend/vite.config.ts` | F0.5 | 🟢 |

### Trilha F — DevOps (depende de F0.2, F0.5, F0.6)

| ID | Tarefa | Arquivos | Depende de | Complexidade |
|----|--------|----------|------------|--------------|
| F1 | `backend/Dockerfile` (multi-stage: build Maven → runtime JRE 17) | `backend/Dockerfile` | F0.2 | 🟢 |
| F2 | `frontend/Dockerfile` (multi-stage: build Vite → serve via nginx) + `nginx.conf` se necessário | `frontend/Dockerfile`, `frontend/nginx.conf` | F0.5 | 🟢 |
| F3 | `docker-compose.yml` completo: `postgres` (com healthcheck e volume), `backend` (depends_on postgres healthy, env vars de datasource), `frontend` (depends_on backend, porta exposta) | `docker-compose.yml` | F0.6, F1, F2 | 🟡 |
| F4 | `.github/workflows/ci.yml` completo: job `backend` (setup-java 17, `mvn -B test`), job `frontend` (setup-node, `npm ci`, `npm run build`, `npm test` se existir) | `.github/workflows/ci.yml` | F0.6 | 🟡 |
| F5 | `.env.example` documentando variáveis usadas pelo `docker-compose.yml` | `.env.example` | F3 | 🟢 |

### Trilha G — Testes (depende dos contratos da Fase 0; pode rodar em paralelo às trilhas A/D usando os mesmos contratos)

| ID | Tarefa | Arquivos | Depende de | Complexidade |
|----|--------|----------|------------|--------------|
| G1 | `CreateTaskUseCaseTest.java` (JUnit5+Mockito: mocka `TaskRepository` e `ApplicationEventPublisher`, valida persistência e publicação do evento) | `backend/src/test/java/.../application/usecase/CreateTaskUseCaseTest.java` | A6 | 🟡 |
| G2 | `UpdateTaskUseCaseTest.java` e `DeleteTaskUseCaseTest.java` (casos de sucesso e `TaskNotFoundException`) | `backend/src/test/java/.../application/usecase/*Test.java` | A9, A10 | 🟡 |
| G3 | `TaskControllerTest.java` (`@WebMvcTest` + `MockMvc`, mocka use cases, testa os 5 endpoints incl. erros 404/400) | `backend/src/test/java/.../api/controller/TaskControllerTest.java` | D3, D5 | 🟡 |
| G4 (opcional/bônus) | Teste de frontend `TaskForm.test.tsx` (Vitest + React Testing Library) | `frontend/src/components/TaskForm.test.tsx` | E3 | 🟡 |

### Trilha H — Documentação (rascunho em paralelo, revisão final na Fase 2)

| ID | Tarefa | Arquivos | Depende de | Complexidade |
|----|--------|----------|------------|--------------|
| H1 | README — visão geral do projeto, stack, estrutura de pastas | `README.md` | F0.1 | 🟢 |
| H2 | README — instruções de execução (Docker Compose e execução local sem Docker) | `README.md` | F0.6 | 🟢 |
| H3 | README — decisões técnicas: arquitetura em camadas, **por que evento in-process em vez de Kafka/Redpanda** (trade-off, simplicidade vs. requisito mínimo atendido), escolha de UUID, Flyway etc. | `README.md` | F0.3, A6 | 🟡 |
| H4 | README — seção obrigatória "Uso de IA": ferramentas usadas (Claude Code), como o trabalho foi dividido entre agentes/modelos, o que foi gerado por IA vs. revisado manualmente | `README.md` | - | 🟢 |
| H5 | README — seção "Estratégias de escalabilidade" (diferencial do edital, item 7): como a arquitetura evoluiria — trocar evento in-process por Kafka (a porta já isola isso), réplicas da API stateless, índices/paginação, cache, observabilidade | `README.md` | H3 | 🟡 |

---

## FASE 2 — Integração final (sequencial, 1 agente, recomendado modelo 🔴)

| ID | Tarefa | Depende de |
|----|--------|------------|
| I1 | Revisar injeção de dependências (use cases recebendo `TaskRepository` e `ApplicationEventPublisher` via construtor; `@Service`/`@Component` corretos) | Trilhas A, B, C, D completas |
| I2 | Rodar `mvn test` no backend e corrigir erros de compilação/integração entre trilhas | I1 |
| I3 | `docker compose up` — validar que Postgres, backend e frontend sobem e se comunicam (CORS, URLs) | F3, I2 |
| I4 | Smoke test ponta a ponta: criar tarefa pelo frontend → aparece na listagem → log estruturado do evento aparece no console do backend → atualizar status → remover | I3 |
| I5 | Criar repositório no GitHub, push e validar execução do pipeline Actions | F4, I2 |
| I6 | Revisão final do `README.md` (preencher seção de uso de IA com o que de fato aconteceu na sessão, revisar instruções de execução) | I4, I5, Trilha H |
| I7 | Garantir histórico de commits limpo e incremental (um commit por trilha/marco, mensagens descritivas) — avaliadores olham o histórico | F0.0 |

---

## Resumo de paralelização

- **Fase 0**: 1 agente, sequencial — desbloqueia tudo.
- **Fase 1**: até **8 agentes simultâneos** (Trilhas A–H), cada um em arquivos próprios.
  - Trilha G depende apenas dos *contratos* (F0), não da implementação final de A/D — pode
    começar em paralelo, mas os testes que dependem de classes concretas (G1-G3) só fecham
    quando A6-A10/D3/D5 existirem (podem ser escritos antes e ajustados depois).
- **Fase 2**: 1 agente, sequencial — integra e fecha o entregável.

Total estimado: 8 tarefas (Fase 0) + 49 tarefas pequenas (Fase 1) + 7 tarefas de integração
(Fase 2) = 64 passos pequenos.
