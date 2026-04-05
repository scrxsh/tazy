import { Routes } from '@angular/router';

import { Layout } from './common/layout/layout';
import { Dashboard } from './pages/dashboard/dashboard';
import { Productos } from './pages/productos/productos';
import { Analytics } from './pages/analytics/analytics';
import { Ventas } from './pages/ventas/ventas';
import { Usuarios } from './pages/usuarios/usuarios';
import { Reportes } from './pages/reportes/reportes';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
    },
    {
        path: '',
        component: Layout,
        children: [

            /* dashboard, analitycs, productos, ventas, usuarios, reportes*/
            {
                path: 'dashboard',
                component: Dashboard
            },
            {
                path: 'analytics',
                component: Analytics
            },
            {
                path: 'productos',
                component: Productos
            },
            {
                path: 'ventas',
                component: Ventas
            },
            {
                path: 'usuarios',
                component: Usuarios
            },
            {
                path: 'reportes',
                component: Reportes
            }
        ]
    }
];
