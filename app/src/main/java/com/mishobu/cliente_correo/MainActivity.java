package com.mishobu.cliente_correo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    private EditText asunto, para, contenido;
    String as, pa, con;
    SharedPreferences misDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        para = (EditText) findViewById(R.id.para);
        asunto = (EditText) findViewById(R.id.asunto);
        contenido = (EditText) findViewById(R.id.contenido);
        misDatos = getSharedPreferences("Preferencias", MODE_PRIVATE);

        pa = misDatos.getString("Para: ", "");
        as = misDatos.getString("Asunto: ", "");
        con = misDatos.getString("Contenido", "");

        para.setText(pa);
        asunto.setText(as);
        contenido.setText(con);
    }

    public void composeEmail(View v) {

        String to = para.getText().toString();
        String subject = asunto.getText().toString();
        String message = contenido.getText().toString();

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
        // email.putExtra(Intent.EXTRA_CC, new String[]{ to});
        // email.putExtra(Intent.EXTRA_BCC, new String[]{to});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);

        // need this to prompts email client only
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

    @Override
    protected void onPause() {
        super.onPause();

        as = asunto.getText().toString();
        pa = para.getText().toString();
        con = contenido.getText().toString();

        SharedPreferences.Editor miEditor = misDatos.edit();
        miEditor.putString("Para: ", pa);
        miEditor.putString("Asunto: ", as);
        miEditor.putString("Contenido", con);

        miEditor.commit();
    }

}
