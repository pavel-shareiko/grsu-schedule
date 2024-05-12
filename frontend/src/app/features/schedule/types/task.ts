export type SchedulePullTask = {
  id: number;
  status: SchedulePullStatus;
  trigger: PullTaskTrigger;
  createTimestamp: string;
  updateTimestamp: string;
  createdBy: string;
}

export enum SchedulePullStatus {
  PENDING = 'PENDING',
  IN_PROGRESS = 'IN_PROGRESS',
  COMPLETED = 'COMPLETED',
  FAILED = 'FAILED'
}

export enum PullTaskTrigger {
  MANUAL = 'MANUAL',
  SCHEDULE = 'SCHEDULE'
}
