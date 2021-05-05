import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Token } from '../model/token';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  cabecera = {
    'Accept': 'application/json',
    'Authorization': ''
  }

  parametros = {}

  url = '/shop/v1.0/authenticate';

  obtenerToken(username: string, password: string){
    this.parametros = {
      'username': username,
      'password': password
    }
    return this.http.post<Token>(this.url,this.parametros);
  }
}
