import { Component } from '@angular/core';
import { User } from '../../../models/user';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
    styleUrl: './profile.component.scss',
    imports: [ FormsModule, CommonModule ]
})
export class ProfileComponent {
  user: User = {
    id: '1',
    username: 'movie_user',
    email: 'user@gmail.com',
    role: 'USER'
  };

  private originalUser = { ...this.user };

  save() {
    console.log('Save user:', this.user);
    // gọi API update user
  }

  reset() {
    this.user = { ...this.originalUser };
  }
}
