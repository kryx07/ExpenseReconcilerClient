package kryx07.expensereconcilerclient.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

  /*  @FormUrlEncoded
    @POST("Account/Login")
    Call<LoginResponse> login(@Field("Email") String email, @Field("Password") String password);
*/
   /* @GET("Activities/Get")
    Call<List<CallActivity>> getActivities();

    @GET("Clients/Get")
    Call<List<Client>> getClients();

    @PUT("Clients/Put")
    Call<Client> updateClient(@Body Client client);

    @POST("Clients/Post")
    Call<List<Client>> createClient(@Body List<Client> clients);

    @GET("Contacts/Get")
    Call<List<Contact>> getContacts();

    @GET("Contacts/Get/Property/{id}")
    Call<Contact> getContactByProperty(@Path("id") int value);
*/
}
