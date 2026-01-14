import { Component } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { MovieHeroComponent } from './movie-hero/movie-hero.component';
import { SmallCardComponent } from "../../components/small-card/small-card.component";
import { Movie } from '../../models/movie';
import { CardComponent } from "../../components/card/card.component";
import { MovieService } from '../../service/movie.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [MovieHeroComponent, SmallCardComponent, CardComponent, CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  movies!: Movie[];
  recentlyUpdatedMovie!: Movie[];
  constructor(private movieService: MovieService, private router: Router, private route: ActivatedRoute) { }
  loadMovies() {
    this.route.paramMap.subscribe(params => {
      const value = params.get('value');

      if (!value) {
        this.movieService.getAll(0, 10).subscribe(movies => this.movies = movies);
        return;
      }

      if (this.route.snapshot.routeConfig?.path?.startsWith('genre')) {
        this.movieService.getMovieByGenre(value, 0, 10)
          .subscribe(movies => this.movies = movies);
      }
      else if (this.route.snapshot.routeConfig?.path?.startsWith('country')) {
        this.movieService.getMovieByCountry(value, 0, 10)
          .subscribe(movies => this.movies = movies);
      }
    });
  }
  loadRecentlyUpdateMovie(){
    this.movieService.getRecentlyUpdateMovie(6).subscribe((movies)=>{
      this.recentlyUpdatedMovie = movies;
    })
  }
  ngOnInit() {
    this.loadRecentlyUpdateMovie();
    this.loadMovies();
  }
}
