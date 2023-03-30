import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { MarvelChar } from '../models/MarvelChar';
import { SearchService } from '../services/search.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CharacterComment } from '../models/CharacterComment';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit, OnDestroy {
  commentForm!: FormGroup
  aRouteSub$!: Subscription
  characterId!: number
  character!: MarvelChar

  constructor(
    private fb: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private searchSvc: SearchService,
    private router: Router) { }

  ngOnDestroy(): void {
    this.aRouteSub$.unsubscribe()
  }

  async ngOnInit() {
    this.commentForm = this.fb.group({
      comment: this.fb.control<string>('', [Validators.required]),
    })
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
    // get form contents
    const commentText: string = this.commentForm.value['comment'].trim()
    console.info("Sending comment > " + commentText)
    let comment: CharacterComment = {
      characterId: this.characterId,
      comment: commentText
    }
    this.searchSvc.postComment(this.characterId, comment)
    // after saving comment, navigate to view 2
    this.router.navigate(['/characters', this.characterId])
  }

}
