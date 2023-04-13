package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DBHelper dbHelper;
    private LinkedList<Data> dataList;
    private int currentIndex = 0;
    private EditText numKvEditText, waterEditText, energyEditText;
    private Button addBut, saveBut, delBut, nextBut, backBut;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        numKvEditText = findViewById(R.id.editTextNumber);
        waterEditText = findViewById(R.id.editTextNumber2);
        energyEditText = findViewById(R.id.editTextNumber3);
        nextBut = findViewById(R.id.next);
        backBut = findViewById(R.id.back);
        addBut = findViewById(R.id.add);
        delBut = findViewById(R.id.del);
        saveBut = findViewById(R.id.save);

        dbHelper = new DBHelper(this);

        addBut.setOnClickListener(this);
        saveBut.setOnClickListener(this);
        delBut.setOnClickListener(this);
        nextBut.setOnClickListener(this);
        backBut.setOnClickListener(this);

        dataList = dbHelper.GetAll();
        if (!dataList.isEmpty()) {
            setEditTextValues(dataList.get(0));
        }
    }

    private void setEditTextValues(Data data) {
        numKvEditText.setText(String.valueOf(data.getNumKv()));
        waterEditText.setText(String.valueOf(data.getWater()));
        energyEditText.setText(String.valueOf(data.getEnergy()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.del:
                if (!dataList.isEmpty()) {
                    int numKvToDelete = dataList.get(currentIndex).getNumKv();
                    dbHelper.Delete(numKvToDelete);
                    dataList.remove(currentIndex);
                    if (!dataList.isEmpty()) {
                        if (currentIndex >= dataList.size()) {
                            currentIndex = dataList.size() - 1;
                        }
                        Data newData = dataList.get(currentIndex);
                        setEditTextValues(newData);
                    } else {
                        currentIndex = 0;
                        numKvEditText.setText("");
                        waterEditText.setText("");
                        energyEditText.setText("");
                    }
                    Toast.makeText(getApplicationContext(), "Запись удалена", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.add:
                String numKvStr = numKvEditText.getText().toString();
                String waterStr = waterEditText.getText().toString();
                String energyStr = energyEditText.getText().toString();
                if (numKvStr.isEmpty() || waterStr.isEmpty() || energyStr.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Не все поля заполнены", Toast.LENGTH_SHORT).show();
                } else {
                    int numKv = Integer.parseInt(numKvStr);
                    float water = Float.parseFloat(waterStr);
                    float energy = Float.parseFloat(energyStr);
                    Data data = new Data(numKv, water, energy);
                    dbHelper.Add(data);
                    dataList.add(data);
                    currentIndex = dataList.size() - 1;
                    setEditTextValues(data);
                    Toast.makeText(getApplicationContext(), "Запись добавлена", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.save:
                if (!dataList.isEmpty()) {
                    int numKvToUpdate = dataList.get(currentIndex).getNumKv();
                    int newNumKv = Integer.parseInt(numKvEditText.getText().toString());
                    float newWater = Float.parseFloat(waterEditText.getText().toString());
                    float newEnergy = Float.parseFloat(energyEditText.getText().toString());
                    Data updatedData = new Data(newNumKv, newWater, newEnergy);
                    dbHelper.Update(numKvToUpdate, updatedData);
                    dataList.set(currentIndex, updatedData);
                    Toast.makeText(getApplicationContext(), "Запись обновлена", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.back:
                if (currentIndex > 0) {
                    currentIndex--;
                    setEditTextValues(dataList.get(currentIndex));
                }
                break;
            case R.id.next:
                if (currentIndex < dataList.size() - 1) {
                    currentIndex++;
                    setEditTextValues(dataList.get(currentIndex));
                } else {
                    currentIndex = -1;
                    numKvEditText.setText("");
                    waterEditText.setText("");
                    energyEditText.setText("");
                }
                break;
        }
    }
}
