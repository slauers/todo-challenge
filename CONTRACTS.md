# Contratos — referência para todas as trilhas

> Fonte de verdade: os arquivos Java em `backend/src/main/java/com/inter/todo/domain` e
> `api/dto` (já escritos na Fase 0). Este documento resume para consulta rápida
> (especialmente trilhas E/Frontend e G/Testes). NÃO altere os contratos sem atualizar aqui.

## Status da tarefa

| Enum (API/JSON) | Exibição (frontend) |
|-----------------|---------------------|
| `PENDING` | Pendente |
| `IN_PROGRESS` | Em andamento |
| `DONE` | Concluído |

## Endpoints REST (base: `http://localhost:8080`)

| Método | Path | Request body | Sucesso | Erros |
|--------|------|--------------|---------|-------|
| POST | `/api/tasks` | `TaskRequest` | 201 + `TaskResponse` (header `Location`) | 400 validação |
| GET | `/api/tasks` | - | 200 + `TaskResponse[]` | - |
| GET | `/api/tasks/{id}` | - | 200 + `TaskResponse` | 404 |
| PUT | `/api/tasks/{id}` | `UpdateTaskRequest` | 200 + `TaskResponse` | 400, 404 |
| DELETE | `/api/tasks/{id}` | - | 204 sem corpo | 404 |

Healthcheck: `GET /actuator/health` → `{"status":"UP"}`.

## JSON Schemas

`TaskRequest` (criação):
```json
{ "title": "string (obrigatório, ≤255)", "description": "string|null (≤4000)" }
```

`UpdateTaskRequest` (atualização — título, descrição E status):
```json
{ "title": "string (obrigatório, ≤255)", "description": "string|null", "status": "PENDING|IN_PROGRESS|DONE" }
```

`TaskResponse`:
```json
{
  "id": "uuid",
  "title": "string",
  "description": "string|null",
  "status": "PENDING|IN_PROGRESS|DONE",
  "createdAt": "2026-06-12T18:00:00Z"
}
```

`ApiError` (corpo de qualquer erro 4xx/5xx):
```json
{
  "timestamp": "2026-06-12T18:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Task not found: <uuid>",
  "path": "/api/tasks/<uuid>",
  "details": ["lista de erros de validação (vazia se n/a)"]
}
```

## Domínio (resumo das classes já criadas)

- `Task` — campos `id: UUID`, `title`, `description`, `status: TaskStatus`, `createdAt: Instant`.
  Fábricas: `Task.create(title, description)` (status inicial PENDING) e
  `Task.restore(id, title, description, status, createdAt)` (para a camada de persistência).
  Mutadores: `updateDetails(title, description)`, `updateStatus(status)`.
- `TaskRepository` (porta) — `save`, `findById`, `findAll`, `deleteById`, `existsById`.
- Eventos (records): `TaskCreatedEvent(taskId, title, status, occurredAt)` e
  `TaskUpdatedEvent(taskId, title, status, occurredAt)` — publicados pelos use cases via
  `ApplicationEventPublisher` APÓS persistir.
- Exceções: `TaskNotFoundException(UUID)` → 404; `InvalidTaskException(String)` → 400.

## Banco de dados (trilha B)

Tabela `tasks`: `id UUID PK`, `title VARCHAR(255) NOT NULL`, `description TEXT NULL`,
`status VARCHAR(20) NOT NULL`, `created_at TIMESTAMPTZ NOT NULL`.
Datasource já configurado em `application.yml` (env vars `DB_HOST/DB_PORT/DB_NAME/DB_USER/DB_PASSWORD`,
defaults `localhost:5432/todo/todo/todo`). `ddl-auto: validate` — o schema é do Flyway.

## Frontend (trilha E)

- Base URL da API: `import.meta.env.VITE_API_URL` (default dev: `http://localhost:8080`).
- CORS liberado para `http://localhost:5173` (D7).

## Regras para agentes

1. Trabalhe APENAS nos arquivos da sua trilha (ver PLAN.md).
2. NÃO edite `application.yml`, `pom.xml` nem os arquivos de contrato (domain/dto) — já estão prontos.
3. Marque os checkboxes da sua trilha no PLAN.md IMEDIATAMENTE ao concluir cada tarefa.
4. Ao final da trilha, faça um único commit: `git add <seus arquivos> && git commit -m "<tipo>: <trilha>"`.
