import { Component } from '@angular/core';
import { LoginDTO } from '../dtos/user/login.dto';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrl: './login.component.scss'
})
export class LoginComponent {
    phoneNumber: string
    password: string

    constructor(private router: Router, private userService: UserService) {
        this.phoneNumber = '0329118061'
        this.password = 'test1234'
    }

    onPhoneNumberChange() {
        console.log(`Phone typed: ${this.phoneNumber}`)
    }

    login() {
        const loginDTO: LoginDTO = {
            "phone_number": this.phoneNumber,
            "password": this.password,
        }
        this.userService.login(loginDTO).subscribe({
            next: (response: any) => {
                debugger
                // this.router.navigate(['/login'])
            },
            complete: () => {
                debugger
            },
            error: (error: any) => {
                alert(`Cannot login, error: ${error.error}`)
            }
        })
    }

}
