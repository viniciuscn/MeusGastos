package nichele.meusgastos.retrofit;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nichele.meusgastos.Classes.Conta;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class sync {
   String strEndPoint = "https://192.168.15.51/mgapi/";
   public void postConta(final Context context, Conta conta){
      Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(strEndPoint)
            // as we are sending data in json format so
            // we have to add Gson converter factory
            .addConverterFactory(GsonConverterFactory.create())
            // at last we are building our retrofit builder.
            .build();
      // below line is to create an instance for our retrofit api class.

      RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

      // passing data from our text fields to our modal class.
      //passar lista de dados


      // calling a method to create a post and passing our modal class.
      Call<Conta> call = retrofitAPI.createPost(conta);

      // on below line we are executing our method.
      call.enqueue(new Callback<Conta>() {
         @Override
         public void onResponse(Call<Conta> call, Response<Conta> response) {
            // this method is called when we get response from our api.
            Toast.makeText(context, "Data added to API", Toast.LENGTH_SHORT).show();
            Log.v("Envio", "Sucesso Conta \n"+response.body());

            // below line is for hiding our progress bar.
            //loadingPB.setVisibility(View.GONE);

            // we are getting response from our body
            // and passing it to our modal class.
            //Conta responseFromAPI = response.body();

            // on below line we are getting our data from modal class and adding it to our string.
            //String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getNome() ;
         }

         @Override
         public void onFailure(Call<Conta> call, Throwable t) {
            // setting text to our text view when
            // we get error response from API.
            //responseTV.setText("Error found is : " + t.getMessage());
            Log.v("Envio","Erro Conta \n"+t.getMessage());

         }
      });
   }
   public void postContas(final Context context){
      Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(strEndPoint)
            // as we are sending data in json format so
            // we have to add Gson converter factory
            .addConverterFactory(GsonConverterFactory.create())
            // at last we are building our retrofit builder.
            .build();
      // below line is to create an instance for our retrofit api class.

      RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

      // passing data from our text fields to our modal class.
      //passar lista de dados
      List<Conta> list = new ArrayList<>();
      list.add(new Conta(1,"Bradesco"));
      list.add(new Conta(2,"Caixa"));
      list.add(new Conta(3,"Santander"));

      // calling a method to create a post and passing our modal class.
      Call<ResponseBody> call = retrofitAPI.createPost(list);

      // on below line we are executing our method.
      call.enqueue(new Callback<ResponseBody>() {
         @Override
         public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            // this method is called when we get response from our api.
            Toast.makeText(context, "Data added to API", Toast.LENGTH_SHORT).show();
            Log.v("Envio", "Sucesso Conta \n"+response.body());

            // below line is for hiding our progress bar.
            //loadingPB.setVisibility(View.GONE);

            // on below line we are setting empty text
            // to our both edit text.
            //jobEdt.setText("");
            //nameEdt.setText("");

            // we are getting response from our body
            // and passing it to our modal class.
            //Conta responseFromAPI = response.body();

            // on below line we are getting our data from modal class and adding it to our string.
            //String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getNome() ;

            // below line we are setting our
            // string to our text view.
            //responseTV.setText(responseString);
         }

         @Override
         public void onFailure(Call<ResponseBody> call, Throwable t) {
            // setting text to our text view when
            // we get error response from API.
            //responseTV.setText("Error found is : " + t.getMessage());
            Log.v("Envio","Erro Conta \n"+t.getMessage());

         }
      });
   }
}
