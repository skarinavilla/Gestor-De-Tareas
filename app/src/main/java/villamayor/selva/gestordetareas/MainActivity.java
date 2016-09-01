package villamayor.selva.gestordetareas;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements com.mobsandgeeks.saripaar.Validator.ValidationListener {


    @NotEmpty(messageResId = R.string.escriba_nombre)
    EditText nombreEdiText;
    @Email(messageResId = R.string.escriba_email)
    EditText emailEditText;
    Button guardarButton;
    com.mobsandgeeks.saripaar.Validator validator;

    ListView usuariosListView;

    List<String> usuarios = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //downcast and upcast

        nombreEdiText = (EditText) findViewById(R.id.editTextNombre);
        emailEditText = (EditText) findViewById(R.id.editTextEmail);
        guardarButton = (Button) findViewById(R.id.buttonGuardar);
        validator = new com.mobsandgeeks.saripaar.Validator(this);
        validator.setValidationListener(this);
        usuariosListView = (ListView)findViewById(R.id.listViewUsuarios);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,usuarios);

        usuariosListView.setAdapter(adapter);


        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // validator.validate();
                String nombre = nombreEdiText.getText().toString();
                if (nombre.length()<3) {
                    Toast.makeText(MainActivity.this, R.string.min_char, Toast.LENGTH_SHORT).show();
                    nombreEdiText.requestFocus();
                }else {
                    validator.validate();
                }
            }
        });

    }

    @Override
    public void onValidationSucceeded() {
        String nombre = nombreEdiText.getText().toString();
        String email = emailEditText.getText().toString();
        String mensaje = getString(R.string.welcome_msj)+ " "+nombre+" "+email;
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        String datos = nombre+" "+email;
        usuarios.add(datos);
        adapter.notifyDataSetChanged();
        nombreEdiText.setText(null);
        emailEditText.setText(null);
    }

   @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors){
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText){
                ((EditText)view).setError(message);
            }else{
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
