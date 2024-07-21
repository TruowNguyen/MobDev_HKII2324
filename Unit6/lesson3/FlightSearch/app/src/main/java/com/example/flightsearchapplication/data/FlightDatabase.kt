package com.example.flightsearchapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {

    }
}

@Database(
    entities = [Airport::class, Favorite::class],
    version = 1,
    exportSchema = false

)
abstract class FlightDatabase: RoomDatabase() {
    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao

    abstract fun favoriteAirportDao(): FavoriteAirportDao
    abstract fun airportFavoriteDao(): AirportFavoriteDao

    companion object{
        private var Instance: FlightDatabase? = null

        fun getInstance(context: Context): FlightDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    FlightDatabase::class.java,
                    "flight_database"
                )
                    .createFromAsset("database/flight_search.db")
                    //.fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also {
                        Instance = it
                    }
            }
        }
    }
}