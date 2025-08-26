package com.example.securitydemo.common.thread;

public class RequestHolder{
    private static final ThreadLocal<Object> holder = new ThreadLocal<>();
    public static Object get(){
        return holder.get();
    }
    public static void set(Object object){
        holder.set(object);
    }
    public static void remove(){
        holder.remove();
    }
}
