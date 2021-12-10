import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  urlApi = 'http://localhost:8080/admin/';

  dateFormat = 'dd/MM/yyy';

  constructor(private http: HttpClient) { }

  findAll(): Observable<any[]> {
    return this.http.get<any[]>(`${this.urlApi}product/find-all`);
  }

  save(data: any) {
    return this.http.post(`${this.urlApi}store-product`, data);
  }

  update(data: any) {
    return this.http.put(`${this.urlApi}update-product`, data);
  }
}
