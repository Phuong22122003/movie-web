import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MovieService } from '../../../service/movie.service';
import { Movie } from '../../../models/movie';
import { Router, RouterLink } from '@angular/router';
@Component({
  selector: 'app-movie-hero',
  imports: [CommonModule,RouterLink],
  templateUrl: './movie-hero.component.html',
  styleUrl: './movie-hero.component.scss'
})
export class MovieHeroComponent {
  constructor(private movieService: MovieService){}
  currentIndex = 0;
  movies!:Movie[];

  setSlide(index: number) {
    this.currentIndex = index;
  }

  ngOnInit() {
    this.movieService.getMovieSlot().subscribe((movies)=>{
      this.movies = movies;
      // setInterval(() => {
      //   this.currentIndex = (this.currentIndex + 1) % this.movies.length;
      // }, 5000);
    });

  }

}
