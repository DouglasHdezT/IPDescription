package com.debugprojects.ipdeescription;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static java.lang.Math.pow;
import static java.lang.Math.round;

public class MainActivity extends AppCompatActivity {

    private EditText IP_Parte1;
    private EditText IP_Parte2;
    private EditText IP_Parte3;
    private EditText IP_Parte4;
    private EditText Mask;

    private TextView Id_Red;
    private TextView Broadcast;
    private TextView Cant_Hosts;
    private TextView Cant_Redes;
    private TextView Clase_Red;
    private TextView Parte_Host;
    private TextView Parte_red;
    private TextView Debugg;
    private TextView title_Redes;

    private Button Calcular;

    private int IP_Parte1_int;
    private int IP_Parte2_int;
    private int IP_Parte3_int;
    private int IP_Parte4_int;
    private int Submask;

    private String Id_Red_Var="";
    private String Broadcast_Var="";
    private String Cant_Host_Var="";
    private String Clase_Red_Var="";
    private String Cant_Redes_Var="";
    private String Primer_Ip_Var ="";
    private String Ultima_Ip_Var ="";
    private String title_Redes_Var="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViews();

        if(savedInstanceState!=null){
            Id_Red_Var= savedInstanceState.getString("Id_Red");
            Broadcast_Var= savedInstanceState.getString("Broadcast");
            Cant_Host_Var= savedInstanceState.getString("Cant_Host");
            Cant_Redes_Var= savedInstanceState.getString("Cant_Redes");
            Ultima_Ip_Var = savedInstanceState.getString("Parte_Red");
            Primer_Ip_Var = savedInstanceState.getString("Parte_Host");
            Clase_Red_Var= savedInstanceState.getString("Clase_Red");

        }

        Parte_red.setText(Ultima_Ip_Var);
        Parte_Host.setText(Primer_Ip_Var);
        Clase_Red.setText(Clase_Red_Var);
        Id_Red.setText(Id_Red_Var);
        Broadcast.setText(Broadcast_Var);
        Cant_Redes.setText(Cant_Redes_Var);
        Cant_Hosts.setText(Cant_Host_Var);

        Calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    if(IP_Parte1.getText().toString().matches("") || IP_Parte2.getText().toString().matches("") ||
                            IP_Parte3.getText().toString().matches("") || IP_Parte4.getText().toString().matches("")
                            || Mask.getText().toString().matches("")){
                        throw new Exception();
                    }

                    IP_Parte1_int= Integer.parseInt(IP_Parte1.getText().toString());
                    IP_Parte2_int= Integer.parseInt(IP_Parte2.getText().toString());
                    IP_Parte3_int= Integer.parseInt(IP_Parte3.getText().toString());
                    IP_Parte4_int= Integer.parseInt(IP_Parte4.getText().toString());
                    Submask= Integer.parseInt(Mask.getText().toString());

                    CalcularDatos(IP_Parte1_int,IP_Parte2_int,IP_Parte3_int,IP_Parte4_int,Submask);

