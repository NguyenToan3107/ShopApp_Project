import { Component, OnInit } from '@angular/core';
import { LoginDTO } from '../../dtos/user/login.dto';
import { UserService } from '../../services/user.service';
import { LoginResponse } from '../../responses/user/login.response'
import { Router } from '@angular/router';
import { TokenService } from '../../services/token.service';
import { RoleService } from '../../services/role.service';
import { Role } from '../../models/role';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {
    phoneNumber: string = '0329118061'
    password: string = 'test1234'
    selectedRole: Role | undefined
    roles: Role[] = []
    rememberMe: boolean = true

    constructor(
        private router: Router,
        private userService: UserService,
        private tokenService: TokenService,
        private roleService: RoleService
    ) { }

    ngOnInit() {
        debugger
        this.roleService.getRoles().subscribe({
            next: (roles: Role[]) => {
                debugger
                this.roles = roles
                this.selectedRole = roles.length > 0 ? roles[0] : undefined
            },
            error: (error: any) => {
                debugger
                console.error(`Error getting roles:`, error)
            }
        })
    }
    login() {
        const loginDTO: LoginDTO = {
            phone_number: this.phoneNumber,
            password: this.password,
            role_id: this.selectedRole?.id ?? 1
        }
        this.userService.login(loginDTO).subscribe({
            next: (response: LoginResponse) => {
                debugger
                const { token } = response
                this.tokenService.setToken(token)
                // this.router.navigate(['/login'])
            },
            complete: () => {
                debugger
            },
            error: (error: any) => {
                debugger
                alert(error?.error?.message)
            }
        })
    }

}
