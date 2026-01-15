import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { PopUpService } from '../../service/popup.service';

@Component({
  selector: 'app-pop-up',
  imports:[CommonModule],
  templateUrl: './pop-up.component.html',
  styleUrls: ['./pop-up.component.scss']
})
export class PopUpComponent {
  title = '';
  message = '';

  visible = false;
  constructor(private popUpService: PopUpService){}
  ngOnInit(){
    this.popUpService.registerOpen((title:string ,msg:string)=>{
      this.message = msg;
      this.title = title;
      this.visible = true;
    })
  }
  yes() {
    this.visible = false;
  }

  no() {
    this.visible = false;
  }
}
