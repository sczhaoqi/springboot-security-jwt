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

    public static class MsgBuilder<E>
            implements Builder<Msg>
    {

        int code;
        String msg;
        E data;

        public MsgBuilder(int code)
        {
            this.code = code;
        }

        public MsgBuilder<E> code(int code)
        {
            this.code = code;
            return this;
        }

        public MsgBuilder<E> msg(String msg)
        {
            this.msg = msg;
            return this;
        }

        public MsgBuilder<E> data(E data)
        {
            this.data = data;
            return this;
        }

        @Override
        public Msg build()
        {
            return new Msg(this);
        }
    }

    public static Msg ok()
    {
        return new MsgBuilder<>(200).build();
    }

    public static Msg ok(String msg)
    {
        return new MsgBuilder<>(200).msg(msg).build();
    }
    public static Msg ok(String msg,Object data)
    {
        return new MsgBuilder(200).msg(msg).build();
    }
    public static Msg error(String msg)
    {
        return new MsgBuilder<>(400).msg(msg).build();
    }
    public static Msg sysError()
    {
        return new MsgBuilder<>(500).msg("系统内部错误!").build();
    }
}
