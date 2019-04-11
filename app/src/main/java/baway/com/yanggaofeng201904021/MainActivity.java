package baway.com.yanggaofeng201904021;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.GestureLibraries;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    String file = Environment.getExternalStorageDirectory() + "t.jpg";
    private ImageView viewById1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button viewById = findViewById(R.id.btn1);
        viewById1 = findViewById(R.id.img);

        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"123",Toast.LENGTH_LONG).show();
                String[] item = {"拍照", "相册"};
                //弹出框
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("选择")
                        .setItems(item, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    //0代表拍照
                                    case 0:
                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(file)));
                                        startActivityForResult(intent, 100);
                                        break;
                                    case 1:
                                        //1代表相册
                                        Intent intent1 = new Intent(Intent.ACTION_PICK);
                                        intent1.setType("image/*");
                                        startActivityForResult(intent1, 200);
                                        break;
                                }
                            }
                        }).show();
            }
        });
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        //相机
        if (resultCode == 100) {
            Uri uri = Uri.fromFile(new File(file));
            Bitmap bitmap = data.getParcelableExtra("data");
            Glide.with(MainActivity.this).load(bitmap).into(viewById1);
        }   
        //相册
        if (resultCode == 200) {
            Uri uri = data.getData();
            Bitmap bitmap = data.getParcelableExtra("data");
            Glide.with(MainActivity.this).load(bitmap).into(viewById1);
        }
    }
}

