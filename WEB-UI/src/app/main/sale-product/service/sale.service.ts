import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Sale } from '../model/sale';

@Injectable({
  providedIn: 'root'
})
export class SaleService {
  private urlSale = 'http://localhost:8080/sale-admin';
  constructor(private http: HttpClient) { }

  public findAll(): Observable<any[]> {
    return this.http.get<any[]>(`${this.urlSale}`);
  }

  public createSale(data:Sale){
    return this.http.post(`${this.urlSale}`,data);
  }

  public updateSale(data:Sale){
    return this.http.put(`${this.urlSale}`,data);
  }

  public deleteSale(saleCode: String){
    return this.http.delete(`${this.urlSale}/${saleCode}`);
  }
}
