import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { APIStore } from '../../stores/api.store';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly router = inject(Router);
// 
  private readonly apiStore = inject(APIStore)

  protected searchForm!: FormGroup
  protected savedSearches$!: Observable<string[]>

  ngOnInit(): void {
    this.searchForm = this.createSearchForm()
    this.savedSearches$ = this.apiStore.getSavedSearches
  }

  search() {
    //
    const values = this.searchForm.value
    console.info(">>> values: ", values)
    const queryParams = {q: values['q']}
    console.log(queryParams)
    this.router.navigate(['/results'], {queryParams})
  }

  viewAllParks() {
    this.searchForm.get('q')?.setValue('');
    this.search();
  }

  private createSearchForm(): FormGroup{
    return this.fb.group({
      q: this.fb.control<string>("", [Validators.required])
    })
  }
}
