import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Tip } from '../../Models/tip';
import { TipService } from '../../Services/tip.service';

@Component({
  selector: 'app-tip-modal',
  standalone: true,
  imports: [],
  templateUrl: './tip-modal.component.html',
  styleUrl: './tip-modal.component.css'
})
export class TipModalComponent {
  @Input() selectedTip: Tip | null = null;
  @Output() modalClosed = new EventEmitter<void>();

  constructor(private tipServ:TipService) {}

  closeModal() {
    this.modalClosed.emit();
  }

  closeModalOut(event: Event) {
    if (event.target === event.currentTarget) {
      this.closeModal();
    }
  }
}