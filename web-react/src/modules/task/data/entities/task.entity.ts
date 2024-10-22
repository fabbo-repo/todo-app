export interface TaskEntity {
    id?: string;
    title: string;
    description: string;
    isCompleted: boolean;
    deadline: Date;
    createdAt?: Date;
}