package com.lazooo.wifi.app.android.data.api;/**
 * Lazooo copyright 2012
 */

import com.google.gson.GsonBuilder;

import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 13/07/14
 * Time: 21:46
 */
public interface Service {


    public static class ServiceFactory {

        private static ServiceFactory serviceFactory = null;
        private Service service;
        ServiceFactory(){

            generateService();
        }

        Service generateService(){

            throw new RuntimeException("Override this method on superclass");
        }

        void setService(Service service){

            this.service = service;
        }

        Service getService(){

            return service;
        }

        Converter getConverter(){

            GsonConverter converter = new GsonConverter(
                    new GsonBuilder()
                    .create()
            );
            return converter;
        }

        public static Service get(){

            if(serviceFactory == null){

                serviceFactory = new ServiceFactory();
            }
            return serviceFactory.getService();
        }
    }
}
