import type { Task, TaskStatus } from '../types/task';

const BASE = import.meta.env.VITE_API_URL ?? 'http://localhost:8080';

export async function getTasks(): Promise<Task[]> {
  const res = await fetch(`${BASE}/api/tasks`);
  if (!res.ok) {
    const error = await res.text();
    throw new Error(error);
  }
  return res.json();
}

export async function createTask(data: {
  title: string;
  description?: string;
}): Promise<Task> {
  const res = await fetch(`${BASE}/api/tasks`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      title: data.title,
      description: data.description ?? null,
    }),
  });
  if (!res.ok) {
    const error = await res.text();
    throw new Error(error);
  }
  return res.json();
}

export async function updateTask(
  id: string,
  data: {
    title: string;
    description?: string | null;
    status: TaskStatus;
  }
): Promise<Task> {
  const res = await fetch(`${BASE}/api/tasks/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      title: data.title,
      description: data.description ?? null,
      status: data.status,
    }),
  });
  if (!res.ok) {
    const error = await res.text();
    throw new Error(error);
  }
  return res.json();
}

export async function deleteTask(id: string): Promise<void> {
  const res = await fetch(`${BASE}/api/tasks/${id}`, {
    method: 'DELETE',
  });
  if (!res.ok) {
    const error = await res.text();
    throw new Error(error);
  }
}
