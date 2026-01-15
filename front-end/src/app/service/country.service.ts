import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Country } from '../models/country';
import { environment} from "../../environments/environment"
@Injectable({
  providedIn: 'root'
})
export class CountryService {
    constructor(private http: HttpClient){}
    url = environment.apiUrl +'/countries'

    getAll():Observable<Country[]>{
        return this.http.get<Country[]>(this.url);
    }
    add(country: Country){
        return this.http.post<Country>(this.url,country);
    }
    update(id:string, country: Country){
        return this.http.put<Country>(this.url + '/' +id, country);
    }
    delete(id:string){
        return this.http.delete(`${this.url}/${id}`);
    }
}
