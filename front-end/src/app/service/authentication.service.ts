import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Login } from '../models/login';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
    constructor(private http: HttpClient){}
    url = 'http://localhost/api/v1/authenticate'
    login(param:Login):Observable<any>{
        return this.http.post(this.url+'/login',param);
    }
    sigup(param: any): Observable<any>{
        return this.http.post(this.url+'/sign-up',param);
    }
    logout(){
        localStorage.removeItem('jwt');
    }
    isLoggedIn(){
        return localStorage.getItem('jwt')!=null;
    }
}
