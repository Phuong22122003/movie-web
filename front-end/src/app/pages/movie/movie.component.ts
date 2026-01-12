import { ChangeDetectorRef, Component } from '@angular/core';
import { FilmCardComponent } from "./film-card/film-card.component";
import { DetailComponent } from "./detail/detail.component";
import { CardComponent } from "../../components/card/card.component";
import { RecommendationComponent } from "./recommendation/recommendation.component";
import { CommentComponent } from "./comment/comment.component";
import { MovieService } from '../../service/movie.service';
import { Movie } from '../../models/movie';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-movie',
  imports: [FilmCardComponent, DetailComponent, RecommendationComponent, CommentComponent, CommonModule],
  templateUrl: './movie.component.html',
  styleUrl: './movie.component.scss'
})
export class MovieComponent {
  movie!: Movie;
  constructor(private route: ActivatedRoute, private movieService: MovieService) {
  }
  loadMovie(id:any) {
    this.movieService.getById(id).subscribe((movie) => { 
      this.movie = movie;
      console.log(this.movie);
    });
  }
  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.loadMovie(id);
      }
    });
  }
}
