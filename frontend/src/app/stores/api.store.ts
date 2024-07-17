import { Injectable } from "@angular/core";
import { SearchResult, SearchResultSlice } from "../models/models";
import { ComponentStore } from '@ngrx/component-store';

// need to initialise SearchResultSlice
const INIT_VALUE: SearchResultSlice = {
    results: [],
  };
  
  @Injectable()
  export class APIStore extends ComponentStore<SearchResultSlice> {
    //
    constructor() {
      super(INIT_VALUE);
    }
    // updater / reducer
    readonly saveResult = this.updater<SearchResult>(
      (currStore: SearchResultSlice, result: SearchResult) => {
        // create a new copy of the store
        const newStore: SearchResultSlice = { ...currStore };
        newStore.results.push(result);
        return newStore;
      }
    );
  
    // create query/selector
    readonly getSavedSearches = this.select<string[]>(
      (currStore: SearchResultSlice) => {
        return currStore.results.map((result) => result.q);
      }
    );
  
    // write a selector that searches through the results, if find one, return that
    // instead of returning everyting, i only want to return one of the store results
    // need to do with typescript
    // component store does not directly support it
    // use typescript and closure
  
    readonly getFullSavedSearches = this.select<SearchResult[]>(
      (currStore: SearchResultSlice) => currStore.results
    );
  
    /////
    // create a function which takes whatever you pass in
    // return a function that executes the filter and returns the result
    // creating a function thaat takes one param, our search, which returns a select that returns everything, but i only want one.
    // need to pass in a function to say how to select, how to get the result.
    // when it returns it captures the value
    readonly getSavedSearchesByQ = (qText: string) =>
      this.select<SearchResult | undefined>(
        (currStore: SearchResultSlice) =>
          currStore.results.find((s) => s.q == qText) // qText is free
      );
  
  
    readonly getCachedResultCount = this.select<number>(
      // this is your query
      (currStore: SearchResultSlice) => currStore.results.length
      // select reutnrs and observale which executes yourquery
    );
  }
  