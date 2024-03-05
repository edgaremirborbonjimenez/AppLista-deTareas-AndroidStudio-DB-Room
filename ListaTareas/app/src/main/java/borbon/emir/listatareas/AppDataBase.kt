package borbon.emir.listatareas

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Tarea::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun tareaDao(): TareaDAO
}