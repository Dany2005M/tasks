import { Routes } from '@angular/router';
import { HomeComponent } from './home-component/home-component';
import { MyTaskComponent } from './my-task-component/my-task-component';
import { SearchComponent } from './search-component/search-component';
import { NewTaskComponent } from './new-task-component/new-task-component';
import { LoginComponent } from './login-component/login-component';
import { LoggedInGuard } from './services/logged-in-guard';
export const routes: Routes = [
{ path: 'home', component: HomeComponent, canActivate: [LoggedInGuard] },
{path: 'my-task', component: MyTaskComponent, canActivate: [LoggedInGuard]},
{path: 'new-task', component: NewTaskComponent,canActivate: [LoggedInGuard]},
{path: 'search', component: SearchComponent,canActivate: [LoggedInGuard]},
{path: 'login', component: LoginComponent}
];
