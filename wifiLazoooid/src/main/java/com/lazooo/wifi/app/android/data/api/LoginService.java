package com.lazooo.wifi.app.android.data.api;/**
 * Lazooo copyright 2014
 */

import com.google.gson.GsonBuilder;
import com.lazooo.wifi.app.android.data.api.bean.SignIn;
import com.lazooo.wifi.app.android.data.api.bean.SignUp;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 13/07/14
 * Time: 21:30
 */
public interface LoginService {

    @GET("/users/{user}/repos")
    public void signIn(@Path("user") String user, Callback<SignIn> csi);

    @GET("/users/{user}/repos")
    public void signUp(@Path("user") String user, Callback<SignUp> csu);


    public static class LoginServiceFactory {

        static LoginService loginService = null;

        static LoginService generateService(){

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://wifi.lazooo.com/api/v1/login")
                    .setConverter(new GsonConverter(new GsonBuilder().create()))
                    .build();

            return restAdapter.create(LoginService.class);
        }

        public static LoginService get(){

            if(loginService == null)
                loginService = generateService();
            return loginService;
        }
    }
}
