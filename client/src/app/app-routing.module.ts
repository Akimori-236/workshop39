import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SearchComponent } from './components/search.component';
import { CharacterListComponent } from './components/character-list.component';
import { CharacterDetailsComponent } from './components/character-details.component';
import { CommentsComponent } from './components/comments.component';

const routes: Routes = [
  { path: '', component: SearchComponent },
  { path: 'characters', component: CharacterListComponent },
  { path: 'characters/:characterId', component: CharacterDetailsComponent },
  { path: 'characters/:characterId/comment', component: CommentsComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
