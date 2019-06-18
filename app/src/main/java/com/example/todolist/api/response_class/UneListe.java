package com.example.todolist.api.response_class;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/** Définition de la classe UneListe.
 * Cette classe structure une ToDoList telle que conçue dans l'API, et est utilisée dans la classe
 *          Lists
 */
@Entity(tableName = "lists", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "idUser"))
public class UneListe {

    /**
     * L'identifiant de la ToDoList
     */
    @PrimaryKey
    @SerializedName("id")
    public int id;

    /**
     * La titre (label) associé à la ToDoList
     */
    @ColumnInfo(name = "label")
    @SerializedName("label")
    public String titreListeToDO;

    @ColumnInfo(name = "idUser")
    @SerializedName("idUser")
    public int idUser;
}
