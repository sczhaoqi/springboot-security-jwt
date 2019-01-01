package com.sc.zhaoqi.ssj.bean;

import javafx.util.Builder;

public class Msg<T>
{
    int code;
    String msg;
    T data;

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    private Msg(MsgBuilder<T> builder)
    {
        this.code = builder.code;
        this.msg = builder.msg;
        this.data = builder.data;
    }

    public static class MsgBuilder<T>
            implements Builder
    {
        int code;
        String msg;
        T data;

        public MsgBuilder(){

        }
        public MsgBuilder(int code)
        {
            this.code = code;
        }

        public MsgBuilder<T> code(int code)
        {
            this.code = code;
            return this;
        }

        public MsgBuilder<T> msg(String msg)
        {
            this.msg = msg;
            return this;
        }

        public MsgBuilder<T> data(T data)
        {
            this.data = data;
            return this;
        }

        @Override
        public Msg<T> build()
        {
            return new Msg<T>(this);
        }
    }

    public static Msg ok()
    {
        return new MsgBuilder<>(200).build();
    }

    public static Msg ok(String msg)
    {
        return new MsgBuilder<String>(200).msg(msg).build();
    }

    public static <T>  Msg<T> ok(String msg, T data)
    {
        return new MsgBuilder<T>(200).data(data).msg(msg).build();
    }
    public static Msg error(String msg)
    {
        return new MsgBuilder<String>(400).msg(msg).build();
    }
    public static Msg sysError()
    {
        return new MsgBuilder<String>(500).msg("系统内部错误!").build();
    }
}
