import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { environment } from '../../environments/environment';
import { Category } from '../../models/category';
import { CategoryService } from '../../services/category.service';
import { Route, Router } from '@angular/router';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
    categories: Category[] = [];
    products: Product[] = [];
    selectedCategoryId: number = 0;
    currentPage: number = 1;
    itemsPerPage: number = 12;
    keyword: string = "";
    // to use page 
    page: number[] = []
    totalPages: number = 0
    visiblePages: number[] = []

    constructor(
        private productService: ProductService,
        private categoryService: CategoryService,
        private router: Router) {

    }
    ngOnInit() {
        this.getProducts(this.keyword, this.selectedCategoryId, this.currentPage, this.itemsPerPage);
        this.getCategories(1, 100)
    }
    searchProducts() {
        this.currentPage = 1;
        this.itemsPerPage = 12;
        this.getProducts(this.keyword, this.selectedCategoryId, this.currentPage, this.itemsPerPage);
    }
    getCategories(page: number, limit: number) {
        this.categoryService.getCategories(page, limit).subscribe({
            next: (response: Category[]) => {
                this.categories = response
            },
            complete: () => {

            },
            error: (error) => {

            }
        })
    }

    getProducts(keyword: string, selectedCategoryId: number, currentPage: number, itemsPerPage: number) {
        this.productService.getProducts(keyword, selectedCategoryId, currentPage, itemsPerPage).subscribe({
            next: (response: any) => {
                debugger
                response.products.forEach((product: Product) => {
                    product.url = `${environment.apiBaseUrl}/products/images/${product.thumbnail}`
                })
                this.products = response.products
                this.totalPages = response.totalPages
                this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages)
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
    onProductClick(productId: number) {
        this.router.navigate(['/products', productId])
    }

    onPageChange(page: number) {
        this.currentPage = page
        this.getProducts(this.keyword, this.selectedCategoryId, this.currentPage, this.itemsPerPage);
    }

    generateVisiblePageArray(currentPage: number, totalPages: number): number[] {
        const maxVisiblePages = 5;
        const halfVisiblePages = Math.floor(maxVisiblePages / 2);

        let startPage = Math.max(currentPage - halfVisiblePages, 1);
        let endPage = Math.min(startPage + maxVisiblePages - 1, totalPages);

        if (endPage - startPage + 1 < maxVisiblePages) {
            startPage = Math.max(endPage - maxVisiblePages + 1, 1);
        }

        return new Array(endPage - startPage + 1).fill(0).map((_, index) => startPage + index);
    }

}
