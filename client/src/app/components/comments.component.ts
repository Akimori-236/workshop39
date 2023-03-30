import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { MarvelChar } from '../models/MarvelChar';
import { SearchService } from '../services/search.service';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit, OnDestroy {

  aRouteSub$!: Subscription
  characterId!: number
  character!: MarvelChar

  constructor(
    private activatedRoute: ActivatedRoute,
    private searchSvc: SearchService,
    private router: Router) { }

  ngOnDestroy(): void {
    this.aRouteSub$.unsubscribe()
  }

  async ngOnInit() {
    this.aRouteSub$ = this.activatedRoute.params.subscribe(
      (params) => {
        this.characterId = params['characterId']
      }
    )
    await this.searchSvc.getCharById(this.characterId)
      .then(v => this.character = v)
      .catch(err => console.warn(err))
    console.info(this.character)
  }

  postComment() {
    // after saving comment, navigate to view 2
  }

}
