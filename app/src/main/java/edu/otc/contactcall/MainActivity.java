package edu.otc.contactcall;

import static android.content.Intent.*;
import static android.content.Intent.ACTION_CALL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author Daniel
 * @author Joey
 * @author Nick
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hardcoded phone numbers linked to the buttons by index.
        String[] callNumbers = new String[] {
          "5735557060",
          "9888552330",
          "7428882387"
        };
        Button[] buttons = new Button[] {
          findViewById(R.id.btn_call_one),
          findViewById(R.id.btn_call_two),
          findViewById(R.id.btn_call_three)
        };
        // Loops through each contact button and applies logic to the click event.
        for (int i = 0; i < buttons.length; i++) {
            // Final must be created since the index can't be seen outside ClickListener event.
            final int tempIndex = i;
            Button button = buttons[i];

            button.setOnClickListener( v -> {
                // Get device call permission.
                // Prompt device for permission if it's denied.
                if (checkPermission("android.permission.CALL_PHONE", 100)) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[] { "android.permission.CALL_PHONE" }, 100);
                }
                // If permission granted create call intent and start the phone call activity.
                else {
                    // Parse phone number to used in the intent activity.
                    Uri call = Uri.parse("tel:" + callNumbers[tempIndex]);
                    Intent intent = new Intent(ACTION_CALL, call);
                    startActivity(intent);
                }
            });
        }
    }

    // Gets whether the specified permission was denied or granted by the device.
    // Only understands if it's denied, so its return is inversed.
    public boolean checkPermission(String permission, int requestCode)
    {
        return ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED;
    }
}