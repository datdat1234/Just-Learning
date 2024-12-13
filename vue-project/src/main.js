import './assets/main.css'

import { createApp } from 'vue'
import TestItem from '@/components/TestItem.vue'
import App from './App.vue'
import App2 from './App2.vue'

// The object we are passing into createApp is in fact a component. 
// Every app requires a "root component" that can contain other 
// components as its children.;
const app = createApp(App)

// For registering app-scoped assets
app.component('TestItem', TestItem)

// The .mount() method should always be called after all 
// app configurations and asset registrations are done.
// Also note that its return value, unlike the asset registration methods, 
// is the root component instance instead of the application instance.
app.mount('#app')

// You are not limited to a single application instance on the same page. 
// The createApp API allows multiple Vue applications to co-exist on the same page, 
// each with its own scope for configuration and global assets:
const app2 = createApp(App2)
app2.mount('#app2')