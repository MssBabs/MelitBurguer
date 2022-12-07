import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoProducto } from 'app/shared/model/tipo-producto.model';

@Component({
  selector: 'jhi-tipo-producto-detail',
  templateUrl: './tipo-producto-detail.component.html'
})
export class TipoProductoDetailComponent implements OnInit {
  tipoProducto: ITipoProducto;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoProducto }) => {
      this.tipoProducto = tipoProducto;
    });
  }

  previousState() {
    window.history.back();
  }
}
