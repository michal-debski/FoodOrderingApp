export interface ConfirmDialogData {
  type: 'delete' | 'add' | 'edit' | 'finish' | 'cancel';
  message?: string;
}
