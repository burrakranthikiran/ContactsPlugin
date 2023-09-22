package com.kranthi.contacts;
import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import android.widget.Toast;
import android.util.Log;

import java.util.ArrayList;


@CapacitorPlugin(name = "ContactsPlugin")
public class ContactsPluginPlugin extends Plugin {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    private ContactsPlugin implementation = new ContactsPlugin();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }
    @PluginMethod
    public void getContacts(PluginCall call) {
        ArrayList<String> contactNumbers = readContacts(); // Call the method to read contacts

        JSObject ret = new JSObject();
        ret.put("contacts", contactNumbers);
            Log.e("999999999999999999999999",ret.toString());
        call.resolve(ret);
    }
    
       private ArrayList<String> readContacts() {
        ArrayList<String> contactNumbers = new ArrayList<>();

        // Query the contacts database
        Cursor cursor = getActivity().getContentResolver().query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int phoneNumberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String phoneNumber = cursor.getString(phoneNumberIndex);
                contactNumbers.add(phoneNumber);
            } while (cursor.moveToNext());
            cursor.close();
        }
        

        return contactNumbers;
    }
    }
