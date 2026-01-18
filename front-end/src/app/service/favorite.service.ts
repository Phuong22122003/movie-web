import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Login } from '../models/login';
import { HttpClient } from '@angular/common/http';
import { Movie } from '../models/movie';
import { Genre } from '../models/genre';
import { environment} from "../../environments/environment"
import { Favorite } from '../models/favorite';
@Injectable({
  providedIn: 'root'
})
export class FavoriteService {
    constructor(private http: HttpClient){}
    url = environment.apiUrl + '/favorites'

    getAll():Observable<Favorite[]>{
        return this.http.get<Favorite[]>(this.url);
    }
    
    addFavorite(movieId:number){
        return this.http.post(`${this.url}/${movieId}`,null);
    }
}
