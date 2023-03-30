import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SearchService } from '../services/search.service';
import { Subscription } from 'rxjs';
import { MarvelChar } from '../models/MarvelChar';
import { CharacterComment } from '../models/CharacterComment';

@Component({
  selector: 'app-character-details',
  templateUrl: './character-details.component.html',
  styleUrls: ['./character-details.component.css']
})
export class CharacterDetailsComponent implements OnInit, OnDestroy {
  aRouteSub$!: Subscription
  characterId!: number
  character!: MarvelChar
  commentList: CharacterComment[] = []


  constructor(
    private activatedRoute: ActivatedRoute,
    private searchSvc: SearchService,
    private router: Router) { }

  ngOnDestroy(): void {
    this.aRouteSub$.unsubscribe()
  }

  async ngOnInit() {
    // get path variable
    // this.characterId = this.activatedRoute.snapshot.params['characterId']
    this.aRouteSub$ = this.activatedRoute.params.subscribe(
      (params) => {
        this.characterId = params['characterId']
      }
    )
    // console.debug(this.characterId)
    // get character promise
    await this.searchSvc.getCharById(this.characterId)
      .then(v => this.character = v)
      .catch(err => console.warn(err))
    console.info("character > " + this.character)

    // get mongo comments
    await this.searchSvc.getComments(this.characterId)
      .then(v => this.commentList = v)
    console.info("comments > " + this.commentList)
  }


}
