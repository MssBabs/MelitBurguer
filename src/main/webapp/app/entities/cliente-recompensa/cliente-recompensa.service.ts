import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClienteRecompensa } from 'app/shared/model/cliente-recompensa.model';

type EntityResponseType = HttpResponse<IClienteRecompensa>;
type EntityArrayResponseType = HttpResponse<IClienteRecompensa[]>;

@Injectable({ providedIn: 'root' })
export class ClienteRecompensaService {
  public resourceUrl = SERVER_API_URL + 'api/cliente-recompensas';

  constructor(protected http: HttpClient) {}

  create(clienteRecompensa: IClienteRecompensa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(clienteRecompensa);
    return this.http
      .post<IClienteRecompensa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(clienteRecompensa: IClienteRecompensa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(clienteRecompensa);
    return this.http
      .put<IClienteRecompensa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IClienteRecompensa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IClienteRecompensa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(clienteRecompensa: IClienteRecompensa): IClienteRecompensa {
    const copy: IClienteRecompensa = Object.assign({}, clienteRecompensa, {
      fecha: clienteRecompensa.fecha != null && clienteRecompensa.fecha.isValid() ? clienteRecompensa.fecha.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((clienteRecompensa: IClienteRecompensa) => {
        clienteRecompensa.fecha = clienteRecompensa.fecha != null ? moment(clienteRecompensa.fecha) : null;
      });
    }
    return res;
  }
}
