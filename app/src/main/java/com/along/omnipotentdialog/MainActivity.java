package com.along.omnipotentdialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.along.dialoglibrary.OmnipotentDialog;
import com.along.dialoglibrary.ViewHelper;

import static com.along.omnipotentdialog.R.id.tv_word;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvHello;
    private TextView tvWorld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHello = (TextView) findViewById(R.id.tv_hello);
        tvWorld = (TextView) findViewById(tv_word);

        tvWorld.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new OmnipotentDialog.Builder(this)
                .setContentView(R.layout.dialog_test)
                .setText(R.id.et_input, "abc")
                .addOnclickListener(R.id.bt_click, new OmnipotentDialog.OnClickListener() {

                    @Override
                    public void onClick(ViewHelper helper, View view) {
                        TextView input = helper.findViewById(R.id.et_input);
                        tvHello.setText("点击:" + input.getText());
                        Toast.makeText(MainActivity.this, "点击:" + input.getText(), Toast.LENGTH_SHORT).show();
                    }
                })
                .matchWidth(true)
//                .matchHeight(true)
//                .matchParent()
                .setGravity(Gravity.BOTTOM)
                .setAnimation(R.style.Animation_Dialog_Scale)
                .setCancelable(false)
                .show();
    }
}
