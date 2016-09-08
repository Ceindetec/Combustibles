package co.org.ceindetec.combustible.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import co.org.ceindetec.combustible.entities.Usuario;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "combustible_ceidentec.db";
    public static final int DATABASE_VERSION = 1;
    public static final String USUARIO_TABLE_NAME = "usuario";
    public static final String USUARIO_COLUMN_ID = "usuario_id";
    public static final String USUARIO_COLUMN_NOMBRE = "usuario_nombre";
    public static final String USUARIO_COLUMN_CODIGO_SEGURIDAD = "usuario_codigo_seguridad";

    public static final String PRECIO_TABLE_NAME = "precio";
    public static final String PRECIO_COLUMN_ID = "precio_id";
    public static final String PRECIO_COLUMN_NOMBRE = "precio_nombre";
    public static final String PRECIO_COLUMN_VALOR = "precio_valor";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Sobreescritura de la interface para SQLiteOpenHelper para el evento obtenerListadoUsuarios
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS  " + USUARIO_TABLE_NAME +
                        "("
                        + USUARIO_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + USUARIO_COLUMN_NOMBRE + " TEXT, "
                        + USUARIO_COLUMN_CODIGO_SEGURIDAD + " TEXT);"
        );
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS  " + PRECIO_TABLE_NAME +
                        "("
                        + PRECIO_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + PRECIO_COLUMN_NOMBRE + " TEXT, "
                        + PRECIO_COLUMN_VALOR + " TEXT);"
        );
    }

    /**
     * Sobreescritura de la interface para SQLiteOpenHelper para el evento onUpgrade
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + USUARIO_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PRECIO_TABLE_NAME);
        onCreate(db);
    }

    /**
     * Metodo para la creacion de un usuario en la aplicacion
     *
     * @param nombreUsuario
     * @param codigoSeguridadUsuario
     * @return
     */
    public boolean insertUsuario(String nombreUsuario, String codigoSeguridadUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USUARIO_COLUMN_NOMBRE, nombreUsuario);
        contentValues.put(USUARIO_COLUMN_CODIGO_SEGURIDAD, codigoSeguridadUsuario);
        db.insert(USUARIO_TABLE_NAME, null, contentValues);
        return true;
    }

    /**
     * Metodo para la actualizacion de un usuario en la aplicacion
     *
     * @param idUsuario
     * @param nombreUsuario
     * @param codigoSeguridadUsuario
     * @return
     */
    public boolean updateUsuario(Integer idUsuario, String nombreUsuario, String codigoSeguridadUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USUARIO_COLUMN_NOMBRE, nombreUsuario);
        contentValues.put(USUARIO_COLUMN_CODIGO_SEGURIDAD, codigoSeguridadUsuario);
        db.update(USUARIO_TABLE_NAME, contentValues, USUARIO_COLUMN_ID + "  ? ", new String[]{Integer.toString(idUsuario)});
        return true;
    }

    /**
     * Metodo para la eliminacion de un usuario en la aplicacion
     *
     * @param idUsuario
     * @return
     */
    public Integer deleteUsuario(int idUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USUARIO_TABLE_NAME, USUARIO_COLUMN_ID + " = ? ", new String[]{Integer.toString(idUsuario)});
    }

    /**
     * Metodo para obtener los datos de un usuario en la aplicacion
     *
     * @param idUsuario
     * @return
     */
    public Cursor getDataUsuario(int idUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorResult = db.rawQuery("SELECT * FROM " + USUARIO_TABLE_NAME + " WHERE " + USUARIO_COLUMN_ID + " = " + idUsuario + "", null);
        return cursorResult;
    }

    /**
     * Metodo para verificar los datos de un usuario en la aplicacion
     *
     * @param nombreUsuario
     * @param codigoSeguridadUsuario
     * @return
     */
    public Cursor verifyUsuario(String nombreUsuario, String codigoSeguridadUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorResult = db.rawQuery("SELECT " + USUARIO_COLUMN_ID + " FROM " + USUARIO_TABLE_NAME + " WHERE " + USUARIO_COLUMN_NOMBRE + " = '" + nombreUsuario + "' AND " + USUARIO_COLUMN_CODIGO_SEGURIDAD + " = '" + codigoSeguridadUsuario + "';", null);
        return cursorResult;
    }

    /**
     * Metodo para verificar los datos de un usuario en la aplicacion
     *
     * @param nombreUsuario
     * @param codigoSeguridadUsuario
     * @return
     */
    public int getIdUsuario(String nombreUsuario, String codigoSeguridadUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorResult = db.rawQuery("SELECT " + USUARIO_COLUMN_ID + " FROM " + USUARIO_TABLE_NAME + " WHERE " + USUARIO_COLUMN_NOMBRE + " = '" + nombreUsuario + "' AND " + USUARIO_COLUMN_CODIGO_SEGURIDAD + " = '" + codigoSeguridadUsuario + "';", null);
        cursorResult.moveToFirst();
        if (cursorResult.isAfterLast() == false && cursorResult.getCount() == 1) {
            return cursorResult.getInt(cursorResult.getColumnIndex(USUARIO_COLUMN_ID));
        } else {
            return 0;
        }

    }

    /**
     * Metodo para la obtencion de los datos de todos los usuarios en la aplicacion
     *
     * @return
     */
    public List<Usuario> getDataAllUsuario() {

        List<Usuario> usuarioList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + USUARIO_TABLE_NAME, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            Usuario usuario = new Usuario(
                    Integer.valueOf(res.getString(res.getColumnIndex(USUARIO_COLUMN_ID))),
                    res.getString(res.getColumnIndex(USUARIO_COLUMN_NOMBRE)),
                    res.getString(res.getColumnIndex(USUARIO_COLUMN_CODIGO_SEGURIDAD))
            );
            usuarioList.add(usuario);
            res.moveToNext();
        }
        return usuarioList;
    }

    /**
     * Metodo para la obtencion del numero de filas de usuarios en la aplicacion
     *
     * @return
     */
    public int numberOfRowsUsuario() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, USUARIO_TABLE_NAME);
        return numRows;
    }

    /**
     * Metodo para la creacion de un precio en la aplicacion
     *
     * @param nombrePrecio
     * @param valorPrecio
     * @return
     */
    public boolean insertPrecio(String nombrePrecio, String valorPrecio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRECIO_COLUMN_NOMBRE, nombrePrecio);
        contentValues.put(PRECIO_COLUMN_VALOR, valorPrecio);
        db.insert(PRECIO_TABLE_NAME, null, contentValues);
        return true;
    }

    /**
     * Metodo para la actualizacion de un precio en la aplicacion
     *
     * @param idPrecio
     * @param nombrePrecio
     * @param valorPrecio
     * @return
     */
    public boolean updatePrecio(Integer idPrecio, String nombrePrecio, String valorPrecio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRECIO_COLUMN_NOMBRE, nombrePrecio);
        contentValues.put(PRECIO_COLUMN_VALOR, valorPrecio);
        db.update(PRECIO_TABLE_NAME, contentValues, PRECIO_COLUMN_ID + "  ? ", new String[]{Integer.toString(idPrecio)});
        return true;
    }

    /**
     * Metodo para la eliminacion de un precio en la aplicacion
     *
     * @param idPrecio
     * @return
     */
    public Integer deletePrecio(Integer idPrecio) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PRECIO_TABLE_NAME,
                PRECIO_COLUMN_ID + " = ? ",
                new String[]{Integer.toString(idPrecio)});
    }

    /**
     * Metodo para la obtencion de un precio en la aplicacion
     *
     * @param idPrecio
     * @return
     */
    public Cursor getDataPrecio(int idPrecio) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorResult = db.rawQuery("SELECT * FROM " + PRECIO_TABLE_NAME + " WHERE " + PRECIO_COLUMN_ID + " = " + idPrecio + ";", null);
        return cursorResult;
    }

    /**
     * Metodo para la obtencion de todos los precios en la aplicacion
     *
     * @return
     */
    public ArrayList<String> getDataAllPrecio() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + PRECIO_TABLE_NAME + ";", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(PRECIO_COLUMN_ID)));
            res.moveToNext();
        }
        return array_list;
    }

    /**
     * Metodo para la obtencion del numero de filas de precios en la aplicacion
     *
     * @return
     */
    public int numberOfRowsPrecio() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PRECIO_TABLE_NAME);
        return numRows;
    }

}
