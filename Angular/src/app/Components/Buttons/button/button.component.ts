import { NgStyle } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-button',
  standalone: true,
  imports: [NgStyle],
  templateUrl: './button.component.html',
  styleUrl: './button.component.css',
})
export class ButtonComponent {
  @Input() buttonText: string = 'Button';
  @Input() fontSize: string = '2rem';
  @Input() padding: string = '1rem 2rem';
  @Input() customClass: string = '';
}
