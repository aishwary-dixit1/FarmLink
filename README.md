# Farm Link App

> [!NOTE]
> The app is still under development.


> [!CAUTION]
> Never make commits directly to the master branch.


## Project Structure

```
├──   src
|        ├── common
|        |         ├── AsyncImageLoader.kt
|        |         ├── DataCard.kt          // Card to display data with image
|        |         └── SearchFeature.kt     // Search bar
|        ├── data
|        |       ├── MongoDB.kt
|        |       └── MongoDBRepository.kt
|        ├── di      // dependency injection
|        |    └── AppModule
|        ├── model      // structures of our data
|        |            ├── Category
|        |            ├── Item
|        |            ├── User
|        |            ├── Seller
|        |            ├── Buyer
|        |            ├── SaleItem
|        |            └── Review
|        ├── navigation
|        |             ├── NavGraph.kt
|        |             ├── AuthenticationGraph.kt
|        |             ├── MainAppGraph.kt
|        |             ├── NavigationDrawer.kt
|        |             ├── CustomNavType
|        |             └── Routes.kt
|        ├──    ui
|        |        ├── home   
|        |        |       ├── HomeScreen.kt
|        |        |       └── HomeViewModel.kt
|        |        ├── items   
|        |        |        ├── ItemsScreen.kt
|        |        |        └── ItemsViewModel.kt
|        |        ├── sellers   
|        |        |          ├── SellersScreen.kt
|        |        |          └── SellersViewModel.kt
|        |        ├── seller_details   
|        |        |                 ├── SellerDetailsScreen.kt
|        |        |                 └── SellerDetailsViewModel.kt
|        |        ├── chat
|        |        |       ├── ChatScreen.kt
|        |        |       └── ChatViewModel.kt
|        |        ├── theme
|        |        |        ├── Color.kt
|        |        |        ├── Theme.kt
|        |        |        └── Type.kt
|        ├── util
|        |        └── Constants
|        ├── FarmLinkApp.kt
|        ├── AppViewModel.kt
|        ├── FarmLinkApplication
|        └── MainActivity          // App Entry Point
```

## Todos
- Implement Navigation Drawer
- String resources
- Implement sign out and delete account features
- Brand Logo

## Version Info and Libraries Used:
- Kotlin (2.0.10) and Compose Compiler (1.6.8)
- Compose Navigation (2.8.0-rc01)
- Kotlin Serialization (1.7.1) for type-safe navigation
- Coil (2.7.0) for asynchronous image loading
- Dagger-Hilt (2.52) and Hilt Navigation (1.2.0) for dependency injection
- MongoDB Realm (2.0.0) for local persistence of data
 
