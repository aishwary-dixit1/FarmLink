//package com.example.farmlinkapp1.di
//
//import com.example.farmlinkapp1.model.Item
//import com.example.farmlinkapp1.model.Category
//import com.example.farmlinkapp1.model.Seller
//import com.example.farmlinkapp1.model.SaleItems
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import io.realm.kotlin.Realm
//import io.realm.kotlin.RealmConfiguration
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//
//    @Provides
//    @Singleton
//    fun provideRealmDatabase() : Realm {
//        return Realm.open(
//            configuration = RealmConfiguration.create(
//                schema = setOf(
//                    Category::class,
//                    Item::class,
//                    Seller::class,
//                    SaleItems::class
//                )
//            )
//        )
//    }
//}