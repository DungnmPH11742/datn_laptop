import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private urlApiCate = 'http://localhost:8080/';
  constructor(private http: HttpClient) { }

  findAll() {
    return this.http.get(`${this.urlApiCate}cate-find-all`);
  }

  findAllByParentId(id: number) {
    let params = new HttpParams().set('parent_id', id);
    return this.http.get(`${this.urlApiCate}cate-find`, { params: params });
  }

  findNodeCate() {
    return this.http.get(`${this.urlApiCate}node-cate`);
  }
}
