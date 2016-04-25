package fr.zyra.training.inventaire;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.math.BigDecimal;

public class PremiereActivite extends Activity implements View.OnClickListener {

    BigDecimal compteur = new BigDecimal(0);
    EditText editText;
    Button increaseButton;
    Button decreaseButton;
    ProgressBar progressBar;
    ImageView imageView;
    final BigDecimal POS_STEP = new BigDecimal(1);
    final BigDecimal NEG_STEP = new BigDecimal(-1);
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.edit_text_compteur);
        increaseButton = (Button) findViewById(R.id.bouton_increase);
        decreaseButton = (Button) findViewById(R.id.bouton_decrease);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        imageView = (ImageView) findViewById(R.id.image_view);

        increaseButton.setOnClickListener(this);
        decreaseButton.setOnClickListener(this);

        // your oncreate code

        IntentFilter filter = new IntentFilter();
        filter.addAction("SOME_ACTION");
        filter.addAction("fr.zyra.training.inventaire.CUSTOM_INTENT");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                imageView.setImageAlpha(Math.round((float)Math.random() * 255));
            }
        };
        registerReceiver(receiver, filter);

    }

    @Override
    public void onClick(View v) {
        // Toast.makeText(PremiereActivite.this.getApplicationContext(), (v.getId() == R.id.bouton_increase) ? "inc" : "dec", Toast.LENGTH_SHORT).show();

        try {
            compteur = new BigDecimal(editText.getText().toString());
        } catch (Exception e) {
            Log.e("android training", compteur.toString());
        }

        switch (v.getId()) {
            case R.id.bouton_decrease:
                compteur = compteur.add(NEG_STEP);
                break;
            case R.id.bouton_increase:
                compteur = compteur.add(POS_STEP);
                break;
            default:
                // do nothing
                break;
        }
        sendIntentForFun();

        editText.setText(compteur.toString());


    }

    private void sendIntentForFun(){
        System.out.println("sendintent");
        Intent intent = new Intent();
        intent.setAction("fr.zyra.training.inventaire.CUSTOM_INTENT");
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }


}
