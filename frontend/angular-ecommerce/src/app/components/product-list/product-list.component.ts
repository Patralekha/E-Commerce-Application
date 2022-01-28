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
  currentCategoryId: number ;
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
    

    if(hasCategoryId){
      //get categoryId and convert it to a number
      this.currentCategoryId = Number(this.route.snapshot.paramMap.get('id'));
      //console.log(this.currentCategoryId);
    }else{
      //no category id available hence default to 1
      this.currentCategoryId = 1;
    }
    this.productService.getProductList(this.currentCategoryId).subscribe( data => {this.products = data});
  }

}
