import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Cliente } from '../models/cliente.model';

@Injectable({
  providedIn: 'root',
})
export class ClienteService {
  private http = inject(HttpClient);
  private url = 'http://localhost:8080/api/clientes/todos';

  clientes = signal<Cliente[]>([]);

  cargarClientes(){
    this.http.get<Cliente[]>(this.url).subscribe({
      next: (clientes) => {
        this.clientes.set(clientes);
      },
      error: (error) => {
        console.error('Error al cargar clientes: ', error)
      }
    });
  }
}
