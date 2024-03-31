import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { environment } from '../../environments/environment';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
    products: Product[] = [];
    selectedCategoryId: number = 0;
    currentPage: number = 1;
    itemsPerPage: number = 12;
    keyword: string = "";

    constructor(private productService: ProductService) {

    }
    ngOnInit() {
        this.productService.getProducts(this.keyword, this.selectedCategoryId, this.currentPage, this.itemsPerPage).subscribe({
            next: (response: any) => {
                debugger
                response.products.forEach((product: Product) => {
                    product.url = `${environment.apiBaseUrl}/products/images/${product.thumbnail}`
                })
                this.products = response.products
            },
            complete: () => {
                debugger
            },
            error: (error: any) => {
                debugger;
                console.error('Error fetching products:', error);
            }
        })
    }


}
