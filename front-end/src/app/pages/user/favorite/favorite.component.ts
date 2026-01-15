import { Component } from "@angular/core";
import { CardComponent } from "../../../components/card/card.component";
import { CommonModule } from "@angular/common";
import { Movie } from "../../../models/movie";
import { FavoriteService } from "../../../service/favorite.service";
import { Favorite } from "../../../models/favorite";

@Component({
  selector: 'app-search',
  imports: [CardComponent, CommonModule,],
  templateUrl: './favorite.component.html',
  styleUrl: './favorite.component.scss'
})
export class FavoriteComponent {
  favorites: Favorite[] = [];

  constructor(private favoriteService: FavoriteService) { }

  ngOnInit() {
    this.favoriteService.getAll().subscribe((favorites) => {
        this.favorites = favorites.map(fav => fav);
    });
  }
}
