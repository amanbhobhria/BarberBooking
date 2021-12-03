package android.example.barberbooking.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.barberbooking.R;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class HelpAssistantActivity extends AppCompatActivity {
Button proceedBtn;
EditText queryTxt;
Button callBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_assistant);

        initialise();
        callForHelp();
        mail();
    }

    private void mail() {
        proceedBtn.setOnClickListener(v -> {
            if(queryTxt.getText().toString().isEmpty())
            {
                Toast.makeText(HelpAssistantActivity.this,"Enter a query to proceed",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"bhobhriaaman333@gmail.com" });
                email.putExtra(Intent.EXTRA_SUBJECT, "Stylist Booking Query");
                email.putExtra(Intent.EXTRA_TEXT, queryTxt.getText().toString());

//need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });

    }

    private void callForHelp() {


            callBtn.setOnClickListener(v -> {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + "95181 56020"));//change the number
                    startActivity(callIntent);
                }
                catch (Exception e)
                    {
                        Toast.makeText(HelpAssistantActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                    }
            });
        }




    private void initialise() {
        proceedBtn = findViewById(R.id.proceedBtn);
        queryTxt = findViewById(R.id.queryTxt);
        callBtn = findViewById(R.id.callHelpBtn);


    }
}