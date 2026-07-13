import { Routes } from '@angular/router';
import { HomeComponent } from './home-component/home-component';
import { MyTaskComponent } from './my-task-component/my-task-component';
import { SearchComponent } from './search-component/search-component';
import { NewTaskComponent } from './new-task-component/new-task-component';

export const routes: Routes = [
{ path: 'home', component: HomeComponent},
{path: 'my-task', component: MyTaskComponent},
{path: 'new-task', component: NewTaskComponent},
{path: 'search', component: SearchComponent},

];
