import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';



@Injectable({
  providedIn: 'root'
})
export class SearchService {

  searchTerm!: string

  constructor(private http: HttpClient) { }

  lookup(searchTerm: string) {
    this.searchTerm = searchTerm
    // send for springboot
  }
}
