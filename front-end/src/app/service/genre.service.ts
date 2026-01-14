import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Login } from '../models/login';
import { HttpClient } from '@angular/common/http';
import { Movie } from '../models/movie';
import { Genre } from '../models/genre';
import { environment} from "../../environments/environment"
@Injectable({
  providedIn: 'root'
})
export class GenreService {
    constructor(private http: HttpClient){}
    url = environment.apiUrl + '/genres'

    getAll():Observable<Genre[]>{

        return this.http.get<Genre[]>(this.url);
    }
}
