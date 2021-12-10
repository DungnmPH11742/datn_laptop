import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ProviceVnService {
  private urlApi = 'https://provinces.open-api.vn/api/?depth=2';

  constructor(private http: HttpClient) { }

  findAll() {
    return this.http.get(this.urlApi);
  }
}
