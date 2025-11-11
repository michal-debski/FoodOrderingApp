import {inject, Injectable} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {ConfirmDialogData} from '../models/confirm-dialog-data';
import {Observable} from 'rxjs';
import {Dialog} from '../components/dialog/dialog';

@Injectable({ providedIn: 'root' })
export class DialogService {
  private readonly dialog = inject(MatDialog);

  openConfirmDialog(data: ConfirmDialogData): Observable<boolean> {
    const dialogRef = this.dialog.open(Dialog, {
      width: '400px',
      data
    });

    return dialogRef.afterClosed();
  }
}
