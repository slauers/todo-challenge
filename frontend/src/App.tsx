import { useEffect, useState } from 'react';
import { STATUS_LABELS } from './types/task';
import type { Task, TaskStatus } from './types/task';
import * as taskApi from './api/taskApi';
import TaskForm from './components/TaskForm';
import TaskList from './components/TaskList';
import './App.css';

type StatusFilter = 'ALL' | TaskStatus;

const FILTERS: { value: StatusFilter; label: string }[] = [
  { value: 'ALL', label: 'Todas' },
  { value: 'PENDING', label: STATUS_LABELS.PENDING },
  { value: 'IN_PROGRESS', label: STATUS_LABELS.IN_PROGRESS },
  { value: 'DONE', label: STATUS_LABELS.DONE },
];

function App() {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [filter, setFilter] = useState<StatusFilter>('ALL');

  useEffect(() => {
    let cancelled = false;
    taskApi
      .getTasks()
      .then((data) => {
        if (!cancelled) setTasks(data);
      })
      .catch((err) => {
        if (!cancelled) {
          setError(
            err instanceof Error ? err.message : 'Erro ao carregar tarefas'
          );
        }
      })
      .finally(() => {
        if (!cancelled) setLoading(false);
      });
    return () => {
      cancelled = true;
    };
  }, []);

  const handleCreate = async (title: string, description?: string) => {
    try {
      const newTask = await taskApi.createTask({ title, description });
      setTasks([...tasks, newTask]);
    } catch (err) {
      setError(
        err instanceof Error
          ? err.message
          : 'Erro ao criar tarefa'
      );
    }
  };

  const handleStatusChange = async (task: Task, newStatus: TaskStatus) => {
    try {
      const updated = await taskApi.updateTask(task.id, {
        title: task.title,
        description: task.description,
        status: newStatus,
      });
      setTasks(tasks.map((t) => (t.id === task.id ? updated : t)));
    } catch (err) {
      setError(
        err instanceof Error
          ? err.message
          : 'Erro ao atualizar tarefa'
      );
    }
  };

  const handleDelete = async (task: Task) => {
    try {
      await taskApi.deleteTask(task.id);
      setTasks(tasks.filter((t) => t.id !== task.id));
    } catch (err) {
      setError(
        err instanceof Error
          ? err.message
          : 'Erro ao deletar tarefa'
      );
    }
  };

  const visibleTasks =
    filter === 'ALL' ? tasks : tasks.filter((t) => t.status === filter);

  return (
    <div className="app-container">
      <header className="app-header">
        <div className="header-content">
          <div className="header-mark">✓</div>
          <div className="header-text">
            <h1>Gerenciador de Tarefas</h1>
            <p>Organize seu dia com simplicidade</p>
          </div>
        </div>
      </header>

      {error && (
        <div className="error-message">
          <p>{error}</p>
        </div>
      )}

      <main className="app-main">
        <aside className="form-column">
          <div className="section-title">
            <h2>Nova tarefa</h2>
          </div>
          <TaskForm onCreate={handleCreate} />
        </aside>

        <section className="list-column">
          <div className="section-title">
            <h2>Minhas tarefas</h2>
            {!loading && <span className="task-count">{visibleTasks.length}</span>}
          </div>

          <div className="filter-chips" role="tablist" aria-label="Filtrar por status">
            {FILTERS.map(({ value, label }) => (
              <button
                key={value}
                role="tab"
                aria-selected={filter === value}
                className={`filter-chip${filter === value ? ' active' : ''}`}
                onClick={() => setFilter(value)}
              >
                {label}
              </button>
            ))}
          </div>

          {loading ? (
            <div className="loading">Carregando tarefas...</div>
          ) : (
            <TaskList
              tasks={visibleTasks}
              onStatusChange={handleStatusChange}
              onDelete={handleDelete}
            />
          )}
        </section>
      </main>
    </div>
  );
}

export default App;
