package com.example.study_rfd40_test_01;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.zebra.rfid.api3.ENUM_TRANSPORT;
import com.zebra.rfid.api3.InvalidUsageException;
import com.zebra.rfid.api3.OperationFailureException;
import com.zebra.rfid.api3.RFIDReader;
import com.zebra.rfid.api3.ReaderDevice;
import com.zebra.rfid.api3.Readers;

import com.zebra.rfid.api3.*;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static Readers readers;
    private static ArrayList availableRFIDReaderList;
    private static ReaderDevice readerDevice;
    private static RFIDReader reader;
    private static String TAG = "DEMO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (readers == null) {
            readers = new Readers(this, ENUM_TRANSPORT.ALL);
        }

        try {
            if (readers != null) {
                if (readers.GetAvailableRFIDReaderList() != null) {
                    availableRFIDReaderList = readers.GetAvailableRFIDReaderList();
                    if (availableRFIDReaderList.size() != 0) {
                        // get first reader from list
                        readerDevice = (ReaderDevice) availableRFIDReaderList.get(0);
                        reader = readerDevice.getRFIDReader();
                        if (!reader.isConnected()) {
                            // Establish connection to the RFID Reader
                            reader.connect();
                        }
                    }
                }
            }
        } catch (InvalidUsageException e) {
            e.printStackTrace();
        } catch (OperationFailureException e) {
            e.printStackTrace();
            Log.d(TAG, "OperationFailureException " + e.getVendorMessage());
        }
    }
}