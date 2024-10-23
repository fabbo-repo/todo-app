export interface TaskUpdateProps {
  id: string;
  title?: string;
  description?: string;
  isCompleted?: boolean;
  deadline?: Date;
}
