import { Component, OnInit } from '@angular/core';
import { SearchService } from '../services/search.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MarvelChar } from '../models/MarvelChar';

@Component({
  selector: 'app-character-list',
  templateUrl: './character-list.component.html',
  styleUrls: ['./character-list.component.css']
})
export class CharacterListComponent implements OnInit {
  search!: string
  limit: number = 10
  offset: number = 0
  characterList: MarvelChar[] = []

  constructor(private activatedRoute: ActivatedRoute, private searchSvc: SearchService, private router: Router) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(
      queryparams => this.search = queryparams['search']
    )
    console.debug("searching for.. ", this.search)
    this.searchSvc.lookupList(this.search).then(
      v => this.characterList = v
    )
  }
  

}
