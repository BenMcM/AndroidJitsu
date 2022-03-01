package jitsu.ben.uk.consumerest.debug;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseDebug {

    Context context;
    String dbName = "JitsuDB";

    public DatabaseDebug(Context context) {
        this.context = context;
    }

    public void getDB() {
        File f = context.getDatabasePath(dbName);
        FileInputStream fis = null;
        FileOutputStream fos = null;

        File externalDir = context.getExternalFilesDir("db");

        try {
            fis = new FileInputStream(f);
            fos = new FileOutputStream(externalDir.getAbsolutePath() + "/jitsu.db");
            while (true) {
                int i = fis.read();
                if (i != -1) {
                    fos.write(i);
                } else {
                    break;
                }
            }
            fos.flush();
            System.out.println("Happy days");
        } catch (FileNotFoundException e) {
            System.out.println("DB Not found");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
                fis.close();
            } catch (IOException ioe) {
                System.out.println("Oh Dear");
            }
        }
    }

    public void importDB(){

        try {
            InputStream myInput = context.getAssets().open("jitsu.db");


            //Open the old db as the output stream
            OutputStream myOutput = new FileOutputStream(context.getDatabasePath(dbName));

            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();

            System.out.println("done");

        }catch (IOException e){
            System.out.println("Oops");
            e.printStackTrace();
        }
    }
}
