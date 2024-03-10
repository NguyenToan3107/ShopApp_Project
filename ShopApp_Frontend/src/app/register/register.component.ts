import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  @ViewChild('registerForm') registerForm!: NgForm

  phone: string
  password: string
  retypePassword: string
  fullName: string
  address: string
  isAccepted: boolean
  dateOfBirth: Date

  constructor() {
    this.phone = ''
    this.password = ''
    this.retypePassword = ''
    this.fullName = ''
    this.address = ''
    this.isAccepted = false
    this.dateOfBirth = new Date()
    this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear() - 18)
  }

  onPhoneChange() {
    console.log(`Phone typed: ${this.phone}`)
  }
  register() {
    const message = `phone: ${this.phone} ` +
      `password: ${this.password} ` +
      `retypePassword: ${this.retypePassword} ` +
      `address: ${this.address} ` +
      `fullName: ${this.fullName} ` +
      `isAccepted: ${this.isAccepted} ` +
      `dateOfBirth: ${this.dateOfBirth} `;

    alert(message)
  }

  // how to check pasword match
  checkPasswordsMatch() {
    if (this.password !== this.retypePassword) {
      this.registerForm.form.controls['retypePassword'].setErrors({ 'passwordMismatch': true })
    } else {
      this.registerForm.form.controls['retypePassword'].setErrors(null)
    }
  }

  checkAge() {
    const today = new Date()
    const birthDate = new Date(this.dateOfBirth)
    let age = today.getFullYear() - birthDate.getFullYear()
    const monthDiff = today.getMonth() - birthDate.getMonth()
    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
      age--;
    }
    if (age < 18) {
      this.registerForm.form.controls['dateOfBirth'].setErrors({ 'invalidAge': true })
    } else {
      this.registerForm.form.controls['dateOfBirth'].setErrors(null)
    }
  }
}
