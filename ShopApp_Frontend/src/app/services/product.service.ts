import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product';

@Injectable({
    providedIn: 'root'
})
export class ProductService {

    private apiGetProducts = `${environment.apiBaseUrl}/products`

    constructor(private http: HttpClient) { }

    getProducts(
        keyword: string, categoryId: number, page: number, limit: number
    ): Observable<Product[]> {
        const params = new HttpParams()
            .set('page', page.toString())
            .set('limit', limit.toString())
            .set('keyword', keyword)
            .set('categoru_id', categoryId.toString())

        return this.http.get<Product[]>(this.apiGetProducts, { params })
    }
}
