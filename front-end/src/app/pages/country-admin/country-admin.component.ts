import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Country } from '../../models/country';
import { CountryService } from '../../service/country.service';
import { PopUpComponent } from '../../components/pop-up/pop-up.component';
import { PopUpService } from '../../service/popup.service';

@Component({
  selector: 'app-country-admin',
  imports: [CommonModule,FormsModule, PopUpComponent],
  templateUrl: './country-admin.component.html',
  styleUrl: './country-admin.component.scss'
})
export class CountryAdminComponent {
  countries!: Country[];
  isOpen = false;
  editingCountry: any;
  countryName = '';
  constructor(private countryService: CountryService, private popupService: PopUpService){}
  openAddModal() {
    this.editingCountry = null;
    this.countryName = '';
    this.isOpen = true;
  }
  loadCountries() {
    this.countryService.getAll().subscribe(data => {
      this.countries = data;
    });
  }
  delete(id: any){
    this.popupService
      .confirm('Delete', 'Do you want to delete this country')
      .then(ok => {
        this.countryService.delete(id);
      })
    }
  ngOnInit() {
    this.loadCountries();
  }
  edit(country: any) {
    this.editingCountry = country;
    this.countryName = country.name;
    this.isOpen = true;
  }

  closeModal() {
    this.isOpen = false;
  }

  saveCountry() {
    if (!this.countryName.trim()) return;

    if (this.editingCountry) {
      // UPDATE
      this.editingCountry.name = this.countryName;
      this.countryService
        .update(this.editingCountry.id, this.editingCountry)
        .subscribe((res)=>{
          this.countries.push(res);
        });
    } else {
      this.countryService.add({
        id: '',
        name: this.countryName
      }).subscribe((res)=>{
        this.countries.push(res);
      })
      
    }

    this.closeModal();
    }
}
