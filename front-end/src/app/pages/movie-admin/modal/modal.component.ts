import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MovieService } from '../../../service/movie.service';
import { GenreService } from '../../../service/genre.service';
import { Genre } from '../../../models/genre';
import { CommonModule } from '@angular/common';
import { Movie } from '../../../models/movie';
import { NgSelectModule } from '@ng-select/ng-select';
import { Country } from '../../../models/country';
import { CountryService } from '../../../service/country.service';
@Component({
  selector: 'app-modal',
  imports: [ReactiveFormsModule, CommonModule, NgSelectModule],
  templateUrl: './modal.component.html',
  styleUrl: './modal.component.scss'
})
export class ModalComponent {
  imageFile!: File;
  videoFile!: File;
  movieId!: number;
  movieForm: FormGroup;
  genres!: Genre[];
  countries!: Country[];
//   @Output
  showModal = false;
  isEditing = false;
  constructor(
    private fb: FormBuilder,
    private movieService: MovieService,
    private genreService: GenreService,
    private countryService: CountryService
  ) {
    this.movieForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      countryId: [],
      genreIds: [[]]
    });
    this.loadGenres();
    this.loadCountries();
  }
  loadGenres() {
    this.genreService.getAll().subscribe((data) => {
      this.genres = data;
    });
  }
  loadCountries(){
    this.countryService.getAll().subscribe((countries)=>{
      this.countries = countries;
    })
  }
  onVideoSelected(event: Event) {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files?.length) {
      this.videoFile = fileInput.files[0];
    }
  }
  onImageSelected(event: Event) {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files?.length) {
      this.imageFile = fileInput.files[0];
    }
  }
  closeModal() {
    this.movieForm.reset();
    this.isEditing = false;
    this.showModal = false;
  }
  openModal(movie: Movie | null = null) {
    if (movie != null) {
      this.movieForm.patchValue(movie);
      this.isEditing = true;
      this.movieId = movie.id;
    }
    this.showModal = true;
  }
  submit() {
    if (this.movieForm.invalid) return;

    const formValue = this.movieForm.value;

    const moviePayload = {
      name: formValue.name,
      description: formValue.description,
      countryId: formValue.countryId,
      genreIds: formValue.genreIds
    };

    const formData = new FormData();
    formData.append('image', this.imageFile);
    formData.append('video', this.videoFile);
    formData.append('movie', new Blob([JSON.stringify(moviePayload)], { type: 'application/json' }));

    if(!this.isEditing){
      if (this.movieForm.invalid || !this.imageFile || !this.videoFile) return;
      this.movieService.addMovie(formData).subscribe({
        next: (res) => {
          console.log('Movie added:', res);
          this.closeModal();
        },
        error: (err) => {
          console.error('Upload failed:', err);
        }
      });
    }else{
      this.movieService.updateMovie(this.movieId,formData).subscribe({
        next: (res) => {
          console.log('Movie added:', res);
          this.closeModal();
        },
        error: (err) => {
          console.error('Upload failed:', err);
        }
      });
    }
  }

}