                    printViews();

                }catch (Exception e){
                    Debugg.setTextColor(Debugg.getContext().getResources().getColor(R.color.Error));
                    Debugg.setText("Error de ingreso...");
                }


            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("Id_Red", Id_Red_Var);
        outState.putString("Broadcast", Broadcast_Var);
        outState.putString("Cant_Host", Cant_Host_Var);
        outState.putString("Cant_Redes", Cant_Redes_Var);
        outState.putString("Clase_Red", Clase_Red_Var);
        outState.putString("Parte_Red", Ultima_Ip_Var);
        outState.putString("Parte_Host", Primer_Ip_Var);
        outState.putString("tilte_redes", title_Redes_Var);
        super.onSaveInstanceState(outState);
    }

    private void CalcularDatos(int IP_Parte1_int, int IP_Parte2_int, int IP_Parte3_int, int IP_Parte4_int, int SubMask){

        int Residuo;



        if(IP_Parte1_int >= 0 && IP_Parte1_int <= 127 && SubMask >=8 && SubMask <=31 && VerificarDatos()){ // 01111111
            Clase_Red_Var= "IP PUBLICA - A";
            Residuo = SubMask-8;
            if(Residuo == 0){
                title_Redes_Var="CANT. REDES";
                Cant_Redes_Var = round(pow(2,7))+"";
                Cant_Host_Var= round(pow(2,24))+"";
            }else{
                title_Redes_Var="CANT. SUBREDES";
                Cant_Redes_Var = round(pow(2,Residuo))+"";
                Cant_Host_Var= round(pow(2,32-SubMask)) - 2+"";
            }


        }
        else if(IP_Parte1_int >= 128 && IP_Parte1_int <= 191 && SubMask >=16 && SubMask <=31 && VerificarDatos()){ // 10111111 11111111
            Clase_Red_Var= "IP PUBLICA - B";
            Residuo = SubMask-16;
            if(Residuo == 0){
                title_Redes_Var="CANT. REDES";
                Cant_Redes_Var = round(pow(2,14))+"";
                Cant_Host_Var= (round(pow(2,16)) - 2) +"";
            }else{
                title_Redes_Var="CANT. SUBREDES";
                Cant_Redes_Var = round(pow(2,Residuo))+"";
                Cant_Host_Var= round(pow(2,32-SubMask)) - 2 +"";
            }
        }
        else if(IP_Parte1_int >= 192 && IP_Parte1_int <= 223 && SubMask >=24 && SubMask <=31 && VerificarDatos()){ //11011111 11111111 11111111
            Clase_Red_Var= "IP PUBLICA - C";
            Residuo = SubMask-24;
            if(Residuo == 0){
                title_Redes_Var="CANT. REDES";
                Cant_Redes_Var = round(pow(2,21))+"";
                Cant_Host_Var= round(pow(2,8))+"";
            }else{
                title_Redes_Var="CANT. SUBREDES";
                Cant_Redes_Var = round(pow(2,Residuo))+"";
                Cant_Host_Var= round(pow(2,32-SubMask)) - 2 +"";
            }
        }
        else if(IP_Parte1_int >= 224 && IP_Parte1_int <= 239 && VerificarDatos()){
            Clase_Red_Var= "IP RESTRINGIDA - D";
            Id_Red_Var="";
            title_Redes_Var="CANT. REDES";
            Primer_Ip_Var ="";
            Ultima_Ip_Var ="";
            Cant_Redes_Var="";
            Cant_Host_Var="";
            Broadcast_Var="";
            return;
        }
        else if(IP_Parte1_int >= 240 && IP_Parte1_int <= 255 && VerificarDatos()){
            Clase_Red_Var= "IP RESTRINGIDA - E";
            Id_Red_Var="";
            Primer_Ip_Var ="";
            title_Redes_Var="CANT. REDES";
            Ultima_Ip_Var ="";
            Cant_Redes_Var="";
            Cant_Host_Var="";
            Broadcast_Var="";
            return;
        }
        else{
            Clase_Red_Var= "";
            Id_Red_Var="";
            Primer_Ip_Var ="";
            Ultima_Ip_Var ="";
            Cant_Redes_Var="";
            title_Redes_Var="CANT. REDES";
            Cant_Host_Var="";
            Broadcast_Var="";
            Debugg.setTextColor(Debugg.getContext().getResources().getColor(R.color.Error));
            Debugg.setText("FUERA DE RANGO...");
            return;
        }

        int[] Submask_Array= getSubmask(SubMask);
        int[] IP_Partes= {IP_Parte1_int,IP_Parte2_int,IP_Parte3_int,IP_Parte4_int};
        int[] MaxIp= maxIP(IP_Partes, Submask_Array);
        int IdParte1, IdParte2, IdParte3, IdParte4;

        IdParte1 = IP_Parte1_int & Submask_Array[0];
        IdParte2 = IP_Parte2_int & Submask_Array[1];
        IdParte3 = IP_Parte3_int & Submask_Array[2];
        IdParte4 = IP_Parte4_int & Submask_Array[3];

        Id_Red_Var= IdParte1+"."+IdParte2+"."+IdParte3+"."+IdParte4;
        Broadcast_Var = MaxIp[0]+"."+MaxIp[1]+"."+MaxIp[2]+"."+MaxIp[3];

        if (SubMask != 31) {
            Primer_Ip_Var = IdParte1+"."+IdParte2+"."+IdParte3+"."+(IdParte4+1);
            Ultima_Ip_Var = MaxIp[0]+"."+MaxIp[1]+"."+MaxIp[2]+"."+(MaxIp[3]-1);
        }else{
            Primer_Ip_Var= Id_Red_Var;
            Ultima_Ip_Var = Broadcast_Var;
        }

        Debugg.setTextColor(Debugg.getContext().getResources().getColor(R.color.Passed));
        Debugg.setText("Calculo completo!");
    }

    private boolean VerificarDatos(){
        if(Submask < 8 || Submask > 31){
            return false;
        }else if(this.IP_Parte1_int < 0 || this.IP_Parte1_int >255){
            return false;
        }else if(this.IP_Parte2_int < 0 || this.IP_Parte2_int >255){
            return false;
        }else if (this.IP_Parte3_int < 0 || this.IP_Parte3_int >255){
            return false;
        }else if (this.IP_Parte4_int < 0 || this.IP_Parte4_int >255){
            return false;
        }else{
            return true;
        }
    }

    private void getViews(){
        IP_Parte1= findViewById(R.id.edit_ip_id1);
        IP_Parte2= findViewById(R.id.edit_ip_id2);
        IP_Parte3= findViewById(R.id.edit_ip_id3);
        IP_Parte4= findViewById(R.id.edit_ip_id4);
        Mask= findViewById(R.id.edit_submask);

        Id_Red= findViewById(R.id.edit_id_red);
        Broadcast= findViewById(R.id.edit_broadcast);
        Cant_Hosts= findViewById(R.id.edit_cant_host);
        Cant_Redes= findViewById(R.id.edit_cant_redes);
        Clase_Red= findViewById(R.id.edit_clase_red);
        Parte_Host= findViewById(R.id.edit_parte_host);
        Parte_red= findViewById(R.id.edit_parte_red);
        Debugg= findViewById(R.id.text_debug);
        title_Redes = findViewById(R.id.title_redes);

        Calcular= findViewById(R.id.button_calc);
    }

    private int[] getSubmask(int Submask){
        int[] Submask_array = new int[4];

        Submask_array[0]=0;
        Submask_array[1]=0;
        Submask_array[2]=0;
        Submask_array[3]=0;

        int Cant_Octetos = (int)Submask/8;
        int Octeto_Parcial= Submask % 8;
        int Pos_Octetos_Parcial= Cant_Octetos;

        for (int i=0; i<Cant_Octetos; i++){
            Submask_array[i]= 255;
        }

        for(int i=7; i>= 8-Octeto_Parcial; i--){
            Submask_array[Cant_Octetos]= (int) (Submask_array[Cant_Octetos]+ pow(2,i));
        }

        return Submask_array;

    }

    private int[] maxIP(int[] Partes, int[] Submask_array){

        int[] MaxIp = Partes;
        int Pos_Vacio = (Submask/8);
        int Residuo= Submask%8;

        if(Residuo != 0){
            int cont= Partes[Pos_Vacio];
            int cont2=Partes[Pos_Vacio] & Submask_array[Pos_Vacio];
            int cont3=Partes[Pos_Vacio] & Submask_array[Pos_Vacio];

            while(cont2 == cont3){
                cont++;
                cont2 = cont & Submask_array[Pos_Vacio];
            }

            MaxIp[Pos_Vacio]= cont -1;
        }else{
            MaxIp[Pos_Vacio]=255;
        }



        for (int i=3 ; i> Pos_Vacio;i--){
            MaxIp[i]=255;
        }

        return MaxIp;
    }

    private void printViews(){
        title_Redes.setText(title_Redes_Var);
        Parte_red.setText(Ultima_Ip_Var);
        Parte_Host.setText(Primer_Ip_Var);
        Clase_Red.setText(Clase_Red_Var);
        Id_Red.setText(Id_Red_Var);
        Broadcast.setText(Broadcast_Var);
        Cant_Redes.setText(Cant_Redes_Var);
        Cant_Hosts.setText(Cant_Host_Var);
    }
}