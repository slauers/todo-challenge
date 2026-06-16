import type { Task, TaskStatus } from '../types/task';
import TaskItem from './TaskItem';

interface TaskListProps {
  tasks: Task[];
  onStatusChange: (task: Task, newStatus: TaskStatus) => Promise<void>;
  onDelete: (task: Task) => Promise<void>;
}

export default function TaskList({
  tasks,
  onStatusChange,
  onDelete,
}: TaskListProps) {
  if (tasks.length === 0) {
    return (
      <div className="empty-state">
        <span className="empty-icon">📋</span>
        <p>Nenhuma tarefa por aqui. Crie a primeira para começar!</p>
      </div>
    );
  }

  return (
    <div className="task-list">
      {tasks.map((task) => (
        <TaskItem
          key={task.id}
          task={task}
          onStatusChange={onStatusChange}
          onDelete={onDelete}
        />
      ))}
    </div>
  );
}
