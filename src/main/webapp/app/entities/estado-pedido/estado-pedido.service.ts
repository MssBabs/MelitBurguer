import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEstadoPedido } from 'app/shared/model/estado-pedido.model';

type EntityResponseType = HttpResponse<IEstadoPedido>;
type EntityArrayResponseType = HttpResponse<IEstadoPedido[]>;

@Injectable({ providedIn: 'root' })
export class EstadoPedidoService {
  public resourceUrl = SERVER_API_URL + 'api/estado-pedidos';

  constructor(protected http: HttpClient) {}

  create(estadoPedido: IEstadoPedido): Observable<EntityResponseType> {
    return this.http.post<IEstadoPedido>(this.resourceUrl, estadoPedido, { observe: 'response' });
  }

  update(estadoPedido: IEstadoPedido): Observable<EntityResponseType> {
    return this.http.put<IEstadoPedido>(this.resourceUrl, estadoPedido, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstadoPedido>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstadoPedido[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
