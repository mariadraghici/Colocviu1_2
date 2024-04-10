package ro.pub.cs.systems.eim.colocviu1_2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Colocviu1_2MainActivity extends AppCompatActivity {
    EditText nextTerm;
    TextView allTerms;
    Button addButton, computeButton;
    private int sum = 0;

    private ActivityResultLauncher<Intent> activityResultLauncher;

    int serviceStatus = 0;
    private IntentFilter intentFilter = new IntentFilter();

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_2_main);
        intentFilter.addAction(Constants.SEND_MESSAGE);

        nextTerm = findViewById(R.id.next_term);
        allTerms = findViewById(R.id.all_terms);
        addButton = findViewById(R.id.add_button);
        computeButton = findViewById(R.id.compute_button);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                 sum  =   result.getData().getExtras().getInt(Constants.SUM);
//                Toast.makeText(this, "The activity returned ok", Toast.LENGTH_LONG).show();
                Toast.makeText(this, "The activity returned " + sum, Toast.LENGTH_LONG).show();
                Log.d("debug", "The activity returned " + sum);

                if (sum > 10 && serviceStatus == 0 || serviceStatus == 1) {
                    Intent intent = new Intent(getApplicationContext(), Colocviu1_2Service.class);
                    intent.putExtra(Constants.SUM_SAVE, sum);
                    getApplicationContext().startService(intent);
                    serviceStatus = 1;
                }
        });

        addButton.setOnClickListener(new ButtonClickListener());
        computeButton.setOnClickListener(new ButtonClickListener());


    }

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.add_button) {
                String term = nextTerm.getText().toString();
                if (term != null && !term.isEmpty()) {
                    String all = allTerms.getText().toString();
                    if (all != null && !all.isEmpty()) {
                        all += "+";
                    }
                    all += term;
                    allTerms.setText(all);
                }
            } else if (v.getId() == R.id.compute_button) {
                Intent intent = new Intent(getApplicationContext(), Colocviu1_2SecondaryActivity.class);
                intent.putExtra(Constants.ALL_TERMS, allTerms.getText().toString());
                activityResultLauncher.launch(intent);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(Constants.SUM_SAVE, sum);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey(Constants.SUM_SAVE)) {
            Toast.makeText(this, "The activity returned " + savedInstanceState.getInt(Constants.SUM_SAVE), Toast.LENGTH_LONG).show();
            Log.d("debug", "I saved the sum and it is  " + savedInstanceState.getInt(Constants.SUM_SAVE));
        }
    }

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Broadcast " + intent.getStringExtra(Constants.BROADCAST_RECEIVER), Toast.LENGTH_LONG).show();
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        registerReceiver(messageBroadcastReceiver, intentFilter);
//    }
//
//    @Override
//    protected void onPause() {
//        unregisterReceiver(messageBroadcastReceiver);
//        super.onPause();
//    }
}