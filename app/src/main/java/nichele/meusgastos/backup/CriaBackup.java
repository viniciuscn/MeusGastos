package nichele.meusgastos.backup;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.NetworkInterface;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by vinicius on 22/10/2019.
 */

public class CriaBackup {
    private  Context context;
    public CriaBackup(Context context){
        this.context=context;
    }

    public void executeLocal(String idDispositivo){

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            FileChannel source = null;
            FileChannel destination = null;

            File backupDB = null;
            try {
                String currentDBPath = "/data/nichele.meusgastos/databases/meusgastos";
                File currentDB = new File(data, currentDBPath);
                source = new FileInputStream(currentDB).getChannel();
            } catch (Exception e) {
                Log.e("bkp - origem", e.getMessage());
            }
            try {
                String backupDBPath = "meusgastos-" + new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()) + idDispositivo +".bkp";
                backupDB = new File(sd, backupDBPath);
                destination = new FileOutputStream(backupDB).getChannel();
            } catch (Exception e) {
                Log.e("bkp - destino", e.getMessage());
            }

            try {
                destination.transferFrom(source, 0, source.size());
            } catch (Exception e) {
                Log.e("bkp - transferencia", e.getMessage());
            }
            source.close();
            destination.close();
//            Toast.makeText(context, "CriaBackup gerado com sucesso em \n" + backupDB.toString(), Toast.LENGTH_LONG).show();
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_SEND);
//            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(backupDB.toString())));
//            intent.setType("*/*");
//            context.startActivity(Intent.createChooser(intent,"Comartilhar banco de dados via"));

        } catch (Exception ex) {
            Log.e("CriaBackup", ex.getMessage());
        }
    }

    public void executeRemoto(){
        String address = getMacAddr();
        //Toast.makeText(context,address,Toast.LENGTH_SHORT).show();



    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) return "";
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) res1.append(String.format("%02X:",b));
                if (res1.length() > 0) res1.deleteCharAt(res1.length() - 1);
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }
}
