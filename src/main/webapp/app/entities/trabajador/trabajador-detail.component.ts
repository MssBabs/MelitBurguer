import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrabajador } from 'app/shared/model/trabajador.model';

@Component({
  selector: 'jhi-trabajador-detail',
  templateUrl: './trabajador-detail.component.html'
})
export class TrabajadorDetailComponent implements OnInit {
  trabajador: ITrabajador;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ trabajador }) => {
      this.trabajador = trabajador;
    });
  }

  previousState() {
    window.history.back();
  }
}
