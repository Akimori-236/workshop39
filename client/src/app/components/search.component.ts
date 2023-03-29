import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SearchService } from '../services/search.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent {

  searchForm!: FormGroup

  constructor(
    private fb: FormBuilder,
    private searchSvc: SearchService,
    private router: Router) { }

  ngOnInit(): void {
    this.searchForm = this.fb.group({
      search: this.fb.control<string>('', [Validators.required]),
    })
  }

  search() {
    const search = this.searchForm.value['search']
    console.debug("searchTerm > ", search)
    this.router.navigate(['/characters'], {
      queryParams: { search }
    })
  }
}
