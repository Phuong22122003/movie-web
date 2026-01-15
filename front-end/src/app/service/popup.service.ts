import { Injectable } from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class PopUpService {
    private openPopUp!: (title: string, message: string) => void;
    private resolveResult!: (result: boolean) => void;

    confirm(title: string, message: string): Promise<boolean> {
        return new Promise(resolve => {
            console.log(resolve);
            this.resolveResult = resolve;
            this.openPopUp(title, message);
        });
    }

    close(result: boolean) {
        this.resolveResult(result);
    }

    // Pop up component pass the method to service
    // fn is function is used to show popup.
    registerOpen(fn: (title:string, message: string) => void) {
        this.openPopUp = fn;
    }
}