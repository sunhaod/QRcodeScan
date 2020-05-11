package com.example.sh.qrcodescan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;
import com.yzq.zxinglibrary.encode.CodeCreator;

import org.w3c.dom.Text;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int choice = 0;
    Button btnRelease;
    Button btnScan;
    RelativeLayout relativeLayout;
    ImageView imgQRcode;
    Button btnAdmit;
    TextView tvTitle;
    EditText etContent;
    final int REQUEST_CODE_SCAN = 10086;
    int[] mark;
    String content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mark = new int[10000];
        for(int i = 0; i < 10000; i ++) mark[i] = 0;
        btnRelease = (Button)findViewById(R.id.btn_release);
        btnScan = (Button)findViewById(R.id.btn_scan);
        relativeLayout = (RelativeLayout)findViewById(R.id.rl_result);
        imgQRcode = (ImageView)findViewById(R.id.img_qrcode);
        btnAdmit = (Button)findViewById(R.id.btn_admit);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        etContent = (EditText)findViewById(R.id.et_content);

        btnRelease.setOnClickListener(this);
        btnAdmit.setOnClickListener(this);
        btnScan.setOnClickListener(this);

        PermissionUtils.isGrantExternalRW(this,1);
    }


    private void showSingSelect() {
        AlertDialog.Builder builder;
        //默认选中第一个
        final String[] items = {"匿名投票", "平均年龄", "平均工资"};

        builder = new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher).setTitle("发起的项目")
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        choice = i;
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(choice == 0) {
                            showVote();
                        }else if(choice == 1) {
                            showAge();
                        }else if(choice == 2) {
                            showSalary();
                        }
                    }
                });
        builder.create().show();
    }

    private void showAge() {
        AlertDialog.Builder builder;
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(this).inflate(R.layout.input_1, null);
        final EditText etTitle = view.findViewById(R.id.et_title);
        final EditText etContent = view.findViewById(R.id.et_mycontent);
        final TextView tvTitleSet = view.findViewById(R.id.tv_title1);
        final TextView tvContent = view.findViewById(R.id.tv_content);
        tvTitleSet.setVisibility(View.GONE);
        etTitle.setVisibility(View.GONE);
        tvContent.setText("请输入你的年龄：");

        builder = new AlertDialog.Builder(this).setView(view).setTitle("发起平均年龄统计").setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Random r = new Random();
                        int temkey = (int)(r.nextDouble()*9999);
                        mark[temkey] = 1;
                        try{
                            String str = temkey+"&&1&&"+etContent.getText().toString()+"&&1&&0";
                            Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                            Bitmap bitmap = CodeCreator.createQRCode(str, 400, 400, logo);
                            imgQRcode.setImageBitmap(bitmap);
                            imgQRcode.setVisibility(View.VISIBLE);
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        builder.create().show();
    }

    private void showSalary() {
        AlertDialog.Builder builder;
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(this).inflate(R.layout.input_1, null);
        final EditText etTitle = view.findViewById(R.id.et_title);
        final EditText etContent = view.findViewById(R.id.et_mycontent);
        final TextView tvTitleSet = view.findViewById(R.id.tv_title1);
        final TextView tvContent = view.findViewById(R.id.tv_content);
        tvTitleSet.setVisibility(View.GONE);
        etTitle.setVisibility(View.GONE);
        tvContent.setText("请输入你的工资：");

        builder = new AlertDialog.Builder(this).setView(view).setTitle("发起平均工资统计").setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Random r = new Random();
                        int temkey = (int)(r.nextDouble()*9999);
                        mark[temkey] = 1;
                        try{
                            String str = temkey+"&&2&&"+etContent.getText().toString()+"&&1&&0";
                            Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                            Bitmap bitmap = CodeCreator.createQRCode(str, 400, 400, logo);
                            imgQRcode.setImageBitmap(bitmap);
                            imgQRcode.setVisibility(View.VISIBLE);
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        builder.create().show();
    }

    private void showVote() {
        try {
            AlertDialog.Builder builder;
            @SuppressLint("InflateParams")
            View view = LayoutInflater.from(this).inflate(R.layout.input_1, null);
            final EditText etTitle = view.findViewById(R.id.et_title);
            final EditText etContent = view.findViewById(R.id.et_mycontent);
            final TextView tvTitleSet = view.findViewById(R.id.tv_title1);
            final TextView tvContent = view.findViewById(R.id.tv_content);
            tvTitleSet.setText("请输入被投票的人的名字：");
            tvContent.setText("请输入你给他投几票：");

            builder = new AlertDialog.Builder(this).setView(view).setTitle("发起投票").setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                Random r = new Random();
                                int temkey = (int)(r.nextDouble()*9999);
                                mark[temkey] = 1;
                                String str = temkey + "&&0&&" + etTitle.getText().toString() + "&&" + etContent.getText().toString() + "&&1&&0";
                                Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                                Bitmap bitmap = CodeCreator.createQRCode(str, 400, 400, logo);
                                imgQRcode.setImageBitmap(bitmap);
                                imgQRcode.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                Log.e("haha", e.getMessage().toString());
                            }
                        }
                    });

            builder.create().show();
        }catch (Exception e) {
            Log.e("haha",e.getMessage().toString());
        }
    }



    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_release:
                showSingSelect();
                break;
            case R.id.btn_admit:
                int type = Integer.parseInt(content.split("&&")[1]);
                String temstr = content.split("&&")[0]+"&&"+type+"&&";
                switch (type){
                    case 0:
                        temstr = temstr + content.split("&&")[2]+"&&"+(Integer.parseInt(content.split("&&")[3])+Integer.parseInt(etContent.getText().toString()))+"&&";
                        break;
                    case 1:
                        temstr = temstr + Integer.parseInt(content.split("&&")[2])+Integer.parseInt(etContent.getText().toString())+"&&";
                        break;
                    case 2:
                        temstr = temstr + Integer.parseInt(content.split("&&")[2])+Integer.parseInt(etContent.getText().toString())+"&&";
                        break;
                }
                int temkey = Integer.parseInt(content.split("&&")[0]);
                if(mark[temkey] == 0) {
                    if(type == 0)
                        temstr = temstr + Integer.parseInt(content.split("&&")[4])+1+"&&"+Integer.parseInt(content.split("&&")[5]);
                    else
                        temstr = temstr + Integer.parseInt(content.split("&&")[3])+1+"&&"+Integer.parseInt(content.split("&&")[4]);
                }else {
                    if(type == 0)
                        temstr = temstr + Integer.parseInt(content.split("&&")[4])+"&&"+Integer.parseInt(content.split("&&")[5])+1;
                    else
                        temstr = temstr + Integer.parseInt(content.split("&&")[3])+"&&"+Integer.parseInt(content.split("&&")[4])+1;
                }
                if (temstr.split("&&")[4].equals(temstr.split("&&")[5]) || temstr.split("&&")[5].equals(temstr.split("&&")[6])) {
                    AlertDialog.Builder builder;
                    if(type == 0) {
                        builder = new AlertDialog.Builder(this).setTitle("统计结束！")
                                .setMessage(temstr.split("&&")[2]+"获得的总票数是"+temstr.split("&&")[3])
                                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                        builder.create().show();
                    }else if (type == 1) {
                        builder = new AlertDialog.Builder(this).setTitle("统计结束！")
                                .setMessage("平均年龄是"+Integer.parseInt(temstr.split("&&")[2])/Integer.parseInt(temstr.split("&&")[3]))
                                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                        builder.create().show();
                    }else if (type == 2) {
                        builder = new AlertDialog.Builder(this).setTitle("统计结束！")
                                .setMessage("平均工资是"+Integer.parseInt(temstr.split("&&")[2])/Integer.parseInt(temstr.split("&&")[3])+"元")
                                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                        builder.create().show();
                    }
                }else {
                    try {
                        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                        Bitmap bitmap = CodeCreator.createQRCode(temstr, 400, 400, logo);
                        imgQRcode.setImageBitmap(bitmap);
                        relativeLayout.setVisibility(View.GONE);
                        imgQRcode.setVisibility(View.VISIBLE);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn_scan:
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent,REQUEST_CODE_SCAN);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                content = data.getStringExtra(Constant.CODED_CONTENT);
                int temkey = Integer.parseInt(content.split("&&")[0]);
                int type = Integer.parseInt(content.split("&&")[1]);
                if(mark[temkey] > 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher)
                            .setTitle("出错了").setMessage("你已经填过两次了").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    builder.create().show();
                }else {
                    imgQRcode.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    if(type == 0)
                        tvTitle.setText("请输入你要给"+content.split("&&")[2]+"投的票数");
                    else if (type == 1)
                        tvTitle.setText("年龄");
                    else
                        tvTitle.setText("工资");
                }
            }
        }
    }
}
