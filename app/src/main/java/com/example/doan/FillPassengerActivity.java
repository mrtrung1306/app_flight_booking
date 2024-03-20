package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.model.Passenger;

import org.w3c.dom.Text;

import java.util.Calendar;

public class FillPassengerActivity extends AppCompatActivity implements View.OnClickListener {

    private DatePickerDialog.OnDateSetListener mDataSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_passenger);

        findViewById(R.id.btnExit).setOnClickListener(this);
        findViewById(R.id.btnNext).setOnClickListener(this);
        findViewById(R.id.txtBirthday).setOnClickListener(this);
        findViewById(R.id.txtGender).setOnClickListener(this);

        TextView txtFullName = findViewById(R.id.txtUsername);
        TextView txtBirthday = findViewById(R.id.txtBirthday);
        TextView txtGender =findViewById(R.id.txtGender);
        TextView txtEmail = findViewById(R.id.txtEmail);
        TextView txtPhone = findViewById(R.id.txtPhone);
        TextView txtAddress = findViewById(R.id.txtAddress);

        Intent intent = getIntent();
        Passenger obj = (Passenger)intent.getExtras().getSerializable("Passenger");
        if(obj != null){
            txtFullName.setText(obj.FullName);
            txtBirthday.setText(obj.NgaySinh);
            txtGender.setText(obj.Gender);
            txtEmail.setText(obj.Email);
            txtPhone.setText(obj.Phone);
            txtAddress.setText(obj.Address);

        }
        mDataSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = day + "/" + month + "/" + year;
                txtBirthday.setText(date);
            }
        };
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnExit){
            finish();
        }
        if(view.getId() == R.id.btnNext){
            Passenger passenger = new Passenger();
            passenger.FullName = ((TextView) findViewById(R.id.txtUsername)).getText().toString();
            passenger.Phone = ((TextView) findViewById(R.id.txtPhone)).getText().toString();
            passenger.Email = ((TextView) findViewById(R.id.txtEmail)).getText().toString();
            passenger.Gender = ((TextView) findViewById(R.id.txtGender)).getText().toString();
            passenger.NgaySinh = ((TextView) findViewById(R.id.txtBirthday)).getText().toString();
            passenger.Address = ((TextView) findViewById(R.id.txtAddress)).getText().toString();
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("Passenger", passenger);
            int position = getIntent().getExtras().getInt("position");
            bundle.putInt("position", position);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            onBackPressed();
        }
        TextView txtGender =findViewById(R.id.txtGender);
        if(view.getId() == R.id.txtGender){
            String [] gioiTinh= new String[]{"Nam","Nữ","Khác"};

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Hãy chọn giới tính");
            alertDialogBuilder.setIcon(R.drawable.baseline_person_24);
            alertDialogBuilder.setItems(gioiTinh, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    txtGender.setText(gioiTinh[i]);
                    Toast.makeText(FillPassengerActivity.this,"Giới tính" + gioiTinh[i],Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
        if(view.getId() == R.id.txtBirthday){
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog1 = new DatePickerDialog(FillPassengerActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDataSetListener,
                    year,month,day);
            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog1.setCanceledOnTouchOutside(false);
            dialog1.show();
        }
    }
}