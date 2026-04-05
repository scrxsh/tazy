import { Component, inject, OnInit, signal, computed } from '@angular/core';
import { ClienteService } from './services/cliente-service';

@Component({
  selector: 'app-usuarios',
  imports: [],
  templateUrl: './usuarios.html',
  styleUrl: './usuarios.css',
})
export class Usuarios implements OnInit {
  public clienteService = inject(ClienteService);

  //Paginación de los clientes
  page = signal(1);
  pageSize = 10;

  //Paginar de 10 en 10 clientes
  clientesPaginacion = computed(() => {
    const inicio = (this.page()-1) * this.pageSize;
    const final = inicio + this.pageSize;
    return this.clienteService.clientes().slice(inicio, final);
  });

  //Total de las paginas
  totalPaginas = computed(() => {
    const paginasCalculadas = Math.ceil(this.clienteService.clientes().length / this.pageSize);
    return paginasCalculadas;
  });

  //Navegacion
  siguiente(){
    if(this.page() < this.totalPaginas()){
      this.page.update(p => p + 1)
    }
  }
  anterior(){
    if(this.page() > 1){
      this.page.update(p => p - 1)
    }
  }

  //Rango visual
  rango = computed(() => {
    const inicio = (this.page() - 1) * this.pageSize + 1;
    const fin = Math.min(this.page() * this.pageSize, this.clienteService.clientes().length);
    return { inicio, fin };
  });
  
  ngOnInit() {
    this.clienteService.cargarClientes();
  }

  
}
