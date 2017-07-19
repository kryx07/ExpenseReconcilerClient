package kryx07.expensereconcilerclient.network;

import io.reactivex.Observable;
import kryx07.expensereconcilerclient.model.transactions.Payables;
import kryx07.expensereconcilerclient.model.transactions.Transactions;
import kryx07.expensereconcilerclient.model.users.Users;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("users/all")
    Call<Users> getUsers();

    @GET("transactions/by-user")
    Call<Transactions> getTransactions(@Query("username") String username);

    @GET("/reconciliation/payables-by-user")
    Observable<Payables> getPayables(@Query("username") String username);
/*

    @GET("payables/all")
    Call<List<Transaction>> getPayables();
*/


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
