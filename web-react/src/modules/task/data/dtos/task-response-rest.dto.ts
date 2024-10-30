export interface TaskResponseRestDto {
    id: string;
    title: string;
    description: string;
    isFinished: boolean;
    deadline?: string;
    createdAt: string;
}