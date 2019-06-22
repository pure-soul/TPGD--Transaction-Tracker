/**
 * Implemented by Rhadjni Phipps
 */

package com.example.rhadjni.lifeplanner.PlannerDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rhadjni.lifeplanner.Tables.Expenses;
import com.example.rhadjni.lifeplanner.Tables.Product;

import java.util.ArrayList;
import java.util.List;


public class SqliteDatabase extends SQLiteOpenHelper {

    private	static final int DATABASE_VERSION =	5;
    private	static final String DATABASE_NAME = "product";

    public static String getTableProducts() {
        return TABLE_PRODUCTS;
    }

    private	static final String TABLE_PRODUCTS = "products";
    private	static final String TABLE_EXPENSES = "expenses";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_PRODUCTNAME = "productname";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_DATE = "Date";
    private static final String ECOLUMN_ID = "_id";
    private static final String ECOLUMN_EXPENSENAME = "expensename";
    private static final String ECOLUMN_AMOUNT = "expenseamount";
    private static final String ECOLUMN_INCOME = "Income";
    private static final String ECOLUMN_CHECKBOX="checked";

    public SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE	TABLE " + TABLE_PRODUCTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_PRODUCTNAME + " TEXT," + COLUMN_QUANTITY + " FLOAT," +COLUMN_DATE + " TEXT" + ")";
        String CREATE_EXPENSE_TABLE = "CREATE	TABLE " + TABLE_EXPENSES + "(" + ECOLUMN_ID + " INTEGER PRIMARY KEY," + ECOLUMN_EXPENSENAME + " TEXT," + ECOLUMN_AMOUNT + " FLOAT," + ECOLUMN_INCOME + " FLOAT," + ECOLUMN_CHECKBOX + " TEXT" + ")";

        db.execSQL(CREATE_EXPENSE_TABLE);
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        onCreate(db);
    }

    public List<Product> listProducts(){
        String sql = "select * from " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Product> storeProducts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                float quantity = Float.parseFloat(cursor.getString(2));
                String date = cursor.getString(3);
                storeProducts.add(new Product(id, name, quantity,date));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }
    public List<Product> dateOrderProducts(){
        String sql = "select * from products ORDER BY(Date)";
        SQLiteDatabase db = this.getReadableDatabase();
        List<Product> storeProducts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                float quantity = Float.parseFloat(cursor.getString(2));
                String date = cursor.getString(3);
                storeProducts.add(new Product(id, name, quantity,date));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }

    public List<Expenses> listExpenses(){
        String sql = "select * from " + TABLE_EXPENSES;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Expenses> storeExpenses = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                float amount = Float.parseFloat(cursor.getString(2));
                float income = Float.parseFloat(cursor.getString(3));
                String checked = cursor.getString(4);
                storeExpenses.add(new Expenses(id, name, amount,income,checked));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeExpenses;
    }

    public List<Expenses> checkedExpenses(){
        String state= "CK";
        String sql = "select * from " + TABLE_EXPENSES + " WHERE " + ECOLUMN_CHECKBOX + " = " + "'"+state+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        List<Expenses> storeExpenses = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                float amount = Float.parseFloat(cursor.getString(2));
                float income = Float.parseFloat(cursor.getString(3));
                String checked = cursor.getString(4);
                storeExpenses.add(new Expenses(id, name, amount,income,checked));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeExpenses;
    }

    public float CalcSpending(){
        String sql = "select * from " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getReadableDatabase();
        //List<Float> storetots = new ArrayList<>();
        float totals=0;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                float quantity = Float.parseFloat(cursor.getString(2));
                String date = cursor.getString(3);
                totals=totals+quantity;
               // storeProducts.add(new Product(id, name, quantity,date));

            }while (cursor.moveToNext());
        }
        cursor.close();
        return totals;
    }
    public float CalcExpenses(){
        String state= "CK";
        String sql = "select * from " + TABLE_EXPENSES + " WHERE " + ECOLUMN_CHECKBOX + " = " + "'"+state+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        //List<Float> storetots = new ArrayList<>();
        float totals=0;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                float amount = Float.parseFloat(cursor.getString(2));
                float income = Float.parseFloat(cursor.getString(3));
                String checked = cursor.getString(4);
                totals=totals+amount;
                // storeProducts.add(new Product(id, name, quantity,date));

            }while (cursor.moveToNext());
        }
        cursor.close();
        return totals;
    }

    public void addProduct(Product product){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.getName());
        values.put(COLUMN_QUANTITY, product.getQuantity());
        values.put(COLUMN_DATE, product.getPurchasedate());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
    }
    public void addExpense(Expenses expense){
        ContentValues values = new ContentValues();
        values.put(ECOLUMN_EXPENSENAME, expense.getName());
        values.put(ECOLUMN_AMOUNT, expense.getAmount());
        values.put(ECOLUMN_INCOME, expense.getTotalincome());
        values.put(ECOLUMN_CHECKBOX,expense.getChecker());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_EXPENSES, null, values);
    }

    public void updateExpense(Expenses expense) {
        ContentValues values = new ContentValues();
        values.put(ECOLUMN_EXPENSENAME, expense.getName());
        values.put(ECOLUMN_AMOUNT, expense.getAmount());
        values.put(ECOLUMN_INCOME, expense.getTotalincome());
        values.put(ECOLUMN_CHECKBOX,expense.getChecker());
        SQLiteDatabase db = this.getWritableDatabase();

        db.update(TABLE_EXPENSES, values, COLUMN_ID + "	= ?", new String[]{String.valueOf(expense.getId())});
    }

    public void updateProduct(Product product){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.getName());
        values.put(COLUMN_QUANTITY, product.getQuantity());
        values.put(COLUMN_DATE, product.getPurchasedate());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_PRODUCTS, values, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(product.getId())});
    }

    public Expenses findExpense(String name){
        String query = "Select * FROM "	+ TABLE_EXPENSES + " WHERE " + ECOLUMN_EXPENSENAME + " = " + "name";
        SQLiteDatabase db = this.getWritableDatabase();
        Expenses mExpenses = null;
        Cursor cursor = db.rawQuery(query,	null);
        if	(cursor.moveToFirst()){
            int id = Integer.parseInt(cursor.getString(0));
            String expenseName = cursor.getString(1);
            float amount = Float.parseFloat(cursor.getString(2));
            float income = Float.parseFloat(cursor.getString(3));
            String checker = cursor.getString(4);

            mExpenses = new Expenses(id, expenseName, amount,income,checker);
        }
        cursor.close();
        return mExpenses;
    }

    public float getIncome(Expenses expense){
        float fincome=expense.getTotalincome();

        return fincome;

    }

    public Product findProduct(String name){
        String query = "Select * FROM "	+ TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + " = " + "name";
        SQLiteDatabase db = this.getWritableDatabase();
        Product mProduct = null;
        Cursor cursor = db.rawQuery(query,	null);
        if	(cursor.moveToFirst()){
            int id = Integer.parseInt(cursor.getString(0));
            String productName = cursor.getString(1);
            float productQuantity = Float.parseFloat(cursor.getString(2));
            String date = cursor.getString(3);
            mProduct = new Product(id, productName, productQuantity,date);
        }
        cursor.close();
        return mProduct;
    }

    public void deleteProduct(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }

    public void deleteExpense(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPENSES, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }
    public void ClearDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS,"1",null);

    }
    public void ClearExpensesDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPENSES,"1",null);

    }

}
