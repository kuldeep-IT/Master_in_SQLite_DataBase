package com.example.masterinsqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText name,type;
    Button add,delete;
    ListView listView;

    List<Computer> computerList;

    ArrayList<String> computersName;

    MySqliteHandler mySqliteHandler;

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        type = findViewById(R.id.type);
        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);

        listView = findViewById(R.id.list_view);

        mySqliteHandler = new MySqliteHandler(MainActivity.this);

        computerList = mySqliteHandler.getAllComputerObjects();

        computersName = new ArrayList<>();

        final String name1 = name.getText().toString();
        final String type1 = type.getText().toString();

        if (computerList.size()>0)
        {
            for (int i=0; i<computerList.size(); i++)
            {
                Computer computer = computerList.get(i);
                computersName.add(computer.getComputerName() + " - " + computer.getComputerType());

            }
        }

        adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,computersName);
        listView.setAdapter(adapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//
//                if (name.getText().toString().matches("") || type.getText().toString().matches(""));
//                {
//                    return;
//                }

//                if (TextUtils.isEmpty(name1) || TextUtils.isEmpty(type1));
//                {
//                    return ;
//                }

                    Computer computer = new Computer(name.getText().toString(),type.getText().toString());
                Log.d("TAGER","name " +name1 + "  " +type1);

                    computerList.add(computer);
                    mySqliteHandler.addComputer(computer);
                    computersName.add(computer.getComputerName() + " - " + computer.getComputerType());

                    name.setText("");
                    type.setText("");


            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (computerList.size()>0)
                {
                        computersName.remove(0);
                        mySqliteHandler.deleteData(computerList.get(0));
                        computerList.remove(0);
                    }

                    else
                    {
                        return;
                    }



            }
        });


        adapter.notifyDataSetChanged();



    }
}
