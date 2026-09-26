import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StatusService } from './core/services/status.service';
import { StatusResponse } from './core/models/status.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  private readonly statusService = inject(StatusService);

  // Fine-grained reactivity powered by Angular Signals
  readonly status = signal<StatusResponse | null>(null);
  readonly isLoading = signal<boolean>(false);
  readonly errorMessage = signal<string | null>(null);

  constructor() {
    this.checkHealth();
  }

  checkHealth(): void {
    this.isLoading.set(true);
    this.errorMessage.set(null);

    this.statusService.checkBackendStatus().subscribe({
      next: (res) => {
        this.status.set(res);
        this.isLoading.set(false);
      },
      error: () => {
        this.status.set(null);
        this.isLoading.set(false);
        this.errorMessage.set(
          'Could not connect to the Backend (ensure it is running on port 8080)'
        );
      }
    });
  }
}
