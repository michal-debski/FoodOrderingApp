import {ChangeDetectionStrategy, Component, Inject, inject} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent, MatDialogModule, MatDialogRef,
  MatDialogTitle
} from '@angular/material/dialog';
import {ConfirmDialogData} from '../../models/confirm-dialog-data';

@Component({
  selector: 'app-dialog',
  imports: [MatButtonModule, MatDialogActions, MatDialogContent, MatDialogTitle, MatDialogClose],
  templateUrl: './dialog.html',
  styleUrl: './dialog.css'
})
export class Dialog {

  title = '';
  confirmText = '';
  cancelText = 'Cancel';

  readonly dialog = inject(MatDialog);

  constructor(
    private dialogRef: MatDialogRef<Dialog>,
    @Inject(MAT_DIALOG_DATA) public data: ConfirmDialogData
  ) {
    switch (data.type) {
      case 'delete':
        this.title = 'Delete confirmation';
        this.confirmText = 'Delete';
        break;
      case 'add':
        this.title = 'Add confirmation';
        this.confirmText = 'Add';
        break;
      case 'edit':
        this.title = 'Edit confirmation';
        this.confirmText = 'Save';
        break;
    }
  }

  onConfirm(): void {
    this.dialogRef.close(true);
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }
}
