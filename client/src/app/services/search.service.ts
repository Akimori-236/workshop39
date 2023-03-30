import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { MarvelChar } from '../models/MarvelChar';

const SPRINGBOOT_URL = "http://localhost:8080/api/characters"

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private http: HttpClient) { }

  lookupList(search: string, limit: number, offset: number): Promise<MarvelChar[]> {
    let params = new HttpParams()
      .set('search', search)
      .set('limit', limit)
      .set('offset', offset)

    // send for springboot
    return lastValueFrom(
      this.http.get<MarvelChar[]>(SPRINGBOOT_URL, { params })
    )
  }
}
