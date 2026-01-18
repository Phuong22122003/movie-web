import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Movie } from '../../models/movie';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FavoriteService } from '../../service/favorite.service';

@Component({
  selector: 'app-card',
  imports: [CommonModule,RouterLink],
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss'
})
export class CardComponent {
  @Input() movie!: Movie;
  constructor(private favoriteService: FavoriteService){}
  ngOnInit(){
    this.movie.genres = this.movie.genres?.slice(0,2)||[];
  }
  onClick(event:any){
    event.stopPropagation();
    this.favoriteService.addFavorite(this.movie.id).subscribe(()=>{
      this.movie.isFavorited = !this.movie.isFavorited;
    });
  }
}
