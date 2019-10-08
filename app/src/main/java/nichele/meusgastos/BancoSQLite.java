package nichele.meusgastos;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import nichele.meusgastos.Classes.Categoria;
import nichele.meusgastos.Classes.Conta;
import nichele.meusgastos.Classes.Transacao;

/**
 * Created by vinicius on 09/01/2018.
 */

public class BancoSQLite extends SQLiteOpenHelper {

    /* O nome do arquivo de base de dados no sistema de arquivos */
    private static final String NOME_BD = "meusgastos";
    /* A versão da base de dados que esta classe compreende. */
    private static final int VERSAO_BD = 1;

    private static final String TAG = "log_mg";
    /* Mantém rastreamento do contexto que nós podemos carregar SQL */

    private final Context contexto;

    public BancoSQLite(Context context) {
        super(context, NOME_BD, null, VERSAO_BD);
        this.contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table transacoes("+
                "id int not null" +
                ", data SmallDateTime"+
                ", funcao varchar(1)"+
                ", codconta int"+
                ", codcategoria int"+
                ", descricao varchar(255)"+
                ", valor float"+
                ", primary key(id))");

        db.execSQL("CREATE TABLE contas(" +
                "codconta Int Not Null" +
                ", nome VarChar(30)" +
                ", Primary key(codconta))");

        db.execSQL("CREATE TABLE categorias(" +
                "codcategoria Int Not Null" +
                ", nome VarChar(30)" +
                ", tipo VarChar(1)" +
                ", ordem int" +
                ", Primary key(codcategoria))");

        Log.v(TAG, "criou as tabelas");
//        db.execSQL("CREATE TABLE configuracoes(" +
//                "pedsenha Int," +
//                "senha VarChar(10)," +
//                "confirmacaosenha VarChar(10)," +
//                "email VarChar(255))");

        //inseredadosiniciais();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //onCreate(db);
    }

    public void execute(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL(sql);
    }

    public void zerabanco() {
        //SQLiteDatabase db = this.getWritableDatabase();
        execute("delete from transacoes");
        execute("delete from contas");
        execute("delete from categorias");
        //execute("delete from configuracoes");
        //db.close();
        inseredadosiniciais();


    }

    public void inseredadosiniciais(){
        execute("INSERT INTO contas VALUES(1, 'Minha Conta Corrente')");
        execute("INSERT INTO contas VALUES(2, 'Minha Carteira')");

        execute("INSERT INTO categorias VALUES(0, 'Receitas diversas', 'E', 0)");
        execute("INSERT INTO categorias VALUES(1, 'Salário', 'E', 1)");

        execute("INSERT INTO categorias VALUES(2, 'Despesas diversas', 'S',2)");
        execute("INSERT INTO categorias VALUES(3, 'Comer fora', 'S',3)");
        execute("INSERT INTO categorias VALUES(4, 'Combustível', 'S',4)");
    }

    public String gravaconta(String comando, int codconta, String nome) {
        SQLiteDatabase db = getWritableDatabase();
        String nomedatabela = "contas", nomedachave = "codconta";
        try {
            if (comando == "INC")
                codconta = ultimocodigo(nomedatabela, nomedachave) + 1;

            ContentValues values = new ContentValues();
            values.put(nomedachave, codconta);
            values.put("nome", nome);
            if (comando.toUpperCase().equals("INC")){
                db.insert("contas", null, values);
                Log.v(TAG, "conta gravada");
            }else {
                db.update(nomedatabela, values, nomedachave + " = " + codconta, null);
                Log.v(TAG, "conta atualizada");
            }
            return "Sucesso";

        } catch (Exception e) {
            Log.e("transação", "Erro -> " + e.getMessage());
            return e.getMessage();
        } finally {
            db.close();
        }
    }

    public String gravacategoria(String comando, int codcategoria, String nome, String tipo) {
        SQLiteDatabase db = getWritableDatabase();
        String nomedatabela = "categorias", nomedachave = "codcategoria";
        try {
            if (comando == "INC")
                codcategoria = ultimocodigo(nomedatabela, nomedachave) + 1;

            ContentValues values = new ContentValues();
            values.put(nomedachave, codcategoria);
            values.put("nome", nome);
            values.put("tipo", tipo);
            if (comando.toUpperCase().equals("INC")){
                db.insert(nomedatabela, null, values);
                Log.v(TAG, "categoria gravada");
            }else {
                db.update(nomedatabela, values, nomedachave + " = " + codcategoria, null);
                Log.v(TAG, "categoria atualizada");
            }
            return "Sucesso";

        } catch (Exception e) {
            Log.e("transação", "Erro -> " + e.getMessage());
            return e.getMessage();
        } finally {
            db.close();
        }
    }
    public String gravatransacoes(String situacao, int id, String data, String funcao, int codconta, int codcategoria, String descricao, String valor){
        SQLiteDatabase db = getWritableDatabase();
        try {
            if(situacao == "INC")
                id = ultimocodigo("transacoes", "id")+1;

            ContentValues values = new ContentValues();
            values.put("id", id);
            //values.put("data", data.substring(6,10)+"-"+data.substring(3,5)+"-"+data.substring(0,2));
            values.put("data", data);
            values.put("funcao", funcao);
            values.put("codconta", codconta);
            values.put("codcategoria", codcategoria);
            values.put("descricao", descricao);
            values.put("valor", new rotinas().format(valor));
            if (situacao.toUpperCase().equals("INC"))
                db.insert("transacoes", null, values);
            else
                db.update("transacoes", values, "id = " + id, null);

            return "Sucesso";

        }catch (Exception e){
            Log.e("transação", "Erro -> " + e.getMessage());
            return e.getMessage();
        } finally {
            db.close();
        }

    }

