import { Component, OnDestroy, OnInit } from '@angular/core';
import { SearchService } from '../services/search.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MarvelChar } from '../models/MarvelChar';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-character-list',
  templateUrl: './character-list.component.html',
  styleUrls: ['./character-list.component.css']
})
export class CharacterListComponent implements OnInit, OnDestroy {
  aRouteSub$!: Subscription
  search!: string
  limit: number = 20
  offset: number = 0
  characterList: MarvelChar[] = []

  constructor(
    private activatedRoute: ActivatedRoute,
    private searchSvc: SearchService,
    private router: Router) { }

  ngOnDestroy(): void {
    this.aRouteSub$.unsubscribe()
  }

  async ngOnInit() {
    // get search term from query param
    this.aRouteSub$ = this.activatedRoute.queryParams.subscribe(
      queryparams => this.search = queryparams['search']
    )
    console.debug("searching for.. ", this.search)
    // get char list promise
    await this.searchSvc.getCharList(this.search, this.limit, this.offset)
      .then(v => this.characterList = v)
    console.info(this.characterList)
  }

  previous() {
    if (this.offset >= this.limit) {
      this.offset -= this.limit
    }
    console.debug("new offset > " + this.offset)
    // trigger service
    this.searchSvc.getCharList(this.search, this.limit, this.offset).then(
      v => this.characterList = v
    )
    console.info(this.characterList)
  }

  next() {
    this.offset += this.limit
    console.debug("new offset > " + this.offset)
    // trigger service
    this.searchSvc.getCharList(this.search, this.limit, this.offset).then(
      v => this.characterList = v
    )
    console.info(this.characterList)
  }
}
