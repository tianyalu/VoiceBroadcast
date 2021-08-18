package com.genlot.voicebroadcastdemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.genlot.voicebroadcast.VoiceBuilder;
import com.genlot.voicebroadcast.VoicePlay;
import com.genlot.voicebroadcast.VoiceTextTemplate;
import com.genlot.voicebroadcast.constant.VoiceConstants;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity {

    private boolean mCheckNum;
    private EditText editText;
    private Button btPlay;
    private Button btDel;
    private LinearLayout llMoneyList;
    private SwitchMaterial switchView;
    private Button btCommunicationError;
    private Button btNotWinning;
    private Button btPrizeGiving;
    private Button btTestAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initClick();
    }

    void initView() {
        editText = findViewById(R.id.edittext);
        btPlay = findViewById(R.id.bt_play);
        btDel = findViewById(R.id.bt_del);
        llMoneyList = findViewById(R.id.ll_money_list);
        switchView = findViewById(R.id.switch_view);
        btCommunicationError = findViewById(R.id.bt_communication_error);
        btNotWinning = findViewById(R.id.bt_not_winning);
        btPrizeGiving = findViewById(R.id.bt_prize_giving);
        btTestAll = findViewById(R.id.bt_test_all);
    }

    void initClick() {
        btPlay.setOnClickListener(view -> {
            String amount = editText.getText().toString().trim();
            if (TextUtils.isEmpty(amount)) {
                Toast.makeText(MainActivity.this, "请输入金额", Toast.LENGTH_SHORT).show();
                return;
            }

            VoicePlay.with(MainActivity.this).play(amount, mCheckNum);
            Log.e("sty", "amount: " + amount + "   checkNum: " + mCheckNum);

            llMoneyList.addView(getTextView(amount), 0);
            editText.setText("");
        });

        btCommunicationError.setOnClickListener(v -> {
            VoiceBuilder voiceBuilder = new VoiceBuilder.Builder()
                    .start(VoiceConstants.COMMUNICATION_ERROR)
                    .builder();
            VoicePlay.with(MainActivity.this).play(voiceBuilder);
        });

        btNotWinning.setOnClickListener(v -> {
            VoiceBuilder voiceBuilder = new VoiceBuilder.Builder()
                    .start(VoiceConstants.NOT_WINNING)
                    .builder();
            VoicePlay.with(MainActivity.this).play(voiceBuilder);
        });

        btPrizeGiving.setOnClickListener(v -> {
            VoiceBuilder voiceBuilder = new VoiceBuilder.Builder()
                    .start(VoiceConstants.PRIZE_GIVING)
                    .builder();
            VoicePlay.with(MainActivity.this).play(voiceBuilder);
        });

        btTestAll.setOnClickListener(v -> {
            VoicePlay.with(MainActivity.this).play("12345670.89", mCheckNum);
        });

        btDel.setOnClickListener(view -> llMoneyList.removeAllViews());

        switchView.setOnCheckedChangeListener((compoundButton, b) -> mCheckNum = b);
    }

    TextView getTextView(String amount) {
        VoiceBuilder voiceBuilder = new VoiceBuilder.Builder()
                .start(VoiceConstants.WINNING)
                .money(amount)
                .unit(VoiceConstants.YUAN)
                .checkNum(mCheckNum)
                .builder();

        StringBuffer text = new StringBuffer()
                .append("角标: ").append(llMoneyList.getChildCount())
                .append("\n")
                .append("输入金额: ").append(amount)
                .append("\n");
        if (mCheckNum) {
            text.append("全数字式: ").append(VoiceTextTemplate.genVoiceList(voiceBuilder).toString());
        } else {
            text.append("中文样式: ").append(VoiceTextTemplate.genVoiceList(voiceBuilder).toString());
        }

        TextView view = new TextView(MainActivity.this);
        view.setPadding(0, 8, 0, 0);
        view.setText(text.toString());
        return view;
    }
}