    public ArrayList<Conta> listacontas() {
        ArrayList<Conta> contas = new ArrayList<Conta>();
        try{
            String sql = "SELECT * FROM contas";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            Log.v(TAG, "abriu o cursor");
            if(cursor.moveToFirst()) {
                do {
                    contas.add(new Conta(cursor.getInt(cursor.getColumnIndex("codconta")),
                                    cursor.getString(cursor.getColumnIndex("nome"))
                            )
                    );
                    Log.v(TAG,cursor.getString(cursor.getColumnIndex("nome")));
                } while (cursor.moveToNext());
            }
            cursor.close();
            Log.v(TAG, "fechou o cursor");
            db.close();
            Log.v(TAG, "fechou o banco");
        }catch (Exception ex){
            Log.v(TAG, "deu_merda -> "+ex.getMessage());
        }


        return contas;
    }

    public ArrayList<Categoria> listacategorias(TipoDado tipodado) {
        ArrayList<Categoria> categorias = new ArrayList<Categoria>();
        String sql = "select * from categorias where tipo = ";
        if (tipodado.equals(TipoDado.entradas))
            sql += "'E'";
        else if (tipodado.equals(TipoDado.saidas))
            sql += "'S'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()) {
            do {
                categorias.add(new Categoria(cursor.getInt(cursor.getColumnIndex("codcategoria")),
                                cursor.getString(cursor.getColumnIndex("nome"))
                        )
                );
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categorias;
    }
    ///////////////////////////ROTINAS GLOBAIS///////////////////////////
    public int ultimocodigo(String nomedatabela, String nomedocampo) {
        SQLiteDatabase ult = getReadableDatabase();
        int chave = 0;

        String sql = "SELECT MAX(" + nomedocampo + ") AS chave FROM " + nomedatabela;
        Cursor rstultcodigo = ult.rawQuery(sql, null);
        try {
            if (rstultcodigo.moveToNext()) {
                chave = rstultcodigo.getInt(rstultcodigo.getColumnIndex("chave"));
            }
        } catch (Exception e) {
            System.out.println("Erro -->" + e.getMessage());
            Toast.makeText(contexto,"Falha ao acessar o banco de dados.", Toast.LENGTH_SHORT);
        } finally {

        }
        return chave;
    }

    public int abrircomsenha() {

        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor cursor = db2.rawQuery("select * from sis_configuracoes", null);

        if(cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("pedsenha"));
        }
        cursor.close();
        db2.close();
        return 0;
    }


    public ArrayList<Transacao> getTransacoes(TipoDado listar, String datinicial, String datfinal) {
        ArrayList<Transacao> transacoes = new ArrayList<Transacao>();
        String funcao = "";
        switch (listar){
            case entradas:
                funcao = " and funcao = 'ENT'";
                break;
            case saidas:
                funcao = " and funcao = 'BAI'";
                break;
            case extrato:
                funcao = "";
                break;
        }

        String sql = "SELECT t.*, cnt.nome nomeconta, cat.nome nomecategoria " +
                " FROM Transacoes t, contas cnt, categorias cat" +
                " WHERE t.codconta = cnt.codconta and t.codcategoria = cat.codcategoria" +
                funcao +
                " AND data >= '"+datinicial+"'" +
                " AND data <= '"+datfinal+"'" +
                " ORDER BY data DESC, ordem";
        Log.v("sql", sql);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()) {
            do {
                transacoes.add(new Transacao(cursor.getInt(cursor.getColumnIndex("id")),
                                cursor.getString(cursor.getColumnIndex("data")),
                                cursor.getString(cursor.getColumnIndex("funcao")),
                                new Conta(cursor.getInt(cursor.getColumnIndex("codconta")),
                                        cursor.getString(cursor.getColumnIndex("nomeconta"))),
                                new Categoria(cursor.getInt(cursor.getColumnIndex("codcategoria")),
                                        cursor.getString(cursor.getColumnIndex("nomecategoria"))),
                                cursor.getString(cursor.getColumnIndex("descricao")),
                                cursor.getFloat(cursor.getColumnIndex("valor"))
                        )
                );
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return transacoes;
    }
}

