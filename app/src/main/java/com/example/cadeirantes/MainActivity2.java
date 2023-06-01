package com.example.cadeirantes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    Button notifyBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        final ImageButton irmapa = findViewById(R.id.btnmapa);
        final ImageButton btnQuemSomos = findViewById(R.id.btnQuemSomos);
        final TextView irmapatxt = findViewById(R.id.textView);
        final TextView quemsomostxt = findViewById(R.id.textView4);
        final Button voltar = findViewById(R.id.button);
        //tela quem somos
final ImageView quemsomos = findViewById(R.id.imageView2);
final TextView txtquemsomos = findViewById(R.id.textView2);
quemsomos.setVisibility(View.GONE);
txtquemsomos.setVisibility(View.GONE);
        voltar.setVisibility(View.GONE);





        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

            irmapa.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(MainActivity2.this, MapsActivity.class);
                    startActivity(intent);

                } catch (Exception e) {
                    Toast.makeText(MainActivity2.this, "Não é possível abrir o mapa", Toast.LENGTH_SHORT).show();

                }

            });


//            final ImageButton btnQuemSomos = findViewById(R.id.btnQuemSomos);
            btnQuemSomos.setOnClickListener(view -> {

                irmapa.setVisibility(View.GONE);
                irmapatxt.setVisibility(View.GONE);
btnQuemSomos.setVisibility(View.GONE);
quemsomos.setVisibility(View.VISIBLE);
                Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
                quemsomos.startAnimation(aniFade);
                quemsomostxt.startAnimation(aniFade);
                txtquemsomos.startAnimation(aniFade);
                txtquemsomos.setVisibility(View.VISIBLE);
                voltar.setVisibility(View.VISIBLE);
                voltar.startAnimation(aniFade);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity2.this, "My Notification");
                builder.setContentTitle("Quem Somos Acessado!");
                builder.setContentText("Descrição Acessada com Sucesso!");
                builder.setSmallIcon(R.drawable.a6175);
                builder.setAutoCancel(true);
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity2.this);
                managerCompat.notify(1, builder.build());
            });

            voltar.setOnClickListener(view -> {
                Animation aniFade2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadeout);
                Animation aniFade3 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
                irmapa.setVisibility(View.VISIBLE);
                irmapa.startAnimation(aniFade3);
                irmapatxt.setVisibility(View.VISIBLE);
                irmapa.startAnimation(aniFade3);
                btnQuemSomos.setVisibility(View.VISIBLE);
                irmapa.startAnimation(aniFade3);
                quemsomos.setVisibility(View.GONE);

                txtquemsomos.setVisibility(View.GONE);

                voltar.setVisibility(View.GONE);

            });


            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
            getSupportActionBar().hide();
        }

    }
}



