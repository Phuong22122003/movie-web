import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { MovieComponent } from './pages/movie/movie.component';
import { LoginComponent } from './pages/login/login.component';
import { SigupComponent } from './pages/sigup/sigup.component';
import { authGuard } from './service/auth.guard';
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';
import { MovieAdminComponent } from './pages/movie-admin/movie-admin.component';
import { SearchComponent } from './pages/search/search.component';
import { GenreAdminComponent } from './pages/genre-admin/genre-admin.component';
import { CountryAdminComponent } from './pages/country-admin/country-admin.component';
import { FavoriteComponent } from './pages/user/favorite/favorite.component';
import { ProfileComponent } from './pages/user/profile/profile.component';

export const routes: Routes = [
    {
        path: "",
        component: MainLayoutComponent,
        children: [
            {
                path: "home",
                component: HomeComponent
            },
            {
                path: "favorite",
                component: FavoriteComponent,
            },
            {
                path: "profile",
                component: ProfileComponent,
            },
            {
                path: "country/:value",
                component: HomeComponent
            },
            {
                path: "genres/:value",
                component: HomeComponent
            },
            {
                path: "movie/:id",
                component: MovieComponent,
                canActivate: [authGuard]
            },
            {
                path: "search",
                component: SearchComponent,
            },
            {
                path: "",
                redirectTo: "home",
                pathMatch: "full"
            }
        ],
    },
    {
        path:"admin",
        component: AdminLayoutComponent,
        children:[
            {
                path: "movie",
                component: MovieAdminComponent
            },
            {
                path:'',
                redirectTo:'movie',
                pathMatch:'full'
            },
            {
                path:'genre',
                component: GenreAdminComponent
            },
            {
                path:'country',
                component: CountryAdminComponent
            }
        ],
    },
    {
        path: "login",
        component: LoginComponent,
    },
    {
        path: "sigup",
        component: SigupComponent
    },

];
