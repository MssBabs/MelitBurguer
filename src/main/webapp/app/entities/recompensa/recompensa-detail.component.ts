import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecompensa } from 'app/shared/model/recompensa.model';

@Component({
  selector: 'jhi-recompensa-detail',
  templateUrl: './recompensa-detail.component.html'
})
export class RecompensaDetailComponent implements OnInit {
  recompensa: IRecompensa;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ recompensa }) => {
      this.recompensa = recompensa;
    });
  }

  previousState() {
    window.history.back();
  }
}
