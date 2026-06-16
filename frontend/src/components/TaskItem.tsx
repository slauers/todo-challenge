import { STATUS_LABELS } from '../types/task';
import type { Task, TaskStatus } from '../types/task';

interface TaskItemProps {
  task: Task;
  onStatusChange: (task: Task, newStatus: TaskStatus) => Promise<void>;
  onDelete: (task: Task) => Promise<void>;
}

export default function TaskItem({
  task,
  onStatusChange,
  onDelete,
}: TaskItemProps) {
  const createdAtDate = new Date(task.createdAt);
  const formattedDate = createdAtDate.toLocaleDateString('pt-BR', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
  });

  return (
    <div className={`task-item${task.status === 'DONE' ? ' is-done' : ''}`}>
      <div className="task-content">
        <div className="task-header">
          <h3>{task.title}</h3>
          <span className={`status-badge status-${task.status.toLowerCase()}`}>
            {STATUS_LABELS[task.status]}
          </span>
        </div>
        {task.description && (
          <p className="task-description">{task.description}</p>
        )}
        <small className="task-date">{formattedDate}</small>
      </div>
      <div className="task-actions">
        <select
          value={task.status}
          onChange={(e) =>
            onStatusChange(task, e.target.value as TaskStatus)
          }
          className="status-select"
        >
          <option value="PENDING">{STATUS_LABELS.PENDING}</option>
          <option value="IN_PROGRESS">{STATUS_LABELS.IN_PROGRESS}</option>
          <option value="DONE">{STATUS_LABELS.DONE}</option>
        </select>
        <button
          onClick={() => onDelete(task)}
          className="delete-button"
        >
          Remover
        </button>
      </div>
    </div>
  );
}
