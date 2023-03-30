import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { MarvelChar } from '../models/MarvelChar';
import { CharacterComment } from '../models/CharacterComment';

const SPRINGBOOT_URL = "/api/characters"

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

  postComment(characterId: number, comment: CharacterComment) {
    const completeUrl = SPRINGBOOT_URL + "/" + characterId
    const payload = comment
    console.info("payload > " + payload)
    return lastValueFrom(
      this.http.post<Comment>(completeUrl, payload)
    )
  }

  getComments(characterId: number): Promise<CharacterComment[]> {
    const completeUrl = SPRINGBOOT_URL + "/" + characterId + "/comments"
    return lastValueFrom(
      this.http.get<CharacterComment[]>(completeUrl)
    )
  }
}
