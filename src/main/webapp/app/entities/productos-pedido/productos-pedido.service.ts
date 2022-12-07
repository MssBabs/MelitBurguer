import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductosPedido } from 'app/shared/model/productos-pedido.model';

type EntityResponseType = HttpResponse<IProductosPedido>;
type EntityArrayResponseType = HttpResponse<IProductosPedido[]>;

@Injectable({ providedIn: 'root' })
export class ProductosPedidoService {
  public resourceUrl = SERVER_API_URL + 'api/productos-pedidos';

  constructor(protected http: HttpClient) {}

  create(productosPedido: IProductosPedido): Observable<EntityResponseType> {
    return this.http.post<IProductosPedido>(this.resourceUrl, productosPedido, { observe: 'response' });
  }

  update(productosPedido: IProductosPedido): Observable<EntityResponseType> {
    return this.http.put<IProductosPedido>(this.resourceUrl, productosPedido, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductosPedido>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductosPedido[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
