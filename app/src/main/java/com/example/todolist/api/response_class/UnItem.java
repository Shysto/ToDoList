package com.example.todolist.api.response_class;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/** Définition de la classe UnItem.
 * Cette classe structure un item tel que conçu dans l'API, et est utilisée dans la classe Items
 */
@Entity(tableName = "items", foreignKeys = @ForeignKey(entity = UneListe.class,
        parentColumns = "id",
        childColumns = "idListe"))
public class UnItem {

    /**
     * L'identifiant de l'item
     */
    @PrimaryKey
    @SerializedName("id")
    public int id;

    /**
     * Le label de l'item
     */
    @ColumnInfo(name = "label")
    @SerializedName("label")
    public String label;

    /**
     * Indique si l'item est fait ou non (vaut 1 si fait, vaut n'importe quel autre entier sinon)
     */
    @ColumnInfo(name = "checked")
    @SerializedName("checked")
    public int checked;

    @ColumnInfo(name = "idListe")
    @SerializedName("idList")
    public int idListe;

}
