package borbon.emir.listatareas

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TareaDAO {

    @Query("SELECT * FROM tareas")
    fun obtenerTareas(): List<Tarea>

    @Query("SELECT * from tareas where `desc` = :description")
    fun getTareaByDescription(description:String):Tarea

    @Insert
    fun agregarTarea(tarea:Tarea)

    @Delete
    fun eliminarTarea(tarea:Tarea)

    @Query("UPDATE tareas SET `desc` = :newDesc WHERE `desc`=:oldDesc")
    fun updateDescription(oldDesc:String,newDesc:String)
}