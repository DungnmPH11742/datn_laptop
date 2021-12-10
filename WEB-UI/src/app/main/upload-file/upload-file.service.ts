import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {
  urlApi = 'http://localhost:8080/';
  constructor(private httpClient: HttpClient) { }

  // Upload file
  uploadFile(file: File) {
    const formData: FormData = new FormData();
    formData.append('file', file);
    return this.httpClient.post(`${this.urlApi}uploadFile`, formData);
  }
}
