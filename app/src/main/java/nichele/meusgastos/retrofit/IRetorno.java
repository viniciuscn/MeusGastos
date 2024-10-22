package nichele.meusgastos.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IRetorno {

   @GET("jogos")
   Call<Retorno> getRetorno();


}
