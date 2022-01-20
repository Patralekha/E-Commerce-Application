import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from 'src/app/common/product';
import { ProductService } from 'src/app/service/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[] = [];
  //currentCategoryId: number ;
  constructor(private productService:ProductService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(()=>{
      this.listProducts();
    }
    );
  }

  listProducts(){
    //check if id parameter is available
    const hasCategoryId : boolean= this.route.snapshot.paramMap.has('id');
    var currentCategoryId: number;

    if(hasCategoryId){
      //get categoryId and convert it to a number
      currentCategoryId = this.route.snapshot.paramMap.get('id');
    }else{
      //no category id available hence default to 1
      currentCategoryId=1;
    }
    this.productService.getProductList(currentCategoryId).subscribe( data => {this.products = data});
  }

}
