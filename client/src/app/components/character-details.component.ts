import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SearchService } from '../services/search.service';
import { Subscription } from 'rxjs';
import { MarvelChar } from '../models/MarvelChar';

@Component({
  selector: 'app-character-details',
  templateUrl: './character-details.component.html',
  styleUrls: ['./character-details.component.css']
})
export class CharacterDetailsComponent implements OnInit, OnDestroy {
  aRouteSub$!: Subscription
  characterId!: number
  character!: MarvelChar


  constructor(
    private activatedRoute: ActivatedRoute,
    private searchSvc: SearchService,
    private router: Router) { }

  ngOnDestroy(): void {
    console.debug(this.character)
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
    console.info(this.character)
  }


}
