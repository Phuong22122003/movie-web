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
}
