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

  getCharList(search: string, limit: number, offset: number): Promise<MarvelChar[]> {
    let params = new HttpParams()
      .set('search', search)
      .set('limit', limit)
      .set('offset', offset)
    // send for springboot
    return lastValueFrom(
      this.http.get<MarvelChar[]>(SPRINGBOOT_URL, { params })
    )
  }

  getCharById(characterId: number): Promise<MarvelChar> {
    const completeUrl = SPRINGBOOT_URL + "/" + characterId
    console.debug(completeUrl)
    return lastValueFrom(
      this.http.get<MarvelChar>(completeUrl)
    )
  }

  postComment(characterId: number, comment: Comment) {
    const completeUrl = SPRINGBOOT_URL + "/" + characterId
    return lastValueFrom(
      this.http.post<Comment>(completeUrl, comment)
    )
  }
}
