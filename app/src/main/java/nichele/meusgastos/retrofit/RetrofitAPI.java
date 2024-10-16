package nichele.meusgastos.retrofit;

import java.util.List;

import nichele.meusgastos.Classes.Conta;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {

   @POST("contas")
      //on below line we are creating a method to post our data.
   Call<ResponseBody> createPost(@Body List<Conta> contaslist);

   @POST("contas")
      //on below line we are creating a method to post our data.
   Call<Conta> createPost(@Body Conta c);
}
