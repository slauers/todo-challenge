import { useState } from 'react';
import type { FormEvent } from 'react';

interface TaskFormProps {
  onCreate: (title: string, description?: string) => Promise<void>;
}

export default function TaskForm({ onCreate }: TaskFormProps) {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setLoading(true);
    try {
      await onCreate(title, description);
      setTitle('');
      setDescription('');
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="task-form">
      <div className="form-group">
        <label htmlFor="title">Título</label>
        <input
          id="title"
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
          disabled={loading}
          placeholder="Digite o título da tarefa"
        />
      </div>
      <div className="form-group">
        <label htmlFor="description">Descrição (opcional)</label>
        <textarea
          id="description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          disabled={loading}
          placeholder="Digite a descrição da tarefa"
          rows={3}
        />
      </div>
      <button type="submit" disabled={loading}>
        {loading ? 'Criando...' : 'Criar Tarefa'}
      </button>
    </form>
  );
}
