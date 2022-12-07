import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRecompensa } from 'app/shared/model/recompensa.model';

type EntityResponseType = HttpResponse<IRecompensa>;
type EntityArrayResponseType = HttpResponse<IRecompensa[]>;

@Injectable({ providedIn: 'root' })
export class RecompensaService {
  public resourceUrl = SERVER_API_URL + 'api/recompensas';

  constructor(protected http: HttpClient) {}

  create(recompensa: IRecompensa): Observable<EntityResponseType> {
    return this.http.post<IRecompensa>(this.resourceUrl, recompensa, { observe: 'response' });
  }

  update(recompensa: IRecompensa): Observable<EntityResponseType> {
    return this.http.put<IRecompensa>(this.resourceUrl, recompensa, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRecompensa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRecompensa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
