package jitsu.ben.uk.consumerest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JitsuSQLiteHelper extends SQLiteOpenHelper {

	public static final String COLUMN_ID = "_id";

    public static final String GRADE_TABLE_NAME = "grade";
    public static final String GRADE_COLUMN_ID = "_id";
    public static final String GRADE_COLUMN_NAME = "name";
    public static final String GRADE_COLUMN_ORDERING = "ordering";

    public static final String ANKLELOCK_TABLE_NAME = "ankle_lock";
    public static final String ANKLELOCK_COLUMN_ID = "_id";
    public static final String ANKLELOCK_COLUMN_NUMBER = "number";
    public static final String ANKLELOCK_COLUMN_DESCRIPTION = "description";

    public static final String ARMLOCK_TABLE_NAME = "arm_lock";
    public static final String ARMLOCK_COLUMN_ID = "_id";
    public static final String ARMLOCK_COLUMN_NUMBER = "number";
    public static final String ARMLOCK_COLUMN_DESCRIPTION = "description";
    public static final String ARMLOCK_COLUMN_ENTRANCE = "entrance";

	public static final String ARMLOCKCOUNTER_TABLE_NAME = "arm_lock_counter";
	public static final String ARMLOCKCOUNTER_COLUMN_ID = "_id";
	public static final String ARMLOCKCOUNTER_COLUMN_NUMBER = "number";
	public static final String ARMLOCKCOUNTER_COLUMN_DESCRIPTION = "description";

    public static final String FINGERLOCK_TABLE_NAME = "finger_lock";
    public static final String FINGERLOCK_COLUMN_ID = "_id";
    public static final String FINGERLOCK_COLUMN_NUMBER = "number";
    public static final String FINGERLOCK_COLUMN_DESCRIPTION = "description";

	public static final String FINGERLOCKAPPLICATION_TABLE_NAME = "finger_lock_application";
	public static final String FINGERLOCKAPPLICATION_COLUMN_ID = "_id";
	public static final String FINGERLOCKAPPLICATION_COLUMN_NUMBER = "number";
	public static final String FINGERLOCKAPPLICATION_COLUMN_DESCRIPTION = "description";

	public static final String GROUNDWORK_TABLE_NAME = "ground_work";
	public static final String GROUNDWORK_COLUMN_ID = "_id";
	public static final String GROUNDWORK_COLUMN_NAME = "name";
	public static final String GROUNDWORK_COLUMN_TRANSLATION = "translation";
	public static final String GROUNDWORK_COLUMN_STARTPOSITION = "start_position";
	public static final String GROUNDWORK_COLUMN_DESCRIPTION = "description";

    public static final String KATA_TABLE_NAME = "kata";
    public static final String KATA_COLUMN_ID = "_id";
    public static final String KATA_COLUMN_NAME = "name";
    public static final String KATA_COLUMN_TRANSLATION = "translation";

    public static final String KATASTEP_TABLE_NAME = "kata_step";
    public static final String KATASTEP_COLUMN_ID = "_id";
    public static final String KATASTEP_COLUMN_KATA_ID = "kata_id";
    public static final String KATASTEP_COLUMN_STEP_NUMBER = "step_number";
    public static final String KATASTEP_COLUMN_DESCRIPTION = "description";

    public static final String KYUSHO_TABLE_NAME = "kyusho";
    public static final String KYUSHO_COLUMN_ID = "_id";
    public static final String KYUSHO_COLUMN_NUMBER = "number";
    public static final String KYUSHO_COLUMN_DESCRIPTION = "description";

    public static final String LEGLOCK_TABLE_NAME = "leg_lock";
    public static final String LEGLOCK_COLUMN_ID = "_id";
    public static final String LEGLOCK_COLUMN_NUMBER = "number";
    public static final String LEGLOCK_COLUMN_DESCRIPTION = "description";

	public static final String LEGLOCKCOUNTER_TABLE_NAME = "leg_lock_counter";
	public static final String LEGLOCKCOUNTER_COLUMN_ID = "_id";
	public static final String LEGLOCKCOUNTER_COLUMN_NUMBER = "number";
	public static final String LEGLOCKCOUNTER_COLUMN_DESCRIPTION = "description";

    public static final String NECKLOCK_TABLE_NAME = "neck_lock";
    public static final String NECKLOCK_COLUMN_ID = "_id";
    public static final String NECKLOCK_COLUMN_NUMBER = "number";
    public static final String NECKLOCK_COLUMN_DESCRIPTION = "description";

    public static final String THROW_TABLE_NAME = "throw";
    public static final String THROW_COLUMN_ID = "_id";
    public static final String THROW_COLUMN_NAME = "name";
    public static final String THROW_COLUMN_TRANSLATION = "translation";
    public static final String THROW_COLUMN_DESCRIPTION = "description";
    public static final String THROW_COLUMN_ENTRANCE = "entrance";
    public static final String THROW_COLUMN_VARIATION = "variation";

    public static final String WRISTLOCK_TABLE_NAME = "wrist_lock";
    public static final String WRISTLOCK_COLUMN_ID = "_id";
    public static final String WRISTLOCK_COLUMN_NUMBER = "number";
    public static final String WRISTLOCK_COLUMN_DESCRIPTION = "description";
    public static final String WRISTLOCK_COLUMN_ENTRANCE = "entrance";

    public static final String FOREIGN_GRADE_ID = "grade_id";

    private static final String DATABASE_NAME = "JitsuDB";
    private static final int DATABASE_VERSION = 2;

    public JitsuSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        createTables(db);
    }

    private void createTables(SQLiteDatabase db){
        //Create all the tables

        String CREATE_TABLE_GRADE = "CREATE TABLE IF NOT EXISTS " + GRADE_TABLE_NAME + "("
                + GRADE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + GRADE_COLUMN_NAME + " TEXT, "
                + GRADE_COLUMN_ORDERING + " INTEGER )";

        String CREATE_TABLE_ANKLELOCK = "CREATE TABLE IF NOT EXISTS " + ANKLELOCK_TABLE_NAME + "("
                + ANKLELOCK_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + ANKLELOCK_COLUMN_NUMBER + " INTEGER, "
                + ANKLELOCK_COLUMN_DESCRIPTION + " TEXT,"
                + FOREIGN_GRADE_ID + " INTEGER, "
                + "FOREIGN KEY (" + FOREIGN_GRADE_ID + " ) REFERENCES " + GRADE_TABLE_NAME + "(" + GRADE_COLUMN_ID + "))";

        String CREATE_TABLE_ARMLOCK = "CREATE TABLE IF NOT EXISTS " + ARMLOCK_TABLE_NAME + "("
                + ARMLOCK_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + ARMLOCK_COLUMN_NUMBER + " INTEGER, "
                + ARMLOCK_COLUMN_DESCRIPTION + " TEXT,"
                + ARMLOCK_COLUMN_ENTRANCE + " TEXT,"
                + FOREIGN_GRADE_ID + " INTEGER, "
                + "FOREIGN KEY (" + FOREIGN_GRADE_ID + " ) REFERENCES " + GRADE_TABLE_NAME + "(" + GRADE_COLUMN_ID + "))";

        String CREATE_TABLE_ARMLOCKCOUNTER = "CREATE TABLE IF NOT EXISTS " + ARMLOCKCOUNTER_TABLE_NAME + "("
				+ ARMLOCKCOUNTER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ ARMLOCKCOUNTER_COLUMN_NUMBER + " INTEGER, "
				+ ARMLOCKCOUNTER_COLUMN_DESCRIPTION + " TEXT,"
				+ FOREIGN_GRADE_ID + " INTEGER, "
				+ "FOREIGN KEY (" + FOREIGN_GRADE_ID + " ) REFERENCES " + GRADE_TABLE_NAME + "(" + GRADE_COLUMN_ID + "))";

        String CREATE_TABLE_FINGERLOCK = "CREATE TABLE IF NOT EXISTS " + FINGERLOCK_TABLE_NAME + "("
                + FINGERLOCK_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + FINGERLOCK_COLUMN_NUMBER + " INTEGER, "
                + FINGERLOCK_COLUMN_DESCRIPTION + " TEXT,"
                + FOREIGN_GRADE_ID + " INTEGER, "
                + "FOREIGN KEY (" + FOREIGN_GRADE_ID + " ) REFERENCES " + GRADE_TABLE_NAME + "(" + GRADE_COLUMN_ID + "))";

        String CREATE_TABLE_FINGERLOCKAPPLICATION = "CREATE TABLE IF NOT EXISTS " + FINGERLOCKAPPLICATION_TABLE_NAME + "("
				+ FINGERLOCKAPPLICATION_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ FINGERLOCKAPPLICATION_COLUMN_NUMBER + " INTEGER, "
				+ FINGERLOCKAPPLICATION_COLUMN_DESCRIPTION + " TEXT,"
				+ FOREIGN_GRADE_ID + " INTEGER, "
				+ "FOREIGN KEY (" + FOREIGN_GRADE_ID + " ) REFERENCES " + GRADE_TABLE_NAME + "(" + GRADE_COLUMN_ID + "))";

		String CREATE_TABLE_GROUNDWORK = "CREATE TABLE IF NOT EXISTS " + GROUNDWORK_TABLE_NAME + "("
				+ GROUNDWORK_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ GROUNDWORK_COLUMN_NAME + " TEXT, "
				+ GROUNDWORK_COLUMN_TRANSLATION + " TEXT, "
				+ GROUNDWORK_COLUMN_STARTPOSITION + " TEXT, "
				+ THROW_COLUMN_DESCRIPTION + " TEXT, "
				+ FOREIGN_GRADE_ID + " INTEGER, "
				+ "FOREIGN KEY (" + FOREIGN_GRADE_ID + " ) REFERENCES " + GRADE_TABLE_NAME + "(" + GRADE_COLUMN_ID + "))";

        String CREATE_TABLE_KATA = "CREATE TABLE IF NOT EXISTS " + KATA_TABLE_NAME + "("
                + KATA_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + KATA_COLUMN_NAME + " TEXT, "
                + KATA_COLUMN_TRANSLATION + " TEXT,"
                + FOREIGN_GRADE_ID + " INTEGER, "
                + "FOREIGN KEY (" + FOREIGN_GRADE_ID + " ) REFERENCES " + GRADE_TABLE_NAME + "(" + GRADE_COLUMN_ID + "))";

        String CREATE_TABLE_KATASTEP = "CREATE TABLE IF NOT EXISTS " + KATASTEP_TABLE_NAME + "("
                + KATASTEP_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + KATASTEP_COLUMN_STEP_NUMBER + " INTEGER, "
                + KATASTEP_COLUMN_DESCRIPTION + " TEXT,"
                + KATASTEP_COLUMN_KATA_ID + " INTEGER, "
                + "FOREIGN KEY (" + KATASTEP_COLUMN_KATA_ID + " ) REFERENCES " + KATA_TABLE_NAME + "(" + KATA_COLUMN_ID + "))";

        String CREATE_TABLE_KYUSHU = "CREATE TABLE IF NOT EXISTS " + KYUSHO_TABLE_NAME + "("
                + KYUSHO_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + KYUSHO_COLUMN_NUMBER + " TEXT, "
                + KYUSHO_COLUMN_DESCRIPTION + " TEXT,"
                + FOREIGN_GRADE_ID + " INTEGER, "
                + "FOREIGN KEY (" + FOREIGN_GRADE_ID + " ) REFERENCES " + GRADE_TABLE_NAME + "(" + GRADE_COLUMN_ID + "))";

        String CREATE_TABLE_LEGLOCK = "CREATE TABLE IF NOT EXISTS " + LEGLOCK_TABLE_NAME + "("
                + LEGLOCK_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + LEGLOCK_COLUMN_NUMBER + " INTEGER, "
                + LEGLOCK_COLUMN_DESCRIPTION + " TEXT,"
                + FOREIGN_GRADE_ID + " INTEGER, "
                + "FOREIGN KEY (" + FOREIGN_GRADE_ID + " ) REFERENCES " + GRADE_TABLE_NAME + "(" + GRADE_COLUMN_ID + "))";

		String CREATE_TABLE_LEGLOCKCOUNTER = "CREATE TABLE IF NOT EXISTS " + LEGLOCKCOUNTER_TABLE_NAME + "("
				+ LEGLOCKCOUNTER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ LEGLOCKCOUNTER_COLUMN_NUMBER + " INTEGER, "
				+ LEGLOCKCOUNTER_COLUMN_DESCRIPTION + " TEXT,"
				+ FOREIGN_GRADE_ID + " INTEGER, "
				+ "FOREIGN KEY (" + FOREIGN_GRADE_ID + " ) REFERENCES " + GRADE_TABLE_NAME + "(" + GRADE_COLUMN_ID + "))";

        String CREATE_TABLE_NECKLOCK = "CREATE TABLE IF NOT EXISTS " + NECKLOCK_TABLE_NAME + "("
                + NECKLOCK_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + NECKLOCK_COLUMN_NUMBER + " INTEGER, "
                + NECKLOCK_COLUMN_DESCRIPTION + " TEXT,"
                + FOREIGN_GRADE_ID + " INTEGER, "
                + "FOREIGN KEY (" + FOREIGN_GRADE_ID + " ) REFERENCES " + GRADE_TABLE_NAME + "(" + GRADE_COLUMN_ID + "))";

        String CREATE_TABLE_THROW = "CREATE TABLE IF NOT EXISTS " + THROW_TABLE_NAME + "("
                + THROW_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + THROW_COLUMN_NAME + " TEXT, "
                + THROW_COLUMN_TRANSLATION + " TEXT, "
                + THROW_COLUMN_DESCRIPTION + " TEXT, "
                + THROW_COLUMN_ENTRANCE + " TEXT, "
                + THROW_COLUMN_VARIATION + " TEXT, "
                + FOREIGN_GRADE_ID + " INTEGER, "
                + "FOREIGN KEY (" + FOREIGN_GRADE_ID + " ) REFERENCES " + GRADE_TABLE_NAME + "(" + GRADE_COLUMN_ID + "))";

        String CREATE_TABLE_WRISTLOCK = "CREATE TABLE IF NOT EXISTS " + WRISTLOCK_TABLE_NAME + "("
                + WRISTLOCK_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + WRISTLOCK_COLUMN_NUMBER + " INTEGER, "
                + WRISTLOCK_COLUMN_DESCRIPTION + " TEXT,"
                + FOREIGN_GRADE_ID + " INTEGER, "
                + "FOREIGN KEY (" + FOREIGN_GRADE_ID + " ) REFERENCES " + GRADE_TABLE_NAME + "(" + GRADE_COLUMN_ID + "))";


        db.execSQL(CREATE_TABLE_GRADE);
        db.execSQL(CREATE_TABLE_ANKLELOCK);
        db.execSQL(CREATE_TABLE_ARMLOCK);
        db.execSQL(CREATE_TABLE_ARMLOCKCOUNTER);
        db.execSQL(CREATE_TABLE_FINGERLOCK);
        db.execSQL(CREATE_TABLE_FINGERLOCKAPPLICATION);
        db.execSQL(CREATE_TABLE_GROUNDWORK);
        db.execSQL(CREATE_TABLE_KATA);
        db.execSQL(CREATE_TABLE_KATASTEP);
        db.execSQL(CREATE_TABLE_KYUSHU);
        db.execSQL(CREATE_TABLE_LEGLOCK);
        db.execSQL(CREATE_TABLE_LEGLOCKCOUNTER);
        db.execSQL(CREATE_TABLE_NECKLOCK);
        db.execSQL(CREATE_TABLE_THROW);
        db.execSQL(CREATE_TABLE_WRISTLOCK);
    }
}