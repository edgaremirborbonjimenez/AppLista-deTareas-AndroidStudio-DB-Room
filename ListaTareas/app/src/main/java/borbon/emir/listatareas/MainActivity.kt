package borbon.emir.listatareas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.room.Room

class MainActivity : AppCompatActivity() {

    lateinit var et_tarea: EditText
    lateinit var btn_agregar: Button
    lateinit var listview_tareas: ListView
    lateinit var list_tareas: ArrayList<String>
    lateinit var adaptor:ArrayAdapter<String>
    lateinit var db: AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_tarea = findViewById(R.id.et_tarea)
        btn_agregar = findViewById(R.id.btn_agregar)
        listview_tareas = findViewById(R.id.listview_tareas)

        list_tareas = ArrayList()

        db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,"tareas-db"
        ).allowMainThreadQueries().build()

        cargarTareas()

        adaptor = ArrayAdapter(this,android.R.layout.simple_list_item_1,list_tareas)
        listview_tareas.adapter = adaptor


        btn_agregar.setOnClickListener {
            var tareaText = et_tarea.text.toString()
            if(!tareaText.isNullOrEmpty()){
                var tarea = Tarea(desc = tareaText)
                db.tareaDao().agregarTarea(tarea)
                list_tareas.add(tareaText)
                adaptor.notifyDataSetChanged()
                et_tarea.setText("")
            } else{
                Toast.makeText(this,"llenar campo",Toast.LENGTH_SHORT).show()
            }
         }

        listview_tareas.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
            var tareaDesc = list_tareas[position]
            var tareaText = et_tarea.text.toString()
            if(tareaText.isNullOrEmpty()){
                eliminarTarea(tareaDesc,position)
            }else{
                actualizarTarea(tareaDesc,tareaText,position)
            }
        }

    }

    private fun eliminarTarea(tareaDesc:String,position:Int){
        var tarea = db.tareaDao().getTareaByDescription(tareaDesc)

        db.tareaDao().eliminarTarea(tarea)
        list_tareas.removeAt(position)
        adaptor.notifyDataSetChanged()
    }

    private fun actualizarTarea(oldDesc:String,newDesc:String,position:Int){
        var tarea = db.tareaDao().getTareaByDescription(oldDesc)

        db.tareaDao().updateDescription(tarea.desc,newDesc)
        list_tareas.set(position,newDesc)
        adaptor.notifyDataSetChanged()
    }

    private fun cargarTareas(){
        var listaTareasDB = db.tareaDao().obtenerTareas()
        for(tarea in listaTareasDB){
            list_tareas.add(tarea.desc)
        }
    }
}