package ro.pub.cs.systems.eim.colocviu1_2;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Colocviu1_2SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String allTerms = intent.getStringExtra(Constants.ALL_TERMS);

        // sum all terms
        String[] terms = allTerms.split("\\+");
        int sum = 0;
        for (String term : terms) {
            sum += Integer.parseInt(term);
        }

        Intent result = new Intent();
        result.putExtra(Constants.SUM, sum);
        setResult(RESULT_OK, result);
        finish();

    }
}