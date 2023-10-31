package com.financas.model.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.financas.model.entities.Cartao;
import com.financas.model.entities.Lancamento;

import java.util.List;

@Database(entities = {Lancamento.class, Cartao.class}, version = 5)
public abstract class DatabaseHelper extends RoomDatabase {

    public abstract LancamentoDao lancamentoDao();

    public abstract CartaoDao cartaoDao();

    public static DatabaseHelper getInstance(Context context) {
        return Room.databaseBuilder(context, DatabaseHelper.class, "financas.db")
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_3_4, MIGRATION_4_5)
                .build();
    }

    @Dao
    public interface LancamentoDao {

        @Insert
        void insert(Lancamento lancamento);

        @Update
        void update(Lancamento lancamento);

        @Query("SELECT * FROM LANCAMENTOS")
        List<Lancamento> findAll();

        @Query("SELECT * FROM LANCAMENTOS WHERE diaVencimento > 0 AND recdesp = -1 AND TOTPARCELAS IS NULL")
        List<Lancamento> findDepesas();

        @Query("SELECT * FROM LANCAMENTOS WHERE diaVencimento > 0 AND recdesp = 1 AND TOTPARCELAS IS NULL")
        List<Lancamento> findReceitas();

        @Query("SELECT * FROM LANCAMENTOS WHERE recdesp = -1 AND TOTPARCELAS IS NOT NULL AND TOTPARCPAG < TOTPARCELAS")
        List<Lancamento> findParcelamentos();
    }

    @Dao
    public interface CartaoDao {

        @Insert
        void insert(Cartao cartao);

        @Update
        void update(Cartao cartao);

        @Delete
        void delete(Cartao cartao);

        @Query("SELECT * FROM CARTOES")
        List<Cartao> findAll();
    }

    public static Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE CARTOES (ID INTEGER NOT NULL PRIMARY KEY, BANCO INTEGER NOT NULL, DESCRICAO TEXT, FECHAMENTO TEXT, VENCIMENTO TEXT, VLRFATURA REAL)");
        }
    };

    public static Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE LANCAMENTOS ADD OBSERVACAO TEXT");
        }
    };
}