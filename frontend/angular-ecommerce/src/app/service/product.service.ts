import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../common/product';
import { map } from 'rxjs/operators';
import { ProductCategory } from '../common/product-category';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  

  private baseUrl = 'http://localhost:8080/api/products';
  private categoryUrl = 'http://localhost:8080/api/product-category';

  constructor(private httpClient:HttpClient) { }

  getProductList(theCategoryId: number): Observable<Product[]>{

    //@TODO: need to build backend urls based on category id
    //added size=100 at the end of the url
    const searchUrl=`${this.baseUrl}/search/findByCategoryId?id=${theCategoryId}&size=100`;

    return this.httpClient.get<GetResponseProducts>(searchUrl).pipe(
      map((response: { _embedded: { products: any; }; }) => response._embedded.products)
    )
  }

  getProductCategories(): Observable<ProductCategory[]> {
    return this.httpClient.get<GetResponseProductCategory>(this.categoryUrl).pipe(
      map((response: { _embedded: { productCategory: any; }; }) => response._embedded.productCategory)
    )
  }
}


interface GetResponseProducts{
  _embedded: {
    products: Product[];
  }
}

interface GetResponseProductCategory{
  _embedded: {
    productCategory: ProductCategory[];
  }
}