import { StatusDTO } from "./StatusDTO";

export interface TaskDTO {
  taskId?: number;
  name: string;
  dueDate: string; 
  userId: number | null;
  createdByFullName: string;
  statusTypeId: string | null; 
}