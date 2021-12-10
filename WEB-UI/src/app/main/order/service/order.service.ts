import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  urlApi = 'http://localhost:8080/admin/';

  dateFormat = 'dd/MM/yyy';

  constructor(private http: HttpClient) { }

  findAll(): Observable<any[]> {
    return this.http.get<any[]>(`${this.urlApi}list-order`);
  }

  findById() {
    return this.http.get(`${this.urlApi}find-by-id`);
  }

  save(data: any) {
    return this.http.post(`${this.urlApi}store-product`, data);
  }

  update(data: any) {
    return this.http.put(`${this.urlApi}update-order`, data);
  }

  updateOrderDetail(data: any) {
    return this.http.put(`${this.urlApi}update-order-detail`, data);
  }
}
